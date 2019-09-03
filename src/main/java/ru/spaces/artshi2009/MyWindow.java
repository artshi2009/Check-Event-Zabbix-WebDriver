package ru.spaces.artshi2009;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

class MyWindow extends JFrame {
    private JButton[] buttons = {new JButton("Подтвердить check event Zabbix"), new JButton(Params.getNameButton()), new JButton("Exit")};
    private JTextArea printText = new DrawImage("ВНИМАНИЕ!");
    private Font font = new Font("TimesRoman", Font.BOLD, 30);
    private PlayerSound player = new PlayerSound();
    private int widthWindow = MyScreen.SCREEN_WIDTH / 2;
    private int heightWindow = (int)(MyScreen.SCREEN_HEIGHT / 1.5);
    private int autopilot = 1;
    private ActionWeb drv = new ActionWeb();

    MyWindow() {
        System.out.println(MyScreen.SCREEN_WIDTH + " x " + MyScreen.SCREEN_HEIGHT);

        LocalDateTime time = LocalDateTime.now();
        double ms = (time.getMinute() + time.getSecond() * 0.0167) * 60000;
        threadOfRunForm(ms);
        }

    private void threadOfRunForm(double ms) {
        long parsMs;
        parsMs = calculateTime(ms);

        new Thread(() -> {
            try {
                System.out.println("Текущее значение минут в мс: " + ms + "; в мин: " + ms / 60000); // debug
                System.out.println("Время до создания формы (в мс): " + parsMs + "; в мин: " + parsMs / 60000);
                Thread.sleep(parsMs);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
                //begin
                drawWindow();
                drawElements();
                setOnButtonActionListener();
                player.play();
                setVisible(true);
            //end
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //auto mode run
            if (Params.getFlag() == 1){
                closeForm();
                drv.RunActionWeb();
                new MyWindow();
            }
        }).start();
    }

    private long calculateTime(double ms) {
        long parsMs;
        if(ms < 1800000) {
            parsMs = (long) (1800000 - ms);
        }else if(ms > 1800000 && ms != 1800000) {
            parsMs = (long) (3600000 - ms);
        } else {
            parsMs = 1;
        }
        return parsMs;
    }

    private void setOnButtonActionListener() {
        int checkBut = 0;
        buttons[checkBut].addActionListener(e -> {
            try {
                //code WebDriver
                closeForm();
                drv.RunActionWeb();
                new MyWindow();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        });

        buttons[autopilot].addActionListener(actionEvent -> {
            if (Params.getFlag() == 0){
                buttons[autopilot].setText(Params.getNameButton());
                Params.setFlag(1);
            }else {
                buttons[autopilot].setText(Params.getNameButton());
                Params.setFlag(0);
            }
        });

        int exitBut = 2;
        buttons[exitBut].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void closeForm() {
        if (isVisible()) {
            setVisible(false);
            getContentPane().removeAll();
            getContentPane().validate();
            player = null;
            buttons = null;
            font = null;
            printText = null;
        }
    }

    private void drawWindow() {
        setBounds(MyScreen.SCREEN_WIDTH / 2 - widthWindow / 2,MyScreen.SCREEN_HEIGHT / 2 - heightWindow / 2, widthWindow, heightWindow);
        setBackground(Color.RED);
        setTitle("WARNING!");
        setResizable(false);
        setUndecorated(true);
        setAlwaysOnTop(true);
    }

    private void drawElements() {
        setLayout(null);
        add(printText);
        printText.setBounds(0, 0, widthWindow, heightWindow);
        printText.setEditable(false);
        printText.setWrapStyleWord(true);
        printText.setLineWrap(true);
        printText.setForeground(Color.BLACK);
        printText.setBackground(new Color(1,1,1, (float) 0.01));
        printText.setFont(font);
        printText.append("\n Нажми на кнопку <<Подтвердить check event Zabbix>>,\n для автоматического подтверждения\n <<check event monitoring>> и проверь наличие аллертов важности critical.");

        int shiftButtonsY = 0;

        for (JButton button : buttons) {
            printText.add(button);
            Dimension size = button.getPreferredSize();
            button.setBounds(widthWindow / 2 - size.width / 2 , heightWindow - heightWindow / 5 + shiftButtonsY,
                    size.width, size.height);
            shiftButtonsY += size.height + size.height / 2;
        }
    }
}

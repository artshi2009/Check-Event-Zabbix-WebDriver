package ru.spaces.artshi2009;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class DrawImage extends JTextArea {
    private BufferedImage background = null;
    private int widthWindow = MyScreen.SCREEN_WIDTH / 2;
    private int heightWindow = (int)(MyScreen.SCREEN_HEIGHT / 1.5);

    DrawImage(String s){
        super(s);
        try {
            String path = "DSC100085416.png";
            background = ImageIO.read(this.getClass().getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background,
                widthWindow / 2 - background.getWidth() / 4 / 2, heightWindow / 2 - background.getHeight() / 4 / 2,
                background.getWidth() / 4, background.getHeight() / 4, this);
        super.paintComponent(g);
    }
}

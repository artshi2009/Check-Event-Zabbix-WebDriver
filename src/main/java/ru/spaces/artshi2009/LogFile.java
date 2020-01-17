package ru.spaces.artshi2009;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

class LogFile {
    private GregorianCalendar calendar = new GregorianCalendar();

    void writeFile(String message) {
        try (FileWriter writer = new FileWriter("Report.log", true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("[Время события " + calendar.getTime() + "]: " + parsLog(message));
            bufferedWriter.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private String parsLog(String message) {
        StringBuilder sb = new StringBuilder(message);
        if (sb.indexOf("Build") != -1) {
            sb.delete(compareIndexOfSB(sb.indexOf("Build"), sb.indexOf("For")), sb.length());
        }

        return sb + "\n";
    }

    private int compareIndexOfSB(int value1, int value2) {
        return value1 < value2 ? value1 : value2;
    }
}

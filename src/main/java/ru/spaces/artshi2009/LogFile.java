package ru.spaces.artshi2009;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

class LogFile {
    private GregorianCalendar calendar = new GregorianCalendar();

    void logFile(String message) {
        try (FileWriter writer = new FileWriter("Report.log", true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("[Время события " + calendar.getTime() + "]: " + parsLog(message));
            bufferedWriter.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static String parsLog(String message) {
        StringBuilder sb = new StringBuilder(message);
        if (sb.indexOf("Build") != -1)
        sb.delete(sb.indexOf("For"), sb.length());

        return sb + "\n";
    }
}

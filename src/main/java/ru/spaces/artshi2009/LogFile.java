package ru.spaces.artshi2009;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

class LogFile {
    private GregorianCalendar calendar = new GregorianCalendar();

    void logFile(String message) {
        try (FileWriter writer = new FileWriter("Report.txt", true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("Время события " + calendar.getTime() + ": " + message + "\n\n");
            bufferedWriter.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

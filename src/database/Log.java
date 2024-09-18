package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class Log {
    private static final String file = "login_activity.txt";

    /** Writes any log in attempts in login_activity.txt. */
    public static void log (String user, boolean booleanValue) {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(ZonedDateTime.now() + " User: " + user + (booleanValue ? " Log in successful" : " Log in Failed"));
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
    }

}

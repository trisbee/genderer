package matous;

/*
 * This class provide debug launcher of this project.
 */

import javax.swing.*;

import matous.database.DatabaseConnection;

public class DetermineGender {

    Genderer genderer;

    public DetermineGender() {
        DatabaseConnection dc = new DatabaseConnection();
        if (!dc.connect()) {
            System.out.println("DetermineGender.DetermineGender()  Database not connected.");
            return;
        }
        genderer = new Genderer(dc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DetermineGender();
            }
        });
    }
}

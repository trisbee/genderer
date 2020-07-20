package matous;

/*
 * This class provide debug launcher of this project.
 */

import javax.swing.*;

public class DetermineGender {

    Genderer genderer;

    public DetermineGender() {
        genderer = new Genderer();
        if (!genderer.dbConnect()) {
            System.out.println("DetermineGender.DetermineGender()  Database not connected.");
            return;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DetermineGender();
            }
        });
    }
}

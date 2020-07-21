package matous;

/*
 * Determine gender from received name by name occurrence.
 * Provides methods which returns gender or vocative form.
 * It find received name in tables, return most occurrence case.
 *
 * Needs access to database with two tables containing first names and surnames.
 * Tables must contains this values: nominative name, vocative name, occurrence and gender.
 *
 * Provides:
 *
 * 1) Determine gender by received name.
 * 2) Return vocative form by received name.
 *
 * A) Only first name.
 * B) Only surname.
 * C) Both first name and surname.
 *
 * Developed for name inflections in czech language.
 */

/*
 * This class provide debug launcher of this project.
 */

import javax.swing.*;

import matous.database.DatabaseConnection;

public class DetermineGender {

    Genderer genderer;
    Inflectioner inflectioner;

    public DetermineGender() {
        DatabaseConnection dc = new DatabaseConnection();
        if (!dc.connect()) {
            System.out.println("DetermineGender.DetermineGender()  Database not connected.");
            return;
        }
        genderer = new Genderer(dc);
        inflectioner = new Inflectioner(dc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DetermineGender();
            }
        });
    }
}

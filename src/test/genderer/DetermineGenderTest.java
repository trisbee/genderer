package test.genderer;

/*
 * Genderer class and Inflectioner class tested together.
 * Results depends on data inserted in database tables.
 */

import genderer.Genderer;

import genderer.Inflectioner;
import genderer.DatabaseConnection;
import genderer.database.Database;
import genderer.database.PostgreSQL;
import genderer.enumeration.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetermineGenderTest {

    DatabaseConnection dc;
    Database database;

    Genderer genderer;
    Inflectioner inflectioner;

    public DetermineGenderTest() {
    }

    @Test
    void test() {
        String[][][] names;
        // 3D array "String[][][] names": every 'row' contains two 'columns' with several values.
        // First column (input) contains first name and surname.
        // Second column (output) contains gender, vocative name and eventual another values.

        /*
         * Czech names points of interests:
         *
         * "Jan" is male first name (295627), female first name (1), male surname (229) and female surname (4).
         * "Martina" is female first name (81543), male surname (25) and male first name (1).
         * "Koval" is male surname (662) and female surname (100).
         */

        database = new PostgreSQL();
        if (!database.connect()) {
            System.out.println("Genderer_Test.Genderer_Test()  Database not connected.");
            return;
        }
        dc = new DatabaseConnection(database);
        genderer = new Genderer(dc);
        inflectioner = new Inflectioner(dc);

        // First name:
        names = new String[][][] {
                {{"Jan", ""}, {"MALE", "Jane"}},
                {{"Martina", ""}, {"FEMALE", "Martino"}},
                {{"Martin", ""}, {"MALE", "Martine"}},
                {{"Marti", ""}, {"MALE", "Marti"}},
                {{"Mart", ""}, {"FEMALE", "Mart"}},
                {{"Mar", ""}, {null, null}},
                {{"Ma", ""}, {"FEMALE", "Mo"}},
                {{"M", ""}, {null, null}},
                {{"", ""}, {null, null}},
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], enumGenderToString(genderer.firstName(name[0][0])));
            assertEquals(name[1][1], inflectioner.firstName(name[0][0]));
        }

        // Surname:
        names = new String[][][] {
                {{"", "Jan"}, {"MALE", "Jane"}},
                {{"", "Koval"}, {"MALE", "Kovale"}},
                {{"", "Martina"}, {"MALE", "Martino"}},
                {{"", "Martin"}, {"MALE", "Martine"}},
                {{"", "Marti"}, {"FEMALE", "Marti"}},
                {{"", "Mart"}, {"MALE", "Marte"}},
                {{"", "Mar"}, {"MALE", "Mare"}},
                {{"", "Ma"}, {"MALE", "Mo"}},
                {{"", "M"}, {null, null}},
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], enumGenderToString(genderer.surname(name[0][1])));
            assertEquals(name[1][1], inflectioner.surname(name[0][1]));
        }

        // First name and surname:
        names = new String[][][] {
                {{"Martina", "Martina"}, {null, "Martino Martino", "FEMALE", "MALE", "Martino Martino"}},
                {{"Martin", "Martin"}, {"MALE", "Martine Martine", "MALE", "MALE", "Martine Martine"}},
                {{"Martina", "Martin"}, {null, "Martino Martine", "FEMALE", "MALE", "Martino Martine"}},
                {{"Martin", "Martina"}, {"MALE", "Martine Martino", "MALE", "MALE", "Martine Martino"}},
                {{"George", "Hill"}, {"MALE", "George Hille", "MALE", "MALE", "George Hille"}},
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], enumGenderToString(genderer.firstNameAndSurname(name[0][0], name[0][1])));
            assertEquals(name[1][1], inflectioner.firstNameAndSurname(name[0][0], name[0][1]));
            assertEquals(name[1][2], enumGenderToString(genderer.firstNameAndSurname_preferFirstName(name[0][0], name[0][1])));
            assertEquals(name[1][3], enumGenderToString(genderer.firstNameAndSurname_preferSurname(name[0][0], name[0][1])));
            assertEquals(name[1][4], inflectioner.firstNameAndSurname_bothNamesVocative(name[0][0], name[0][1]));
        }

        // First name and surname - random:
        names = new String[][][] {
                {{"", ""}, {null, null, null, null, null}},
                {{"abc", ""}, {null, "abc", null, null, null}},
                {{"", "abc"}, {null, "abc", null, null, null}},
                {{"-", ""}, {null, "-", null, null, null}},
                {{"", "-"}, {null, "-", null, null, null}},
                {{"", "abc_-*"}, {null, "abc_-*", null, null, null}},
                {{"Martina", ""}, {"FEMALE", "Martino", "FEMALE", "FEMALE", null}},
                {{"", "Martina"}, {"MALE", "Martino", "MALE", "MALE", null}},
                {{"Jára", "Cimrman"}, {null, "Járo Cimrmane", "FEMALE", "MALE", "Járo Cimrmane"}},  // Note: there is only female first name "Jára".
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], enumGenderToString(genderer.firstNameAndSurname(name[0][0], name[0][1])));
            assertEquals(name[1][1], inflectioner.firstNameAndSurname(name[0][0], name[0][1]));
            assertEquals(name[1][2], enumGenderToString(genderer.firstNameAndSurname_preferFirstName(name[0][0], name[0][1])));
            assertEquals(name[1][3], enumGenderToString(genderer.firstNameAndSurname_preferSurname(name[0][0], name[0][1])));
            assertEquals(name[1][4], inflectioner.firstNameAndSurname_bothNamesVocative(name[0][0], name[0][1]));
        }

        // Disconnect database.
        database.disconnect();
    }

    String enumGenderToString(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.toString();
    }
}

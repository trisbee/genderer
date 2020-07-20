package test.matous;

/*
 * Genderer class and Inflectioner class tested together.
 * Results depends on data inserted in database tables.
 */

        import matous.Genderer;

        import matous.Inflectioner;
        import matous.database.DatabaseConnection;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetermineGender_Test {

    Genderer genderer;
    Inflectioner inflectioner;

    public DetermineGender_Test() {
        DatabaseConnection dc = new DatabaseConnection();
        if (!dc.connect()) {
            System.out.println("Genderer_Test.Genderer_Test()  Database not connected.");
            return;
        }
        genderer = new Genderer(dc);
        inflectioner = new Inflectioner(dc);
    }

    @Test
    void test() {
        String[][][] names;
        // 3D array "String[][][] names": every row contains two columns with several values.
        // First column (input) contains first name and surname.
        // Second column (output) contains gender, vocative name and eventual another value.

        /*
         * Czech names points of interests:
         *
         * "Jan" is male first name (295627), female first name (1), male surname (229) and female surname (4).
         * "Martina" is female first name (81543), male surname (25) and male first name (1).
         * "Koval" is male surname (662) and female surname (100).
         */

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
            assertEquals(name[1][0], genderer.gender_firstName(name[0][0]));
            assertEquals(name[1][1], inflectioner.vocativeName_firstName(name[0][0]));
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
            assertEquals(name[1][0], genderer.gender_surname(name[0][1]));
            assertEquals(name[1][1], inflectioner.vocativeName_surname(name[0][1]));
        }

        // First name and surname:
        names = new String[][][] {
                {{"Martina", "Martina"}, {null, "Martino Martino"}},
                {{"Martin", "Martin"}, {"MALE", "Martine Martine"}},
                {{"Martina", "Martin"}, {null, "Martino Martine"}},
                {{"Martin", "Martina"}, {"MALE", "Martine Martino"}},
                {{"George", "Hill"}, {"MALE", "George Hille"}},
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], genderer.gender(name[0][0], name[0][1]));
            assertEquals(name[1][1], inflectioner.vocativeName(name[0][0], name[0][1]));
        }

        // First name and surname - random:
        names = new String[][][] {
                {{"", ""}, {null, null, null, null}},
                {{"abc", ""}, {null, "abc", null, null}},
                {{"", "abc"}, {null, "abc", null, null}},
                {{"-", ""}, {null, "-", null, null}},
                {{"", "-"}, {null, "-", null, null}},
                {{"", "abc_-*"}, {null, "abc_-*", null, null}},
                {{"Martina", ""}, {"FEMALE", "Martino", "FEMALE", null}},
                {{"", "Martina"}, {"MALE", "Martino", "MALE", null}},
                {{"Jára", "Cimrman"}, {null, "Járo Cimrmane", "FEMALE", "Járo Cimrmane"}},  // Note: there is only female first name "Jára".
        };
        for (String[][] name : names) {
            assertEquals(name[1][0], genderer.gender(name[0][0], name[0][1]));
            assertEquals(name[1][1], inflectioner.vocativeName(name[0][0], name[0][1]));
        }
        // First name and surname - random - used method vocativeName_isFound():
        for (String[][] name : names) {
            assertEquals(name[1][2], genderer.gender_preferFirstName(name[0][0], name[0][1]));
            assertEquals(name[1][3], inflectioner.vocativeName_bothNamesVocative(name[0][0], name[0][1]));
        }
    }
}

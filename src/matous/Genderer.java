package matous;

/*
 * Methods returns gender.
 */

import matous.database.DatabaseConnection;

public class Genderer {

    DatabaseConnection databaseConnection;

    public Genderer(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String gender_firstName(String firstName) {
        return gender(true, false, firstName);
    }

    public String gender_surname(String surname) {
        return gender(false, true, surname);
    }

    public String gender(String firstName, String surname) {
        /*
         * Return gender determined by first name and surname.
         * If contradict each other, return "null".
         * If not found, return "null".
         */

        String gender_firstName = gender_firstName(firstName);
        String gender_surname = gender_surname(surname);

        if (gender_firstName != null && gender_surname != null && !gender_firstName.equals(gender_surname)) {
            return null;
        }
        else if (gender_firstName != null) {
            return gender_firstName;
        }
        else {
            return gender_surname;
        }
    }

    public String gender_preferFirstName(String firstName, String surname) {
        /*
         * Return gender determined by first name and surname.
         * If contradict each other, prefer first name.
         * If not found, return "null".
         */

        String gender_firstName = gender_firstName(firstName);
        String gender_surname = gender_surname(surname);

        if (gender_firstName != null) {
            return gender_firstName;
        }
        else if (gender_surname != null) {
            return gender_surname;
        }
        else {
            return null;
        }
    }

    private String gender(boolean isFirstName, boolean isSurname, String name) {
        return databaseConnection.getValueFromDatabase(isFirstName, isSurname, databaseConnection.COLUMN_GENDER, name);
    }
}

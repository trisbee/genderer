package genderer;

/*
 * Methods returns gender.
 */

import genderer.database.DatabaseConnection;

public class Genderer {

    DatabaseConnection databaseConnection;

    public Genderer(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String firstName(String firstName) {
        return gender(firstName, databaseConnection.TABLE_FIRST_NAME);
    }

    public String surname(String surname) {
        return gender(surname, databaseConnection.TABLE_SURNAME);
    }

    public String firstNameAndSurname(String firstName, String surname) {
        /*
         * Return gender determined by first name and surname.
         * If contradict each other, return "null".
         * If not found, return "null".
         */

        String gender_firstName = firstName(firstName);
        String gender_surname = surname(surname);

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

    public String firstNameAndSurname_preferFirstName(String firstName, String surname) {
        /*
         * Return gender determined by first name and surname.
         * If contradict each other, prefer first name.
         * If not found, return "null".
         */

        String gender_firstName = firstName(firstName);
        String gender_surname = surname(surname);

        if (gender_firstName != null) {
            return gender_firstName;
        }
        else {
            return gender_surname;
        }
    }

    private String gender(String name, String tableName) {
        String[] result = databaseConnection.getValuesFromDatabase(name, tableName, databaseConnection.COLUMN_GENDER);
        if (result == null) {
            return null;
        }
        return result[0];
    }
}

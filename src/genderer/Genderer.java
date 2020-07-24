package genderer;

/*
 * Methods returns gender.
 */

public class Genderer {

    DatabaseConnection databaseConnection;

    public Genderer(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String firstName(String firstName) {
        return databaseConnection.gender_firstName(firstName);
    }

    public String surname(String surname) {
        return databaseConnection.gender_surname(surname);
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
}

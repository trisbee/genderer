package genderer;

/*
 * Methods returns vocative form name.
 */

import genderer.database.DatabaseConnection;

public class Inflectioner {

    DatabaseConnection databaseConnection;

    public Inflectioner(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String firstName(String firstName) {
        return splitNames(databaseConnection.vocativeName_firstName(firstName));
    }

    public String surname(String surname) {
        return splitNames(databaseConnection.vocativeName_surname(surname));
    }

    public String firstNameAndSurname(String firstName, String surname) {
        /*
         * Return vocative name form. Join first name and surname. If name length equals 0, do not join this name.
         * If not found vocative name form, use origin form.
         */

        String vocativeName_firstName = firstName(firstName);
        String vocativeName_surname = surname(surname);

        if (vocativeName_firstName == null && vocativeName_surname == null) {
            if (firstName.length() == 0 && surname.length() == 0) {
                return null;
            }
            else if (surname.length() == 0) {
                return firstName;
            }
            else if (firstName.length() == 0) {
                return surname;
            }
            else {
                return firstName + " " + surname;
            }
        }
        else if (vocativeName_firstName == null) {
            if (firstName.length() == 0) {
                return vocativeName_surname;
            }
            else {
                return firstName + " " + vocativeName_surname;
            }
        }
        else if (vocativeName_surname == null) {
            if (surname.length() == 0) {
                return vocativeName_firstName;
            }
            else {
                return vocativeName_firstName + " " + surname;
            }
        }
        else {
            return vocativeName_firstName + " " + vocativeName_surname;
        }
    }

    public String firstNameAndSurname_bothNamesVocative(String firstName, String surname) {
        /*
         * Return vocative name form. Join first name and surname.
         * If not found both vocative names form, return "null".
         */

        String vocativeName_firstName = firstName(firstName);
        String vocativeName_surname = surname(surname);

        if (vocativeName_firstName == null || vocativeName_surname == null) {
            return null;
        }
        else {
            return vocativeName_firstName + " " + vocativeName_surname;
        }
    }

    private String splitNames(String multipleValues) {
        /*
         * Split multiple values (names) separated by colon, return first.
         */

        if (multipleValues == null || multipleValues.length() == 0) {
            return null;
        }

        String firstValue = "";
        for (int i1 = 0; i1 < multipleValues.length(); i1++) {
            if (multipleValues.charAt(i1) == ':') {
                break;
            }
            firstValue += multipleValues.charAt(i1);
        }

        return firstValue;
    }
}

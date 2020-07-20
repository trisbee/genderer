package matous;

/*
 * Methods returns vocative form name.
 */

import matous.database.DatabaseConnection;

public class Inflectioner {

    DatabaseConnection databaseConnection;

    public Inflectioner(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public String vocativeName_firstName(String firstName) {
        return vocativeName(true, false, firstName);
    }

    public String vocativeName_surname(String surname) {
        return vocativeName(false, true, surname);
    }

    public String vocativeName(String firstName, String surname) {
        /*
         * Return vocative name form. Join first name and surname. If name length equals 0, do not join this name.
         * If not found vocative name form, use origin form.
         */

        String vocativeName_firstName = vocativeName_firstName(firstName);
        String vocativeName_surname = vocativeName_surname(surname);

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

    public String vocativeName_isFound(String firstName, String surname) {
        /*
         * Return vocative name form. Join first name and surname.
         * If not found vocative name form, return "null".
         */

        String vocativeName_firstName = vocativeName_firstName(firstName);
        String vocativeName_surname = vocativeName_surname(surname);

        if (vocativeName_firstName == null || vocativeName_surname == null) {
            return null;
        }
        else {
            return vocativeName_firstName + " " + vocativeName_surname;
        }
    }

    private String vocativeName(boolean isFirstName, boolean isSurname, String name) {
        return splitNames(databaseConnection.getValueFromDatabase(isFirstName, isSurname, databaseConnection.COLUMN_VOCATIVE, name));
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

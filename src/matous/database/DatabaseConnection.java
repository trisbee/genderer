package matous.database;

/*
 * Connection to database with two tables containing first names and surnames.
 * Table names: "names_inflection_first_name", "names_inflection_surname".
 * Table columns:
 *     "id" uuid NOT NULL,
 *     "its_first_name" boolean NOT NULL,
 *     "its_surname" boolean NOT NULL,
 *     "sex" text NOT NULL,
 *     "name_nominative" text NOT NULL,
 *     "name_vocative" text,
 *     "occurrence_cr" integer.
 *
 * Multiple vocative form names are separated by colon.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    PostgreSQL postgreSQL;

    public final String TABLE_FIRST_NAME = "names_inflection_first_name";
    public final String TABLE_SURNAME = "names_inflection_surname";
    public final String COLUMN_GENDER = "sex";
    public final String COLUMN_NOMINATIVE = "name_nominative";
    public final String COLUMN_VOCATIVE = "name_vocative";
    public final String COLUMN_OCCURRENCE = "occurrence_cr";
//    public final String VALUE_MALE = "MALE";
//    public final String VALUE_FEMALE = "FEMALE";

    //
    final String[][] RESULTSET_COLUMNINDEXES = {
            {"1", COLUMN_GENDER},
            {"2", COLUMN_VOCATIVE},
    };

    public DatabaseConnection() {
        postgreSQL = new PostgreSQL();
    }

    public boolean connect() {
        return postgreSQL.connect();
    }

    public String getValueFromDatabase(boolean isFirstName, boolean isSurname, String getColumn, String name) {
        /*
         * Get value from database at certain column.
         */

        int getColumnIndex = Integer.parseInt(returnValue_2DArray(getColumn));
        ResultSet rs;
        String value;

        rs = databaseQuery(isFirstName, isSurname, name);

        if (rs == null) {  // TODO Is right solution?
            return null;
        }

        try {
            rs.next();
            value = rs.getString(getColumnIndex);
        }
        catch (SQLException e1) {
            // If "ResultSet" is empty, return "null".
            return null;
        }
        if (value == null || value.length() == 0) {
            value = null;
        }

        return value;
    }

    private ResultSet databaseQuery(boolean isFirstName, boolean isSurname, String name) {
        /*
         * Execute query on database.
         * Returns ResultSet containing one record of name with two values: gender and vocative name form.
         */
        String table;

        if (isFirstName) {
            table = TABLE_FIRST_NAME;
        }
        else if (isSurname) {
            table = TABLE_SURNAME;
        }
        else {
            return null;
        }

        return postgreSQL.query("SELECT " + COLUMN_GENDER + ", " + COLUMN_VOCATIVE +
                " FROM " + table + " WHERE " + COLUMN_NOMINATIVE + " = '" + name +
                "' ORDER BY " + COLUMN_OCCURRENCE + " DESC LIMIT 1;");
    }

    String returnValue_2DArray(String findValue) {
        for (String[] strings : RESULTSET_COLUMNINDEXES) {
            if (strings[1].equals(findValue)) {
                return strings[0];
            }
        }

        return null;
    }
}

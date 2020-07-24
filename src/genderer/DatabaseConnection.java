package genderer;

/*
 * Constructor need to receive connection to database. This object can execute queries.
 * Database should contain two tables with first names and surnames.
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
 * Multiple vocative form names are separated by colon at column "name_vocative".
 */

import genderer.database.PostgreSQL;
import genderer.enumeration.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    genderer.database.PostgreSQL postgreSQL;

    // Variables with table names, column names and values at database.
    final String TABLE_FIRST_NAME = "names_inflection_first_name";
    final String TABLE_SURNAME = "names_inflection_surname";
    final String COLUMN_GENDER = "sex";
    final String COLUMN_NOMINATIVE = "name_nominative";
    final String COLUMN_VOCATIVE = "name_vocative";
    final String COLUMN_OCCURRENCE = "occurrence_cr";

    public DatabaseConnection(PostgreSQL postgreSQL) {
        this.postgreSQL = postgreSQL;
    }

    public Gender gender_firstName(String name) {
        return gender(name, TABLE_FIRST_NAME);
    }

    public Gender gender_surname(String name) {
        return gender(name, TABLE_SURNAME);
    }

    private Gender gender(String name, String tableName) {
        String[] result = getValuesFromDatabase(name, tableName, COLUMN_GENDER);
        if (result == null) {
            return null;
        }
        if (result[0].equals(Gender.MALE.toString())) {
            return Gender.MALE;
        }
        else if (result[0].equals(Gender.FEMALE.toString())) {
            return Gender.FEMALE;
        }
        else {
            return null;
        }
    }

    public String vocativeName_firstName(String name) {
        String[] result = getValuesFromDatabase(name, TABLE_FIRST_NAME, COLUMN_VOCATIVE);
        if (result == null) {
            return null;
        }
        return result[0];
    }

    public String vocativeName_surname(String name) {
        String[] result = getValuesFromDatabase(name, TABLE_SURNAME, COLUMN_VOCATIVE);
        if (result == null) {
            return null;
        }
        return result[0];
    }

    public String[] getValuesFromDatabase(String name, String tableName, String ... columnNames) {
        /*
         * Get value from database at certain column.
         */

        ResultSet rs;
        String[] values = new String[columnNames.length];

        rs = databaseQuery(name, tableName, columnNames);

        if (rs == null) {  // TODO Is right solution? It is wanted?
            return null;
        }

        try {
            rs.next();
            for (int i1 = 0; i1 < columnNames.length; i1++) {
                values[i1] = rs.getString(i1 + 1);
                if (values[i1] != null && values[i1].length() == 0) {
                    values[i1] = null;
                }
            }
        }
        catch (SQLException e1) {
            // If "ResultSet" is empty, return "null" (query no one value found).
            return null;
        }

        return values;
    }

    private ResultSet databaseQuery(String name, String tableName, String ... columnNames) {
        /*
         * Execute query on database.
         * Returns ResultSet containing one record with values from passed columns.
         */

        String query_selectColumns;

        query_selectColumns = columnNames[0];
        for (int i1 = 1; i1 < columnNames.length; i1++) {
            query_selectColumns += ", " + columnNames[i1];
        }

        String sqlQuery = "SELECT " + query_selectColumns +
                " FROM " + tableName + " WHERE " + COLUMN_NOMINATIVE + " = '" + name +
                "' ORDER BY " + COLUMN_OCCURRENCE + " DESC LIMIT 1;";

        return postgreSQL.query(sqlQuery);
    }
}

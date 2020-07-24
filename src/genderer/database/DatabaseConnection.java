package genderer.database;

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

    genderer.database.PostgreSQL postgreSQL;

    public final String TABLE_FIRST_NAME = "names_inflection_first_name";
    public final String TABLE_SURNAME = "names_inflection_surname";
    public final String COLUMN_GENDER = "sex";
    public final String COLUMN_NOMINATIVE = "name_nominative";
    public final String COLUMN_VOCATIVE = "name_vocative";
    public final String COLUMN_OCCURRENCE = "occurrence_cr";
//    public final String VALUE_MALE = "MALE";
//    public final String VALUE_FEMALE = "FEMALE";

    public DatabaseConnection() {
        postgreSQL = new PostgreSQL();
    }

    public boolean connect() {
        return postgreSQL.connect();
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

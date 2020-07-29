package genderer.database;

import java.sql.ResultSet;

public interface Database {

    boolean connect();

    void disconnect();

    ResultSet query(String sqlQuery);
    /*
     * Execute SQL query.
     * Returns response in ResultSet.
     */
}

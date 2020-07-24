package genderer.database;

import java.sql.*;

public class PostgreSQL {
    private Connection connect = null;
    private Statement statement = null;
    public boolean isConnected = false;

    public PostgreSQL() {
    }

    public boolean connect() {
        isConnected = false;
        try {
            // Access data get from Environment Variables.
            String url = System.getenv("POSTGRES_URL");
            String user = System.getenv("POSTGRES_USER");
            String password = System.getenv("POSTGRES_PASSWORD");
            // This will load the PostgreSQL (MySQL) driver, each DB has its own driver.
//            Class.forName("org.postgresql.Driver");  // "com.mysql.jdbc.Driver"
            // Setup the connection with the DB.
            connect = DriverManager.getConnection(url, user, password);
            // Statements allow to issue SQL queries to the database.
            statement = connect.createStatement();

            // ... Result set get the result of the SQL query.
            isConnected = true;
        }
        catch (SQLException e1) {
            System.out.println("SQLException  PostgreSQL.connect()  " + e1.getMessage());
            e1.printStackTrace();
        }
        catch (Exception e1) {
            e1.printStackTrace();
            System.out.println("Exception  PostgreSQL.connect()  " + e1.getMessage());
        }

        return isConnected;
    }

    public ResultSet query(String sqlQuery) {
        /*
        * Execute SQL query.
        * Return response in ResultSet.
         */

        ResultSet rs;
        if (!isConnected) {
            return null;
        }
        try {
            rs = statement.executeQuery(sqlQuery);
        }
        catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println("SQLException  PostgreSQL.query()  " + e1.getMessage());
            return null;
        }

        return rs;
    }
}

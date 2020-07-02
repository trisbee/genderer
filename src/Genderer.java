import javax.swing.*;
import java.sql.*;

/*
* -
* 1) Determine gender by received name.
* 2) Return vocative by received name.
*
* A)
*
 */


public class Genderer {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "pozitripharma";
    private String pass = "fakeandgay";


    Genderer() {
        determineGender();
    }


    public String gender_firstName(String firstName) {
        return "";
    }


    void determineGender() {
//        System.out.println("aaa");
        /////
        try {
            // This will load the MySQL (postgresql) driver, each DB has its own driver
            Class.forName("org.postgresql.Driver");  // "com.mysql.jdbc.Driver"
            // Setup the connection with the DB
            connect = DriverManager.getConnection(url, user, pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("SELECT * FROM names_inflection WHERE occurrence_cr = 100;");  // "SELECT 1;"

            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
                String its_first_name = resultSet.getString(2);
                String its_surname = resultSet.getString(3);
                String sex = resultSet.getString(4);
                String name_nominative = resultSet.getString(5);
                String name_vocative = resultSet.getString(6);
                System.out.println(its_first_name + " " + its_surname + " " + sex + " " + name_nominative + " " + name_vocative);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException  " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ClassNotFoundException  " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Genderer();
            }
        });
    }

}

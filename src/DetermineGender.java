import javax.swing.*;
import java.sql.*;

/*

DEL

 */

//public class DetermineGender {
//    private Connection connect = null;
//    private Statement statement = null;
//    private ResultSet resultSet = null;
//    private String url = "jdbc:postgresql://localhost:5432/postgres";
//    private String user = "pozitripharma";
//    private String pass = "fakeandgay";
//
//    DetermineGender() {
//        System.out.println("aaa");
//        try {
//            // This will load the MySQL driver, each DB has its own driver
//            Class.forName("org.postgresql.Driver");  // "com.mysql.jdbc.Driver"
//            // Setup the connection with the DB
//            connect = DriverManager.getConnection(url, user, pass);
//
//            // Statements allow to issue SQL queries to the database
//            statement = connect.createStatement();
//            // Result set get the result of the SQL query
//            resultSet = statement.executeQuery("SELECT * FROM names_inflection WHERE occurrence_cr = 100;");  // "SELECT 1;"
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                System.out.println(id + " " + name);
//            }
//
//
////            writeResultSet(resultSet);
////
////            // PreparedStatements can use variables and are more efficient
////            preparedStatement = connect
////                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
////            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
////            // Parameters start with 1
////            preparedStatement.setString(1, "Test");
////            preparedStatement.setString(2, "TestEmail");
////            preparedStatement.setString(3, "TestWebpage");
////            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
////            preparedStatement.setString(5, "TestSummary");
////            preparedStatement.setString(6, "TestComment");
////            preparedStatement.executeUpdate();
////
////            preparedStatement = connect
////                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
////            resultSet = preparedStatement.executeQuery();
////            writeResultSet(resultSet);
////
////            // Remove again the insert comment
////            preparedStatement = connect
////                    .prepareStatement("delete from feedback.comments where myuser= ? ; ");
////            preparedStatement.setString(1, "Test");
////            preparedStatement.executeUpdate();
////
////            resultSet = statement
////                    .executeQuery("select * from feedback.comments");
////            writeMetaData(resultSet);
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("SQLException  " + e.getMessage());
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("ClassNotFoundException  " + e.getMessage());
//        }
//    }
//
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new DetermineGender();
//            }
//        });
//    }
//
//}

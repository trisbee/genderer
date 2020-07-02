import javax.swing.*;

/*
* - Determine or return:
* 1) Determine gender by received name.
* 2) Return vocative by received name.
*
* - Which name:
* A) First name.
* B) Surname.
*
 */


public class Genderer {
//    private ResultSet resultSet = null;


    Genderer() {
//        System.out.println("aaa");
        returnGender("", "");
    }


    public String returnGender(String firstName, String surname) {
        DatabaseConnection dc = new DatabaseConnection();
        return dc.query(firstName, surname);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Genderer();
            }
        });
    }

}

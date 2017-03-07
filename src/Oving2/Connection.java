package Oving2; /**
 * Created by sebastian on 3/7/17.
 */

import java.sql.*;

public class Connection {


    public static void main(String[] args) {

        java.sql.Connection con  =  null;
            try {
                Class.forName("com.mysql.jdbc.Driver");


                String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/sebasto_database";


                String user = "sebasto_pu";
                String pw = "password";
                System.out.println("before");
                con = DriverManager.getConnection(url,user,pw);
                System.out.println("after");
                System.out.println("Tilkoblingen fungerte.");
            } catch (SQLException ex) {
                System.out.println("Tilkobling feilet: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Feilet under driverlasting: "+ex.getMessage());
            } finally {
                try {
                    if (con !=  null) con.close();
                } catch (SQLException ex) {
                System.out.println("Epic fail: "+ex.getMessage());
            }
        }



    }//end main
}//end FirstExample

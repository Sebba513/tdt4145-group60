package Oving2; /**
 * Created by sebastian on 3/7/17.
 */

import java.sql.*;
import java.util.Scanner;

public class Connection {

    private String user;
    private String pw;
    private java.sql.Connection con;
    private Statement stmt;
    private int oktID;

    public void init() {
        con  =  null;
        stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");


            String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/sebasto_database";


            user = "sebasto_pu";
            pw = "password";
            con = DriverManager.getConnection(url,user,pw);
            System.out.println("Connection Established.");
            setId();
            dbinterface();




//            String sql = "SELECT notater FROM TreningsØkt";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while(rs.next()) {
//                //Retrieve by column name
//                String notat = rs.getString("notater");
//
//                //Display values
//                System.out.print("Notat: " + notat);
//
//            }
//            rs.close();

//                String sql = "INSERT INTO TreningsØkt " +
//                            "VALUES (1,'Første økt', 'Oppstart', '2017-03-10', 'bra luft', 'bra lufting', '2017-03-10 08:15:03', 'det varte lenge', 8, 6, 'dette var en fantastisk økt', 1)";
//                stmt.executeUpdate(sql);


//                String sql = "CREATE TABLE TreningsØkt( " +
//                        "TreningsøktID        NOT NULL AUTO_INCREMENT," +
//                        "Navn            varchar(45), " +
//                        "Dato            date        NOT NULL," +
//                        "Tidspunkt       datetime    NOT NULL," +
//                        "Varighet        varchar(45) NOT NULL," +
//                        "PersonligForm       int check(PersonligForm >=1 and PersonligForm <=10)," +
//                        "Prestasjon      int check(Prestasjon >=1 and Prestasjon <=10)," +
//                        "Notater     varchar(1023)," +
//                        "ResultatID      int, " +
//                         "PRIMARY KEY(TreningsøktID)," +
//                        "FOREIGN KEY(ResultatID) REFERENCES Resultat(ResultatID)"
//);

//                stmt.executeUpdate(sql);
//                System.out.println("table created");



//                String sql1 = "DROP TABLE InnendørsØkt";
//                stmt.executeUpdate(sql1);
//                System.out.println("table dropped");



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
    }

    private void setId() throws SQLException {

        stmt = con.createStatement();

        String sql = "SELECT MAX(TreningsøktID) as maxID FROM TreningsØkt";
        ResultSet rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        while(rs.next()){
            //Retrieve by column name
            int id  = rs.getInt("maxID");
            this.oktID = id;
        }
        rs.close();
    }

    public void dbinterface() throws SQLException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 for inserting a new økt. ");
        System.out.println("Press 2 for notes ");
        System.out.println("Press 3 for statistics ");
        System.out.println("Press 0 for exit ");

        while (scanner.hasNext()) {
            int selection = scanner.nextInt();

            if (selection == 1) {
                insertOkt(scanner);
            }
            if (selection == 2) {
                getNotes();
            }
            if (selection == 3) {
                oktCount();
            }
            if (selection == 0) {
                System.out.println("Exiting. Goodbye! ");

                break;
            }

//            else if (scanner.nextLine().equals("3")) {
//
//            }
        }
    }

    private void createOkt() throws SQLException {
        String sql = "CREATE TABLE TreningsØkt " +
                "(TreningsøktID int not NULL AUTO_INCREMENT, " +
                "Navn varchar(45), " +
                "Periode varchar(45) NOT NULL," +
                "Dato date NOT NULL, " +
                "Tidspunkt datetime NOT NULL, " +
                "Varighet varchar(45) NOT NULL, " +
                "PersonligForm int check(PersonligForm >=1 and PersonligForm <=10), " +
                "Prestasjon int check(Prestasjon >=1 and Prestasjon <=10), " +
                "Notater varchar(1023), " +
                "PRIMARY KEY(TreningsøktID))";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
    }

    private void insertOkt(Scanner scanner) throws SQLException{
        this.oktID = this.oktID+1;
        System.out.print("Navn: ");

        String navn = "'" + scanner.nextLine() + "'";
        System.out.print("Periode: ");
        String periode = "'" + scanner.nextLine() +  "'";
        System.out.print("Dato: i format 2017-03-10: ");
        String dato = "'" + scanner.nextLine() + "'";
        System.out.print("Tidspunk: i format 2017-03-10 08:15:03: ");
        String tidspunkt = "'" + scanner.nextLine() + "'";
        System.out.print("Varighet: ");
        String varighet = "'" + scanner.nextLine() + "'";
        System.out.print("Personlig Form (1-10): ");
        String personligform = scanner.nextLine();
        System.out.print("Prestasjon (1-10): ");
        String prestasjon = scanner.nextLine();
        System.out.print("Notater: ");
        String notater = "'" + scanner.nextLine() + "'";
        String sql = "INSERT INTO TreningsØkt VALUES(" + Integer.toString(oktID) + ", " + navn + ", " +
                periode + ", " + dato + ", " + tidspunkt + ", " + varighet + ", " + personligform +
                ", " + prestasjon + ", " + notater + ")";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        System.out.println("Successfully inserted.");


    }

    private void getNotes() throws SQLException {
        String sql = "SELECT notater FROM TreningsØkt";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String notat = rs.getString("notater");
                System.out.println("Notat: " + notat);

            }
    }

    private void oktCount() throws SQLException {
        String sql = "SELECT TreningsøktID, Prestasjon, PersonligForm, Dato FROM TreningsØkt " +
                "ORDER BY Prestasjon DESC, PersonligForm DESC ";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Beste økter, sortert etter prestasjon og personlig form:");
        while(rs.next()) {
            String treningsøktID = rs.getString("TreningsøktID");
            String dato = rs.getString("Dato");
            String Prestasjon = rs.getString("Prestasjon");
            String personligform = rs.getString("PersonligForm");


            System.out.println("(" + treningsøktID + ") " + dato + "     " + "Prestasjon: " + Prestasjon + "    Personlig Form: " + personligform );

        }
    }

    public static void main(String[] args) {
        Connection connect = new Connection();
        connect.init();
    }



}//end FirstExample

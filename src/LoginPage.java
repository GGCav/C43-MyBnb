import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

import javax.swing.JOptionPane;


public class LoginPage {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";

    public String currentUser = ""; 
    int type = -1;

    public void begin() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, press 1 for login, 2 for sign up");
        String option = input.nextLine();

        if(option.equals("1")) {
            this.logIn();
        } else if(option.equals("2")) {
            this.signUp();
        } else {
            System.out.println("Invalid input, please try again!");
            this.begin();
        }
        input.close();
    }

    private void logIn() throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("1. Login as Host, 2. Login as Renter");
        String option = input.nextLine();


            	//Register JDBC driver
		Class.forName(dbClassName);
		//Database credentials
		final String USER = "root";
		final String PASS = "030210Hjf@";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = conn.createStatement();
            System.out.println("Enter username: ");
            String username = input.nextLine();
            System.out.println("Enter Password: ");
            String password = input.nextLine();
            if (option.equals("1")) {

                String queryString = "SELECT * FROM Hosts where username = " + username + " AND password = " + password + " AND type = 1";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection

                if (rs.next()) {
                    this.currentUser = rs.getString("username");
                    this.type = rs.getInt("type");
                    System.out.println("Logged in successfully!");
                    return;
                }
            } else if (option.equals("2")) {
                String queryString = "SELECT * FROM Renters where username = " + username + " AND password = " + password + " AND type = 2";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection

                if (rs.next()) {
                    this.currentUser = rs.getString("username");
                    this.type = rs.getInt("type");
                    System.out.println("Logged in successfully!");
                    return;
                }
            }
            stmt.close();
            conn.close();
            input.close();
        }catch (SQLException e) {
            System.err.println("Connection error occured!");
        }
        JOptionPane.showMessageDialog(null, "Wrong uid or password!"); 

    }

    private void signUp() throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("press 1 to sign up as Host, 2 to sign up as Renter.");
        String option = input.nextLine();
        System.out.println("Enter username: ");
        String username = input.nextLine();

        if(option.equals("1")) { //sign up as Host
            this.type = 1;
        	Class.forName(dbClassName);
            //Database credentials
            final String USER = "root";
            final String PASS = "030210Hjf@";
            //check username in database hosts
            try {
                Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt = conn.createStatement();
                String queryString = "SELECT * FROM Users inner join Hosts on Hosts.uid = Users.uid where Users.username = \"" + username+"\"";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection
                if (rs.next()) {
                    System.out.println("Username already exists!");
                    return;
                }
                System.out.println("Enter Password: ");
                String password = input.nextLine();
                System.out.println("Enter your first name: ");
                String firstName = input.nextLine();
                System.out.println("Enter your last name: ");
                String lastName = input.nextLine();
                System.out.println("Enter your occupation: ");
                String occupation = input.nextLine();
                System.out.println("Enter your date of birth (yyyy-mm-dd): ");
                String dob = input.nextLine();
                System.out.println("Enter your phone: ");
                String phone = input.nextLine();
                System.out.println("Enter your SIN: ");
                String sin = input.nextLine();
                queryString = "INSERT INTO Users (username, password, first_name, last_name, phone) VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" +  phone + "')";
                stmt.executeUpdate(queryString);
                //get uid for the new user
                queryString = "SELECT * FROM Users where username = \"" + username+"\"";
                rs = stmt.executeQuery(queryString);
                rs.next();
                int uid = rs.getInt("uid");
                System.out.println("uid is " + uid);
                //put in table HOSTS
                queryString = "INSERT INTO Hosts (uid) VALUES ('" + uid + "')";
                stmt.executeUpdate(queryString);
                System.out.println("Signed up successfully!");
                stmt.close();
                conn.close();
                input.close();
            }catch (SQLException e) {
                System.err.println("Connection error occured!");
            }
            
        } else if(option.equals("2")){ //sign up as Renter
            this.type = 2;
            //set up card number
           // System.out.println("Please enter your credit card number");
            //int creditcard = input.nextInt();

        }
        input.close();

    }

    private void logOut() {
        this.currentUser = null;
        this.type = 0;
        System.out.println("Logged out successfully!");
    }


}

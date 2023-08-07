import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;  
import java.time.Period;  


public class LoginPage {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";

    protected int currentUser = -1; 
    protected int type = 0;

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
    }

    private void logIn() throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("1. Login as Host, 2. Login as Renter, 3. Login as Admin");
        String option = input.nextLine();
        if (!option.equals("1") && !option.equals("2") && !option.equals("3")) {
            System.out.println("Invalid input, please try again!");
            this.logIn();
            return;
        }
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

                String queryString = "SELECT Hosts.uid FROM Hosts inner join Users on Hosts.uid = Users.uid where Users.username = \"" + username + "\" AND Users.password = \"" + password+"\"";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection
                if (!rs.next()) {
                    System.out.println("Username or password is incorrect!");
                    return;
                } else {
                    this.currentUser = rs.getInt("uid");
                    this.type = 1;
                }
            }else if (option.equals("2")) {
                String queryString = "SELECT Renters.uid FROM Renters inner join Users on Renters.uid = Users.uid where Users.username = \"" + username + "\" AND Users.password = \"" + password+"\"";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection
                if (!rs.next()) {
                    System.out.println("Username or password is incorrect!");
                    return;
                } else {
                    this.currentUser = rs.getInt("uid");
                    this.type = 2;
                }
            }else if (option.equals("3")) {
                String queryString = "SELECT * FROM Users where username = \"" + username + "\" AND password = \"" + password+"\" AND is_admin = 1";
                ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection
                if (!rs.next()) {
                    System.out.println("Username or password is incorrect!");
                    return;
                } else {
                    this.currentUser = rs.getInt("uid");
                    this.type = 3;
                }
            }
            System.out.println("Logged in successfully!");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Login error occured!");
        }
    }

    private void signUp() throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("press 1 to sign up as Host, 2 to sign up as Renter.");
        String option = input.nextLine();
        System.out.println("Enter username: ");
        String username = input.nextLine();

        if(option.equals("1")) { //sign up as Host
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
                //check date format
                if (dob.length() != 10 || dob.charAt(4) != '-' || dob.charAt(7) != '-') {
                    System.out.println("Invalid date format!");
                    return;
                }
                //calculate age from dob using current time
                LocalDate bdate = LocalDate.parse(dob);
                LocalDate curDate = LocalDate.now();  
                Period age = Period.between(bdate, curDate);
                if (age.getYears() < 18) {
                    System.out.println("You must be at least 18 years old to sign up!");
                    return;
                }
                System.out.println("Enter your phone: ");
                String phone = input.nextLine();
                System.out.println("Enter your SIN: ");
                String sin = input.nextLine();
                queryString = "SELECT * FROM Users inner join Hosts on Hosts.uid = Users.uid where Users.SIN = \"" + sin + "\"";
                rs = stmt.executeQuery(queryString); 
                if (rs.next()) {
                    System.out.println("SIN already exists!");
                    return;
                }

                //Enter addresses
                System.out.println("please enter the latitude of the address:");
                String latitude = input.nextLine();
                System.out.println("please enter the longitude of the address:");
                String longitude = input.nextLine();
                System.out.println("please enter the postal code of the address:");
                String postalcode = input.nextLine();
                System.out.println("please enter the city of the address:");
                String city = input.nextLine();
                System.out.println("please enter the country of the address:");
                String country = input.nextLine();
                queryString = "SELECT * FROM Addresses WHERE latitude = " + latitude + " AND longitude = " + longitude;
                rs = stmt.executeQuery(queryString);
                if (!rs.next()) {
                    queryString = "INSERT INTO Addresses (latitude, longitude, postal_code, city, country) VALUES ('" + latitude + "','" + longitude + "', '" + postalcode + "', '" + city + "', '" + country + "');";
                    stmt.executeUpdate(queryString);
                }
                //put in table USERS
                queryString = "INSERT INTO Users (username, password, first_name, last_name, occupation, date_of_birth, phone, sin, is_admin, latitude, longitude) VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + occupation + "', '" + dob + "', '" + phone + "', '" + sin + "','0','" + latitude + "','" + longitude + "')";
                stmt.executeUpdate(queryString);
                //get uid for the new user
                queryString = "SELECT * FROM Users where username = \"" + username+"\"";
                rs = stmt.executeQuery(queryString);
                rs.next();
                int uid = rs.getInt("uid");
                //put in table HOSTS
                queryString = "INSERT INTO Hosts (uid,number_of_listings) VALUES ('" + uid + "', '0')";
                stmt.executeUpdate(queryString);
                System.out.println("Signed up successfully!");
                stmt.close();
                conn.close();
            }catch (SQLException e) {
                System.err.println("Input error!");
            }        
        } else if(option.equals("2")){ //sign up as Renter
        	Class.forName(dbClassName);
            //Database credentials
            final String USER = "root";
            final String PASS = "030210Hjf@";
            //check username in database hosts
            try {
                Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt = conn.createStatement();
                String queryString = "SELECT * FROM Users inner join Renters on Renters.uid = Users.uid where Users.username = \"" + username+"\"";
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
                //check date format
                if (dob.length() != 10 || dob.charAt(4) != '-' || dob.charAt(7) != '-') {
                    System.out.println("Invalid date format!");
                    return;
                }
                //calculate age from dob using current time
                LocalDate bdate = LocalDate.parse(dob);
                LocalDate curDate = LocalDate.now();  
                Period age = Period.between(bdate, curDate);
                if (age.getYears() < 18) {
                    System.out.println("You must be at least 18 years old to sign up!");
                    return;
                }
                System.out.println("Enter your phone: ");
                String phone = input.nextLine();
                System.out.println("Enter your SIN: ");
                String sin = input.nextLine();
                queryString = "SELECT * FROM Users inner join Renters on Renters.uid = Users.uid where Users.SIN = \"" + sin + "\"";
                rs = stmt.executeQuery(queryString); 
                if (rs.next()) {
                    System.out.println("SIN already exists!");
                    return;
                }

                //Enter addresses
                System.out.println("please enter the latitude of the address:");
                String latitude = input.nextLine();
                System.out.println("please enter the longitude of the address:");
                String longitude = input.nextLine();
                System.out.println("please enter the postal code of the address:");
                String postalcode = input.nextLine();
                System.out.println("please enter the city of the address:");
                String city = input.nextLine();
                System.out.println("please enter the country of the address:");
                String country = input.nextLine();
                queryString = "SELECT * FROM Addresses WHERE latitude = " + latitude + " AND longitude = " + longitude;
                rs = stmt.executeQuery(queryString);
                if (!rs.next()) {
                    queryString = "INSERT INTO Addresses (latitude, longitude, postal_code, city, country) VALUES ('" + latitude + "','" + longitude + "', '" + postalcode + "', '" + city + "', '" + country + "');";
                    stmt.executeUpdate(queryString);
                }

                //put in table USERS
                queryString = "INSERT INTO Users (username, password, first_name, last_name, occupation, date_of_birth, phone, sin, is_admin, latitude, longitude) VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', '" + occupation + "', '" + dob + "', '" + phone + "', '" + sin + "','0','" + latitude + "','" + longitude + "')";
                stmt.executeUpdate(queryString);
                //get uid for the new user
                queryString = "SELECT * FROM Users where username = \"" + username+"\"";
                rs = stmt.executeQuery(queryString);
                rs.next();
                int uid = rs.getInt("uid");
                //put in table HOSTS
                System.out.println("Enter your credit card number: ");
                String CardNumber = input.nextLine();
                queryString = "INSERT INTO Renters (uid,card_number) VALUES ('" + uid + "', '" + CardNumber + "')";
                stmt.executeUpdate(queryString);
                System.out.println("Signed up successfully!");
                stmt.close();
                conn.close();
            }catch (SQLException e) {
                System.err.println("Input error!");
            }
        }   
        else {
            System.out.println("Invalid input, please try again!");
            this.signUp();
        }

    }

    public void logOut() {
        this.currentUser = -1;
        this.type = 0;
    }

    public int getUser() {
        return this.currentUser;
    }

    public int getType() {
        return this.type;
    }

    public String getUsername() throws Exception{
        Class.forName(dbClassName);
        //Database credentials
        final String USER = "root";
        final String PASS = "030210Hjf@";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = conn.createStatement();
            String queryString = "SELECT username FROM Users where uid = \"" + this.currentUser +"\"";
            ResultSet rs = stmt.executeQuery(queryString); //stmt is the prepare statment of connection
            rs.next();
            String username = rs.getString("username");
            stmt.close();
            conn.close();
            return username;
        } catch (SQLException e) {
            System.err.println("Get username error occured!");
        }
        return "";
    }
}

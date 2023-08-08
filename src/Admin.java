import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Admin {
    final String USER = "root";
    final String PASS = "030210Hjf@";
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";

    public void viewTotalBookings(){
        // TODO implement here
        System.out.println("1. The total number of bookings in a specific date range by city");
        System.out.println("2. The total number of bookings in a specific date range by postal code");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        if (option.equals("1")){
            System.out.println("Please enter the start date (YYYY-MM-DD):");
            String start_date = input.nextLine();
            //check if the date is valid
            if (start_date.length() != 10 || start_date.charAt(4) != '-' || start_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            System.out.println("Please enter the end date (YYYY-MM-DD):");
            String end_date = input.nextLine();
            //check if the date is valid
            if (end_date.length() != 10 || end_date.charAt(4) != '-' || end_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                Statement stmt = conn.createStatement();
                //Get the total number of bookings in a specific date range group by city
                String sql = "SELECT COUNT(*) , Addresses.city FROM Bookings inner join Listings on Bookings.lid = Listings.lid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Bookings.start_date >= '" + start_date + "' AND Bookings.end_date <= '"+end_date+"' GROUP BY Addresses.city;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("City: "+rs.getString("Addresses.city"));
                    System.out.println("Total number of bookings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (option.equals("2")){
            System.out.println("Please enter the start date (YYYY-MM-DD):");
            String start_date = input.nextLine();
            //check if the date is valid
            if (start_date.length() != 10 || start_date.charAt(4) != '-' || start_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            System.out.println("Please enter the end date (YYYY-MM-DD):");
            String end_date = input.nextLine();
            //check if the date is valid
            if (end_date.length() != 10 || end_date.charAt(4) != '-' || end_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION,  USER, PASS);
                Statement stmt = conn.createStatement();
                //Get the total number of bookings in a specific date range group by postal code
                String sql = "SELECT COUNT(*) , Addresses.postal_code FROM Bookings inner join Listings on Bookings.lid = Listings.lid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Bookings.start_date >= '" + start_date + "' AND Bookings.end_date <= '"+end_date+"' GROUP BY Addresses.postal_code;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Postal code: "+rs.getString("Addresses.postal_code"));
                    System.out.println("Total number of bookings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid option!");
        }
    }

    public void viewTotalListings(){
        // TODO implement here
        System.out.println("1. The total number of listings per country");
        System.out.println("2. The total number of listings per city");
        System.out.println("3. The total number of listings per postal code");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            if (option.equals("1")){
                String sql = "SELECT COUNT(*) , Addresses.country FROM Listings inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude GROUP BY Addresses.country;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Country: "+rs.getString("Addresses.country"));
                    System.out.println("Total number of listings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                stmt.close();
            }else if (option.equals("2")){
                String sql = "SELECT COUNT(*) , Addresses.city FROM Listings inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude GROUP BY Addresses.city;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("City: "+rs.getString("Addresses.city"));
                    System.out.println("Total number of listings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                stmt.close();
            }else if (option.equals("3")){
                String sql = "SELECT COUNT(*) , Addresses.postal_code FROM Listings inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude GROUP BY Addresses.postal_code;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Postal code: "+rs.getString("Addresses.postal_code"));
                    System.out.println("Total number of listings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                stmt.close();
            }else {
                System.out.println("Invalid option!");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rankHosts(){
        // TODO implement here
        System.out.println("1. Rank the hosts by the total number of listings per country");
        System.out.println("2. Rank the hosts by the total number of listings per city");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            if (option.equals("1")){
                //get countries
                String sql = "SELECT DISTINCT country FROM Addresses;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Country: "+rs.getString("country"));
                    //get hosts
                    String sql2 = "SELECT COUNT(*) , Users.username FROM Listings inner join Users on Listings.uid = Users.uid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.country = '"+rs.getString("country")+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()) {
                        System.out.println("Host: "+rs2.getString("Users.username"));
                        System.out.println("Total number of listings: "+rs2.getString("COUNT(*)"));
                    }
                    System.out.println("-----------------------------");
                    stmt2.close();
                }
                stmt.close();
            } else if (option.equals("2")){
                //get cities
                String sql = "SELECT DISTINCT city FROM Addresses;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("City: "+rs.getString("city"));
                    //get hosts
                    String sql2 = "SELECT COUNT(*) , Users.username FROM Listings inner join Users on Listings.uid = Users.uid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.city = '"+rs.getString("city")+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()) {
                        System.out.println("Host: "+rs2.getString("Users.username"));
                        System.out.println("Total number of listings: "+rs2.getString("COUNT(*)"));
                    }
                    System.out.println("-----------------------------");
                    stmt2.close();
                }
                stmt.close();
            } else {
                System.out.println("Invalid option!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewCommercialHosts(){
        // TODO implement here
        System.out.println("1. View the commercial hosts by the total number of listings per country");
        System.out.println("2. View the commercial hosts by the total number of listings per city");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement(); 
            if (option.equals("1")){
                String sql = "SELECT DISTINCT country FROM Addresses;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Country: "+rs.getString("country"));
                    //get total listings in this country
                    String sql2 = "SELECT COUNT(*) FROM Listings inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.country = '"+rs.getString("country")+"';";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    rs2.next();
                    int total_listings = rs2.getInt("COUNT(*)");
                    System.out.println("Total number of listings: "+total_listings);
                    sql2 = "SELECT COUNT(*) , Users.username FROM Listings inner join Users on Listings.uid = Users.uid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.country = '"+rs.getString("country")+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                    rs2 = stmt2.executeQuery(sql2);
                    System.out.println("Commercial hosts:");
                    while (rs2.next()) {
                        int number = rs2.getInt("COUNT(*)");
                        if (number > total_listings/10){
                            System.out.println("Username: "+rs2.getString("Users.username")+"; Number of listings: "+number);
                        }
                    }
                    stmt2.close();
                    System.out.println("-----------------------------");
                }
            } else if (option.equals("2")){
                String sql = "SELECT DISTINCT city FROM Addresses;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("City: "+rs.getString("city"));
                    //get total listings in this city
                    String sql2 = "SELECT COUNT(*) FROM Listings inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.city = '"+rs.getString("city")+"';";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    rs2.next();
                    int total_listings = rs2.getInt("COUNT(*)");
                    System.out.println("Total number of listings: "+total_listings);
                    sql2 = "SELECT COUNT(*) , Users.username FROM Listings inner join Users on Listings.uid = Users.uid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Addresses.city = '"+rs.getString("city")+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                    rs2 = stmt2.executeQuery(sql2);
                    System.out.println("Commercial hosts:");
                    while (rs2.next()) {
                        int number = rs2.getInt("COUNT(*)");
                        if (number > total_listings/10){
                            System.out.println("Username: "+rs2.getString("Users.username")+"; Number of listings: "+number);
                        }
                    }
                    System.out.println("-----------------------------");
                    stmt2.close();
                }
            } else {
                System.out.println("Invalid option!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rankRenters(){
        // TODO implement here
        System.out.println("1. Rank the renters by the total number of bookings in a specific date range");
        System.out.println("2. Rank the renters by the total number of bookings in one year by city");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        if (option.equals("1")){
            System.out.println("Please enter the start date (YYYY-MM-DD):");
            String start_date = input.nextLine();
            //check if the date is valid
            if (start_date.length() != 10 || start_date.charAt(4) != '-' || start_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            System.out.println("Please enter the end date (YYYY-MM-DD):");
            String end_date = input.nextLine();
            //check if the date is valid
            if (end_date.length() != 10 || end_date.charAt(4) != '-' || end_date.charAt(7) != '-'){
                System.out.println("Invalid date!");
                return;
            }
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                Statement stmt = conn.createStatement();
                //Rank the renters by the total number of bookings in a specific date range
                String sql = "SELECT COUNT(*) , Users.username FROM Bookings inner join Users on Bookings.uid = Users.uid WHERE Bookings.start_date >= '" + start_date + "' AND Bookings.end_date <= '"+end_date+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("Username: "+rs.getString("Users.username"));
                    System.out.println("Total number of bookings: "+rs.getString("COUNT(*)"));
                    System.out.println("-----------------------------");
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (option.equals("2")){
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                Statement stmt = conn.createStatement();
                String sql = "SELECT DISTINCT city FROM Addresses;";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("-----------------------------");
                    System.out.println("City: "+rs.getString("city"));
                    String sql2 = "SELECT COUNT(*) , Users.username FROM Bookings inner join Users on Bookings.uid = Users.uid inner join Listings on Bookings.lid = Listings.lid inner join Addresses on Listings.latitude = Addresses.latitude AND Listings.longitude = Addresses.longitude WHERE Bookings.start_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND Addresses.city = '"+rs.getString("city")+"' GROUP BY Users.username ORDER BY COUNT(*) DESC;";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()) {
                        if (rs2.getInt("COUNT(*)") <= 1){
                            break;
                        }
                        System.out.println("Renter: "+rs2.getString("Users.username"));
                        System.out.println("Total number of bookings: "+rs2.getString("COUNT(*)"));
                    }
                    System.out.println("-----------------------------");
                    stmt2.close();
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid option!");
        }
    }

    public void viewCancellations(){
        // TODO implement here
        try{
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            try {
                String sql = "SELECT COUNT(*) , Users.username FROM Bookings inner join Users inner join Hosts on Bookings.cancelled_by = Users.uid AND Hosts.uid = Users.uid WHERE Bookings.cancelled = 1 AND Bookings.start_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY Users.uid ORDER BY COUNT(*) DESC;";
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                System.out.println("Hosts with the most cancellations:" + rs.getString("Users.username") + " " + rs.getString("COUNT(*)"));
            }catch (Exception e) {
                System.out.println("No hosts with the most cancellations!");
            }
            try {
                String sql = "SELECT COUNT(*) , Users.username FROM Bookings inner join Users inner join Renters on Bookings.cancelled_by = Users.uid AND Renters.uid = Users.uid WHERE Bookings.cancelled = 1 AND Bookings.start_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY Users.uid ORDER BY COUNT(*) DESC;";
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                System.out.println("Renters with the most cancellations:" + rs.getString("Users.username") + " " + rs.getString("COUNT(*)"));
            } catch (Exception e) {
                System.out.println("No renters with the most cancellations!");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            
        }
    }

    public void viewWordCloud(){
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("-----------------------------");
                System.out.println("Listing ID: "+rs.getString("lid"));
                System.out.println("Word cloud: ");
                String sql2 = "SELECT * FROM ListingComments WHERE lid = '"+rs.getString("lid")+"';";
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                //Record the frequency of each word in a dictionary
                Dictionary<String, Integer> dict = new Hashtable<String, Integer>();
                while (rs2.next()) {
                    String[] words = rs2.getString("content").split("[, .?!\\s]+");
                    for (String word : words){
                        if (dict.get(word.toLowerCase()) == null){
                            dict.put(word.toLowerCase(), 1);
                        } else {
                            dict.put(word.toLowerCase(), dict.get(word.toLowerCase())+1);
                        }
                    }
                }
                //Sort the dictionary by the frequency of each word
                List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(((Hashtable<String, Integer>) dict).entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    //Descending order
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue()); 
                    }
                });
                //Print the top 5 words
                int count = 0;
                for (Map.Entry<String, Integer> mapping : list) {
                    System.out.println(mapping.getKey());
                    count++;
                    if (count == 5){
                        break;
                    }
                }
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

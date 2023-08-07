import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class RenterPage {
    final String USER = "root";
    final String PASS = "030210Hjf@";
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";

    public void searchListings(int renter_id) {
        // TODO implement here
    }

    public void bookLising(int renter_id, int listing_id) {
        // TODO implement here
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the start date in yyyy-mm-dd:");
        String start_date = input.nextLine();
        //check date format
        if (start_date.length() != 10 || start_date.charAt(4) != '-' || start_date.charAt(7) != '-') {
            System.out.println("Invalid date!");
            return;
        }
        //check date is not in the past
        if (start_date.compareTo(LocalDate.now().toString()) < 0) {
            System.out.println("Invalid date!");
            return;
        }
        System.out.println("Enter the end date in yyyy-mm-dd:");
        String end_date = input.nextLine();
        //check date format
        if (end_date.length() != 10 || end_date.charAt(4) != '-' || end_date.charAt(7) != '-') {
            System.out.println("Invalid date!");
            return;
        }
        //check date is not in the past
        if (end_date.compareTo(LocalDate.now().toString()) < 0) {
            System.out.println("Invalid date!");
            return;
        }
        //check start date is before end date
        if (start_date.compareTo(end_date) > 0) {
            System.out.println("Invalid date!");
            return;
        }
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            //check if the date and lid is in availabilities for every day in the range
            for (LocalDate date = LocalDate.parse(start_date); date.compareTo(LocalDate.parse(end_date)) <= 0; date = date.plusDays(1)) {
                String sql = "SELECT * FROM Availabilities WHERE lid = " + listing_id + " AND date = '" + date.toString() + "';";
                ResultSet rs = stmt.executeQuery(sql);
                if (!rs.next()) {
                    System.out.println("Listing is not available for the selected dates!");
                    return;
                }
            }
            String sql = "INSERT INTO Bookings (lid, uid, start_date, end_date, cancelled) VALUES (" + listing_id + ", " + renter_id + ", '" + start_date + "', '" + end_date + "', '0');" ;
            stmt.executeUpdate(sql);
            for (LocalDate date = LocalDate.parse(start_date); date.compareTo(LocalDate.parse(end_date)) <= 0; date = date.plusDays(1)) {
                sql = "DELETE FROM Availabilities WHERE lid = " + listing_id + " AND date = '" + date.toString() + "';";
                stmt.executeUpdate(sql);
            }
            System.out.println("Booking successful!");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void cancelBooking(int renter_id) {
        // TODO implement here
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the listing id:");
        int lid = input.nextInt();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + renter_id + " AND cancelled = '0';";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Bookings:");
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Start Date: " + rs.getString("start_date"));
                System.out.println("End Date: " + rs.getString("end_date"));
                System.out.println("Cancelled: " + rs.getString("cancelled"));
                System.out.println("------------------------------------");
            }
            System.out.println("Enter the start date in yyyy-mm-dd:");
            String start_date = input.nextLine();
            //check date format
            if (start_date.length() != 10 || start_date.charAt(4) != '-' || start_date.charAt(7) != '-') {
                System.out.println("Invalid date!");
                return;
            }
            System.out.println("Enter the end date in yyyy-mm-dd:");
            String end_date = input.nextLine();
            //check date format
            if (end_date.length() != 10 || end_date.charAt(4) != '-' || end_date.charAt(7) != '-') {
                System.out.println("Invalid date!");
                return;
            }
            sql = "UPDATE Bookings SET cancelled = '1' AND cancelled_by = '" + renter_id + "' WHERE lid = " + lid + " AND uid = " + renter_id + " AND start_date = '" + start_date + "' AND end_date = '" + end_date + "';";
            stmt.executeUpdate(sql);
            for (LocalDate date = LocalDate.parse(start_date); date.compareTo(LocalDate.parse(end_date)) <= 0; date = date.plusDays(1)) {
                sql = "INSERT INTO Availabilities (lid, date) VALUES (" + lid + ", '" + date.toString() + "');";
                stmt.executeUpdate(sql);
            }
            System.out.println("Booking cancelled!");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void comment(int renter_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT Bookings.lid , Listings.uid FROM Bookings inner join Listings on Bookings.lid = Listings.lid WHERE Bookings.uid = " + renter_id + " AND Bookings.cancelled = '0' AND Bookings.end_date < CURDATE();";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Bookings:");
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Listing ID: " + rs.getInt("lid"));
                System.out.println("Host ID: " + rs.getInt("uid"));
                System.out.println("------------------------------------");
            }
            System.out.println("Enter the listing id:");
            Scanner input = new Scanner(System.in);
            int lid = input.nextInt();
            //check if the listing id is in the bookings
            sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + renter_id + " AND cancelled = '0' AND end_date < CURDATE();";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Invalid listing id!");
                return;
            }
            System.out.println("Enter the comment for the listing:");
            String comment = input.nextLine();
            System.out.println("Enter the rating for the listing:");
            int rating = input.nextInt();
            if (rating < 0 || rating > 5) {
                System.out.println("Invalid rating!");
                return;
            }
            sql = "INSERT INTO ListingComments (lid, uid, content, rating) VALUES (" + lid + ", " + renter_id + ", '" + comment + "', " + rating + ");";
            stmt.executeUpdate(sql);
            System.out.println("Enter the comment for the host:"); 
            comment = input.nextLine();
            System.out.println("Enter the rating for the host:");
            rating = input.nextInt();
            if (rating < 0 || rating > 5) {
                System.out.println("Invalid rating!");
                return;
            }
            sql = "SELECT uid FROM Listings WHERE lid = " + lid + ";";
            rs = stmt.executeQuery(sql);
            rs.next();
            int host_id = rs.getInt("uid");
            sql = "INSERT INTO HostComments (uid1, uid2, content, rating) VALUES (" + host_id + ", " + renter_id + ", '" + comment + "', " + rating + ");";
            stmt.executeUpdate(sql);
            System.out.println("Comment successful!");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void viewBookings(int renter_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Bookings WHERE uid = " + renter_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Bookings:");
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Listing ID: " + rs.getInt("lid"));
                System.out.println("Start Date: " + rs.getString("start_date"));
                System.out.println("End Date: " + rs.getString("end_date"));
                System.out.println("Cancelled: " + rs.getString("cancelled"));
                System.out.println("------------------------------------");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void viewAccountInfo(int renter_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Renters inner join Users on Renters.uid = Users.uid WHERE Renters.uid = \"" + renter_id + "\";";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("====================================");
            System.out.println("User ID: " + rs.getInt("uid"));
            System.out.println("Username: " + rs.getString("username"));
            System.out.println("First Name: " + rs.getString("first_name"));
            System.out.println("Last Name: " + rs.getString("last_name"));
            System.out.println("Date of birth: " + rs.getString("date_of_birth"));
            System.out.println("SIN: " + rs.getString("SIN"));
            System.out.println("Occupation: " + rs.getString("occupation"));
            System.out.println("Phone Number: " + rs.getString("phone"));
            System.out.println("Card number: " + rs.getString("card_number"));
            sql = "SELECT * FROM Addresses WHERE latitude = \"" + rs.getString("latitude") + "\" AND longitude = \"" + rs.getString("longitude") + "\";";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("Address: " + rs.getString("postal_code") + ", " + rs.getString("city") + ", " + rs.getString("country"));
            System.out.println("Latitude: " + rs.getString("latitude"));
            System.out.println("Longitude: " + rs.getString("longitude"));
            sql = "SELECT * FROM RenterComments WHERE uid1 = \"" + renter_id + "\";";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Host ID: " + rs.getInt("uid2"));
                System.out.println("Comment: " + rs.getString("content"));
                System.out.println("Rating: " + rs.getInt("rating"));
                System.out.println("------------------------------------");
            }
            System.out.println("====================================");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void updateAccount(int renter_id) {
        // TODO implement here
        System.out.println("Enter the following information:");
        Scanner input = new Scanner(System.in);
        System.out.println("First Name:");
        String first_name = input.nextLine();
        System.out.println("Last Name:");
        String last_name = input.nextLine();
        System.out.println("Date of birth (yyyy-mm-dd):");
        String date_of_birth = input.nextLine();
        //check date format
        if (date_of_birth.length() != 10 || date_of_birth.charAt(4) != '-' || date_of_birth.charAt(7) != '-') {
            System.out.println("Invalid date!");
            return;
        }
        //check date is not in the future
        if (date_of_birth.compareTo(LocalDate.now().toString()) > 0) {
            System.out.println("Invalid date!");
            return;
        }
        System.out.println("SIN:");
        String sin = input.nextLine();
        System.out.println("Occupation:");
        String occupation = input.nextLine();
        System.out.println("Phone Number:");
        String phone_number = input.nextLine();
        System.out.println("Latitude:");
        String latitude = input.nextLine();
        System.out.println("Longitude:");
        String longitude = input.nextLine();
        System.out.println("Postal Code:");
        String postal_code = input.nextLine();
        System.out.println("City:");
        String city = input.nextLine();
        System.out.println("Country:");
        String country = input.nextLine();
        System.out.println("Card Number:");
        String card_number = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            //check if address already exists
            String sql = "SELECT * FROM Addresses WHERE latitude = " + latitude + " AND longitude = " + longitude;
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                sql = "INSERT INTO Addresses (latitude, longitude, postal_code, city, country) VALUES ('" + latitude + "','" + longitude + "', '" + postal_code + "', '" + city + "', '" + country + "');";
                stmt.executeUpdate(sql);
            }
            sql = "UPDATE Users SET first_name = '" + first_name + "', last_name = '" + last_name + "', date_of_birth = '" + date_of_birth + "', SIN = '" + sin + "', occupation = '" + occupation + "', phone_number = '" + phone_number + "', latitude = '" + latitude + "', longitude = '" + longitude + "' WHERE uid = " + renter_id + ";";
            stmt.executeUpdate(sql);
            sql = "UPDATE Renters SET card_number = '" + card_number + "' WHERE uid = " + renter_id + ";";
            stmt.executeUpdate(sql);
            System.out.println("Account updated!");
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(int renter_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Users WHERE uid = " + renter_id + ";";
            stmt.executeUpdate(sql);
            System.out.println("Account deleted!");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }
}

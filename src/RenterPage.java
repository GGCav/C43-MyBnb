import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import java.util.ArrayList;


public class RenterPage {
    final String USER = "root";
    final String PASS = "030210Hjf@";
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";


    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    //calculate distance between two points
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        // TODO implement here
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1); 
        double a = 
          Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
          Math.sin(dLon/2) * Math.sin(dLon/2)
          ; 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        double d = R * c; // Distance in km
        return d;
    }
    

    public void searchListings(int renter_id) {
        // TODO implement here
        Scanner input = new Scanner(System.in);
        System.out.println("Select the search criteria:");
        System.out.println("1 to view all listings");
        System.out.println("2 to view by search filters");
        String option = input.nextLine();
        if (option.equals("1")){
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                Statement stmt = conn.createStatement();
                System.out.println("Ranked by price:");
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                System.out.println("3. No ranking");
                String rankedbyPrice = input.nextLine();
                String sql = "";
                if (rankedbyPrice.equals("1")) {
                    sql = "SELECT * FROM Listings right join (SELECT lid, AVG(price) AS avg_price FROM Availabilities GROUP BY lid ORDER BY AVG(price) ASC) AS T ON Listings.lid = T.lid GROUP BY T.lid;";
                } else if (rankedbyPrice.equals("2")) {
                    sql = "SELECT * FROM Listings right join (SELECT lid, AVG(price) AS avg_price FROM Availabilities GROUP BY lid ORDER BY AVG(price) DESC) AS T ON Listings.lid = T.lid GROUP BY T.lid;";                    ;
                } else if (rankedbyPrice.equals("3")) {
                    sql = "SELECT * FROM Listings;";
                } else {
                    System.out.println("Invalid option!");
                    return;
                }
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("Listings:");
                while (rs.next()) {
                    System.out.println("------------------------------------");
                    System.out.println("Listing ID: " + rs.getInt("lid"));
                    System.out.println("Host ID: " + rs.getInt("uid"));
                    sql = "SELECT AVG(rating) FROM HostComments WHERE uid1 = " + rs.getInt("uid") + ";";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Host Rating: " + rs2.getString("AVG(rating)"));
                    System.out.println("Latitude: " + rs.getString("latitude"));
                    System.out.println("Longitude: " + rs.getString("longitude"));
                    sql = "SELECT * FROM Addresses WHERE latitude = " + rs.getString("latitude") + " AND longitude = " + rs.getString("longitude") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Address: " + rs2.getString("postal_code") + ", " + rs2.getString("city") + ", " + rs2.getString("country"));
                    sql = "SELECT AVG(price) FROM Availabilities WHERE lid = " + rs.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Average price: " + rs2.getString("AVG(price)"));
                    sql = "SELECT AVG(rating) FROM ListingComments WHERE lid = " + rs.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Average rating: " + rs2.getString("AVG(rating)"));
                    sql = "SELECT content FROM ListingComments WHERE lid = " + rs.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Comments:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("content"));
                    }
                    sql = "SELECT * FROM Amentity WHERE lid = " + rs.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Amentity:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("type"));
                    }
                    sql = "SELECT * FROM Availabilities WHERE lid = " + rs.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Availabilities:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("date") + " " + rs2.getString("price"));
                    }
                    System.out.println("------------------------------------");
                    stmt2.close();
                }
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Error!");
                System.out.println(e.getMessage());
            }
        } else if (option.equals("2")){
            try {
                Class.forName(dbClassName);
                Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                Statement stmt = conn.createStatement();
                String choice = "";
                String latitude = "";
                String longitude = "";
                String distance = "";
                String lowest_price = "";
                String highest_price = "";
                String StartDate = "";
                String EndDate = "";
                List<String> amenities = new ArrayList<String>();
                System.out.println("Filling the filter:");
                while (choice != "0"){
                    System.out.println("1. Latitude");
                    System.out.println("2. Longitude");
                    System.out.println("3. Distance");
                    System.out.println("4. Lowest price");
                    System.out.println("5. Highest price");
                    System.out.println("6. Amenities");
                    System.out.println("7. Start Date");
                    System.out.println("8. End Date");
                    System.out.println("0. Done");
                    choice = input.nextLine();
                    if (choice.equals("1")){
                        System.out.println("Enter the latitude:");
                        latitude = input.nextLine();
                    } else if (choice.equals("2")){
                        System.out.println("Enter the longitude:");
                        longitude = input.nextLine();
                    } else if (choice.equals("3")){
                        System.out.println("Enter the distance:");
                        distance = input.nextLine();
                    } else if (choice.equals("4")){
                        System.out.println("Enter the lowest price:");
                        lowest_price = input.nextLine();
                    } else if (choice.equals("5")){
                        System.out.println("Enter the highest price:");
                        highest_price = input.nextLine();
                    } else if (choice.equals("6")){
                        System.out.println("Enter the amenities:");
                        String amenity = input.nextLine();
                        amenities.add(amenity);
                    } else if (choice.equals("7")){
                        System.out.println("Enter the start date:");
                        StartDate = input.nextLine();
                        if (StartDate.length() != 10 || StartDate.charAt(4) != '-' || StartDate.charAt(7) != '-') {
                            System.out.println("Invalid date!");
                            StartDate = "";
                            continue;
                        }
                    } else if (choice.equals("8")){
                        System.out.println("Enter the end date:");
                        EndDate = input.nextLine();
                        if (EndDate.length() != 10 || EndDate.charAt(4) != '-' || EndDate.charAt(7) != '-') {
                            System.out.println("Invalid date!");
                            EndDate = "";
                            continue;
                        }
                    } else if (choice.equals("0")){
                        break;
                    } else {
                        System.out.println("Invalid option!");
                        continue;
                    }
                }
                //select the listings that match the filters
                String sql = "SELECT * FROM Listings;";
                PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = pstmt.executeQuery(sql);
                RowSetFactory factory = RowSetProvider.newFactory();
                CachedRowSet rowset = factory.createCachedRowSet();
                rowset.populate(rs);
                //copy the forward only result set to a scrollable result set
                if (latitude != "" && longitude != "" && distance != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        if (distance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(rowset.getString("latitude")), Double.parseDouble(rowset.getString("longitude"))) > Double.parseDouble(distance)){
                            rowset.deleteRow();
                        }
                    }
                }
                if (lowest_price != "" && highest_price != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT AVG(price) FROM Availabilities WHERE lid = " + rowset.getInt("lid") + ";";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql);
                        rs2 = stmt2.executeQuery(sql);
                        rs2.next();
                        if (rs2.getDouble("AVG(price)") < Double.parseDouble(lowest_price) || rs2.getDouble("AVG(price)") > Double.parseDouble(highest_price)){
                            rowset.deleteRow();
                        }
                    }
                } else if (lowest_price != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT AVG(price) FROM Availabilities WHERE lid = " + rowset.getInt("lid") + ";";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql);
                        rs2 = stmt2.executeQuery(sql);
                        rs2.next();
                        if (rs2.getDouble("AVG(price)") < Double.parseDouble(lowest_price)){
                            rowset.deleteRow();
                        }
                    }
                } else if (highest_price != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT AVG(price) FROM Availabilities WHERE lid = " + rowset.getInt("lid") + ";";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql);
                        rs2 = stmt2.executeQuery(sql);
                        rs2.next();
                        if (rs2.getDouble("AVG(price)") > Double.parseDouble(highest_price)){
                            rowset.deleteRow();
                        }
                    }
                }
                if (amenities != null){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        for (String amenity : amenities){
                            sql = "SELECT * FROM Amentity WHERE lid = " + rowset.getInt("lid") + " AND type = '" + amenity + "';";
                            ResultSet rs2 = stmt.executeQuery(sql);
                            if (!rs2.next()){
                                rowset.deleteRow();
                            }
                        }
                    }
                }
                if (StartDate != "" && EndDate != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT * FROM Availabilities WHERE lid = " + rowset.getInt("lid") + " AND date >= '" + StartDate + "' AND date <= '" + EndDate + "';";
                        ResultSet rs2 = stmt.executeQuery(sql);
                        if (!rs2.next()){
                            rowset.deleteRow();
                        }
                    }
                } else if (StartDate != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT * FROM Availabilities WHERE lid = " + rowset.getInt("lid") + " AND date >= '" + StartDate + "';";
                        ResultSet rs2 = stmt.executeQuery(sql);
                        if (!rs2.next()){
                            rowset.deleteRow();
                        }
                    }
                } else if (EndDate != ""){
                    rowset.beforeFirst();
                    while (rowset.next()) {
                        sql = "SELECT * FROM Availabilities WHERE lid = " + rowset.getInt("lid") + " AND date <= '" + EndDate + "';";
                        ResultSet rs2 = stmt.executeQuery(sql);
                        if (!rs2.next()){
                            rowset.deleteRow();
                        }
                    }
                }
                //print the listings
                System.out.println("Satisfied listings:");
                rowset.beforeFirst();
                while (rowset.next()) {
                    System.out.println("------------------------------------");
                    System.out.println("Listing ID: " + rowset.getInt("lid"));
                    System.out.println("Host ID: " + rowset.getInt("uid"));
                    sql = "SELECT AVG(rating) FROM HostComments WHERE uid1 = " + rowset.getInt("uid") + ";";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Host Rating: " + rs2.getString("AVG(rating)"));
                    System.out.println("Latitude: " + rowset.getString("latitude"));
                    System.out.println("Longitude: " + rowset.getString("longitude"));
                    sql = "SELECT * FROM Addresses WHERE latitude = " + rowset.getString("latitude") + " AND longitude = " + rowset.getString("longitude") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Address: " + rs2.getString("postal_code") + ", " + rs2.getString("city") + ", " + rs2.getString("country"));
                    sql = "SELECT AVG(price) FROM Availabilities WHERE lid = " + rowset.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Average price: " + rs2.getString("AVG(price)"));
                    sql = "SELECT AVG(rating) FROM ListingComments WHERE lid = " + rowset.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    System.out.println("Average rating: " + rs2.getString("AVG(rating)"));
                    sql = "SELECT content FROM ListingComments WHERE lid = " + rowset.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Comments:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("content"));
                    }
                    sql = "SELECT * FROM Amentity WHERE lid = " + rowset.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Amentity:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("type"));
                    }
                    sql = "SELECT * FROM Availabilities WHERE lid = " + rowset.getInt("lid") + ";";
                    rs2 = stmt2.executeQuery(sql);
                    System.out.println("Availabilities:");
                    while (rs2.next()) {
                        System.out.println(rs2.getString("date") + " " + rs2.getString("price"));
                    }
                    System.out.println("------------------------------------");
                }
                stmt.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Error!");
                System.out.println(e.getMessage());
            }
        }


    }

    public void bookLising(int renter_id) {
        // TODO implement here
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the listing id:");
        String listing_id = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + listing_id+ ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Invalid listing id!");
                return;
            } else {
                int host_id = rs.getInt("uid");
                if (host_id == renter_id) {
                    System.out.println("You cannot book your own listing!");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
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
            String sql = "INSERT INTO Bookings (lid, uid, start_date, end_date, cancelled, cancelled_by) VALUES (" + listing_id + ", " + renter_id + ", '" + start_date + "', '" + end_date + "', '0', '0');" ;
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
        String lid = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + renter_id + " AND cancelled = '0' and start_date >= CURDATE();";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Bookings:");
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Start Date: " + rs.getString("start_date"));
                System.out.println("End Date: " + rs.getString("end_date"));
                System.out.println("------------------------------------");
            }
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
            sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + renter_id + " AND cancelled = '0' AND start_date = '" + start_date + "' AND end_date = '" + end_date + "';";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Booking not found!");
                return;
            }
            sql = "UPDATE Bookings SET cancelled = '1' , cancelled_by = '" + renter_id + "' WHERE lid = " + lid + " AND uid = " + renter_id + " AND start_date = '" + start_date + "' AND end_date = '" + end_date + "';";
            stmt.executeUpdate(sql);
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
            String lid = input.nextLine();
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
            int rating = Integer.parseInt(input.nextLine());
            if (rating < 0 || rating > 5) {
                System.out.println("Invalid rating!");
                return;
            }
            sql = "INSERT INTO ListingComments (lid, uid, content, rating) VALUES (" + lid + ", " + renter_id + ", '" + comment + "', " + rating + ");";
            stmt.executeUpdate(sql);
            System.out.println("Enter the comment for the host:"); 
            comment = input.nextLine();
            System.out.println("Enter the rating for the host:");
            rating = Integer.parseInt(input.nextLine());
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
            sql = "UPDATE Users SET first_name = '" + first_name + "', last_name = '" + last_name + "', date_of_birth = '" + date_of_birth + "', SIN = '" + sin + "', occupation = '" + occupation + "', phone = '" + phone_number + "', latitude = '" + latitude + "', longitude = '" + longitude + "' WHERE uid = " + renter_id + ";";
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

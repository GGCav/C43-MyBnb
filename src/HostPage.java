import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class HostPage {
    final String USER = "root";
    final String PASS = "030210Hjf@";

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/MyBnB";

    public void postListing(int host_id) {
        // TODO implement here
        System.out.println("Please enter the following information:");
        Scanner input = new Scanner(System.in);
        String type = "";
        while (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            System.out.println("Choose the type:");
            System.out.println("1. House");
            System.out.println("2. Apartment");
            System.out.println("3. Guesthouse");
            type = input.nextLine();
        }
        if (type.equals("1")) {
            type = "House";
        } else if (type.equals("2")) {
            type = "Apartment";
        } else if (type.equals("3")) {
            type = "Guesthouse";
        }
        System.out.println("please enter the latitude of the listing:");
        String latitude = input.nextLine();
        System.out.println("please enter the longitude of the listing:");
        String longitude = input.nextLine();
        System.out.println("please enter the postal code of the listing:");
        String postalcode = input.nextLine();
        System.out.println("please enter the city of the listing:");
        String city = input.nextLine();
        System.out.println("please enter the country of the listing:");
        String country = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            //check if address already exists
            String sql = "SELECT * FROM Addresses WHERE latitude = " + latitude + " AND longitude = " + longitude;
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                sql = "INSERT INTO Addresses (latitude, longitude, postal_code, city, country) VALUES ('" + latitude + "','" + longitude + "', '" + postalcode + "', '" + city + "', '" + country + "');";
                stmt.executeUpdate(sql);
            }
            sql = "INSERT INTO Listings (uid, type, latitude, longitude) VALUES ('" + host_id + "', '" + type + "', '" + latitude + "',' " + longitude + "');";
            stmt.executeUpdate(sql);
            //get recently added listing id
            sql = "SELECT MAX(lid) FROM Listings;";
            rs = stmt.executeQuery(sql);
            rs.next();
            int lid = rs.getInt("MAX(lid)");
            System.out.println("Adding amenities for essentials:");
            String amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Wifi");
                System.out.println("2. Kitchen");
                System.out.println("3. Washer");
                System.out.println("4. Dryer");
                System.out.println("5. Air Conditioning");
                System.out.println("6. Heating");
                System.out.println("7. Dedicated Workspace");
                System.out.println("8. TV");
                System.out.println("9. Hair Dryer");
                System.out.println("10. Iron");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Wifi";
                }else if (amenity.equals("2")) {
                    amenity = "Kitchen";
                }else if (amenity.equals("3")) {
                    amenity = "Washer";
                }else if (amenity.equals("4")) {
                    amenity = "Dryer";
                }else if (amenity.equals("5")) {
                    amenity = "Air Conditioning";
                }else if (amenity.equals("6")) {
                    amenity = "Heating";
                }else if (amenity.equals("7")) {
                    amenity = "Dedicated Workspace";
                }else if (amenity.equals("8")) {
                    amenity = "TV";
                }else if (amenity.equals("9")) {
                    amenity = "Hair Dryer";
                }else if (amenity.equals("10")) {
                    amenity = "Iron";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for features:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Free parking");
                System.out.println("2. Pool");
                System.out.println("3. Hot tub");
                System.out.println("4. Gym");
                System.out.println("5. EV charger");
                System.out.println("6. Breakfast");
                System.out.println("7. Crib");
                System.out.println("8. BBQ grill");
                System.out.println("9. Indoor fireplace");
                System.out.println("10. Smoke allowed");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Free parking";
                }else if (amenity.equals("2")) {
                    amenity = "Pool";
                }else if (amenity.equals("3")) {
                    amenity = "Hot tub";
                }else if (amenity.equals("4")) {
                    amenity = "Gym";
                }else if (amenity.equals("5")) {
                    amenity = "EV charger";
                }else if (amenity.equals("6")) {
                    amenity = "Breakfast";
                }else if (amenity.equals("7")) {
                    amenity = "Crib";
                }else if (amenity.equals("8")) {
                    amenity = "BBQ grill";
                }else if (amenity.equals("9")) {
                    amenity = "Indoor fireplace";
                }else if (amenity.equals("10")) {
                    amenity = "Smoke allowed";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for loaction:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Beachfront");
                System.out.println("2. Waterfront");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Beachfront";
                }else if (amenity.equals("2")) {
                    amenity = "Waterfront";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for safety:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Smoke alarm");
                System.out.println("2. Carbon monoxide alarm");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Smoke alarm";
                }else if (amenity.equals("2")) {
                    amenity = "Carbon monoxide alarm";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            //calculate the number of amenities and set a appropriate price
            sql = "SELECT COUNT(*) FROM Amentity WHERE lid = " + lid + ";";
            rs = stmt.executeQuery(sql);
            rs.next();
            int count = rs.getInt("COUNT(*)");
            int price = 0;
            if (count <= 5) {
                price = 200;
            } else if (count <= 10) {
                price = 250;
            } else if (count <= 15) {
                price = 300;
            } else if (count <= 20) {
                price = 350;
            } else {
                price = 400;
            }
            sql = "UPDATE Listings SET suggested_price = " + price + " WHERE lid = " + lid + ";";
            stmt.executeUpdate(sql);
            System.out.println("Listing posted!");
            //increment host's listing count
            sql = "UPDATE Hosts SET number_of_listings = number_of_listings + 1 WHERE uid = " + host_id + ";";
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void updateListing(int host_id) {
        // TODO implement here
        System.out.println("Enter the listing ID you want to update:");
        Scanner input = new Scanner(System.in);
        String lid = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Listing does not exist!");
                return;
            }
            System.out.println("Please enter the following information:");
            String type = "";
            while (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
                System.out.println("Choose the type:");
                System.out.println("1. House");
                System.out.println("2. Apartment");
                System.out.println("3. Guesthouse");
                type = input.nextLine();
            }
            if (type.equals("1")) {
                type = "House";
            } else if (type.equals("2")) {
                type = "Apartment";
            } else if (type.equals("3")) {
                type = "Guesthouse";
            }
            System.out.println("please enter the latitude of the listing:");
            String latitude = input.nextLine();
            System.out.println("please enter the longitude of the listing:");
            String longitude = input.nextLine();
            System.out.println("please enter the postal code of the listing:");
            String postalcode = input.nextLine();
            System.out.println("please enter the city of the listing:");
            String city = input.nextLine();
            System.out.println("please enter the country of the listing:");
            String country = input.nextLine();
            //check if address already exists
            sql = "SELECT * FROM Addresses WHERE latitude = " + latitude + " AND longitude = " + longitude;
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                sql = "INSERT INTO Addresses (latitude, longitude, postal_code, city, country) VALUES ('" + latitude + "','" + longitude + "', '" + postalcode + "', '" + city + "', '" + country + "');";
                stmt.executeUpdate(sql);
            }
            sql = "UPDATE Listings SET type = '" + type + "', latitude = '" + latitude + "', longitude = '" + longitude + "' WHERE lid = " + lid + ";";
            stmt.executeUpdate(sql);
            //get recently added listing id
            System.out.println("Adding amenities for essentials:");
            String amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Wifi");
                System.out.println("2. Kitchen");
                System.out.println("3. Washer");
                System.out.println("4. Dryer");
                System.out.println("5. Air Conditioning");
                System.out.println("6. Heating");
                System.out.println("7. Dedicated Workspace");
                System.out.println("8. TV");
                System.out.println("9. Hair Dryer");
                System.out.println("10. Iron");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Wifi";
                }else if (amenity.equals("2")) {
                    amenity = "Kitchen";
                }else if (amenity.equals("3")) {
                    amenity = "Washer";
                }else if (amenity.equals("4")) {
                    amenity = "Dryer";
                }else if (amenity.equals("5")) {
                    amenity = "Air Conditioning";
                }else if (amenity.equals("6")) {
                    amenity = "Heating";
                }else if (amenity.equals("7")) {
                    amenity = "Dedicated Workspace";
                }else if (amenity.equals("8")) {
                    amenity = "TV";
                }else if (amenity.equals("9")) {
                    amenity = "Hair Dryer";
                }else if (amenity.equals("10")) {
                    amenity = "Iron";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for features:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Free parking");
                System.out.println("2. Pool");
                System.out.println("3. Hot tub");
                System.out.println("4. Gym");
                System.out.println("5. EV charger");
                System.out.println("6. Breakfast");
                System.out.println("7. Crib");
                System.out.println("8. BBQ grill");
                System.out.println("9. Indoor fireplace");
                System.out.println("10. Smoke allowed");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Free parking";
                }else if (amenity.equals("2")) {
                    amenity = "Pool";
                }else if (amenity.equals("3")) {
                    amenity = "Hot tub";
                }else if (amenity.equals("4")) {
                    amenity = "Gym";
                }else if (amenity.equals("5")) {
                    amenity = "EV charger";
                }else if (amenity.equals("6")) {
                    amenity = "Breakfast";
                }else if (amenity.equals("7")) {
                    amenity = "Crib";
                }else if (amenity.equals("8")) {
                    amenity = "BBQ grill";
                }else if (amenity.equals("9")) {
                    amenity = "Indoor fireplace";
                }else if (amenity.equals("10")) {
                    amenity = "Smoke allowed";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for loaction:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Beachfront");
                System.out.println("2. Waterfront");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Beachfront";
                }else if (amenity.equals("2")) {
                    amenity = "Waterfront";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            System.out.println("Adding amenities for safety:");
            amenity = "";
            while (!amenity.equals("0")) {
                System.out.println("Enter 0 to finish adding amenities");
                System.out.println("1. Smoke alarm");
                System.out.println("2. Carbon monoxide alarm");
                amenity = input.nextLine();
                if (amenity.equals("0")) {
                    break;
                }else if (amenity.equals("1")) {
                    amenity = "Smoke alarm";
                }else if (amenity.equals("2")) {
                    amenity = "Carbon monoxide alarm";
                }else {
                    System.out.println("Invalid option!");
                    continue;
                }
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + amenity + "';";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    System.out.println("Amenity already exists!");
                    continue;
                }
                sql = "INSERT INTO Amentity (lid, type) VALUES (" + lid + ", '" + amenity + "');";
                stmt.executeUpdate(sql);
                System.out.println("Amenity added!");
            }
            //show all amenities and let host delete some
            sql = "SELECT * FROM Amentity WHERE lid = " + lid + ";";
            rs = stmt.executeQuery(sql);
            System.out.println("Amenities:");
            while (rs.next()) {
                System.out.println(rs.getString("type"));
            }
            String choice = "";
            while (!choice.equals("0")) {
                System.out.println("Enter 0 to finish deleting amenities");
                System.out.println("Enter the name of the amenity you want to delete:");
                choice = input.nextLine();
                //check if amenity exists
                sql = "SELECT * FROM Amentity WHERE lid = " + lid + " AND type = '" + choice + "';";
                rs = stmt.executeQuery(sql);
                if (!rs.next()) {
                    System.out.println("Amenity does not exist!");
                    continue;
                } else {
                    sql = "DELETE FROM Amentity WHERE lid = " + lid + " AND type = '" + choice + "';";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Amenity deleted!");
            }
            //calculate the number of amenities and set a appropriate price
            sql = "SELECT COUNT(*) FROM Amentity WHERE lid = " + lid + ";";
            rs = stmt.executeQuery(sql);
            rs.next();
            int count = rs.getInt("COUNT(*)");
            int price = 0;
            if (count <= 5) {
                price = 200;
            } else if (count <= 10) {
                price = 250;
            } else if (count <= 15) {
                price = 300;
            } else if (count <= 20) {
                price = 350;
            } else {
                price = 400;
            }
            sql = "UPDATE Listings SET suggested_price = " + price + " WHERE lid = " + lid + ";";
            stmt.executeUpdate(sql);
            System.out.println("Listing updated!");
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void updateAvailabilies(int host_id) {
        // TODO implement here
        System.out.println("Enter the listing ID you want to update:");
        Scanner input = new Scanner(System.in);
        String lid = input.nextLine();
        //check listing belongs to host
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Listing does not exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Please select an option:");
            System.out.println("1. Add availabilities");
            System.out.println("2. Delete availabilities");
            System.out.println("3. Change price");
            System.out.println("0. Exit");
            option = input.nextLine();
            if (option.equals("1")) {
                //add availabilities
                System.out.println("Enter the date you want to add in yyyy-mm-dd:");
                String date = input.nextLine();
                //check date format
                if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-') {
                    System.out.println("Invalid date!");
                    continue;
                }
                //check date is not in the past
                if (date.compareTo(LocalDate.now().toString()) < 0) {
                    System.out.println("Invalid date!");
                    continue;
                }
                System.out.println("Enter the price for this date:");
                String price = input.nextLine();
                try {
                    Class.forName(dbClassName);
                    Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Availabilities WHERE lid = " + lid + " AND Date = '" + date + "';";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        System.out.println("Availability already exists!");
                        continue;
                    }
                    sql = "INSERT INTO Availabilities (lid, Date, price) VALUES (" + lid + ", '" + date + "', '" + price + "');";
                    stmt.executeUpdate(sql);
                    System.out.println("Availability added!");
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error!");
                    System.out.println(e.getMessage());
                }
            } else if (option.equals("2")) {
                //delete availabilities
                System.out.println("Enter the date you want to delete:");
                String date = input.nextLine();
                try {
                    Class.forName(dbClassName);
                    Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Availabilities WHERE lid = " + lid + " AND Date = '" + date + "';";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        System.out.println("Availability does not exist!");
                        continue;
                    }
                    sql = "DELETE FROM Availabilities WHERE lid = " + lid + " AND Date = '" + date + "';";
                    stmt.executeUpdate(sql);
                    System.out.println("Availability deleted!");
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error!");
                    System.out.println(e.getMessage());
                }
            } else if (option.equals("3")) {
                //change price
                System.out.println("Enter the date you want to change price:");
                String date = input.nextLine();
                System.out.println("Enter the new price:");
                String price = input.nextLine();
                try {
                    Class.forName(dbClassName);
                    Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Availabilities WHERE lid = " + lid + " AND Date = '" + date + "';";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        System.out.println("Availability does not exist!");
                        continue;
                    }
                    sql = "UPDATE Availabilities SET price = '" + price + "' WHERE lid = " + lid + " AND Date = '" + date + "';";
                    stmt.executeUpdate(sql);
                    System.out.println("Price changed!");
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error!");
                    System.out.println(e.getMessage());
                }
            } else if (option.equals("0")) {
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    public void cancelBookings(int host_id) {
        // TODO implement here
        System.out.println("Enter the listing ID you want to update:");
        Scanner input = new Scanner(System.in);
        String lid = input.nextLine();
        //check listing belongs to host
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Listing does not exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Please select an option:");
            System.out.println("1. Cancel bookings");
            System.out.println("0. Exit");
            option = input.nextLine();
            if (option.equals("1")) {
                //cancel bookings
                System.out.println("Enter the renter ID you want to cancel:");
                String uid = input.nextLine();
                System.out.println("Enter the start date of the booking you want to cancel:");
                String start_date = input.nextLine();
                System.out.println("Enter the end date of the booking you want to cancel:");
                String end_date = input.nextLine();
                if (end_date.compareTo(LocalDate.now().toString()) < 0) {
                    System.out.println("Invalid end date!");
                    continue;
                }
                try {
                    Class.forName(dbClassName);
                    Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + uid + " AND start_date = '" + start_date + "' AND end_date = '" + end_date + "';";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        System.out.println("Booking does not exist!");
                        continue;
                    }
                    sql = "UPDATE Bookings SET cancelled = 1, cancelled_by = " + uid + " WHERE lid = " + lid + " AND uid = " + uid + " AND start_date = '" + start_date + "' AND end_date = '" + end_date + "';";
                    stmt.executeUpdate(sql);
                    System.out.println("Booking canceled!");
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error!");
                    System.out.println(e.getMessage());
                }
            } else if (option.equals("0")) {
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    public void deleteListing(int host_id) {
        // TODO implement here
        System.out.println("Enter the listing ID you want to delete:");
        Scanner input = new Scanner(System.in);
        String lid = input.nextLine();
        //check listing belongs to host
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Listing does not exist!");
                return;
            }
            sql = "DELETE FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            stmt.executeUpdate(sql);
            System.out.println("Listing deleted!");
            //decrement host's listing count
            sql = "UPDATE Hosts SET number_of_listings = number_of_listings - 1 WHERE uid = " + host_id + ";";
            stmt.executeUpdate(sql);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void viewListings(int host_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String option = "";
            while (!option.equals("0")) {
                String sql = "SELECT * FROM Listings WHERE uid = \"" + host_id + "\";";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("====================================");
                    System.out.println("Listing ID: " + rs.getInt("lid"));
                    System.out.println("Type: " + rs.getString("type"));
                    System.out.println("Latitude: " + rs.getString("latitude"));
                    System.out.println("Longitude: " + rs.getString("longitude"));
                    sql = "SELECT * FROM Addresses WHERE latitude = \"" + rs.getString("latitude") + "\" AND longitude = \"" + rs.getString("longitude") + "\";";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(sql);
                    rs1.next();
                    System.out.println("Postal Code: " + rs1.getString("postal_code"));
                    System.out.println("City: " + rs1.getString("city"));
                    System.out.println("Country: " + rs1.getString("country"));
                    System.out.println("Suggested price: " + rs.getString("suggested_price"));
                    System.out.println("Amenities:");
                    sql = "SELECT * FROM Amentity WHERE lid = \"" + rs.getInt("lid") + "\";";
                    Statement stmt2 = conn.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql);
                    while (rs2.next()) {
                        System.out.println(rs2.getString("type"));
                    }
                    //print bookings for this listing
                    sql = "SELECT * FROM Bookings WHERE lid = \"" + rs.getInt("lid") + "\";";
                    Statement stmt3 = conn.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(sql);
                    System.out.println("Bookings:");
                    while (rs3.next()) {
                        System.out.println("------------------------------------");
                        System.out.println("Renter ID: " + rs3.getInt("uid"));
                        System.out.println("Start Date: " + rs3.getString("start_date"));
                        System.out.println("End Date: " + rs3.getString("end_date"));
                        System.out.println("------------------------------------");
                    }
                    //print availabilities for this listing
                    sql = "SELECT * FROM Availabilities WHERE lid = \"" + rs.getInt("lid") + "\";";
                    Statement stmt4 = conn.createStatement();
                    ResultSet rs4 = stmt4.executeQuery(sql);
                    System.out.println("Availabilities:");
                    while (rs4.next()) {
                        System.out.println("Date: " + rs4.getString("Date"+ "; Price: " + rs4.getString("price")));
                    }
                    //print comments for this listing
                    sql = "SELECT * FROM ListingComments WHERE lid = \"" + rs.getInt("lid") + "\";";
                    Statement stmt5 = conn.createStatement();
                    ResultSet rs5 = stmt5.executeQuery(sql);
                    System.out.println("Comments:");
                    while (rs5.next()) {
                        System.out.println("------------------------------------");
                        System.out.println("Renter ID: " + rs5.getInt("uid"));
                        System.out.println("Comment: " + rs5.getString("comment"));
                        System.out.println("------------------------------------");
                    }
                    System.out.println("====================================");
                    stmt2.close();
                    stmt3.close();
                    stmt4.close();
                    stmt5.close();
                }
                option = "";
                System.out.println("Operation menu:");
                System.out.println("1. Update listing");
                System.out.println("2. Update availabilities");
                System.out.println("3. Update bookings");
                System.out.println("4. Delete listing");
                System.out.println("0. Exit");
                Scanner input = new Scanner(System.in);
                option = input.nextLine();
                if (option.equals("1")){
                    updateListing(host_id);
                }else if (option.equals("2")){
                    updateAvailabilies(host_id);
                }else if (option.equals("3")){
                    cancelBookings(host_id);
                }else if (option.equals("4")){
                    deleteListing(host_id);  
                }else if (option.equals("0")){
                    break;
                }else{
                    System.out.println("Invalid option!");
                }
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void comment(int host_id) {
        // TODO implement here
        System.out.println("Enter the listing ID you want to comment:");
        Scanner input = new Scanner(System.in);
        String lid = input.nextLine();
        //check listing belongs to host
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listings WHERE lid = " + lid + " AND uid = " + host_id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Listing does not exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
        System.out.println("Enter the renter ID you want to comment:");
        String uid = input.nextLine();
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + uid + " AND cancelled = 0;";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Booking does not exist!");
                return;
            } 
            sql = "SELECT * FROM Bookings WHERE lid = " + lid + " AND uid = " + uid + " AND cancelled = 0 AND end_date < CURDATE();";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Booking has not ended yet!");
                return;
            }
            System.out.println("Enter your comment:");
            String comment = input.nextLine();
            System.out.println("Enter your rating from 1 to 5:");
            String rating = input.nextLine();
            if (Integer.parseInt(rating) < 1 || Integer.parseInt(rating) > 5) {
                System.out.println("Invalid rating!");
                return;
            }
            sql = "INSERT INTO RenterComments (uid1, uid2, content, rating) VALUES (" + uid + ", " + host_id + ", '" + comment + "','" + rating + "');";

        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    public void viewAccountInfo(int host_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Hosts inner join Users on Hosts.uid = Users.uid WHERE Hosts.uid = \"" + host_id + "\";";
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
            System.out.println("Number of Listings: " + rs.getInt("number_of_listings"));
            sql = "SELECT * FROM Addresses WHERE latitude = \"" + rs.getString("latitude") + "\" AND longitude = \"" + rs.getString("longitude") + "\";";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("Address: " + rs.getString("postal_code") + ", " + rs.getString("city") + ", " + rs.getString("country"));
            System.out.println("Latitude: " + rs.getString("latitude"));
            System.out.println("Longitude: " + rs.getString("longitude"));
            sql = "SELECT * FROM HostComments WHERE uid1 = \"" + host_id + "\";";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Renter ID: " + rs.getInt("uid2"));
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

    public void updateAccount(int host_id) {
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
            sql = "UPDATE Users SET first_name = '" + first_name + "', last_name = '" + last_name + "', date_of_birth = '" + date_of_birth + "', sin = '" + sin + "', occupation = '" + occupation + "', phone_number = '" + phone_number + "', latitude = '" + latitude + "', longitude = '" + longitude + "' WHERE uid = " + host_id + ";";
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

    public void deleteAccount(int host_id) {
        // TODO implement here
        try {
            Class.forName(dbClassName);
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Users WHERE uid = " + host_id + ";";
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

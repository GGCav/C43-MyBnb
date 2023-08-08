import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the MyBnb System!");
        Scanner input = new Scanner(System.in);
        LoginPage loginPage = new LoginPage();
        while (loginPage.getUser() == -1) {
            System.out.println("Please select an option:");
            System.out.println("1. Enter");
            System.out.println("2. Exit");
            String option = input.nextLine();
            if (option.equals("1")) {
                loginPage.begin();
            } else if (option.equals("2")) {
                System.out.println("Goodbye!");
                input.close();
                return;
            } else {
                System.out.println("Invalid option!");
                continue;
            }

            if (loginPage.getUser() == -1) {
                continue;
            }
            System.out.println("Welcome " + loginPage.getUsername() + "!");
            if (loginPage.getType() == 1){
                //Host interface
                String host_option = "";
                while (!host_option.equals("10")) {
                    System.out.println("Please select an option:");
                    System.out.println("1 to post a listing");
                    System.out.println("2 to view your listings");
                    System.out.println("3 to comment");
                    System.out.println("4 to view account info");
                    System.out.println("5 to update account");
                    System.out.println("6 to delete account");
                    System.out.println("7 to logout");
                    host_option = input.nextLine();
                    HostPage hostPage = new HostPage();
                    if (host_option.equals("1")) {
                        //post a listing
                        hostPage.postListing(loginPage.getUser());
                    } else if (host_option.equals("2")) {
                        //view your listings
                        hostPage.viewListings(loginPage.getUser());
                    } else if (host_option.equals("3")) {
                        //comment
                        hostPage.comment(loginPage.getUser());
                    } else if (host_option.equals("4")) {
                        //view account info
                        hostPage.viewAccountInfo(loginPage.getUser());
                    } else if (host_option.equals("5")) {
                        //update account
                        hostPage.updateAccount(loginPage.getUser());
                    } else if (host_option.equals("6")) {
                        //delete account
                        hostPage.deleteAccount(loginPage.getUser());
                        loginPage.logOut();
                        break;
                    } else if (host_option.equals("7")) {
                        //logout
                        System.out.println("Goodbye! "+loginPage.getUsername());
                        loginPage.logOut();
                        break;
                    } else {
                        System.out.println("Invalid option!");
                        continue;
                    }
                }
            } else if (loginPage.getType() == 2){
                //Renter interface
                String renter_option = "";
                while (!renter_option.equals("8")) {
                    System.out.println("Please select an option:");
                    System.out.println("1 to search listings");
                    System.out.println("2 to book listings");
                    System.out.println("3 to cancel a booking");
                    System.out.println("4 to comment");
                    System.out.println("5 to view past bookings");
                    System.out.println("6 to view account info");
                    System.out.println("7 to update account");
                    System.out.println("8 to delete account");
                    System.out.println("9 to logout");
                    renter_option = input.nextLine();
                    RenterPage renterPage = new RenterPage();
                    if (renter_option.equals("1")) {
                        //search listings
                        renterPage.searchListings(loginPage.getUser());
                    } else if (renter_option.equals("2")) {
                        //book listings
                        renterPage.bookLising(loginPage.getUser());
                    } else if (renter_option.equals("3")) {
                        //cancel a booking
                        renterPage.cancelBooking(loginPage.getUser());
                    } else if (renter_option.equals("4")) {
                        //comment
                        renterPage.comment(loginPage.getUser());
                    } else if (renter_option.equals("5")) {
                        //view past bookings
                        renterPage.viewBookings(loginPage.getUser());
                    } else if (renter_option.equals("6")) {
                        //view account info
                        renterPage.viewAccountInfo(loginPage.getUser());
                    } else if (renter_option.equals("7")) {
                        //update account
                        renterPage.updateAccount(loginPage.getUser());
                    } else if (renter_option.equals("8")) {
                        //delete account
                        renterPage.deleteAccount(loginPage.getUser());
                        loginPage.logOut();
                        break;
                    } else if (renter_option.equals("9")) {
                        //logout
                        System.out.println("Goodbye! "+loginPage.getUsername());
                        loginPage.logOut();
                        break;
                    } else {
                        System.out.println("Invalid option!");
                        continue;
                    }
                }
            } else if (loginPage.getType() == 3){
                //Admin interface
                String admin_option = "";
                while (!admin_option.equals("8")) {
                    System.out.println("Please select an option:");
                    System.out.println("1 to view total number of bookings");
                    System.out.println("2 to view total number of listings");
                    System.out.println("3 to rank hosts by number of listings");
                    System.out.println("4 to view possible commercial hosts");
                    System.out.println("5 to rank renters by number of bookings");
                    System.out.println("6 to view hosts and renters with most cancellations");
                    System.out.println("7 to view the word cloud of listings");
                    System.out.println("8 to logout");
                    admin_option = input.nextLine();
                    Admin admin = new Admin();
                    if (admin_option.equals("1")) {
                        //view total number of bookings
                        admin.viewTotalBookings();
                    } else if (admin_option.equals("2")) {
                        //view total number of listings
                        admin.viewTotalListings();
                    } else if (admin_option.equals("3")) {
                        //rank hosts by number of listings
                        admin.rankHosts();
                    } else if (admin_option.equals("4")) {
                        //view possible commercial hosts
                        admin.viewCommercialHosts();
                    } else if (admin_option.equals("5")) {
                        //rank renters by number of bookings
                        admin.rankRenters();
                    } else if (admin_option.equals("6")) {
                        //view hosts and renters with most cancellations
                        admin.viewCancellations();
                    } else if (admin_option.equals("7")) {
                        //view the word cloud of listings
                        admin.viewWordCloud();
                    } else if (admin_option.equals("8")) {
                        //logout
                        System.out.println("Goodbye! "+loginPage.getUsername());
                        loginPage.logOut();
                        break;
                    } else {
                        System.out.println("Invalid option!");
                        continue;
                    }
                }
            }
        }
        input.close();
    }
}

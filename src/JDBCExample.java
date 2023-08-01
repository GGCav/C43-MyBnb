import java.sql.*;
public class JDBCExample {

	private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/Users";
	
	public static void main(String[] args) throws ClassNotFoundException {
		//Register JDBC driver
		Class.forName(dbClassName);
		//Database credentials
		final String USER = "root";
		final String PASS = "030210Hjf@";
		System.out.println("Connecting to database...");
		
		try {
			//Establish connection
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected to MySQL!");
			
			//Execute a query
			System.out.println("Preparing a statement...");
			Statement stmt = conn.createStatement();
			//Insert 5 users
			String sql = "INSERT INTO Users (id, name, age) VALUES (1, 'John', 20), (2, 'Peter', 25), (3, 'Mark', 30), (4, 'James', 35), (5, 'Luke', 40)";
			stmt.executeUpdate(sql);
			System.out.println("Inserted 5 users!");

			
			System.out.println("Closing connection...");
			stmt.close();
			conn.close();
			System.out.println("Success!");
		} catch (SQLException e) {
			System.err.println("Connection error occured!");
		}
	}

}

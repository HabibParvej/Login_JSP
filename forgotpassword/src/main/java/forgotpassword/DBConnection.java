package forgotpassword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Add serverTimezone and useTimezone parameters
    private static final String URL = "jdbc:mysql://localhost:3306/logindb?useSSL=false&serverTimezone=Asia/Kolkata&useLegacyDatetimeCode=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1083"; // Your MySQL password
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
}

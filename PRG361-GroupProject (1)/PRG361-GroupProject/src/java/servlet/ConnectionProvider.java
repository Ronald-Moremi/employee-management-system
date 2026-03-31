package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    static {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Explicitly register the driver (helps avoid "No suitable driver" issues)
            DriverManager.registerDriver(new org.postgresql.Driver());

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error registering PostgreSQL driver.");
            e.printStackTrace();
        }
    }

    // Get connection to EmployeeDB
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/EmployeeDB",
            "postgres",
            "12345"
        );
    }

    // Get connection to LoginDB
    public static Connection getLoginConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/LoginDB",
            "postgres",
            "12345"
        );
    }
}

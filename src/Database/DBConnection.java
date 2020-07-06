package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class DBConnection {
    //JDBC URL Connection

    private static String protocol = "jdbc";
    private static String vendorName = ":mysql:";
    private static String ipAddress = "//3.227.166.251/U06Bi7";

    private static final String jdbcURL = protocol + vendorName + ipAddress;

    private static String mySQLJDBCDriver = "mysql:mysql-connector-java:5.1.47";
    private static String userName = "U06Bi7";
    private static String password = "53688714951";
    private static Connection connection = null;
    private static DBConnection sInstance;

    public DBConnection(Connection connection) {
        this.connection = connection;
    }

    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successful");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    public Connection connection() {
        return connection;
    }

    public static DBConnection getInstance() {
        if (sInstance == null) {
            synchronized (DBConnection.class) {
                if (sInstance == null) {
                    sInstance = new DBConnection(getConnection());
                }
            }
        }
        return sInstance;
    }
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

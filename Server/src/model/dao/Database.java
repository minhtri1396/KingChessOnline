package model.dao;

import helper.ErrorLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Using MySQL
public class Database {
    
    // MySQL JDBC driver
    public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    
    // Local computer
    private static final String SERVER_NAME = "localhost";
    
    private static final String DB_NAME = "1412278_1412573_1412648_Server";
    
    private static final String USER_NAME = "root";
    
    private static final String PASSWORD = "root";
    
    // JDBC url
    private static final String DB_URL = 
            "jdbc:mysql://" +
            SERVER_NAME +  "/" +
            DB_NAME +
            "?autoReconnect=true&useSSL=false"; // turn on reconnection and turn off using SSL
    
    public static final Database BUILDER = new Database();
    
    private Database() {
        try {
            // Load the JDBC driver (register driver)
            Class.forName(DRIVER_NAME);
//            createDB();
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
            System.out.printf("Could not find the database driver " + e.getMessage());
        }
    }
    
    // Create database if it's not existed
    private void createDB() {
        Connection connection = null;
        try {
            String url =
                    "jdbc:mysql://" +
                    SERVER_NAME +
                    "?autoReconnect=true&useSSL=false";
            
            connection = DriverManager.getConnection(
                    url,
                    USER_NAME,
                    PASSWORD);
            
            String strsql = "CREATE DATABASE " + DB_NAME;

            connection.createStatement().executeUpdate(strsql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(connection);
        }
    }
    
    // Don't forget close the connection after using
    public static Connection createConnection() {
        Connection connection = null;
        
        try {
            // Create a connection to the database
            connection = DriverManager.getConnection(
                    DB_URL,
                    USER_NAME,
                    PASSWORD);
        } catch (SQLException e) {
            // Could not connect to the database
            ErrorLogger.log(Database.BUILDER.getClass(), e);
        }
        
        return connection;
    }
    
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Could not disconnect the database
                ErrorLogger.log(Database.BUILDER.getClass(), e);
            }
        }
    }
}

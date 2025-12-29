
package jobinterviewpreparationsystem.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DatabaseConnection {
 
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "1234";
    private static DatabaseConnection instance;
    private Connection connection;
 
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
 
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null|| instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
        
    public Connection getConnection() {
        return connection;
    }
}


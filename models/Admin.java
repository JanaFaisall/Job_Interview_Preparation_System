
package jobinterviewpreparationsystem.models;

import java.util.Map;

// Administrator class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Administrator class
 */


public class Admin extends User {
    public Admin(int userId, String password, String email, String createdAt) {
        super(userId, password, email, "Administrator", createdAt);
    }

    public void manageUsers(Map<String, User> users) {
        // Logic to manage users
        for (User user : users.values()) {
            System.out.println("Managing user: " + user.getUserId());
        }
    }

    public void addUser(Connection connection, int userId, String password, String userType) {
        String query = "INSERT INTO users_table (user_id, password, email, user_type, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String email = "No email provided"; // Default email
            switch (userType) {
                case "JobSeeker":
                    // Create JobSeeker object if needed
                    break;
                case "Mentor":
                    // Create Mentor object if needed
                    break;
                case "Admin":
                    // Create Admin object if needed
                    break;
                default:
                    System.out.println("Invalid user type.");
                    return;
            }

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, userType);
            preparedStatement.executeUpdate();
            System.out.println("Added new user: " + userId + " as " + userType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(Connection connection, int userId) {
        String query = "DELETE FROM users_table WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Removed user: " + userId);
            } else {
                System.out.println("User with ID " + userId + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Connection connection, int userId, String newPassword) {
        String query = "UPDATE users_table SET password = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated user ID: " + userId + " to new password.");
            } else {
                System.out.println("User with ID " + userId + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
      /*
public boolean signUp(String email, String password, String userType) {
        String query = "INSERT INTO users_table (user_id, password, email, user_type, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        int newUserId = getNextUserId(); // Method to get the next available user ID

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setInt(1, newUserId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, userType);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }*/
}

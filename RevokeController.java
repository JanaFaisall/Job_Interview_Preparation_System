
package jobinterviewpreparationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jobinterviewpreparationsystem.DB.DatabaseConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevokeController {

    @FXML
    private ListView<String> adminListView;  // ListView to display admin users (by user_id)

    // Initialize the controller
    @FXML
    private void initialize() {
        // Set the selection mode to SINGLE to allow only one selection
        adminListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Load administrators into the ListView when the controller is initialized
        loadAdminUsers();
    }

    // Load administrators into the ListView
    private void loadAdminUsers() {
        List<String> adminUsers = new ArrayList<>();
        String query = "SELECT user_id FROM users_table WHERE user_type = 'Administrator'";  // Select only user_id

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Retrieve user_id and add to the ListView
                int userId = rs.getInt("user_id");
                adminUsers.add("ID: " + userId);  // Format user as "ID: <user_id>"
            }

            // If admin users were found, add them to the ListView
            if (!adminUsers.isEmpty()) {
                adminListView.getItems().clear();  // Clear any previous items
                adminListView.getItems().addAll(adminUsers);  // Add new admin users
            } else {
                showAlert("Info", "No administrators found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load admin users. Please check the database connection.");
        }
    }

    @FXML
    private void handleRevokeAdmin(ActionEvent event) {
        String selectedAdmin = adminListView.getSelectionModel().getSelectedItem();

        if (selectedAdmin == null) {
            showAlert("Error", "Please select an administrator to revoke privileges.");
            return;
        }

        int userId = Integer.parseInt(selectedAdmin.split(":")[1].trim());

        boolean success = revokeAdminPrivileges(userId);

        if (success) {
            adminListView.getItems().remove(selectedAdmin);
            showAlert("Success", "Admin privileges for user ID " + userId + " have been revoked.");
        } else {
            showAlert("Error", "Failed to revoke admin privileges.");
        }
        
      
}
    

    // Utility method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Revoke admin privileges for the user with the given user_id
    private boolean revokeAdminPrivileges(int userId) {
        String updateQuery = "UPDATE users_table SET user_type = 'User' WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setInt(1, userId);  // Set the user_id in the query
            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;  // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if the operation failed
    }

    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showOverseesScreen();  // Assuming this method returns to the previous screen
    }
}


    
    
    
       
    



package jobinterviewpreparationsystem;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jobinterviewpreparationsystem.DB.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class GrantController {

    private Connection connection;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button grantButton;  // Reference to the 'Grant Mentor Permission' button

    private ObservableList<String> userList;

    @FXML
    public void initialize() {
        loadUsers();  // Load the users initially

        // Disable the grant button if no user is selected
        userListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            grantButton.setDisable(newValue == null);  // Disable button if no item selected
        });
    }

    // Load the users into the ListView
    private void loadUsers() {
        List<String> users = new ArrayList<>();
        String query = "SELECT user_id FROM users_table WHERE user_type = 'JobSeeker'";  // Assuming you grant mentor permissions to job seekers

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Retrieve user_id and add to the ListView
                int userId = rs.getInt("user_id");
                users.add("ID: " + userId);  // Format the user as "ID: <user_id>"
            }

            // If users were found, add them to the ListView
            if (!users.isEmpty()) {
                userListView.getItems().clear();  // Clear any previous items
                userListView.getItems().addAll(users);  // Add new users
            } else {
                showAlert("Info", "No job seekers found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load users. Please check the database connection.");
        }
    }

    // Handle the granting of mentor permissions
    @FXML
    private void handleGrantMentor(ActionEvent event) {
        String selectedItem = userListView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            int userId = extractUserId(selectedItem);  // Extract the user_id from the selected item

            boolean success = grantMentorPermission(userId);  // Grant permission to the selected user

            if (success) {
                showAlert("Success", "Mentor permission granted successfully.");
                loadUsers();  // Refresh the list after granting permission
            } else {
                showAlert("Error", "Failed to grant mentor permission.");
            }
        }
    }

    // Extract the user_id from the ListView item (e.g., "ID: <user_id>")
    private int extractUserId(String selectedItem) {
        try {
            return Integer.parseInt(selectedItem.split(":")[1].trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid user ID format.");
            return -1;
        }
    }

    // Grant Mentor Permission to the user
    private boolean grantMentorPermission(int userId) {
        int permissionId = 1;  // Assuming permissionId 1 corresponds to 'mentor' permission

        int adminId = getCurrentAdminId();  // Get the ID of the currently logged-in admin

        String query = "INSERT INTO admin_permissions_table (admin_id, permission_id, user_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, adminId);  // Set the admin_id (from currently logged-in admin)
            stmt.setInt(2, permissionId);  // Set the permission_id for 'mentor'
            stmt.setInt(3, userId);  // Set the user_id of the job seeker

            return stmt.executeUpdate() > 0;  // Return true if at least one row is updated

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }

    // Get the current logged-in admin ID (this should be retrieved from the session or login context)
    private int getCurrentAdminId() {
        // For now, we are using a static admin ID (1). You should implement logic to get the real logged-in admin ID.
        return 1;  // Replace with actual logic to get the logged-in admin ID
    }

    // Utility method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handle the 'Go Back' button to return to the previous screen
    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showManageScreen();  // Go back to the management screen
    }
}






package jobinterviewpreparationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobinterviewpreparationsystem.DB.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class AddController {

    @FXML
    private TextField userIdField;  // User ID field
    @FXML
    private PasswordField passwordField;  // Password field

    private DatabaseHandler dbHandler;  // Database handler instance

    public AddController() {
        dbHandler = new DatabaseHandler();  // Initialize the DatabaseHandler
    }

    // Handle Add User action
    @FXML
    private void handleAddUserAction(ActionEvent event) {
        String userId = userIdField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate inputs
        if (userId.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both user ID and password.");
            return;
        }

        // Create default email based on user ID
        String email = "user" + userId + "@example.com";

        // Try to add the user to the database
        boolean success = addUserToDatabase(userId, password, email);

        // Show the result of the operation
        if (success) {
            showAlert("Success", "User " + userId + " added successfully.");
            userIdField.clear();
            passwordField.clear();
        } else {
            showAlert("Error", "Failed to add user. Please try again.");
        }
    }

    // Add user to database using DatabaseHandler
    private boolean addUserToDatabase(String userId, String password, String email) {
        try {
            // Call the addJobSeeker method from DatabaseHandler to add the user
            int userIdInt = Integer.parseInt(userId);  // Ensure userId is an integer
            return dbHandler.addJobSeeker(userIdInt, password, email);  // Call the method to add the user
        } catch (NumberFormatException e) {
            // Handle invalid user ID format (must be an integer)
            showAlert("Error", "User ID must be a valid integer.");
            return false;
        }
    }

    // Show an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handle the back button action to go back to the previous screen
    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showManageScreen();  // Adjust as per your application structure
    }
}





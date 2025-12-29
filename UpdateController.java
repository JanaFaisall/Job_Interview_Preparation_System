
package jobinterviewpreparationsystem;

import java.net.URL;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import jobinterviewpreparationsystem.DB.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class UpdateController implements Initializable {

    @FXML
    private ListView<String> userListView;  
    @FXML
    private TextField newUsernameField;    
    @FXML
    private TextField newPasswordField;    

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    private void loadJobSeekers() {
        String query = "SELECT user_id, email FROM users_table WHERE user_type = 'JobSeeker'";

        try (Connection conn = (Connection) DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            userListView.getItems().clear();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String email = rs.getString("email");
                userListView.getItems().add("ID: " + userId + ", Email: " + email);  // Add to ListView
            }

        } catch (SQLException e) {
            showAlert("Error", "Failed to load JobSeekers from database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateUserAction(ActionEvent event) {
        String selectedItem = userListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Error", "Please select a user to update.");
            return;
        }

        String userIdString = selectedItem.split(",")[0].split(":")[1].trim();  // Get the userId part
        int userId = Integer.parseInt(userIdString);  // Convert to integer

        String newUsername = newUsernameField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        if (newUsername.isEmpty() && newPassword.isEmpty()) {
            showAlert("Error", "Please enter a new password.");
            return;
        }

        if (!newPassword.isEmpty()) {
            boolean updated = databaseHandler.updateJobSeekerPassword(userId, newPassword);
            if (updated) {
                showAlert("Success", "Password updated successfully.");
                loadJobSeekers();  // Refresh the ListView
            } else {
                showAlert("Error", "Failed to update password. Please try again.");
            }
        }

        newUsernameField.clear();
        newPasswordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showManageScreen();  // Replace with your method to navigate back
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ListView and load job seekers from the database
        userListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loadJobSeekers();  // Load job seekers into the ListView from the database
    }
}




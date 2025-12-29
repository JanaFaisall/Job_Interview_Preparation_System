
package jobinterviewpreparationsystem;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import jobinterviewpreparationsystem.DB.DatabaseConnection;
import jobinterviewpreparationsystem.DB.DatabaseHandler;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class RemoveController implements Initializable {

    @FXML
    private ListView<String> userListView;  // ListView to display job seekers
    @FXML
    private Button deleteButton;  // Button to delete selected job seeker

    private DatabaseHandler databaseHandler = new DatabaseHandler();  // Database handler instance

    // Method to load JobSeekers from the database into the ListView
    private void loadJobSeekers() {
        ObservableList<String> jobSeekers = databaseHandler.getJobSeekersList();  // Using method from DatabaseHandler
        userListView.getItems().clear();  // Clear the ListView before adding new items
        userListView.getItems().addAll(jobSeekers);  // Add all job seekers to the ListView
    }

    // Method to handle the deletion of a selected job seeker
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        String selectedItem = userListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Error", "Please select a user to delete.");
            return;
        }

        String userIdString = selectedItem.split(",")[0].split(":")[1].trim();  // Extract user ID from the ListView item
        int userId = Integer.parseInt(userIdString);  // Convert user ID to integer

        // Use DatabaseHandler to delete the job seeker
        boolean success = databaseHandler.removeJobSeeker(userId);

        if (success) {
            showAlert("Success", "JobSeeker with ID " + userId + " has been deleted.");
            loadJobSeekers();  // Refresh the ListView after deletion
        } else {
            showAlert("Error", "Failed to delete the JobSeeker with ID " + userId + ".");
        }
    }

    // Method to show alerts to the user
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to handle the go back action (if you have a back button)
    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showManageScreen();  // Replace with your method to navigate back
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ListView and load job seekers from the database
        userListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loadJobSeekers();  // Load job seekers into ListView at initialization
    }
}



 


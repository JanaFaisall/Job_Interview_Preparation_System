
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jobinterviewpreparationsystem.DB.DatabaseConnection;

public class PreformanceFeedbackController {

    @FXML
    private TextField userIdField;  

    @FXML
    private Label feedbackLabel;  

    private DatabaseConnection dbConnection;  // Database connection instance

    public PreformanceFeedbackController() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();  // Initialize the database connection
    }

    // Handler for the Enter button
    @FXML
    private void handleEnterButton(ActionEvent event) {
        // Get the user_id from the TextField
        String userIdInput = userIdField.getText().trim();

        // Validate the input
        if (userIdInput.isEmpty()) {
            showAlert("Error", "User ID cannot be empty!");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdInput);
            if (isValidUserId(userId)) {
                // Fetch and display feedback for this user
                String feedback = getFeedbackForUser(userId);
                if (feedback != null) {
                    feedbackLabel.setText(feedback);  // Display feedback
                } else {
                    feedbackLabel.setText("No feedback available for this user.");
                }
            } else {
                showAlert("Error", "Invalid User ID. Please enter a valid User ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid User ID format. Please enter a numeric User ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred. Please try again.");
        }
    }

    private boolean isValidUserId(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM users_table WHERE user_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);  // Set the user_id parameter
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // If the count is greater than 0, the user exists
            }
        }
        return false;
    }

    private String getFeedbackForUser(int userId) throws SQLException {
        String query = "SELECT feedback_text, rating FROM feedback_table WHERE job_seeker_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);  // Set the user_id parameter
            ResultSet rs = stmt.executeQuery();
            StringBuilder feedbackBuilder = new StringBuilder();

            // Iterate through the results and build the feedback string
            while (rs.next()) {
                String feedbackText = rs.getString("feedback_text");
                int rating = rs.getInt("rating");
                feedbackBuilder.append("Rating: ").append(rating).append("\n");
                feedbackBuilder.append("Feedback: ").append(feedbackText).append("\n\n");
            }

            return feedbackBuilder.length() > 0 ? feedbackBuilder.toString() : null;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Go back to the previous screen (implement navigation as per your application's flow)
    @FXML
    private void GoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showJobSeekercreen(); // Method to go back to the previous screen
    }
}


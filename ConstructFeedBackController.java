
package jobinterviewpreparationsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import jobinterviewpreparationsystem.DB.DatabaseConnection;

public class ConstructFeedBackController {

    @FXML
    private TextField userName; // User ID input field

    @FXML
    private TextField feedback; // Feedback input field

    @FXML
    private TextField rating; // Rating input field

    @FXML
    private Label submit; // Label to display submit status

  // GoBack button

    private DatabaseConnection dbConnection; // Database connection utility

    public ConstructFeedBackController() throws SQLException {
        dbConnection = DatabaseConnection.getInstance(); // Get the database connection instance
    }

    // Handler for submitting feedback
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        // Retrieve user input from text fields
        String userId = userName.getText().trim();
        String feedbackText = feedback.getText().trim();
        String ratingText = rating.getText().trim();

        // Validate input
        if (userId.isEmpty() || feedbackText.isEmpty() || ratingText.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            // Save feedback to the database
            boolean success = saveFeedbackToDatabase(userId, feedbackText, ratingText);

            if (success) {
                showAlert("Success", "Feedback submitted successfully.");
            } else {
                showAlert("Error", "Failed to submit feedback.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error occurred. Please try again.");
        }
    }

    // Save feedback to the database
    private boolean saveFeedbackToDatabase(String userId, String feedbackText, String ratingText) throws SQLException {
        String query = "INSERT INTO feedback_table (user_id, feedback, rating) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);) {

            stmt.setInt(1, Integer.parseInt(userId)); // Set userId (assumed to be an integer)
            stmt.setString(2, feedbackText); // Set feedback text
            stmt.setInt(3, Integer.parseInt(ratingText)); // Set rating (assumed to be an integer)

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row is inserted
        }
        }
    
        private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Go back to the previous screen (navigate to the previous page)
    @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showMentorScreen(); // Implement your method to navigate back
    }
}



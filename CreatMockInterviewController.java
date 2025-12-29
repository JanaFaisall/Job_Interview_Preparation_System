
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class CreatMockInterviewController {

    @FXML
    private TextField questionTextField;  // Single TextField to input the question

    /**
     * This method is triggered when the Confirm button is pressed.
     * It will handle any logic related to creating a mock interview.
     */
    @FXML
    private void handleConfirmButton(ActionEvent event) {
        // You can handle the mock interview creation logic here
        // For now, just show a confirmation message
        String message = "Mock  Created!";

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mock Interview Created");
        alert.setHeaderText("Mock Interview Created Successfully");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method is triggered when the Add Question button is pressed.
     * It adds the question entered in the TextField to the question bank.
     */
    @FXML
    private void handleAddQuestionButton(ActionEvent event) {
        // Get the text entered in the question text field
        String questionText = questionTextField.getText().trim();
        
        // Validate that the question text is not empty
        if (questionText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter a valid question.");
            alert.showAndWait();
            return;
        }

        // Create a new DatabaseHandler object to interact with the database
        DatabaseHandler dbHandler = new DatabaseHandler();
        boolean success = dbHandler.addQuestion(questionText);

        // Show success or error message based on the result of adding the question
        if (success) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Question Added");
            alert.setContentText("The question has been added to the question bank.");
            alert.showAndWait();
            // Clear the text field after adding the question
            questionTextField.clear();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to Add Question");
            alert.setContentText("There was an error saving the question.");
            alert.showAndWait();
        }
    }

    /**
     * This method is triggered when the Go Back button is pressed.
     * It navigates back to the Mentor screen.
     */
    @FXML
    private void handleGoBackButton(ActionEvent event) {
        // Navigate back to the Mentor screen (adjust the method based on your implementation)
        JobInterviewPreparationSystem.showMentorScreen();
    }
}


    


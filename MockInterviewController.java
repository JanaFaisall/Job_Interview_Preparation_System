
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class MockInterviewController {
    
    @FXML
    private ListView<String> questionListView;  // ListView to display the questions
    
    @FXML
    private TextField answer1, answer2, answer3, answer4, answer5; // TextFields for answers
    
    private DatabaseHandler dbHandler;
    
    private int jobSeekerId;  // Job Seeker ID (could be passed in from another controller or set via a login)
    
    private ObservableList<String> mockInterviewQuestions;
    

    public void initialize() {
        dbHandler = new DatabaseHandler();
        
        mockInterviewQuestions = dbHandler.getMockInterviewQuestions();
        
        questionListView.getItems().addAll(mockInterviewQuestions);
        
        jobSeekerId = 1; 
    }
    

    @FXML
    private void handleSubmit(ActionEvent event) {
        String[] answers = {answer1.getText(), answer2.getText(), answer3.getText(), answer4.getText(), answer5.getText()};
        
        for (String answer : answers) {
            if (answer.isEmpty()) {
                showAlert("Validation Error", "Please fill in all the answers.");
                return;
            }
        }
        
        boolean success = saveAnswers(answers);
        
        if (success) {
            showAlert("Success", "Your answers have been submitted successfully.");
        } else {
            showAlert("Error", "There was an issue saving your answers. Please try again.");
        }
    }
    

    private boolean saveAnswers(String[] answers) {
        boolean result = true;
        
        for (int i = 0; i < answers.length; i++) {
            int questionId = i + 1;  // Assuming question IDs are 1, 2, 3, 4, 5
            
            // Save each answer to the answers_table in the database
            boolean success = dbHandler.submitMockInterviewAnswers(jobSeekerId, questionId, answers[i]);
            
            if (!success) {
                result = false;
                break;
            }
        }
        
        return result;
    }
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    @FXML
    private void handleBackToMainMenuBtn(ActionEvent event) {
        JobInterviewPreparationSystem.showJobSeekercreen();
    }
      
    
}


package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class MentorController  {

    @FXML
    private RadioButton mockInterviewRadio; 

    @FXML
    private RadioButton feedbackRadio;  

    @FXML
    private Button confirmButton;  

    
    
    @FXML
    private ToggleGroup toggleGroup;

   

    @FXML
    private void handleSelection(ActionEvent event) {
      if (mockInterviewRadio.isSelected()) {
            JobInterviewPreparationSystem.showCreateMockScreen();  
        } else if (feedbackRadio.isSelected()) {
            JobInterviewPreparationSystem.showFeedbackScreen(); 
        } else {
            showAlert("Selection Error", "Please select either Mock Interview or Feedback option.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION); 
        alert.setTitle(title);
        alert.setHeaderText(null);  
        alert.setContentText(message); 
        alert.showAndWait(); 
    }
    
    
}




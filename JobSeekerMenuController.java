
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class JobSeekerMenuController {

 @FXML
    private RadioButton Qb;
    
    @FXML
    private RadioButton Mi;
    
    @FXML
    private RadioButton Pf;
    
    @FXML
    private ToggleGroup toggleGroup;
    
    @FXML
    private void handleSubmitAction(ActionEvent event) {
    if (Qb.isSelected()) {
     JobInterviewPreparationSystem.showQBankScreen();
    } else if (Mi.isSelected()) {
        JobInterviewPreparationSystem.showMInterviewScreen();
    } else if (Pf.isSelected()) {
        JobInterviewPreparationSystem.showPFBScreen();
    } else {
        System.out.println("No action selected.");
    }
}
    

    @FXML
    private void handleMockInterview(ActionEvent event) {
        //showScreen("mockInterview.fxml", "Mock Interview");
    }

    @FXML
    private void handlePerformenceFeedback(ActionEvent event) {
        //showScreen("performenceFeedback.fxml", "Performence Feedback");
    }

    
}


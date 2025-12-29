
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ManageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private RadioButton add;
    
    @FXML
    private RadioButton remove;
    
    @FXML
    private RadioButton update;
    
    @FXML
    private ToggleGroup toggleGroup;
    
    
    
    @FXML
    private void handleSubmitAction(ActionEvent event) {
    if (add.isSelected()) {
        JobInterviewPreparationSystem.showAddScreen();
    } else if (remove.isSelected()) {
        JobInterviewPreparationSystem.showRemoveScreen();
    } else if (update.isSelected()) {
        JobInterviewPreparationSystem.showUpdateScreen();
    } else {
        System.out.println("No action selected.");
    }
}
     @FXML
    private void handleGoBackButton(ActionEvent event) {
        JobInterviewPreparationSystem.showAdminScreen();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

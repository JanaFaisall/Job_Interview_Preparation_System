
package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class OverseesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private RadioButton grant;
    
    @FXML
    private RadioButton revoke;
    
    
    @FXML
    private ToggleGroup toggleGroup;
    
    
    @FXML
    private void handleConfirmAction(ActionEvent event) {
    if (grant.isSelected()) {
        JobInterviewPreparationSystem.showGrantScreen();
    } else if (revoke.isSelected()) {
        JobInterviewPreparationSystem.showRevokeScreen();
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


package jobinterviewpreparationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class AdministratorController {

    @FXML
    private RadioButton manage;

    @FXML
    private RadioButton oversees;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        if (manage.isSelected()) {
            JobInterviewPreparationSystem.showManageScreen();
        } else if (oversees.isSelected()) {
            JobInterviewPreparationSystem.showOverseesScreen();
        } else {
            System.out.println("No option selected");
        }
    }
   

}


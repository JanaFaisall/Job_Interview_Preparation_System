
package jobinterviewpreparationsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

public class LoginController implements Initializable {

    @FXML
    private TextField nametxt; // User ID (not email)

    @FXML
    private PasswordField passtext; // Password

    @FXML
    private Label feedbacklb; // Feedback label

    private DatabaseHandler dbHandler;

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String userId = nametxt.getText().trim();  // Get user ID
        String password = passtext.getText().trim();  // Get password

        if (userId.isEmpty() || password.isEmpty()) {
            feedbacklb.setText("Please enter both user ID and password.");
            return;
        }

        // Validate login with the DatabaseHandler
        String userType = dbHandler.validateLogin(userId, password);

        if (userType == null) {
            feedbacklb.setText("Invalid credentials. Please try again.");
        } else {
            feedbacklb.setText(""); // Clear feedback label

            // Navigate to the appropriate interface based on user type
            switch (userType) {
                case "JobSeeker":
                    navigateToJobSeekerScreen();
                    break;

                case "Mentor":
                    navigateToMentorScreen();
                    break;

                case "Administrator":
                    navigateToAdminScreen();
                    break;

                default:
                    feedbacklb.setText("Unknown user type.");
                    break;
            }
        }
    }

    // Navigate to the JobSeeker Screen
    private void navigateToJobSeekerScreen() {
        JobInterviewPreparationSystem.showJobSeekercreen();
    }

    // Navigate to the Mentor Screen
    private void navigateToMentorScreen() {
        JobInterviewPreparationSystem.showMentorScreen();
    }

    // Navigate to the Admin Screen
    private void navigateToAdminScreen() {
        JobInterviewPreparationSystem.showAdminScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbHandler = new DatabaseHandler(); // Initialize the DatabaseHandler
    }
}


    

    


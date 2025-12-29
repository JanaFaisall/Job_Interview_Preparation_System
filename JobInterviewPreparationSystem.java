
package jobinterviewpreparationsystem;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class JobInterviewPreparationSystem extends Application {

    private static Stage stage;
    
         @Override
    public void start(Stage stage) throws Exception {
        //Administrator
        //JobSeekerMenu
        
        JobInterviewPreparationSystem.stage=stage;
        Parent root = FXMLLoader.load(JobInterviewPreparationSystem.class.getResource("views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Job Interview Preparation System Login");
        stage.show();
        
        stage.setOnCloseRequest(event -> {
            event.consume(); 
            showExitConfirmation(stage); 
        });
    }

    
    public static void showui(String fxmlpath, String title){
         
        try {
            Parent root = FXMLLoader.load(JobInterviewPreparationSystem.class.getResource(fxmlpath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JobInterviewPreparationSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
 public static void showMentorScreen(){
     
       showui("views/Mentor.fxml","Mentor");
    }

 public static void showCreateMockScreen(){
     showui("views/CreatMockInterview.fxml","Create Mock Interview");
     }
 
 public static void showFeedbackScreen(){
     showui("views/ConstructFeedBack.fxml","Feedback");
 }
 
public static void showAdminScreen(){
    showui("views/Administrator.fxml","Adimnstaritor");
   }

public static void showManageScreen(){
     showui("views/Manage.fxml","Manage");
 }

public static void showOverseesScreen(){
     showui("views/Oversees.fxml","System Oversees");
 }

public static void showAddScreen(){
    showui("views/Add.fxml","Add user");
}

public static void showRemoveScreen(){
    showui("views/Remove.fxml","Remove user");
}

public static void showUpdateScreen(){
    showui("views/Update.fxml","Update user");
}

public static void showGrantScreen(){
     showui("views/Grant.fxml","Grant");
 }

public static void showRevokeScreen(){
     showui("views/Revoke.fxml","Revoke");
 }

public static void showJobSeekercreen(){
     showui("views/JobSeekerMenu.fxml","Job Seeker");
 }

public static void showQBankScreen(){
     showui("views/QuestionBank.fxml","Question Bank");
 }

public static void showMInterviewScreen(){
     showui("views/MockInterview.fxml","Mock Interview");
 }

public static void showPFBScreen(){
     showui("views/PreformanceFeedback.fxml","Performance Feedback");
 }
    
    
    private void showExitConfirmation(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Any unsaved data may be lost.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();  
            } else {
                primaryStage.show();  
            }
        });
    }
    
    
public static void main(String[] args) {
launch(args);
}}

package jobinterviewpreparationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import jobinterviewpreparationsystem.DB.DatabaseHandler;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuestionBankController {

    @FXML
    private ListView<String> questionListView; 


    private DatabaseHandler databaseHandler;

    public QuestionBankController() {
        databaseHandler = new DatabaseHandler();  // Assuming DatabaseHandler is properly implemented
    }

    @FXML
    private void initialize() {
        loadQuestions();
 
    }

   
    private void loadQuestions() {
        Task<ObservableList<String>> loadQuestionsTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() {
                ObservableList<String> questions = FXCollections.observableArrayList();
                try {
                    questions = databaseHandler.getAllQuestions(); // Assuming this returns an ObservableList<String>
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Database Error", "Failed to load questions.");
                }
                return questions;
            }
        };

        loadQuestionsTask.setOnSucceeded(event -> {
            questionListView.setItems(loadQuestionsTask.getValue());  // Update the ListView with questions
        });

        loadQuestionsTask.setOnFailed(event -> {
            showError("Error", "Failed to fetch questions from the database.");
        });

        new Thread(loadQuestionsTask).start();
    }

    
    @FXML
    private void handleBackToMainMenuBtn(ActionEvent event) {
        JobInterviewPreparationSystem.showJobSeekercreen();
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}





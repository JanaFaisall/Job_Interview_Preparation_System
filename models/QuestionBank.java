
package jobinterviewpreparationsystem.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionBank<T> {

     private int questionId;
    private String questionText;
    private List<T> questions;

    public QuestionBank(int questionId, String questionText, List<T> questions) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questions = questions;
    }

    public QuestionBank() {
        this.questions = new ArrayList<>();
    }

    // Method to load questions from the database
    public void loadQuestionsFromDatabase(Connection connection) {
        String query = "SELECT question_id, question_text FROM question_bank"; // Adjust the table name as necessary

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Assuming T is a String here for simplicity
                String questionText = resultSet.getString("question_text");
                questions.add((T) questionText); // Cast to T
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(T question) {
        questions.add(question);
    }

    public T getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    public List<T> getAllQuestions() {
        return questions;
    }
}
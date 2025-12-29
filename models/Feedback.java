
package jobinterviewpreparationsystem.models;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feedback {

    private List<FeedbackEntry> feedbacks;

    public Feedback() {
        this.feedbacks = new ArrayList<>();
    }

    public List<FeedbackEntry> getFeedbacks() {
        return feedbacks;
    }

    // Method to load feedback from the database
    public void loadFeedbackFromDatabase(Connection connection) {
        String query = "SELECT mentor_id, job_seeker_id, feedback_text FROM feedback_table"; // Adjust table name as necessary

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Integer mentorId = resultSet.getInt("mentor_id");
                Integer jobSeekerId = resultSet.getInt("job_seeker_id");
                String feedbackText = resultSet.getString("feedback_text");
                FeedbackEntry entry = new FeedbackEntry(mentorId, jobSeekerId, feedbackText);
                feedbacks.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class FeedbackEntry {

        private Integer mentorId;
        private Integer jobSeekerId;
        private String feedback;

        public FeedbackEntry(Integer mentorId, Integer jobSeekerId, String feedback) {
            this.mentorId = mentorId;
            this.jobSeekerId = jobSeekerId;
            this.feedback = feedback;
        }

        public Integer getMentorId() {
            return mentorId;
        }

        public Integer getJobSeekerId() {
            return jobSeekerId;
        }

        public String getFeedback() {
            return feedback;
        }
    }
}
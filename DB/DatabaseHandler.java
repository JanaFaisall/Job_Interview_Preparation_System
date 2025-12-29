
package jobinterviewpreparationsystem.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jobinterviewpreparationsystem.models.User;
import java.sql.ResultSet;
import java.util.logging.Logger;
import oracle.jdbc.clio.annotations.Trace.Level;
import java.sql.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.logging.Logger;
 
public class DatabaseHandler {
 
    private Connection connection;
 
    public boolean addJobSeeker(String userId, String password) {
        String query = "INSERT INTO job_seekers (userId, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public boolean updateJobSeeker(String userId, String newPassword) {
        String query = "UPDATE job_seekers SET password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public boolean removeJobSeeker(String userId) {
        String query = "DELETE FROM job_seekers WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    // Grant permission to mentor
    public boolean grantMentorPermission(String userId) {
        String query = "UPDATE users SET role = 'mentor' WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public boolean revokeAdminPermission(String userId) {
        String query = "UPDATE users SET role = 'user' WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public ObservableList<String> getMentorsList() {
        ObservableList<String> mentors = FXCollections.observableArrayList();
        String query = "SELECT userId FROM users WHERE role = 'mentor'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                mentors.add(rs.getString("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentors;
    }
 
    // Get all admins
    public ObservableList<String> getAdminsList() {
        ObservableList<String> admins = FXCollections.observableArrayList();
        String query = "SELECT userId FROM users WHERE role = 'admin'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                admins.add(rs.getString("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

       
     public String validateLogin(String userId, String password) {
        String sql = "SELECT user_type FROM users_table WHERE user_id = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);  // Set user ID
            pstmt.setString(2, password);  // Set password

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("user_type");  // Return the user type (JobSeeker, Mentor, Admin)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no matching credentials are found
    }

     
     
     public ObservableList<String> getAllQuestions() {
    ObservableList<String> questions = FXCollections.observableArrayList();
    String query = "SELECT question_text FROM question_bank_table";
    try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            questions.add(rs.getString("question_text"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return questions;
}

     
     // FOR MOCKINTERVIEW
     public ObservableList<String> getMockInterviewQuestions() {
    ObservableList<String> questions = FXCollections.observableArrayList();
    String query = "SELECT question_text FROM question_bank_table ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 5 ROWS ONLY";
    try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            questions.add(rs.getString("question_text"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return questions;
}

     //SUBMIT ANSWERS 
     public boolean submitMockInterviewAnswers(int jobSeekerId, List<String> answers) {
    String query = "INSERT INTO interview_answers_table (job_seeker_id, answer_text) VALUES (?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        for (String answer : answers) {
            stmt.setInt(1, jobSeekerId);
            stmt.setString(2, answer);
            if (stmt.executeUpdate() <= 0) {
                return false;
            }
        }
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

     
     public ObservableList<String> getFeedback(int jobSeekerId) {
    ObservableList<String> feedbacks = FXCollections.observableArrayList();
    String query = "SELECT feedback_text FROM feedback_table WHERE job_seeker_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, jobSeekerId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(rs.getString("feedback_text"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return feedbacks;
}

     /*public boolean addQuestion(String questionText) {
    String query = "INSERT INTO question_bank_table (question_text) VALUES (?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, questionText);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}*/

     
     public boolean createFeedback(int mentorId, int jobSeekerId, String feedbackText, int rating) {
    String query = "INSERT INTO feedback_table (mentor_id, job_seeker_id, feedback_text, rating) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, mentorId);
        stmt.setInt(2, jobSeekerId);
        stmt.setString(3, feedbackText);
        stmt.setInt(4, rating);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    /* public boolean addJobSeeker(int userId, String password, String email) {
    String query = "INSERT INTO users_table (user_id, password, email, user_type) VALUES (?, ?, ?, 'JobSeeker')";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, userId);
        stmt.setString(2, password);
        stmt.setString(3, email);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }*/

     
     public boolean addJobSeeker(int userId, String password, String email) {
    String query = "INSERT INTO users_table (user_id, password, email, user_type, created_at) "
                 + "VALUES (?, ?, 'default', 'JobSeeker', CURRENT_TIMESTAMP)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, userId);  // Assuming user_id is an integer
        stmt.setString(2, password);
        stmt.setString(3, email);
        return stmt.executeUpdate() > 0;  // Return true if the user was added successfully
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;  // Return false if something went wrong
}


     public boolean removeJobSeeker(int userId) {
    String query = "DELETE FROM users_table WHERE user_id = ? AND user_type = 'JobSeeker'";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, userId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

     
    /* public boolean updateJobSeekerPassword(int userId, String newPassword) {
    String query = "UPDATE users_table SET password = ? WHERE user_id = ? AND user_type = 'JobSeeker'";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, newPassword);
        stmt.setInt(2, userId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}*/

     public boolean grantPermissionToMentor(int adminId, int mentorId) {
    String query = "INSERT INTO admin_permissions_table (admin_id, permission_id) VALUES (?, 1)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, adminId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


     public boolean revokePermissionFromAdmin(int adminId) {
    String query = "DELETE FROM admin_permissions_table WHERE admin_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, adminId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public ObservableList<String> getJobSeekersList() {
    ObservableList<String> jobSeekers = FXCollections.observableArrayList();
    String query = "SELECT user_id FROM users_table WHERE user_type = 'JobSeeker'";  // Removed email reference

    try (Connection conn = DatabaseConnection.getInstance().getConnection();  // Assuming DatabaseConnection is your DB connection utility
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int userId = rs.getInt("user_id");
            jobSeekers.add("ID: " + userId);  // Add the user ID to the list
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return jobSeekers;
}

public boolean updateJobSeekerPassword(int userId, String newPassword) {
    String query = "UPDATE users_table SET password = ? WHERE user_id = ? AND user_type = 'JobSeeker'";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, newPassword);
        stmt.setInt(2, userId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean addQuestion(String questionText) {
    String query = "INSERT INTO question_bank_table (question_text) VALUES (?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, questionText);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean submitMockInterviewAnswers(int jobSeekerId, int questionId, String answerText) {
    String query = "INSERT INTO answers_table (interview_id, question_id, job_seeker_id, answer_text) "
                 + "VALUES (?, ?, ?, ?)";
    
    // Here, we assume that the `interview_id` is auto-generated, or passed in as part of the interview.
    int interviewId = 1;  // Assuming interviewId is 1 for the sake of this example. Adjust accordingly.
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, interviewId);  // Set the interview ID (this could be dynamic)
        stmt.setInt(2, questionId);   // Set the question ID
        stmt.setInt(3, jobSeekerId);  // Set the job seeker ID
        stmt.setString(4, answerText); // Set the answer text
        
        return stmt.executeUpdate() > 0;  // Return true if the insertion is successful
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Return false if there was an issue
    }
}


}

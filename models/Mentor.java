
package jobinterviewpreparationsystem.models;

// Mentor class
public class Mentor extends User {
    
    
    
    public Mentor(int userId, String password, String email, String createdAt) {
        super(userId, password, email, "Mentor", createdAt);
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
    

   /* public boolean signUp(String email, String password, String userType) {
        String query = "INSERT INTO users_table (user_id, password, email, user_type, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        int newUserId = getNextUserId(); // Method to get the next available user ID

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setInt(1, newUserId);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, userType);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    }*/




package jobinterviewpreparationsystem.models;

 public abstract class User {
    protected int userId;
    protected String password;
    protected String email;
    protected String userType;
    protected String createdAt;

    public User(int userId, String password, String email, String userType, String createdAt) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public boolean logIn(int userId, String password) {
        return this.userId == userId && this.password.equals(password);
    }
    
    
}

package ke.co.hostelplus.hostelplus.security;

/**
 * Created by Badru on 4/7/2016.
 */
public class User {
    String username,email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

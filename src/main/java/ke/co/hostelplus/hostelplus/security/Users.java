package ke.co.hostelplus.hostelplus.security;

/**
 * Created by Badru on 4/18/2016.
 */
public class Users {
    String token;
    String id;
    String email;
    String username;

    public Users(String token, String id, String email, String username) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

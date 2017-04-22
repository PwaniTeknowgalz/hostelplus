package ke.co.hostelplus.hostelplus.db;

/**
 * Created by Badru on 4/5/2016.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Badru on 12/16/2015.
 */
@Table(name = "UserDb")
public class UserDb extends Model {

    @Column(name = "token")
    String token;

    @Column(name = "bookmarked")
    String bookmarked;
    

    @Column(name = "identity")
    String identity;




    @Column(name = "email")


    String email;

    @Column(name = "username")
    String username;

    public UserDb() {
        super();
    }
    public UserDb(String token, String bookmarked, String identity, String email, String username) {
        this.token = token;
        this.bookmarked = bookmarked;
        this.identity = identity;
        this.email = email;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdentity() {
        return identity;
    }


    public void setIdentity(String identity) {
        this.identity = identity;
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

    public String getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(String bookmarked) {
        this.bookmarked = bookmarked;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public static List<UserDb> getAll() {
        return new Select()
                .from(UserDb.class)
                .execute();
    }
}
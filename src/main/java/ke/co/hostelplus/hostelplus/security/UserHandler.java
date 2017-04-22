package ke.co.hostelplus.hostelplus.security;

/**
 * Created by Badru on 4/7/2016.
 */
public interface  UserHandler {
    public void onResponse(String token, String username, String email, String id, boolean status, String info);
}

package ke.co.hostelplus.hostelplus.security;

import android.content.Context;
import android.text.TextUtils;

import com.activeandroid.query.Delete;

import java.util.List;

import ke.co.hostelplus.hostelplus.db.UserDb;


/**
 * Created by Badru on 4/17/2016.
 */
public class UserFunctions {
    Context context;

    public UserFunctions(Context context) {
        this.context = context;
    }
    public UserFunctions(){

    }

    public static boolean isLoggedIn(){
        if (!UserDb.getAll().isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean hasBookmark(){
       List<UserDb> db =UserDb.getAll();
        if(!TextUtils.isEmpty(db.get(0).getBookmarked())){
            return true;
        }

        return false;
    }
    public static void logout(){
        List<UserDb> db =UserDb.getAll();
        new Delete().from(UserDb.class).where("id = ?", db.get(0).getId()).execute();
    }


}

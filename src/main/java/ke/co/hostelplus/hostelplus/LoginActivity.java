package ke.co.hostelplus.hostelplus;

/**
 * Created by Badru on 03-08-2016.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.List;

import ke.co.hostelplus.hostelplus.db.UserDb;
import ke.co.hostelplus.hostelplus.security.UserHandler;
import ke.co.hostelplus.hostelplus.security.Users;


/**
 * Created by Badru on 4/5/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText  inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp , btnSignIn;
    TextView header;
    String url;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_layout);
        List<UserDb> db = UserDb.getAll();
        if (!db.isEmpty()) {
            Intent t = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(t);
        } else {

        }

        url=getResources().getString(R.string.baseurl)+getResources().getString(R.string.api);
        toolbar= (Toolbar) findViewById(R.id.holder);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Login");
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        header=(TextView) findViewById(R.id.txtheader);
        header.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_ios_locked_outline).color(getResources().getColor(R.color.red)).sizeDp(16), null, null, null);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignIn.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_log_in).color(getResources().getColor(R.color.white)).sizeDp(16), null, null, null);
        btnSignUp.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_edit).color(getResources().getColor(R.color.white)).sizeDp(16), null, null, null);
        inputEmail.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_person).color(getResources().getColor(R.color.gray)).sizeDp(14),null,null,null);

        inputPassword.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_locked).color(getResources().getColor(R.color.gray)).sizeDp(14), null, null, null);
        inputEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    inputEmail.setCompoundDrawables(new IconicsDrawable(getApplicationContext()).icon(Ionicons.Icon.ion_person).color(getResources().getColor(R.color.lightred)).sizeDp(14), null, null, null);
                }else{
                    inputEmail.setCompoundDrawables(new IconicsDrawable(getApplicationContext()).icon(Ionicons.Icon.ion_person).color(getResources().getColor(R.color.gray)).sizeDp(14),null,null,null);

                }
            }
        });
        inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    inputPassword.setCompoundDrawables(new IconicsDrawable(getApplicationContext()).icon(Ionicons.Icon.ion_locked).color(getResources().getColor(R.color.lightred)).sizeDp(14),null,null,null);
                }else{
                    inputPassword.setCompoundDrawables(new IconicsDrawable(getApplicationContext()).icon(Ionicons.Icon.ion_locked).color(getResources().getColor(R.color.gray)).sizeDp(14), null, null, null);

                }
            }
        });
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        final Loader l = new Loader();
        l.show(getSupportFragmentManager(),"Loader");
        fetchData(new String[]{inputEmail.getText().toString(), inputPassword.getText().toString()}, new UserHandler() {
            @Override
            public void onResponse(String token, String username, String email, String id, boolean status, String info) {
                l.dismiss();
                if (status) {
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                    List<UserDb> db = UserDb.getAll();
                    if (!db.isEmpty()) {
                        UserDb db2 = UserDb.load(UserDb.class, db.get(0).getId());
                        db2.setToken(token);
                        db2.setUsername(username);
                        db2.setEmail(email);
                        db2.setIdentity(id);
                        db2.save();

                    } else {
                        UserDb db3 = new UserDb();
                        db3.setToken(token);
                        db3.setUsername(username);
                        db3.setEmail(email);
                        db3.setIdentity(id);

                        db3.save();
                    }
                    Intent t = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(t);
                } else {
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                }
            }
        });

    }



    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
    public void fetchData(String[] string, final UserHandler listener) {

        Ion.with(getApplicationContext())
                .load(url + "token.json")
                        //.addHeader("Authorization","1234567")
                .setBodyParameter("email", string[0])
                .setBodyParameter("password", string[1])
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject json) {
                        if (e == null) {
                            if (!json.get("success").getAsBoolean()) {
                                listener.onResponse(null,null,null,null, json.get("success").getAsBoolean(), json.get("message").getAsString());
                            } else {

                                Users dta = new Gson().fromJson(json.get("data").getAsJsonObject(),Users.class);
                                listener.onResponse(dta.getToken(), dta.getUsername(), dta.getEmail(), dta.getId(), json.get("success").getAsBoolean(), json.get("message").getAsString());
                            }


                        } else {
                            listener.onResponse(null,null,null,null, false, "Error: "+e.getMessage());
                        }


                    }
                });
    }

}
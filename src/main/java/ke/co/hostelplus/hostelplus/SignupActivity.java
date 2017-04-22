package ke.co.hostelplus.hostelplus;

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
public class SignupActivity extends AppCompatActivity {

    private EditText  inputEmail, inputPassword,inputUsername;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutUsername;
    private Button btnSignUp, btnSignIn;
    TextView header;

    Toolbar toolbar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity_layout);

        url=getResources().getString(R.string.baseurl)+getResources().getString(R.string.api);

        inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        toolbar= (Toolbar) findViewById(R.id.holder);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Create Account");
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        header=(TextView) findViewById(R.id.txtheader);
       /* Typeface type = Typeface.createFromAsset(getAssets(), "fonts/walkaway_ultrabold.ttf");
        header.setTypeface(type);*/
        header.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_ios_locked_outline).color(getResources().getColor(R.color.red)).sizeDp(16), null, null, null);


        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        btnSignIn.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_log_in).color(getResources().getColor(R.color.white)).sizeDp(16), null, null, null);
        btnSignUp.setCompoundDrawables(new IconicsDrawable(this).icon(Ionicons.Icon.ion_edit).color(getResources().getColor(R.color.white)).sizeDp(16), null, null, null);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
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
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
    final Loader l = new Loader();
        l.show(getSupportFragmentManager(),"Loader");
        fetchData(new String[]{inputUsername.getText().toString(),  inputEmail.getText().toString(),inputPassword.getText().toString()}, new UserHandler() {
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
                    t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(t);

                } else {
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
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
    private boolean validateUsername() {
        if (inputUsername.getText().toString().trim().isEmpty()) {
            inputLayoutUsername.setError(getString(R.string.err_msg_username));
            requestFocus(inputUsername);
            return false;
        } else {
            inputLayoutUsername.setErrorEnabled(false);
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
                case R.id.input_username:
                    validateUsername();
                    break;
            }
        }
    }
    public void fetchData(String[] string, final UserHandler listener) {

        Ion.with(getApplicationContext())
                .load(url + "register.json")
                        //.addHeader("Authorization","1234567")
                .setBodyParameter("username", string[0])
                .setBodyParameter("email", string[1])
                .setBodyParameter("password", string[2])
                .setBodyParameter("role_id", "2")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject json) {
                        if (e == null) {

                            if (!json.get("success").getAsBoolean()) {
                                listener.onResponse(null,null,null,null, json.get("success").getAsBoolean(), json.get("message").getAsString());
                            } else {

                                Users dta = new Gson().fromJson(json.get("data").getAsJsonObject(),Users.class);
                                listener.onResponse(dta.getToken(),dta.getUsername(),dta.getEmail(),dta.getId(),json.get("success").getAsBoolean(), json.get("message").getAsString());
                            }


                        } else {
                            listener.onResponse(null,null,null,null, true, "Error: "+ e.getMessage());
                        }


                    }
                });
    }

}
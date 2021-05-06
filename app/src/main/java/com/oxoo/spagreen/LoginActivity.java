package com.oxoo.spagreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.network.RetrofitClient;
import com.oxoo.spagreen.network.apis.FirebaseAuthApi;
import com.oxoo.spagreen.network.apis.LoginApi;
import com.oxoo.spagreen.network.apis.SubscriptionApi;
import com.oxoo.spagreen.network.model.ActiveStatus;
import com.oxoo.spagreen.network.model.User;
import com.oxoo.spagreen.utils.ApiResources;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.RtlUtils;
import com.oxoo.spagreen.utils.ToastMsg;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    FirebaseAuth firebaseAuth;
    private static int RC_PHONE_SIGN_IN = 123;
    private static int RC_FACEBOOK_SIGN_IN = 124;
    private static int RC_GOOGLE_SIGN_IN = 125;
    private EditText etEmail,etPass;
    private TextView tvSignUp,tvReset;
    private Button btnLogin;
    private ProgressDialog dialog;
    private View backgroundView;
    private RelativeLayout mainScreenBg;
    private CardView cardView;
    private ProgressBar progressBar;
    private ImageView phoneAuthButton, facebookAuthButton, googleAuthButton;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (!isDark) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black_window_light));
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(LoginActivity.this);

        //---analytics-----------
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "login_activity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);

        etEmail             = findViewById(R.id.email);
        etPass              = findViewById(R.id.password);
        tvSignUp            = findViewById(R.id.signup);
        btnLogin            = findViewById(R.id.signin);
        tvReset             = findViewById(R.id.reset_pass);
        backgroundView      = findViewById(R.id.background_view);
        cardView            = findViewById(R.id.card_view);
        progressBar         = findViewById(R.id.progress_bar);
        phoneAuthButton     = findViewById(R.id.phoneAuthButton);
        facebookAuthButton  = findViewById(R.id.facebookAuthButton);
        googleAuthButton    = findViewById(R.id.googleAuthButton);

        if (AppConfig.ENABLE_FACEBOOK_LOGIN) {
            facebookAuthButton.setVisibility(View.VISIBLE);
        }
        if (AppConfig.ENABLE_GOOGLE_LOGIN) {
            googleAuthButton.setVisibility(View.VISIBLE);
        }
        if (AppConfig.ENABLE_PHONE_LOGIN) {
            phoneAuthButton.setVisibility(View.VISIBLE);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        if (isDark) {
            backgroundView.setBackgroundColor(getResources().getColor(R.color.nav_head_bg));
            btnLogin.setBackground(ContextCompat.getDrawable( this, R.drawable.login_field_button_dark));
            cardView.setCardBackgroundColor(getResources().getColor(R.color.black));

        }

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PassResetActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isValidEmailAddress(etEmail.getText().toString())){
                    new ToastMsg(LoginActivity.this).toastIconError("Please enter valid email");
                }
                else if(etPass.getText().toString().equals("")){
                    new ToastMsg(LoginActivity.this).toastIconError("Please enter password");
                }else {
                    String email = etEmail.getText().toString();
                    String pass = etPass.getText().toString();
                    login(email,pass);
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }

        });

        phoneAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneSignIn();
            }
        });

        facebookAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignIn();
            }
        });

        googleAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

    }


    private void login(String email, final String password){
        btnLogin.setClickable(false);
        dialog.show();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        LoginApi api = retrofit.create(LoginApi.class);
        Call<User> call = api.postLoginStatus(AppConfig.API_KEY, email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                        db.deleteUserData();
                        db.insertUserData(user);
                        ApiResources.USER_PHONE = user.getPhone();

                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();
                        preferences.commit();

                        //save user login time, expire time
                        updateSubscriptionStatus(db.getUserData().getUserId());
                    }else {
                        new ToastMsg(LoginActivity.this).toastIconError(response.body().getData());
                        dialog.dismiss();
                        btnLogin.setClickable(true);
                    }
                }else {
                    dialog.cancel();
                    new ToastMsg(LoginActivity.this).toastIconError(getString(R.string.error_toast));
                    btnLogin.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.cancel();
                new ToastMsg(LoginActivity.this).toastIconError(getString(R.string.error_toast));
                btnLogin.setClickable(true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void updateSubscriptionStatus(String userId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);

        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(AppConfig.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(Call<ActiveStatus> call, Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        ActiveStatus activeStatus = response.body();

                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                        db.deleteAllActiveStatusData();
                        db.insertActiveStatusData(activeStatus);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        finish();
                        dialog.cancel();
                    }
                }
            }
            @Override
            public void onFailure(Call<ActiveStatus> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*social login related task*/

    private void phoneSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseAuth.getCurrentUser() != null) {
            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()) {
                final String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                //already signed in
                if (!phoneNumber.isEmpty())
                    sendDataToServer();
            }

        } else {
            progressBar.setVisibility(View.GONE);
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_PHONE_SIGN_IN);
        }
    }

    private void facebookSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseAuth.getCurrentUser() != null) {
            if (!FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()) {
                //already signed in
                //send data to server
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                sendFacebookDataToServer(user.getDisplayName(), String.valueOf(user.getPhotoUrl()), user.getEmail());

            }

        } else {
            progressBar.setVisibility(View.GONE);
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.FacebookBuilder()
                            //.setPermissions(Arrays.asList("email", "default"))
                            .build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_FACEBOOK_SIGN_IN);
        }
    }

    private void googleSignIn() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            sendGoogleDataToServer();

        } else {
            progressBar.setVisibility(View.GONE);
            // Choose authentication providers
            GoogleSignInOptions googleOptions = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestProfile()
                    .build();


            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(googleOptions).build());

           /* List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build());*/

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_GOOGLE_SIGN_IN);
        }
    }

    private void sendDataToServer() {
        progressBar.setVisibility(View.VISIBLE);
        String phoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FirebaseAuthApi api = retrofit.create(FirebaseAuthApi.class);
        Call<User> call = api.getPhoneAuthStatus(AppConfig.API_KEY, uid, phoneNo);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("success")) {

                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                        db.deleteUserData();
                        db.insertUserData(user);
                        ApiResources.USER_PHONE = user.getPhone();

                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();
                        preferences.commit();
                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                phoneSignIn();
            }
        });


    }

    private void sendGoogleDataToServer() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getDisplayName();
        String email = user.getEmail();
        Uri image = user.getPhotoUrl();
        String uid = user.getUid();
        String phone = "";
        if (user.getPhoneNumber() != null)
            phone = user.getPhoneNumber();

        Log.d(TAG, "onActivityResult: " + user.getEmail());
        Log.d(TAG, "onActivityResult: " + user.getDisplayName());
        Log.d(TAG, "onActivityResult: " + user.getPhoneNumber());

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FirebaseAuthApi api = retrofit.create(FirebaseAuthApi.class);
        Call<User> call = api.getGoogleAuthStatus(AppConfig.API_KEY, uid, email, username, image, phone);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("success")) {
                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                        db.deleteUserData();
                        db.insertUserData(user);
                        ApiResources.USER_PHONE = user.getPhone();

                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();
                        preferences.commit();

                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                googleSignIn();
            }
        });
    }

    private void sendFacebookDataToServer(String username, String photoUrl, String email) {
        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        FirebaseAuthApi api = retrofit.create(FirebaseAuthApi.class);
        Call<User> call = api.getFacebookAuthStatus(AppConfig.API_KEY, uid, username, email, Uri.parse(photoUrl));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("success")) {

                        User user = response.body();
                        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                        db.deleteUserData();
                        db.insertUserData(user);
                        ApiResources.USER_PHONE = user.getPhone();

                        SharedPreferences.Editor preferences = getSharedPreferences(Constants.USER_LOGIN_STATUS, MODE_PRIVATE).edit();
                        preferences.putBoolean(Constants.USER_LOGIN_STATUS, true);
                        preferences.apply();
                        preferences.commit();

                        //save user login time, expire time
                        updateSubscriptionStatus(user.getUserId());
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                facebookSignIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHONE_SIGN_IN) {

            final IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!user.getPhoneNumber().isEmpty()) {
                    sendDataToServer();
                } else {
                    //empty
                    phoneSignIn();
                }
            } else {
                // sign in failed
                if (response == null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        } else if (requestCode == RC_FACEBOOK_SIGN_IN) {
            final IdpResponse response = com.firebase.ui.auth.IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!user.getUid().isEmpty()) {
                    String username = user.getDisplayName();
                    String photoUrl = String.valueOf(user.getPhotoUrl());
                    String email = user.getEmail();

                    sendFacebookDataToServer(username, photoUrl, email);

                } else {
                    //empty
                    facebookSignIn();
                }
            } else {
                // sign in failed
                if (response == null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == RC_GOOGLE_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!user.getUid().isEmpty()) {
                    sendGoogleDataToServer();

                } else {
                    //empty
                    googleSignIn();
                }
            } else {
                // sign in failed
                if (response == null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    }


}

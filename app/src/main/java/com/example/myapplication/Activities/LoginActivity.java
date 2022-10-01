package com.example.myapplication.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    public LinearLayout onPressToRegister;
    public EditText edtPhoneNumber, edtPassword;
    public Button btnLogin;
    private ImageFilterButton ifbFbLogin, ifbSMSLogin,ifbGoogleLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions googleSignInOptions;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Toast.makeText(LoginActivity.this, ""+result.getResultCode(), Toast.LENGTH_SHORT).show();
                        if (result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleSignInResult(task);
                        }
                    }
                });

        Boolean isLogin = AppHelper.AppCheck.getInstance(LoginActivity.this).getLocalStorageManager().isUserLogin();
        callbackManager = CallbackManager.Factory.create();

                if (isLogin){
            startActivity(new Intent(LoginActivity.this , MainActivity.class));
            finish();
        }


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("==>", exception + "");
                    }
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
        onPressToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        ifbFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });
        ifbSMSLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SMSLoginActivity.class));

            }
        });
        ifbGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(i);
            }
        });

    }

    public void initUI() {
        onPressToRegister = findViewById(R.id.onPressToRegister);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumberLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        ifbFbLogin = findViewById(R.id.ifbFbLogin);
        ifbSMSLogin = findViewById(R.id.ifbSMSLogin);
        ifbGoogleLogin = findViewById(R.id.ifbGoogleLogin);
    }

    public void performLogin() {
        String phoneNumber = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();

        if (AppHelper.ValidateInput.isEmpty(phoneNumber) || AppHelper.ValidateInput.isEmpty(password)) {
            Toast.makeText(this, "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
        } else {
            User account = new User(
                    phoneNumber, null, password, null, null, null, 1, 1
            );

            AppHelper.AppCheck.getInstance(LoginActivity.this).getLocalStorageManager().setUserPhoneNumber(phoneNumber);
            ApiController.ApiService.getService(getApplicationContext()).check_login(account).enqueue(ApiCallback.getCheckLogin(LoginActivity.this));

        }


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("=====>", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}
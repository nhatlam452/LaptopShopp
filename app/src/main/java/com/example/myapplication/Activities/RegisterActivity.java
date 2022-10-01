package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private LinearLayout onPressToLogin;
    public Button btRegister;
    private TextInputEditText edtPhoneNumber, edtPassword, edtConfirmPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignIn();
            }
        });
        onPressToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void initUI() {
        onPressToLogin = findViewById(R.id.onPressToLogin);
        btRegister = findViewById(R.id.btnRegister);
        edtPhoneNumber = findViewById(R.id.edtPhonenumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void performSignIn() {
        String phoneNumber =edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();


        if (AppHelper.ValidateInput.isEmpty(phoneNumber) || AppHelper.ValidateInput.isEmpty(password) || AppHelper.ValidateInput.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
        } else if (!AppHelper.ValidateInput.isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "Sai dinh dang so dien thoai.!!!", Toast.LENGTH_SHORT).show();
        } else if (!password.trim().equals(confirmPassword)) {
            Toast.makeText(this, "ConfirmPasswod ko dung", Toast.LENGTH_SHORT).show();
        } else {
//            User account = new User(
//                    phoneNumber,null,password,null,null,null,1,1
//            );
//            ApiController.ApiService.getService(getApplicationContext()).insert_account(account).enqueue(ApiCallback.registerAccount(RegisterActivity.this));
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber("+84"+phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            signInWithPhoneAuthCredential(phoneAuthCredential);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Log.d("=====>", e + "");
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(verificationId, forceResendingToken);
                            Intent i = new Intent(RegisterActivity.this, OtpVerifyActivity.class);
                            i.putExtra("phonenumber", phoneNumber);
                            i.putExtra("password", password);
                            i.putExtra("verificationId", verificationId);
                            startActivity(i);
                        }
                    })
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);


        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            Log.d("====>", task.getException() + "");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(RegisterActivity.this, "The OTP is invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
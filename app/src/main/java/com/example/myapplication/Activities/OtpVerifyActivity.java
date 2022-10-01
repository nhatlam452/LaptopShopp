package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controllers.ApiCallback;
import com.example.myapplication.Controllers.ApiController;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerifyActivity extends AppCompatActivity {
    TextView tvCountDown, tvResendOtp;
    ImageView imgTimer;
    FirebaseAuth mAuth;
    String phonenumber, password, verificationId;
    Button btnVerifiy;
    EditText edtOTP1, edtOTP2, edtOTP3, edtOTP4, edtOTP5, edtOTP6;
    Boolean isSMSLogin = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        initUI();

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(millisUntilFinished / 1000 + " s ");
            }

            @Override
            public void onFinish() {

                tvCountDown.setText("Khong nhan duoc ma ? ");
                tvResendOtp.setVisibility(View.VISIBLE);
                imgTimer.setVisibility(View.GONE);
                tvResendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendOtp();
                    }
                });
            }
        }.start();

        btnVerifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = edtOTP1.getText().toString() + edtOTP2.getText().toString() + edtOTP3.getText().toString() + edtOTP4.getText().toString() + edtOTP5.getText().toString() + edtOTP6.getText().toString();
                if (OTP.isEmpty())
                    Toast.makeText(getApplicationContext(), "Blank Field can not be processed", Toast.LENGTH_LONG).show();
                else if (OTP.length() != 6)
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void initUI() {
        tvCountDown = findViewById(R.id.tvCountDown);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        mAuth = FirebaseAuth.getInstance();
        imgTimer = findViewById(R.id.imgTimer);
        btnVerifiy = findViewById(R.id.btnVerifiy);
        edtOTP1 = findViewById(R.id.edtOTP1);
        edtOTP2 = findViewById(R.id.edtOTP2);
        edtOTP3 = findViewById(R.id.edtOTP3);
        edtOTP4 = findViewById(R.id.edtOTP4);
        edtOTP5 = findViewById(R.id.edtOTP5);
        edtOTP6 = findViewById(R.id.edtOTP6);
        edtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Intent i = getIntent();
        password = i.getStringExtra("password");
        phonenumber = i.getStringExtra("phonenumber");
        verificationId = i.getStringExtra("verificationId");
    }

    private void sendOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("84"+phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.d("====>", e + "");
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            if (isSMSLogin == false){
                                User account = new User(
                                        phonenumber, null, password, null, null, null, 1, 1
                                );
                                ApiController.ApiService.getService(getApplicationContext()).insert_account(account).enqueue(ApiCallback.registerAccount(OtpVerifyActivity.this));

                            }
                            Log.d("=====>","Dang nhap thanh cong");

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("========>", "signInWithCredential:failure", task.getException());

                        }
                    }
                });


    }
}
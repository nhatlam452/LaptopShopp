package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utilities.AppHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SMSLoginActivity extends AppCompatActivity {
    Button btnSMSLogin;
    EditText edtSMSLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslogin);
        btnSMSLogin = findViewById(R.id.btnSMSLogin);
        edtSMSLogin = findViewById(R.id.edtSMSLogin);
        mAuth = FirebaseAuth.getInstance();


        btnSMSLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtSMSLogin.getText().toString().trim();
                Toast.makeText(SMSLoginActivity.this, ""+phoneNumber, Toast.LENGTH_SHORT).show();
                if (AppHelper.ValidateInput.isEmpty(phoneNumber)) {
                    Toast.makeText(SMSLoginActivity.this, "Khong duoc de trong thong tin", Toast.LENGTH_SHORT).show();
                } else if (!AppHelper.ValidateInput.isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(SMSLoginActivity.this, "Sai dinh dang so dien thoai.!!!", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+84"+phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(SMSLoginActivity.this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted( PhoneAuthCredential phoneAuthCredential) {
                                    signInWithPhoneAuthCredential(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed( FirebaseException e) {
                                    Log.d("=====>", e + "");
                                }

                                @Override
                                public void onCodeSent( String verificationId,  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    Intent i = new Intent(SMSLoginActivity.this, OtpVerifyActivity.class);
                                    i.putExtra("phonenumber", phoneNumber);
                                    i.putExtra("SMSLogin",true);
                                    i.putExtra("verificationId", verificationId);
                                    startActivity(i);
                                }
                            })
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);


                }

            }
        });


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SMSLoginActivity.this, MainActivity.class));
                        } else {
                            Log.d("====>", task.getException() + "");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SMSLoginActivity.this, "The OTP is invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
/*
*
*
*
*   PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
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
}*/
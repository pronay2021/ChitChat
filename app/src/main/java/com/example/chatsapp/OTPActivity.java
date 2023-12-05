package com.example.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    String verifiedOTP;
    ProgressDialog dialog;
    EditText Otp;
    Button next;
    TextView textView;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        Otp = findViewById(R.id.otpView);
        next = findViewById(R.id.next);
        textView = findViewById(R.id.phnLevel);


        mAuth = FirebaseAuth.getInstance();

        phoneNumber = getIntent().getStringExtra("phoneNum");
        textView.setText("Verify " + phoneNumber);
        otpAction();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Otp.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field Can Not Be Process",Toast.LENGTH_LONG).show();
                else if(Otp.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verifiedOTP,Otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void otpAction()
    {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        dialog.dismiss();
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                        Toast.makeText(getApplicationContext(), "Both are Together Verification Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                         super.onCodeSent(s, forceResendingToken);
                        Toast.makeText(getApplicationContext(), "Code send to your device", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        verifiedOTP = s;

                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(OTPActivity.this,profileActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Verification Successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign in Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
package com.example.chatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class phoneNumberActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText phoneNum;
    Button btn1;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(phoneNumberActivity.this,ChatListActivity.class);
            startActivity(intent);
            finish();
        }

        phoneNum=findViewById(R.id.PhoneNum);
        ccp=findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneNum);
        btn1=findViewById(R.id.Continue);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(phoneNumberActivity.this,OTPActivity.class);
                intent.putExtra("phoneNum",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });


    }
}
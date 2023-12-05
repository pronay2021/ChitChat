package com.example.chatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatsapp.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;



public class UserProfileActivity extends AppCompatActivity {

   ActivityUserProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase root;
    DatabaseReference reference;



    Uri selectedImage;
    FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
    String name,phoneNumber,imageUrl,profileUrl,count;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle exteas =getIntent().getExtras();
        name=exteas.getString("name");
        phoneNumber=exteas.getString("phnNum");
        imageUrl=exteas.getString("image");
        profileUrl=exteas.getString("profileUrl");
        count=exteas.getString("friendNumber");
        num=Integer.parseInt(count);
        num=num+1;

        selectedImage=Uri.parse(imageUrl);

        root  = FirebaseDatabase.getInstance();
        reference = root.getReference().child("users");

        binding.profilename.setText(name);
        binding.phoneNumber.setText(phoneNumber);
        if(imageUrl!="No Image"){
            Picasso.get().load(imageUrl).into(binding.userImage);
        }


        binding.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(user.getUid()).child("FriendList").child(num+"").setValue(profileUrl);
                reference.child(user.getUid()).child("numberOfFriend").setValue(num+"");
                Toast.makeText(getApplicationContext(),name+" add as your friend",Toast.LENGTH_LONG).show();

            }
        });

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,AddFriendActivity.class);
                startActivity(intent);
            }
        });

    }
}
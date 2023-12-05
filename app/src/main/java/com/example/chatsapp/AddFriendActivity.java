package com.example.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.chatsapp.databinding.ActivityAddFriendBinding;
import com.example.chatsapp.databinding.RowforaddingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    FirebaseDatabase database;
    ArrayList<User> users;
    ActivityAddFriendBinding binding;
    userAdapter userAdapter;
    FirebaseUser user;
    FirebaseAuth auth;
    RowforaddingBinding rowforaddingBinding;
    RecyclerView recyclerView;
    private userAdapter.RecyclerViewClickListener listener;
    String counter;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityAddFriendBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            database=FirebaseDatabase.getInstance();
            auth= FirebaseAuth.getInstance();
            recyclerView = findViewById(R.id.recyclerView);
            user=FirebaseAuth.getInstance().getCurrentUser();



            database.getReference().child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                    counter=String.valueOf(snapshot.child("numberOfFriend").getValue());
                    Toast.makeText(getApplicationContext(),counter,Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                }
            });

            users=new ArrayList<>();
            listener = new userAdapter.RecyclerViewClickListener() {
                @Override
                public void onClick(View v, int position) {
                    // Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(AddFriendActivity.this,UserProfileActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("name",users.get(position).getName());
                    extras.putString("profileUrl",users.get(position).getUserID());
                    extras.putString("phnNum",users.get(position).getPhoneNumber());
                    extras.putString("image",users.get(position).getImageUrl());
                    extras.putString("friendNumber",counter);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            };
            userAdapter=new userAdapter(this,users,listener);
            binding.recyclerView.setAdapter(userAdapter);

            database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    users.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {

                        User user=snapshot1.getValue(User.class);
                        users.add(user);

                    }
                    userAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }

            });
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.topmenu,menu);
            return super.onCreateOptionsMenu(menu);
        }






}

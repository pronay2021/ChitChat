package com.example.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatsapp.databinding.ActivityChatListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    FirebaseDatabase database;
    ArrayList<User>myFriend1;
    ActivityChatListBinding binding;
    friendAdapter friendAdapter;
    FirebaseAuth auth;
    ArrayList<String> addeduids;
    FirebaseUser user;
    String counter,frienduid;
    int num,i,j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        myFriend1=new ArrayList<>();
        binding.recyclerView.setAdapter(friendAdapter);

        database.getReference().child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                counter=String.valueOf(snapshot.child("numberOfFriend").getValue());
                num=Integer.parseInt(counter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
//ok


    readUids();
    }

    private void readUids() {

        database.getReference().child("users").child(user.getUid()).child("FriendList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                addeduids.clear();
                //problem in this loop
                
                for(i=1;i<=num;i++)
                {
                    frienduid=String.valueOf(snapshot.child(String.valueOf(i)).getValue());
                    addeduids.add(frienduid);
                }
                Toast.makeText(getApplicationContext(),"ok upto this",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addfriend)
        {
            Intent intent= new Intent(ChatListActivity.this,AddFriendActivity.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.search)
        {
            Toast.makeText(getApplicationContext(),"Search Button Clicked",Toast.LENGTH_LONG).show();
        }
        if(item.getItemId()==R.id.settings)
        {
            Toast.makeText(getApplicationContext(),"Settings Button Clicked",Toast.LENGTH_LONG).show();
        }
        if(item.getItemId()==R.id.logout)
        {
            auth.signOut();
            Intent intent =new Intent(ChatListActivity.this,phoneNumberActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
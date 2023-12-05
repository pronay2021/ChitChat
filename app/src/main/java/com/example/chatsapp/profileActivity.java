package com.example.chatsapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    EditText name;
    Button saveBtn;
    CircleImageView profilePic;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
   String imageUrl;

   ProgressDialog dialog,dialog1;

   String uid,username,phone,numOfFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.name);
        saveBtn= findViewById(R.id.save);
        profilePic=findViewById(R.id.profilePicture);

        database= FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        dialog= new ProgressDialog(this);
        dialog.setMessage("Updating Profile...");
        dialog.setCancelable(false);

        dialog1= new ProgressDialog(this);
        dialog1.setMessage("Uploading Image...");
        dialog1.setCancelable(false);


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Please Enter Your Name");
                    return;
                }
                dialog.show();
                if (selectedImage != null) {
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());//Profile is fouldar name and aurh.getUid is file name
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //onSuccess uri is the link of the user profile
                                        imageUrl = uri.toString();
                                        uid = auth.getUid();
                                        phone = auth.getCurrentUser().getPhoneNumber();
                                        username = name.getText().toString();
                                        numOfFriend="0";

                                        User user = new User(uid, username, phone, imageUrl,numOfFriend);
                                        database.getReference()
                                                .child("users")
                                                .child(uid) //with out it profile data willbe replace by new user data
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(profileActivity.this, ChatListActivity.class);
                                                        startActivity(intent);
                                                        finishAffinity();

                                                    }
                                                });

                                    }
                                });
                            }
                        }
                    });
                } else
                    {


                        uid = auth.getUid();
                        phone = auth.getCurrentUser().getPhoneNumber();
                        username = name.getText().toString();

                        User user = new User(uid, username, phone, "No Image",numOfFriend);
                        database.getReference()
                                .child("users")
                                .child(uid) //with out it profile data willbe replace by new user data
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(profileActivity.this, ChatListActivity.class);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                });

                    }
            }
        });
    }
    ActivityResultLauncher<String> mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result!=null)
                    {
                        selectedImage=result;
                        dialog1.show();
                        uploadImage();
                    }
                }
            }
    );

    private void uploadImage(){
        if(selectedImage != null)
        {
            StorageReference reference=storage.getReference().child("image/"+ UUID.randomUUID().toString());
            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        profilePic.setImageURI(selectedImage);
                        Toast.makeText(getApplicationContext(),"Image Uploded Successfully",Toast.LENGTH_LONG).show();
                        dialog1.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Image is not Uploded Successfully",Toast.LENGTH_LONG).show();
                        dialog1.dismiss();
                    }
                }
            });
        }
    }


}
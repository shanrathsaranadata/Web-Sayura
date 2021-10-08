package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.websayuraapp.websayura.Prevalite.Prevalite;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileimage;
    private TextView editprofile,usernameprofile;
    private EditText fullnameet,addresset,emailet,mobilenoet;
    private Button save;

    private Uri imageuri;
    private String myuri="";
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private String checker="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        profileimage =(CircleImageView) findViewById(R.id.profile_image);
        editprofile =(TextView) findViewById(R.id.profile_imageedit);
        usernameprofile =(TextView) findViewById(R.id.profile_imageusername);
        fullnameet=(EditText) findViewById(R.id.profile_name);
        emailet=(EditText) findViewById(R.id.profile_email);
        addresset=(EditText) findViewById(R.id.profile_address);
        mobilenoet=(EditText) findViewById(R.id.profile_mobileno);
        save=(Button) findViewById(R.id.profile_save);

        usernameprofile.setText("Hi there "+Prevalite.CurrentonlineUsers.getUsername());
        userInfoDisplay();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){

                    userInfosaved();

                }
                else {
                    onlyuserInfo();
                }
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker ="clicked";
                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(ProfileActivity.this);
            }
        });



        FloatingActionButton fab = findViewById(R.id.home_floabtnprofile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationprofile);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(ProfileActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(ProfileActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();;
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){

            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            imageuri = result.getUri();
            profileimage.setImageURI(imageuri);

        }
        else{
            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
            finish();
        }
    }

    private void onlyuserInfo() {

        String usertypekey =Paper.book().read(Prevalite.Usertypekey);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(usertypekey);

        HashMap<String,Object> usermap = new HashMap<>();
        usermap.put("name",fullnameet.getText().toString());
        usermap.put("email",emailet.getText().toString());
        usermap.put("address",addresset.getText().toString());
        usermap.put("phone",mobilenoet.getText().toString());
        reference.child(Prevalite.CurrentonlineUsers.getUsername()).updateChildren(usermap);

        Toast.makeText(ProfileActivity.this, "Profile Info Update is Complete!", Toast.LENGTH_SHORT).show();
    }

    private void userInfosaved() {

        if(TextUtils.isEmpty(fullnameet.getText().toString())){

            Toast.makeText(this, "Name is Empty...", Toast.LENGTH_SHORT).show();

        }


        else if(TextUtils.isEmpty(emailet.getText().toString())){

            Toast.makeText(this, "Email is Empty...", Toast.LENGTH_SHORT).show();

        }


        else if(TextUtils.isEmpty(addresset.getText().toString())){

            Toast.makeText(this, "Address is Empty...", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(mobilenoet.getText().toString())){

            Toast.makeText(this, "Mobileno is Empty...", Toast.LENGTH_SHORT).show();

        }

        else if(checker.equals("clicked")){

            uploadImage();

        }
    }

    private void uploadImage() {

        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait,while we are updating your account info");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageuri != null){

            final StorageReference fileref = storageReference
                    .child(Prevalite.CurrentonlineUsers.getUsername()+".jpg");

            uploadTask =fileref.putFile(imageuri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }


                    return fileref.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if(task.isSuccessful()){

                                Uri downloadUri =task.getResult();
                                myuri =downloadUri.toString();

                                String usertypekey =Paper.book().read(Prevalite.Usertypekey);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(usertypekey);

                                HashMap<String,Object>usermap = new HashMap<>();
                                usermap.put("name",fullnameet.getText().toString());
                                usermap.put("email",emailet.getText().toString());
                                usermap.put("address",addresset.getText().toString());
                                usermap.put("phone",mobilenoet.getText().toString());
                                usermap.put("image",myuri);

                                reference.child(Prevalite.CurrentonlineUsers.getUsername()).updateChildren(usermap);

                                progressDialog.dismiss();

                                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
                                Toast.makeText(ProfileActivity.this, "Profile Info Update is Sucess!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
        else{
            Toast.makeText(this, "Image is Null", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDisplay() {

        String usertypekey =Paper.book().read(Prevalite.Usertypekey);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child(usertypekey).child(Prevalite.CurrentonlineUsers.getUsername());

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("image").exists()){

                        String image =dataSnapshot.child("image").getValue().toString();
                        String name =dataSnapshot.child("name").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();
                        String address =dataSnapshot.child("address").getValue().toString();
                        String email =dataSnapshot.child("email").getValue().toString();

                        Picasso.get().load(image).into(profileimage);
                        fullnameet.setText(name);
                        emailet.setText(email);
                        addresset.setText(address);
                        mobilenoet.setText(phone);
                    }
                    else if(dataSnapshot.child("name").exists()){

                        String name =dataSnapshot.child("name").getValue().toString();
                        String email =dataSnapshot.child("email").getValue().toString();

                        fullnameet.setText(name);
                        emailet.setText(email);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
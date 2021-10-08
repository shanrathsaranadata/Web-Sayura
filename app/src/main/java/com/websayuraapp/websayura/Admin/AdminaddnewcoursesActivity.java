package com.websayuraapp.websayura.Admin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class AdminaddnewcoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ImageView ancimage;
    private EditText ancdescription,ancduration,ancenrolled,ancfee,anclevel,ancname;
    private Button addnewcoursesbtn;
    private String slectedtype;
    private Spinner courses_spinner;
    private ProgressDialog lodingbar;
    private static final int Gallerypick =1;
    private Uri imageuri;
    private StorageReference coursesImageref;
    private DatabaseReference coursesref;
    private String coursesname,coursesenrolled,courseslevel,coursesfee,coursesduration,coursesdescription,downloadimageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddnewcourses);

        coursesImageref= FirebaseStorage.getInstance().getReference().child("Courses Image");
        coursesref= FirebaseDatabase.getInstance().getReference().child("Courses");

        ancimage = findViewById(R.id.addnewcourse_coursesimage);
        ancdescription = findViewById(R.id.addnewcourse_coursesdescription);
        ancduration = findViewById(R.id.addnewcourse_coursesduration);
        ancenrolled = findViewById(R.id.addnewcourse_coursesenrolled);
        ancfee = findViewById(R.id.addnewcourse_coursesfee);
        anclevel = findViewById(R.id.addnewcourse_courseslevel);
        ancname = findViewById(R.id.addnewcourse_coursesname);
        addnewcoursesbtn = findViewById(R.id.addnewcourse_coursebtn);

        courses_spinner= findViewById(R.id.addnewcoursesid_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Courses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courses_spinner.setAdapter(adapter);
        courses_spinner.setOnItemSelectedListener(this);

        lodingbar=new ProgressDialog(this);

        ancimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addnewcoursesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Valitadedata();

            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnadmin_addnewuser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdminaddnewcoursesActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsadmin_addnewuser);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(AdminaddnewcoursesActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(AdminaddnewcoursesActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(AdminaddnewcoursesActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(AdminaddnewcoursesActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(AdminaddnewcoursesActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }


    private void openGallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,Gallerypick);
    }

    private void Valitadedata(){

        coursesname  = ancname.getText().toString();
        coursesenrolled = ancenrolled.getText().toString();
        courseslevel= anclevel.getText().toString();
        coursesfee = ancfee.getText().toString();
        coursesduration = ancduration.getText().toString();
        coursesdescription = ancdescription.getText().toString();

        if(imageuri==null){
            Toast.makeText(this, "courses image is null", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(coursesname)){
            Toast.makeText(this, "Please write courses name.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(coursesenrolled)){
            Toast.makeText(this, "Please write courses enrolled.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(courseslevel)){
            Toast.makeText(this, "Please write your courses level.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(coursesfee)){
            Toast.makeText(this, "Please write courses fee.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(coursesduration)){
            Toast.makeText(this, "Please write courses duration.....", Toast.LENGTH_SHORT).show();
        }
        else if(slectedtype.equals("Select Courses Type")){

            Toast.makeText(this, "Please Select Courses Type.....", Toast.LENGTH_SHORT).show();
        }
        else{
            storeproductimageinformation();
        }


    }

    private void storeproductimageinformation() {

        lodingbar.setTitle("Adding new courses");
        lodingbar.setMessage("Dear Seller, please wait,while we are adding the new courses");
        lodingbar.setCanceledOnTouchOutside(false);
        lodingbar.show();

        final StorageReference filepath = coursesImageref.child(imageuri.getLastPathSegment() + coursesname +".jpg");
        final UploadTask uploadTask=filepath.putFile(imageuri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String massage = e.toString();
                Toast.makeText(AdminaddnewcoursesActivity.this, massage, Toast.LENGTH_SHORT).show();
                lodingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminaddnewcoursesActivity.this, "Upload are Successful", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
                        downloadimageurl = filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadimageurl=task.getResult().toString();
                            Toast.makeText(AdminaddnewcoursesActivity.this, "Courses image save to Database Successfully", Toast.LENGTH_SHORT).show();
                            saveproductinfotodatabase();
                        }

                    }
                });
            }
        });

    }

    private void saveproductinfotodatabase(){

        HashMap<String,Object> coursesmap = new HashMap<>();

        coursesmap.put("description",coursesdescription);
        coursesmap.put("duration",coursesduration);
        coursesmap.put("enrolled",coursesenrolled);
        coursesmap.put("fee",coursesfee);
        coursesmap.put("id",slectedtype);
        coursesmap.put("image",downloadimageurl);
        coursesmap.put("level",courseslevel);
        coursesmap.put("name",coursesname);

        coursesref.child(coursesname).updateChildren(coursesmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Intent intent =new Intent(AdminaddnewcoursesActivity.this, AdminmaintaincoursesActivity.class);
                            startActivity(intent);
                            lodingbar.dismiss();
                            Toast.makeText(AdminaddnewcoursesActivity.this, "Courses is adding succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            lodingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminaddnewcoursesActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallerypick && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
            ancimage.setImageURI(imageuri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectedtype=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, slectedtype, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
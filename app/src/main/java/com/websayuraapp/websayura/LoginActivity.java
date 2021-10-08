package com.websayuraapp.websayura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.websayuraapp.websayura.Admin.AdminHomeActivity;
import com.websayuraapp.websayura.Admin.AdmindashboardActivity;
import com.websayuraapp.websayura.Lecturers.LecturersHomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersdashboardActivity;
import com.websayuraapp.websayura.Model.Users;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.Students.StudentsHomeActivity;
import com.websayuraapp.websayura.Students.StudentsdashboardActivity;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText usernameet,passwordet;
    private Button login_btn;
    private Spinner login_spinner;
    private String slectedtype;
    private CheckBox remberme;
    private ProgressDialog lodingbar;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameet = findViewById(R.id.usrname_login);
        passwordet = findViewById(R.id.pasword_login);
        login_btn= findViewById(R.id.login_btn);
        remberme = findViewById(R.id.rember_chk);

        login_spinner= findViewById(R.id.login_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Login, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        login_spinner.setAdapter(adapter);

        login_spinner.setOnItemSelectedListener(this);
        lodingbar=new ProgressDialog(this);
        Paper.init(this);

        FloatingActionButton fab = findViewById(R.id.home_floabtnlogin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationlogin);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(LoginActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(LoginActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginuser();

            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectedtype=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, slectedtype, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        String usernamekey=Paper.book().read(Prevalite.Userusernamekey);
        String userpasswordkey =Paper.book().read(Prevalite.Userpasswordkey);
        String usertypekey =Paper.book().read(Prevalite.Usertypekey);

        if(!TextUtils.isEmpty(usernamekey) && !TextUtils.isEmpty(userpasswordkey) && !TextUtils.isEmpty(usertypekey)){

                AloowAcessToaccountAuto(usernamekey,userpasswordkey,usertypekey);
                lodingbar.setTitle("Already Logged in");
                lodingbar.setMessage("please wait...");
                lodingbar.setCanceledOnTouchOutside(false);
                lodingbar.show();
            }

    }



    private void loginuser() {

        String username = usernameet.getText().toString();
        String password = passwordet.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please write your username.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password.....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(slectedtype) || slectedtype.equals("Select Your Type")){
            Toast.makeText(this, "Please Select Your Type.....", Toast.LENGTH_SHORT).show();
        }

        else {

            lodingbar.setTitle("Login Account");
            lodingbar.setMessage("please wait,while we are checking");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();
            AloowAcessToaccount(username,password);

        }
    }

    private void AloowAcessToaccount(String username, String password) {

        if(remberme.isChecked())
        {
            Paper.book().write(Prevalite.Userusernamekey,username);
            Paper.book().write(Prevalite.Userpasswordkey, password);
            Paper.book().write(Prevalite.Usertypekey, slectedtype);
        }
        final DatabaseReference loginref;
        loginref = FirebaseDatabase.getInstance().getReference();
        loginref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(slectedtype).child(username).exists()){

                    Paper.book().write(Prevalite.Userusernamekey,username);
                    Paper.book().write(Prevalite.Userpasswordkey, password);
                    Paper.book().write(Prevalite.Usertypekey, slectedtype);

                    Users usersdata = dataSnapshot.child(slectedtype).child(username).getValue(Users.class);

                    if(usersdata.getUsername().equals(username)|| usersdata.getEmail().equals(username))
                    {
                        if(usersdata.getPassword().equals(password))
                        {
                            senduserslogindatatofirestore(username,password);

                            if(slectedtype.equals("Admin")){
                                Toast.makeText(LoginActivity.this, "Admin Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this, AdmindashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }
                            else if(slectedtype.equals("Lecturers")){
                                Toast.makeText(LoginActivity.this, "Lecturers Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this, LecturersdashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }
                            else if(slectedtype.equals("Students")){
                                Toast.makeText(LoginActivity.this, "Students Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this,StudentsdashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }

                        }
                        else{

                            lodingbar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

                else{
                    Toast.makeText(LoginActivity.this, "Account with this "+username+" not exisits", Toast.LENGTH_SHORT).show();
                    lodingbar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lodingbar.dismiss();
                Toast.makeText(LoginActivity.this, "DatabaseError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void senduserslogindatatofirestore(String username, String password) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String,Object> loginuser= new HashMap<>();
        loginuser.put("username",username);
        loginuser.put("password",password);

        firebaseFirestore.collection("Users").document(username).set(loginuser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LoginActivity.this, "Users Login details Update", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Firestore Error: "+e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AloowAcessToaccountAuto(String username, String password, String usertypekey) {


        final DatabaseReference loginref;
        loginref = FirebaseDatabase.getInstance().getReference();
        loginref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(usertypekey).child(username).exists()){

                    Users usersdata = dataSnapshot.child(usertypekey).child(username).getValue(Users.class);

                    if(usersdata.getUsername().equals(username)|| usersdata.getEmail().equals(username))
                    {
                        if(usersdata.getPassword().equals(password))
                        {
                            if(usertypekey.equals("Admin")){
                                Toast.makeText(LoginActivity.this, "Admin Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this, AdmindashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }
                            else if(usertypekey.equals("Lecturers")){
                                Toast.makeText(LoginActivity.this, "Lecturers Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this, LecturersdashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }
                            else if(usertypekey.equals("Students")){
                                Toast.makeText(LoginActivity.this, "Students Logged in Successfull", Toast.LENGTH_SHORT).show();
                                lodingbar.dismiss();
                                Intent intent =new Intent(LoginActivity.this, StudentsdashboardActivity.class);
                                Prevalite.CurrentonlineUsers=usersdata;
                                startActivity(intent);
                            }

                        }
                        else{

                            lodingbar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

                else{
                    Toast.makeText(LoginActivity.this, "Account with this "+username+" not exisits", Toast.LENGTH_SHORT).show();
                    lodingbar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lodingbar.dismiss();
                Toast.makeText(LoginActivity.this, "DatabaseError", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
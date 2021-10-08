package com.websayuraapp.websayura.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.R;

import java.util.HashMap;

import io.paperdb.Paper;

public class AdminaddnewusersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText anuusername,anuemail,anuname,anucourses,anupassword;
    private Button addnewusersbtn;
    private String slectedtype;
    private Spinner users_spinner;
    private ProgressDialog lodingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddnewusers);

        anuusername = findViewById(R.id.addnewusers_username);
        anuemail = findViewById(R.id.addnewusers_email);
        anuname = findViewById(R.id.addnewusers_name);
        anucourses = findViewById(R.id.addnewusers_courses);
        anupassword = findViewById(R.id.addnewusers_password);
        addnewusersbtn = findViewById(R.id.addnewusers_userbtn);

        users_spinner= findViewById(R.id.addnewusers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users_spinner.setAdapter(adapter);
        users_spinner.setOnItemSelectedListener(this);

        lodingbar=new ProgressDialog(this);

        addnewusersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();

            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnadmin_addnewuser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdminaddnewusersActivity.this, HomeActivity.class);
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
                        startActivity(new Intent(AdminaddnewusersActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(AdminaddnewusersActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(AdminaddnewusersActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(AdminaddnewusersActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(AdminaddnewusersActivity.this, MoreActivity.class));
                        break;

                }
                return false;
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

    private void CreateAccount() {

        String regusername = anuusername.getText().toString();
        String regemail = anuemail.getText().toString();
        String regname = anuname.getText().toString();
        String regcourses = anucourses.getText().toString();
        String regpassword = anupassword.getText().toString();


        if(TextUtils.isEmpty(regusername)){
            Toast.makeText(this, "Please write your username..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regemail)){
            Toast.makeText(this, "Please write your email..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regname)){
            Toast.makeText(this, "Please write your name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regcourses)){
            Toast.makeText(this, "Please write your courses..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(regpassword)){
            Toast.makeText(this, "Please write your password..", Toast.LENGTH_SHORT).show();
        }
        else if(slectedtype.equals("Select Users Type")){

            Toast.makeText(this, "Please Select Users Type.....", Toast.LENGTH_SHORT).show();
        }
        else {

            lodingbar.setTitle("Create Account");
            lodingbar.setMessage("Please wait,while we are checking");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();

            Valitadeusername(regusername,regemail,regname,regcourses,regpassword);

        }

    }

    private void Valitadeusername(String regusername, String regemail, String regname, String regcourses, String regpassword) {

        final DatabaseReference usersref;
        usersref = FirebaseDatabase.getInstance().getReference();

        usersref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(slectedtype).child(regusername).exists()))
                {
                    HashMap<String,Object>userdata = new HashMap<>();
                    userdata.put("username",regusername);
                    userdata.put("email",regemail);
                    userdata.put("name",regname);
                    userdata.put("courses",regcourses);
                    userdata.put("password",regpassword);

                    usersref.child(slectedtype).child(regusername).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    if(task.isSuccessful()){
                                        Toast.makeText(AdminaddnewusersActivity.this, "Add new Users has been create", Toast.LENGTH_SHORT).show();
                                        lodingbar.dismiss();
                                        Intent intent =new Intent(AdminaddnewusersActivity.this,AdminmaintainusersActivity.class);
                                        startActivity(intent);

                                    }
                                    else{
                                        lodingbar.dismiss();
                                        Toast.makeText(AdminaddnewusersActivity.this, "Network error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
                else{
                    Toast.makeText(AdminaddnewusersActivity.this, "this "+regusername+" alreday exisits", Toast.LENGTH_SHORT).show();
                    lodingbar.dismiss();
                    Toast.makeText(AdminaddnewusersActivity.this, "Please try again different Username", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(AdminaddnewusersActivity.this,AdminmaintainusersActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
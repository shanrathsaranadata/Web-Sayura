package com.websayuraapp.websayura.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.websayuraapp.websayura.AssignmentsActivity;
import com.websayuraapp.websayura.DiscussionActivity;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersaddclassActivity;
import com.websayuraapp.websayura.Lecturers.LecturersdashboardActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.Model.Joinclass;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.ProjectActivity;
import com.websayuraapp.websayura.R;

import io.paperdb.Paper;

public class StudentsdashboardActivity extends AppCompatActivity {

    private Button stujoinclass,stujoinactivity,stujoinvideos,stuprojects,studiscuss,stuprofile;
    private String onlineusername,onlineuserlastregno;
    private int  lastregno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsdashboard);

        stujoinclass = findViewById(R.id.studentsjoinclzbtn);
        stujoinactivity = findViewById(R.id.studentsactivity);
        stujoinvideos = findViewById(R.id.studentsvideos);
        stuprojects = findViewById(R.id.studentsprojects);
        studiscuss = findViewById(R.id.studentsforums);
        stuprofile = findViewById(R.id.studentsprofile);


        onlineusername = Prevalite.CurrentonlineUsers.getUsername();
        onlineuserlastregno = onlineusername.substring(3,onlineusername.length());
        lastregno = Integer.parseInt(onlineuserlastregno);

        Toast.makeText(this, "Last reg no is: "+lastregno, Toast.LENGTH_SHORT).show();

        if(lastregno>=1000){

            stujoinclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StudentsdashboardActivity.this, StudentsjoinclassActivity.class));
                }
            });
            stujoinvideos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StudentsdashboardActivity.this, StudentsjoinvideosActivity.class));
                }
            });
            stujoinactivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StudentsdashboardActivity.this, AssignmentsActivity.class));
                }
            });


        }
        else{

            stujoinclass.setVisibility(View.INVISIBLE);
            stujoinactivity.setVisibility(View.INVISIBLE);
            stujoinvideos.setVisibility(View.INVISIBLE);

        }

        stuprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentsdashboardActivity.this, ProjectActivity.class));
            }
        });
        studiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentsdashboardActivity.this, DiscussionActivity.class));
            }
        });
        stuprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentsdashboardActivity.this, ProfileActivity.class));
            }
        });


        FloatingActionButton fab = findViewById(R.id.home_floabtnstudent_dashbord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(StudentsdashboardActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsstudent_dashbord);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(StudentsdashboardActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(StudentsdashboardActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(StudentsdashboardActivity.this, StudentsdashboardActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(StudentsdashboardActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(StudentsdashboardActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }
}
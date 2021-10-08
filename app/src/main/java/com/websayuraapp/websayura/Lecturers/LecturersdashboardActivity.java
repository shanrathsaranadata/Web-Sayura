package com.websayuraapp.websayura.Lecturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.esotericsoftware.minlog.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.websayuraapp.websayura.CoursedettailsActivity;
import com.websayuraapp.websayura.DiscussionActivity;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.ProjectActivity;
import com.websayuraapp.websayura.R;
import com.websayuraapp.websayura.Students.StudentsHomeActivity;
import com.websayuraapp.websayura.Students.StudentsdashboardActivity;

import io.paperdb.Paper;

public class LecturersdashboardActivity extends AppCompatActivity {

    private Button lecaddclass,lecaddquiz,lecprojects,lecdiscuss,lecprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturersdashboard);

        lecaddclass = findViewById(R.id.lectureraddclassbtn);
        lecprojects = findViewById(R.id.lecturerprojects);
        lecaddquiz = findViewById(R.id.lectureraddquiz);
        lecdiscuss= findViewById(R.id.lecturerforums);
        lecprofile = findViewById(R.id.lecturerprofile);

        lecaddclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturersdashboardActivity.this, LecturersaddclassActivity.class));
            }
        });

        lecaddquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturersdashboardActivity.this, LecturersaddquizActivity.class));
            }
        });

        lecprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturersdashboardActivity.this, ProjectActivity.class));
            }
        });

        lecdiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturersdashboardActivity.this, DiscussionActivity.class));
            }
        });

        lecprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturersdashboardActivity.this, ProfileActivity.class));
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnlecdashboard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LecturersdashboardActivity.this,HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationslecdashboard);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(LecturersdashboardActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(LecturersdashboardActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(LecturersdashboardActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(LecturersdashboardActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(LecturersdashboardActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}
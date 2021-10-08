package com.websayuraapp.websayura.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.websayuraapp.websayura.DiscussionActivity;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersHomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersdashboardActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.ProjectActivity;
import com.websayuraapp.websayura.R;

import io.paperdb.Paper;

public class AdmindashboardActivity extends AppCompatActivity {

    private Button adminmaintainusers,adminmaintaincourses,adminmaintainclass,adminmaintainprojects,adminforums,adminprojects,adminprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        adminmaintainusers = findViewById(R.id.adminmaintendusers);
        adminmaintaincourses = findViewById(R.id.adminmaintendcourses);
        adminmaintainclass = findViewById(R.id.adminmaintendclass);
        adminmaintainprojects = findViewById(R.id.adminmaintendprojects);
        adminforums = findViewById(R.id.adminforums);
        adminprojects = findViewById(R.id.adminprojects);
        adminprofile = findViewById(R.id.adminprofile);



        adminmaintainusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, AdminmaintainusersActivity.class));
            }
        });

        adminmaintaincourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, AdminmaintaincoursesActivity.class));
            }
        });

        adminmaintainclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, AdminmaintainclassActivity.class));
            }
        });

        adminmaintainprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, AdminmaintainprojectsActivity.class));
            }
        });

        adminprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, ProjectActivity.class));
            }
        });

        adminforums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, DiscussionActivity.class));
            }
        });
        adminprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmindashboardActivity.this, ProfileActivity.class));
            }
        });


        FloatingActionButton fab = findViewById(R.id.home_floabtnadmin_dashboard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdmindashboardActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsadmin_dashboard);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(AdmindashboardActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(AdmindashboardActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(AdmindashboardActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(AdmindashboardActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(AdmindashboardActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}
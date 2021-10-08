package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        FloatingActionButton fab = findViewById(R.id.home_floabtncourses);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CoursesActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationcourses);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(CoursesActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(CoursesActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(CoursesActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(CoursesActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        Paper.book().destroy();
                        Intent intent=new Intent(CoursesActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                }
                return false;
            }
        });
    }
}
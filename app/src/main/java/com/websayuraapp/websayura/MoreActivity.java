package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.websayuraapp.websayura.Prevalite.Prevalite;

import io.paperdb.Paper;

public class MoreActivity extends AppCompatActivity {

    private ImageView mycouiv,notificationiv,inboxiv,feedbackiv,aboutusiv,projectsiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mycouiv = findViewById(R.id.coursesiv);
        notificationiv = findViewById(R.id.notificationsiv);
        inboxiv = findViewById(R.id.inboxiv);
        feedbackiv= findViewById(R.id.feedbackiv);
        aboutusiv = findViewById(R.id.aboutusiv);
        projectsiv = findViewById(R.id.projectsiv);
        Paper.init(this);

        mycouiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,LoginActivity.class));
            }
        });
        notificationiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernamekey =Paper.book().read(Prevalite.Userusernamekey);
                String userpasswordkey =Paper.book().read(Prevalite.Userpasswordkey);

                if(usernamekey!= "" && userpasswordkey != "" )
                {
                    if(!TextUtils.isEmpty(usernamekey) && !TextUtils.isEmpty(userpasswordkey)){
                        startActivity(new Intent(MoreActivity.this,NotificationActivity.class));
                    }
                    else{
                        startActivity(new Intent(MoreActivity.this,LoginActivity.class));
                    }

                }
                else{
                    startActivity(new Intent(MoreActivity.this,LoginActivity.class));
                }

                }

        });
        inboxiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,LoginActivity.class));
            }
        });
        feedbackiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,FeedbackActivity.class));
            }
        });

        projectsiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,ProjectActivity.class));
            }
        });

        aboutusiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, WebsayuraActivity.class);
                intent.putExtra("link","https://www.shanrathsarana.com");
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnmore);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MoreActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationmore);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MoreActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(MoreActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MoreActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(MoreActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(MoreActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}
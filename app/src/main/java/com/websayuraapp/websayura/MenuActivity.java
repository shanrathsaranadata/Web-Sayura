package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class MenuActivity extends AppCompatActivity {

    private ImageView arduinoiv,combasiv,plciv,softwareiv,webiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        arduinoiv = findViewById(R.id.arduinoiv);
        combasiv = findViewById(R.id.computerbasiciv);
        plciv = findViewById(R.id.plciv);
        softwareiv = findViewById(R.id.softwareiv);
        webiv = findViewById(R.id.webiv);

        arduinoiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CoursedettailsActivity.class);
                intent.putExtra("id", "arduino");
                startActivity(intent);
            }
        });

        combasiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CoursedettailsActivity.class);
                intent.putExtra("id", "combas");
                startActivity(intent);
            }
        });

        plciv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CoursedettailsActivity.class);
                intent.putExtra("id", "plc");
                startActivity(intent);
            }
        });

        softwareiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CoursedettailsActivity.class);
                intent.putExtra("id", "software");
                startActivity(intent);
            }
        });

        webiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CoursedettailsActivity.class);
                intent.putExtra("id", "web");
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnmenu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationmenu);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MenuActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(MenuActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MenuActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(MenuActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(MenuActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}
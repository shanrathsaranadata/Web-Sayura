package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class WebsayuraActivity extends AppCompatActivity {

    private WebView websayura;
    private String websayuralink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websayura);

        websayura =(WebView) findViewById(R.id.websayurawebview);
        websayuralink=getIntent().getStringExtra("link");

        WebSettings webSettings = websayura.getSettings();

        webSettings.setJavaScriptEnabled(true);

        websayura.loadUrl(websayuralink);

        websayura.setWebViewClient(new WebViewClient());

        Toast.makeText(WebsayuraActivity.this, "Please Wait to loading mobile view", Toast.LENGTH_SHORT).show();


        FloatingActionButton fab = findViewById(R.id.home_floabtnwebsayura);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(WebsayuraActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationswebsayura);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(WebsayuraActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(WebsayuraActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(WebsayuraActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(WebsayuraActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(WebsayuraActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });


    }
}
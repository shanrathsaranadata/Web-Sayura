package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import io.paperdb.Paper;

public class VideosActivity extends AppCompatActivity {

    private VideoView zoomvideoView;
    private Uri uri;
    private MediaController mediaController;
    private String videolink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        videolink=getIntent().getStringExtra("videolink");

        zoomvideoView = findViewById(R.id.websayurazoomvides);
        Toast.makeText(VideosActivity.this, "Please wait Zoom Video is loading", Toast.LENGTH_SHORT).show();

        mediaController =new MediaController(this);
        mediaController.setAnchorView(zoomvideoView);
        uri = Uri.parse(videolink);
        zoomvideoView.setMediaController(mediaController);
        zoomvideoView.setVideoURI(uri);
        zoomvideoView.start();

        FloatingActionButton fab = findViewById(R.id.home_floabtnvideos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(VideosActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsvideos);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(VideosActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(VideosActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(VideosActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(VideosActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();;
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(VideosActivity.this,ProfileActivity.class));
                        break;

                }
                return false;
            }
        });

    }
}
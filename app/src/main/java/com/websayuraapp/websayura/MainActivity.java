package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.Cursor;
import com.websayuraapp.websayura.Model.Notification;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Uri uri;
    private Button skipbtn;
    private DatabaseReference VideosReference;
    private int timeInMillisec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.websayuravideo);
        skipbtn = findViewById(R.id.skipbtn);
        VideosReference = FirebaseDatabase.getInstance().getReference().child("Videos").child("mainvideo");

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        VideosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){



                    String videolink = dataSnapshot.child("videolink").getValue().toString();
                    uri = Uri.parse(videolink);
                    videoView.setVideoURI(uri);
                    Toast.makeText(MainActivity.this, "Please wait Main Video is loading", Toast.LENGTH_SHORT).show();
                    videoView.start();

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            int duration=mp.getDuration()/1000;
                            int hours = duration / 3600;
                            int minutes = (duration / 60) - (hours * 60);
                            int seconds = duration - (hours * 3600) - (minutes * 60) ;

                            timeInMillisec =mp.getDuration();
                            String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                            Toast.makeText(getApplicationContext(), "Video Duration is " + formatted ,  Toast.LENGTH_LONG).show();

                            Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            },timeInMillisec);

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
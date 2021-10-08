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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.Model.Notification;
import com.websayuraapp.websayura.Model.Users;

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference NotificationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        NotificationReference = FirebaseDatabase.getInstance().getReference().child("Notification").child("mainnotification");

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this,MainActivity.class));

            }
        },5000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        NotificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                        Notification notificationdata = dataSnapshot.getValue(Notification.class);

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                            NotificationChannel channel = new NotificationChannel("web sayura","web sayura",NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);

                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(SplashActivity.this,"web sayura");
                                builder.setSmallIcon(R.drawable.ic_notifications);
                                builder.setContentTitle(notificationdata.getTitle());
                                builder.setContentText(notificationdata.getText());
                                builder.setAutoCancel(true);
                        Intent intent = new Intent(SplashActivity.this,NotificationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        PendingIntent pendingIntent = PendingIntent.getActivity(SplashActivity.this,
                                0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(SplashActivity.this);
                        notificationManagerCompat.notify(1,builder.build());

                        Toast.makeText(SplashActivity.this, "welcome web sayura", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
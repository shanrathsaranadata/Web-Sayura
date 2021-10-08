package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.MessagingService.MyFirebaseMessagingService;
import com.websayuraapp.websayura.Model.Notification;
import com.websayuraapp.websayura.Model.Projects;
import com.websayuraapp.websayura.Viewholder.NotificationViewHolder;
import com.websayuraapp.websayura.Viewholder.ProjectsViewHolder;

import io.paperdb.Paper;

public class NotificationActivity extends AppCompatActivity {

    private DatabaseReference notificationref;
    private ImageView notification_backbtn;
    private RecyclerView notificationlist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent notificationintent = new Intent(NotificationActivity.this, MyFirebaseMessagingService.class);
        startService(notificationintent);

        notificationref = FirebaseDatabase.getInstance().getReference().child("Notification");
        notificationlist=findViewById(R.id.recyclernotification);
        notificationlist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        notificationlist.setLayoutManager(layoutManager);
        notification_backbtn = findViewById(R.id.notification_backbtn);

        notification_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this,MoreActivity.class));
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnnotification);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationnotification);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(NotificationActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(NotificationActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(NotificationActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(NotificationActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(NotificationActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Notification> options=
                new FirebaseRecyclerOptions.Builder<Notification>().setQuery(notificationref.orderByChild("dateandtime"),Notification.class)
                        .build();
        FirebaseRecyclerAdapter<Notification, NotificationViewHolder> adapter=
                new FirebaseRecyclerAdapter<Notification, NotificationViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull Notification model) {

                        holder.notification_dateandtimetv.setText(model.getDateandtime());
                        holder.notification_usernametv.setText(model.getName());
                        holder.notification_titletv.setText(model.getTitle());
                        holder.notification_texttv.setText(model.getText());

                        if(model.getImage().equals("noimage")) {

                            holder.notification_dateandtimetv.setText(model.getDateandtime());
                            holder.notification_usernametv.setText(model.getName());
                            holder.notification_titletv.setText(model.getTitle());
                            holder.notification_texttv.setText(model.getText());
                            holder.cartnotification_image.setVisibility(View.INVISIBLE);


                        }
                        else{

                            holder.notification_dateandtimetv.setText(model.getDateandtime());
                            holder.notification_usernametv.setText(model.getName());
                            holder.notification_titletv.setText(model.getTitle());
                            holder.notification_texttv.setText(model.getText());
                            Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_photo).into(holder.cartnotification_image);


                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification,viewGroup,false);
                        NotificationViewHolder holder =new NotificationViewHolder(view);
                        return holder;
                    }
                };

       notificationlist.setAdapter(adapter);
        adapter.startListening();
    }

}
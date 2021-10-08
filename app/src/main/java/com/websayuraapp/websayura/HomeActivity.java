package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderPager;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.Adapter.SliderAdapter;
import com.websayuraapp.websayura.Adapter.ViewPagerAdapter;
import com.websayuraapp.websayura.Model.Courses;
import com.websayuraapp.websayura.Model.Notification;
import com.websayuraapp.websayura.Model.SliderData;
import com.websayuraapp.websayura.Viewholder.CoursesViewHolder;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference coursesref;
    private DatabaseReference SlideshowsReference,NotificationReference;
    private RecyclerView courseslist;
    private SliderView sliderView;
    RecyclerView.LayoutManager layoutManager;
    private String url1,url2,url3,url4,url5,url6,url7,url8,url9,url10;
    private WebView websayurahome;
    private String websayurahomelink;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        coursesref = FirebaseDatabase.getInstance().getReference().child("Courses");
        NotificationReference = FirebaseDatabase.getInstance().getReference().child("Notification").child("mainnotification");
        SlideshowsReference = FirebaseDatabase.getInstance().getReference().child("Slideshows");
        courseslist=findViewById(R.id.recyclerhome);
        sliderView = findViewById(R.id.imageSlider);
        courseslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        courseslist.setLayoutManager(layoutManager);

        websayurahome =(WebView) findViewById(R.id.websayurawebviewhome);
        websayurahomelink="https://websayura.com/";

        WebSettings webSettings = websayurahome.getSettings();
        webSettings.setJavaScriptEnabled(true);

        websayurahome.loadUrl(websayurahomelink);
        websayurahome.setWebViewClient(new WebViewClient());

        Toast.makeText(this, "Please Wait to loading our Websayura Home", Toast.LENGTH_SHORT).show();

        autoplayslideshows();
        newnotification();

        FloatingActionButton fab = findViewById(R.id.home_floabtnhome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationhome);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(HomeActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(HomeActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    private void autoplayslideshows() {

        SlideshowsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Toast.makeText(HomeActivity.this, "Please wait Slideshows is loading", Toast.LENGTH_SHORT).show();

                    url1 = dataSnapshot.child("slidelink01").getValue().toString();
                    url2 = dataSnapshot.child("slidelink02").getValue().toString();
                    url3 = dataSnapshot.child("slidelink03").getValue().toString();
                    url4 = dataSnapshot.child("slidelink04").getValue().toString();
                    url5 = dataSnapshot.child("slidelink05").getValue().toString();
                    url6 = dataSnapshot.child("slidelink06").getValue().toString();
                    url7 = dataSnapshot.child("slidelink07").getValue().toString();
                    url8 = dataSnapshot.child("slidelink08").getValue().toString();
                    url9 = dataSnapshot.child("slidelink09").getValue().toString();
                    url10 = dataSnapshot.child("slidelink10").getValue().toString();


                    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                    sliderDataArrayList.add(new SliderData(url1));
                    sliderDataArrayList.add(new SliderData(url2));
                    sliderDataArrayList.add(new SliderData(url3));
                    sliderDataArrayList.add(new SliderData(url4));
                    sliderDataArrayList.add(new SliderData(url5));
                    sliderDataArrayList.add(new SliderData(url6));
                    sliderDataArrayList.add(new SliderData(url7));
                    sliderDataArrayList.add(new SliderData(url8));
                    sliderDataArrayList.add(new SliderData(url9));
                    sliderDataArrayList.add(new SliderData(url10));
                    SliderAdapter adapter = new SliderAdapter(HomeActivity.this, sliderDataArrayList);
                    sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setScrollTimeInSec(5);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void newnotification(){
        NotificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Notification notificationdata = dataSnapshot.getValue(Notification.class);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        NotificationChannel channel = new NotificationChannel("web sayura","web sayura", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);

                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this,"web sayura");
                    builder.setSmallIcon(R.drawable.ic_notifications);
                    builder.setContentTitle(notificationdata.getTitle());
                    builder.setContentText(notificationdata.getText());
                    builder.setAutoCancel(true);
                    Intent intent = new Intent(HomeActivity.this,NotificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this,
                            0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(HomeActivity.this);
                    notificationManagerCompat.notify(1,builder.build());

                    Toast.makeText(HomeActivity.this, "welcome web sayura", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Courses> options=
                new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("name"),Courses.class)
                        .build();
        FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                        holder.courses_name.setText(model.getName());
                        holder.courses_level.setText(model.getLevel());
                        holder.courses_enrolled.setText(model.getEnrolled());
                        holder.courses_fee.setText(model.getFee());
                        holder.courses_duration.setText(model.getDuration());
                        Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                    Intent intent = new Intent(HomeActivity.this, CoursedettailsActivity.class);
                                    intent.putExtra("id", model.getId());
                                    startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.courses,viewGroup,false);
                        CoursesViewHolder holder =new CoursesViewHolder(view);
                        return holder;
                    }
                };

        courseslist.setAdapter(adapter);
        adapter.startListening();

    }
}
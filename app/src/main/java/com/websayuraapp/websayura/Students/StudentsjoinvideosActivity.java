package com.websayuraapp.websayura.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.Model.Projects;
import com.websayuraapp.websayura.Model.Videos;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.ProjectActivity;
import com.websayuraapp.websayura.R;
import com.websayuraapp.websayura.VideosActivity;
import com.websayuraapp.websayura.Viewholder.ProjectsViewHolder;
import com.websayuraapp.websayura.Viewholder.VideosViewHolder;
import com.websayuraapp.websayura.WebsayuraActivity;

import io.paperdb.Paper;

public class StudentsjoinvideosActivity extends AppCompatActivity {

    private DatabaseReference videosref;
    private RecyclerView videoslist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsjoinvideos);

        videosref = FirebaseDatabase.getInstance().getReference().child("Videos");
        videoslist=findViewById(R.id.recyclerstudentsjoinvideos);
        videoslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        videoslist.setLayoutManager(layoutManager);

        FloatingActionButton fab = findViewById(R.id.home_floabtnstudent_joinvideos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentsjoinvideosActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsstudent_joinvideos);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(StudentsjoinvideosActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(StudentsjoinvideosActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(StudentsjoinvideosActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(StudentsjoinvideosActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(StudentsjoinvideosActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }

    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Videos> options=
                new FirebaseRecyclerOptions.Builder<Videos>().setQuery(videosref.orderByChild("subject"),Videos.class)
                        .build();
        FirebaseRecyclerAdapter<Videos, VideosViewHolder> adapter=
                new FirebaseRecyclerAdapter<Videos, VideosViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull VideosViewHolder holder, int position, @NonNull Videos model) {

                        if(model.getSubject().equals("Main Video")){

                            holder.videosbutton.setVisibility(View.INVISIBLE);
                        }
                        else{

                            holder.videosbutton.setText(model.getSubject());
                            holder.videosbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(StudentsjoinvideosActivity.this, VideosActivity.class);
                                    intent.putExtra("videolink", model.getVideolink());
                                    startActivity(intent);

                                }
                            });
                        }


                    }

                    @NonNull
                    @Override
                    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos,viewGroup,false);
                        VideosViewHolder holder =new VideosViewHolder(view);
                        return holder;
                    }
                };

        videoslist.setAdapter(adapter);
        adapter.startListening();
    }
}
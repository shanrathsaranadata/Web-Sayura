package com.websayuraapp.websayura;

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
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.Model.Courses;
import com.websayuraapp.websayura.Viewholder.CoursesViewHolder;

import io.paperdb.Paper;

public class CoursedettailsActivity extends AppCompatActivity {

    private DatabaseReference coursesref;
    private RecyclerView courseslist;
    private String courseid;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dettails_activity);

        coursesref = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseslist=findViewById(R.id.recyclercousedettails);
        courseslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        courseslist.setLayoutManager(layoutManager);

        courseid=getIntent().getStringExtra("id");

        FloatingActionButton fab = findViewById(R.id.home_floabtncousedettails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CoursedettailsActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationcousedettails);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(CoursedettailsActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(CoursedettailsActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(CoursedettailsActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(CoursedettailsActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        Paper.book().destroy();
                        Intent intent=new Intent(CoursedettailsActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                }
                return false;
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Courses> options=
                new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("id").equalTo(courseid),Courses.class)
                        .build();
        FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                        holder.courses_name.setText(model.getName());
                        holder.courses_level.setText("Level:- "+model.getLevel());
                        holder.courses_enrolled.setText("Enrolled:- "+model.getEnrolled());
                        holder.courses_fee.setText("Fee: "+model.getFee());
                        holder.courses_duration.setText("Duration: "+model.getDuration());
                        holder.courses_description.setText(model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(CoursedettailsActivity.this, LoginActivity.class);
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
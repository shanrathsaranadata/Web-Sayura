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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.Model.Courses;
import com.websayuraapp.websayura.Model.Projects;
import com.websayuraapp.websayura.Viewholder.CoursesViewHolder;
import com.websayuraapp.websayura.Viewholder.ProjectsViewHolder;

public class ProjectActivity extends AppCompatActivity {

    private DatabaseReference projectsref;
    private RecyclerView projectslist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        projectsref = FirebaseDatabase.getInstance().getReference().child("Projects");
        projectslist=findViewById(R.id.recyclerproject);
        projectslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        projectslist.setLayoutManager(layoutManager);

        FloatingActionButton fab = findViewById(R.id.home_floabtnprojects);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ProjectActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsprojects);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(ProjectActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(ProjectActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(ProjectActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(ProjectActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(ProjectActivity.this,LoginActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Projects> options=
                new FirebaseRecyclerOptions.Builder<Projects>().setQuery(projectsref.orderByChild("batchname"),Projects.class)
                        .build();
        FirebaseRecyclerAdapter<Projects, ProjectsViewHolder> adapter=
                new FirebaseRecyclerAdapter<Projects, ProjectsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position, @NonNull Projects model) {

                        holder.projectsbutton.setText(model.getBatchname());
                        holder.projectsbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(ProjectActivity.this, WebsayuraActivity.class);
                                intent.putExtra("link", model.getLink());
                                startActivity(intent);


                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.projects,viewGroup,false);
                        ProjectsViewHolder holder =new ProjectsViewHolder(view);
                        return holder;
                    }
                };

        projectslist.setAdapter(adapter);
        adapter.startListening();
    }
}
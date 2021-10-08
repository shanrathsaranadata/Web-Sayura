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
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.websayuraapp.websayura.Model.Courses;
import com.websayuraapp.websayura.Model.Feedback;
import com.websayuraapp.websayura.Viewholder.CoursesViewHolder;
import com.websayuraapp.websayura.Viewholder.FeedbackViewHolder;

import io.paperdb.Paper;

public class FeedbackActivity extends AppCompatActivity {

    private DatabaseReference feedbackref;
    private RecyclerView feedbacklist;
    RecyclerView.LayoutManager layoutManager;
    private ImageView feedbackbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackref = FirebaseDatabase.getInstance().getReference().child("Comments");
        feedbacklist=findViewById(R.id.recyclerfeedback);
        feedbacklist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        feedbacklist.setLayoutManager(layoutManager);

        feedbackbackbtn = findViewById(R.id.backbtnfeedback);

        feedbackbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this,MoreActivity.class));
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnfeedback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationfeedback);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(FeedbackActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(FeedbackActivity.this,LoginActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(FeedbackActivity.this,MoreActivity.class));
                        break;

                    case R.id.navigation_home:
                        startActivity(new Intent(FeedbackActivity.this,HomeActivity.class));
                        break;

                    case R.id.navigation_profile:
                        Paper.book().destroy();
                        Intent intent=new Intent(FeedbackActivity.this,MainActivity.class);
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

        FirebaseRecyclerOptions<Feedback> options=
                new FirebaseRecyclerOptions.Builder<Feedback>().setQuery(feedbackref.orderByChild("name"),Feedback.class)
                        .build();
        FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder> adapter=
                new FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position, @NonNull Feedback model) {

                        holder.feedback_sname.setText(model.getName());
                        holder.feedback_role.setText(model.getEducation());
                        holder.feedback_description.setText(model.getDescription());
                        Picasso.get().load(model.getComment()).into(holder.cartfeedback_image);
                        Picasso.get().load(model.getProfile()).into(holder.feedback_sprofileimage);

                    }

                    @NonNull
                    @Override
                    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback,viewGroup,false);
                        FeedbackViewHolder holder =new FeedbackViewHolder(view);
                        return holder;
                    }
                };

        feedbacklist.setAdapter(adapter);
        adapter.startListening();
    }
}
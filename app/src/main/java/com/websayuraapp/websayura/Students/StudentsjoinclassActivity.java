package com.websayuraapp.websayura.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersHomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersaddclassActivity;
import com.websayuraapp.websayura.Lecturers.LecturersdashboardActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.Model.Joinclass;
import com.websayuraapp.websayura.Model.Projects;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.ProjectActivity;
import com.websayuraapp.websayura.R;
import com.websayuraapp.websayura.Viewholder.JoinclassViewHolder;
import com.websayuraapp.websayura.Viewholder.ProjectsViewHolder;
import com.websayuraapp.websayura.WebsayuraActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class StudentsjoinclassActivity extends AppCompatActivity {

    private DatabaseReference joinclassref,attendancesref;
    private RecyclerView joinclasslist;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog lodingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsjoinclass);



        joinclasslist=findViewById(R.id.recyclerstudentsjoinclass);
        joinclasslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        joinclasslist.setLayoutManager(layoutManager);

        lodingbar=new ProgressDialog(this);
        attendancesref = FirebaseDatabase.getInstance().getReference();
        joinclassref = FirebaseDatabase.getInstance().getReference().child("Class");

        FloatingActionButton fab = findViewById(R.id.home_floabtnstudent_joinclass);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentsjoinclassActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsstudent_joinclass);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(StudentsjoinclassActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(StudentsjoinclassActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(StudentsjoinclassActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(StudentsjoinclassActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(StudentsjoinclassActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Joinclass> options=
                new FirebaseRecyclerOptions.Builder<Joinclass>().setQuery(joinclassref.orderByChild("time"),Joinclass.class)
                        .build();
        FirebaseRecyclerAdapter<Joinclass, JoinclassViewHolder> adapter=
                new FirebaseRecyclerAdapter<Joinclass, JoinclassViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull JoinclassViewHolder holder, int position, @NonNull Joinclass model) {

                        holder.joinclass_title.setText(model.getClasstitle());
                        holder.joinclass_lecname.setText(model.getLecturename());
                        holder.joinclass_batch.setText(model.getClassbatch());
                        holder.joinclass_dateandtime.setText(model.getDate()+" "+model.getTime());
                        holder.joinclass_massage.setText(model.getClassmassage());

                        holder.joinclzbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                joinclassattendance(model.getClasstitle(),model.getLecturename(),model.getClassbatch());

                                Uri uri = Uri.parse(model.getClasslink()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);


                            }
                        });

                    }

                    @NonNull
                    @Override
                    public JoinclassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.joinclass,viewGroup,false);
                        JoinclassViewHolder holder =new JoinclassViewHolder(view);
                        return holder;
                    }
                };

       joinclasslist.setAdapter(adapter);
        adapter.startListening();
    }

    private void joinclassattendance(String classtitle, String lecturename, String classbatch) {

        lodingbar.setTitle("Attendance Marks");
        lodingbar.setMessage("Dear students, please wait,while we are Attendance marking");
        lodingbar.setCanceledOnTouchOutside(false);
        lodingbar.show();

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        String clzjoindate=currentdate.format(calender.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        String clzjointime =currenttime.format(calender.getTime());

        HashMap<String,Object> attendancemap = new HashMap<>();
        attendancemap.put("lecturename",lecturename);
        attendancemap.put("date",clzjoindate);
        attendancemap.put("time",clzjointime);
        attendancemap.put("classtitle",classtitle);
        attendancemap.put("classbatch",classbatch);
        attendancemap.put("username", Prevalite.CurrentonlineUsers.getUsername());

       attendancesref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Attendances").child(Prevalite.CurrentonlineUsers.getUsername()).exists()))
                {

                    attendancesref.child("Attendances").child(Prevalite.CurrentonlineUsers.getUsername()).updateChildren(attendancemap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        lodingbar.dismiss();
                                        Toast.makeText(StudentsjoinclassActivity.this, "Attendance  is marks Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(StudentsjoinclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
                else{

                    attendancesref.child("Attendances").child(Prevalite.CurrentonlineUsers.getUsername()).updateChildren(attendancemap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        lodingbar.dismiss();
                                        Toast.makeText(StudentsjoinclassActivity.this, "Attendance  is marks Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(StudentsjoinclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lodingbar.dismiss();
                String message = databaseError.toString();
                Toast.makeText(StudentsjoinclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
            }

        });

    }
}
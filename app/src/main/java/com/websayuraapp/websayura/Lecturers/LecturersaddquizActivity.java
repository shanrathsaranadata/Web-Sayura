package com.websayuraapp.websayura.Lecturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class LecturersaddquizActivity extends AppCompatActivity {

    private EditText lecaddquiz01,lecaddquiz02,lecaddquiz03,lecaddquiz04,lecaddquiz05,lecaddquiz06,lecaddquiz07,lecaddquiz08,lecaddquiz09,lecaddquiz10;
    private EditText lecaddans01,lecaddans02,lecaddans03,lecaddans04,lecaddans05,lecaddans06,lecaddans07,lecaddans08,lecaddans09,lecaddans10;
    private Button addquizbtn;
    private String clztype,lecname,clzadddate,clzaddtime;
    private ProgressDialog lodingbar;
    private DatabaseReference quizandansweref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturersaddquiz);

        lecaddquiz01 = findViewById(R.id.quiz01et);
        lecaddquiz02 = findViewById(R.id.quiz02et);
        lecaddquiz03 = findViewById(R.id.quiz03et);
        lecaddquiz04 = findViewById(R.id.quiz04et);
        lecaddquiz05 = findViewById(R.id.quiz05et);
        lecaddquiz06 = findViewById(R.id.quiz06et);
        lecaddquiz07 = findViewById(R.id.quiz07et);
        lecaddquiz08 = findViewById(R.id.quiz08et);
        lecaddquiz09 = findViewById(R.id.quiz09et);
        lecaddquiz10 = findViewById(R.id.quiz10et);

        lecaddans01 = findViewById(R.id.answer01et);
        lecaddans02 = findViewById(R.id.answer02et);
        lecaddans03 = findViewById(R.id.answer03et);
        lecaddans04 = findViewById(R.id.answer04et);
        lecaddans05 = findViewById(R.id.answer05et);
        lecaddans06 = findViewById(R.id.answer06et);
        lecaddans07 = findViewById(R.id.answer07et);
        lecaddans08 = findViewById(R.id.answer08et);
        lecaddans09 = findViewById(R.id.answer09et);
        lecaddans10 = findViewById(R.id.answer10et);

        addquizbtn = findViewById(R.id.quiz_addbtn);

        quizandansweref = FirebaseDatabase.getInstance().getReference().child("Quiz").child("lecturersaddquiz");
        lodingbar=new ProgressDialog(this);
        Paper.init(this);

        addquizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valitadedata();
            }
        });



        FloatingActionButton fab = findViewById(R.id.home_floabtnlecaddquiz);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LecturersaddquizActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationslecaddquiz);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(LecturersaddquizActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(LecturersaddquizActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(LecturersaddquizActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(LecturersaddquizActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(LecturersaddquizActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    private void Valitadedata() {

        lecname = Prevalite.CurrentonlineUsers.getName();
        clztype = Prevalite.CurrentonlineUsers.getCourses();

        if(TextUtils.isEmpty(lecaddquiz01.getText().toString())||TextUtils.isEmpty(lecaddquiz02.getText().toString())||TextUtils.isEmpty(lecaddquiz03.getText().toString())||TextUtils.isEmpty(lecaddquiz04.getText().toString())||TextUtils.isEmpty(lecaddquiz05.getText().toString())||TextUtils.isEmpty(lecaddquiz06.getText().toString())||TextUtils.isEmpty(lecaddquiz07.getText().toString())||TextUtils.isEmpty(lecaddquiz08.getText().toString())||TextUtils.isEmpty(lecaddquiz09.getText().toString())||TextUtils.isEmpty(lecaddquiz10.getText().toString())){

            Toast.makeText(this, "Please Type Your All Question", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lecaddans01.getText().toString())||TextUtils.isEmpty(lecaddans02.getText().toString())||TextUtils.isEmpty(lecaddans03.getText().toString())||TextUtils.isEmpty(lecaddans04.getText().toString())||TextUtils.isEmpty(lecaddans05.getText().toString())||TextUtils.isEmpty(lecaddans06.getText().toString())||TextUtils.isEmpty(lecaddans07.getText().toString())||TextUtils.isEmpty(lecaddans08.getText().toString())||TextUtils.isEmpty(lecaddans09.getText().toString())||TextUtils.isEmpty(lecaddans10.getText().toString())){
            Toast.makeText(this, "Please Type Your All Answer", Toast.LENGTH_SHORT).show();
        }

        else{
            saveclassinfo();
        }
    }
    private void saveclassinfo() {

        lodingbar.setTitle("Adding new quiz");
        lodingbar.setMessage("Dear lecturer, please wait,while we are adding the new quiz");
        lodingbar.setCanceledOnTouchOutside(false);
        lodingbar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        clzadddate=currentdate.format(calender.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        clzaddtime =currenttime.format(calender.getTime());

        HashMap<String,Object> quizmap = new HashMap<>();
        quizmap.put("lecturename",lecname);
        quizmap.put("date",clzadddate);
        quizmap.put("time",clzaddtime);
        quizmap.put("quiz01",lecaddquiz01.getText().toString());
        quizmap.put("answer01",lecaddquiz01.getText().toString());
        quizmap.put("marks01",10);
        quizmap.put("quiz02",lecaddquiz02.getText().toString());
        quizmap.put("answer02",lecaddquiz02.getText().toString());
        quizmap.put("marks02",10);
        quizmap.put("quiz03",lecaddquiz03.getText().toString());
        quizmap.put("answer03",lecaddquiz03.getText().toString());
        quizmap.put("marks03",10);
        quizmap.put("quiz04",lecaddquiz04.getText().toString());
        quizmap.put("answer04",lecaddquiz04.getText().toString());
        quizmap.put("marks04",10);
        quizmap.put("quiz05",lecaddquiz05.getText().toString());
        quizmap.put("answer05",lecaddquiz05.getText().toString());
        quizmap.put("marks05",10);
        quizmap.put("quiz06",lecaddquiz06.getText().toString());
        quizmap.put("answer06",lecaddquiz06.getText().toString());
        quizmap.put("marks06",10);
        quizmap.put("quiz07",lecaddquiz07.getText().toString());
        quizmap.put("answer07",lecaddquiz07.getText().toString());
        quizmap.put("marks07",10);
        quizmap.put("quiz08",lecaddquiz08.getText().toString());
        quizmap.put("answer08",lecaddquiz08.getText().toString());
        quizmap.put("marks08",10);
        quizmap.put("quiz09",lecaddquiz09.getText().toString());
        quizmap.put("answer09",lecaddquiz09.getText().toString());
        quizmap.put("marks09",10);
        quizmap.put("quiz10",lecaddquiz10.getText().toString());
        quizmap.put("answer10",lecaddquiz10.getText().toString());
        quizmap.put("marks10",10);
        quizmap.put("totalmarks",100);

        quizandansweref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child(clztype+lecname).exists()))
                {

                    quizandansweref.child(clztype+lecname).updateChildren(quizmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Intent intent =new Intent(LecturersaddquizActivity.this, LecturersdashboardActivity.class);
                                        startActivity(intent);
                                        lodingbar.dismiss();
                                        Toast.makeText(LecturersaddquizActivity.this, "Quiz is adding Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(LecturersaddquizActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
                else{

                    quizandansweref.child(clztype+lecname).updateChildren(quizmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Intent intent =new Intent(LecturersaddquizActivity.this, LecturersdashboardActivity.class);
                                        startActivity(intent);
                                        lodingbar.dismiss();
                                        Toast.makeText(LecturersaddquizActivity.this, "Quiz is adding Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(LecturersaddquizActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lodingbar.dismiss();
                String message = databaseError.toString();
                Toast.makeText(LecturersaddquizActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
            }

        });

    }
}
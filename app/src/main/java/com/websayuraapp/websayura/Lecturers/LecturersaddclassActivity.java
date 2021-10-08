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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.websayuraapp.websayura.Students.StudentsHomeActivity;
import com.websayuraapp.websayura.Students.StudentsdashboardActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class LecturersaddclassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText classtitle,classmassage,classzoomlink;
    private Spinner batchsl;
    private Button sendclassbtn;
    private ProgressDialog lodingbar;
    private String lecname,clzadddate,clzaddtime,clztitle,clzmassage,clzlink,clztype,clzbatchsl;
    private DatabaseReference classref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturersaddclass);

        classtitle = findViewById(R.id.class_titleet);
        classmassage = findViewById(R.id.class_massageet);
        classzoomlink= findViewById(R.id.class_lecturerlinket);
        sendclassbtn= findViewById(R.id.class_sendbtn);


        batchsl= findViewById(R.id.batch_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Batch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchsl.setAdapter(adapter);

        batchsl.setOnItemSelectedListener(this);
        lodingbar=new ProgressDialog(this);
        Paper.init(this);

        classref = FirebaseDatabase.getInstance().getReference();

        sendclassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valitadedata();
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnlecaddclz);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LecturersaddclassActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationslecaddclz);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(LecturersaddclassActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(LecturersaddclassActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(LecturersaddclassActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(LecturersaddclassActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(LecturersaddclassActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }

    private void Valitadedata() {

        lecname = Prevalite.CurrentonlineUsers.getName();
        clztype = Prevalite.CurrentonlineUsers.getCourses();
        clztitle = classtitle.getText().toString();
        clzmassage = classmassage.getText().toString();
        clzlink = classzoomlink.getText().toString();

        if(TextUtils.isEmpty(lecname)){
            Toast.makeText(this, "Lecture Name Null", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(clztype)){
            Toast.makeText(this, "Lecture  Class type Null", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(clztitle)){
            Toast.makeText(this, "Please write class title..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(clzmassage)){
            Toast.makeText(this, "Please write Class massage..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(clzlink)){
            Toast.makeText(this, "Please write link..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(clzbatchsl) || clzbatchsl.equals("Select Your Batch")){

            Toast.makeText(this, "Please select Batch..", Toast.LENGTH_SHORT).show();

        }

        else{
            saveclassinfo();
        }
    }

    private void saveclassinfo() {

        lodingbar.setTitle("Adding new class");
        lodingbar.setMessage("Dear lecturer, please wait,while we are adding the new class");
        lodingbar.setCanceledOnTouchOutside(false);
        lodingbar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        clzadddate=currentdate.format(calender.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        clzaddtime =currenttime.format(calender.getTime());

        HashMap<String,Object> classmap = new HashMap<>();
        classmap.put("lecturename",lecname);
        classmap.put("date",clzadddate);
        classmap.put("time",clzaddtime);
        classmap.put("classtitle",clztitle);
        classmap.put("classmassage",clzmassage);
        classmap.put("classtype",clztype);
        classmap.put("classlink",clzlink);
        classmap.put("classbatch",clzbatchsl);

        classref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Class").child(clztype+clzbatchsl).exists()))
                {

                    classref.child("Class").child(clztype+clzbatchsl).updateChildren(classmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Intent intent =new Intent(LecturersaddclassActivity.this, LecturersdashboardActivity.class);
                                        startActivity(intent);
                                        lodingbar.dismiss();
                                        Toast.makeText(LecturersaddclassActivity.this, "Class is adding Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(LecturersaddclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
                else{

                    classref.child("Class").child(clztype+clzbatchsl).updateChildren(classmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Intent intent =new Intent(LecturersaddclassActivity.this, LecturersdashboardActivity.class);
                                        startActivity(intent);
                                        lodingbar.dismiss();
                                        Toast.makeText(LecturersaddclassActivity.this, "Class is adding Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                        lodingbar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(LecturersaddclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lodingbar.dismiss();
                String message = databaseError.toString();
                Toast.makeText(LecturersaddclassActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        clzbatchsl =parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
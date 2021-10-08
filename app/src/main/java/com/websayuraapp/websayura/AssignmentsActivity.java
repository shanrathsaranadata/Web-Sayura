package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.Students.StudentsdashboardActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

import static com.websayuraapp.websayura.R.drawable.ic_photo;

public class AssignmentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText assignmentstitle,assignmentsmassage;
    private Spinner assignments;
    private ImageView assignmentsbrowserbtn;
    private Button sendassignmentsbtn;
    private ProgressDialog lodingbar;
    private String studentname,assadddateandtime,asstitle,assmassage,studenttype,asssl,studentcourse;
    private DatabaseReference assignmentref;
    private Uri fileuri;
    private String myuri="";
    private StorageTask uploadTask;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        assignmentstitle = findViewById(R.id.assignments_titleet);
        assignmentsmassage = findViewById(R.id.assignments_massageet);
        sendassignmentsbtn = findViewById(R.id.assignments_sendbtn);
        assignmentsbrowserbtn = findViewById(R.id.assignments_browserbtn);


        assignments= findViewById(R.id.assignments_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Assignments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignments.setAdapter(adapter);

        assignments.setOnItemSelectedListener(this);
        lodingbar=new ProgressDialog(this);
        Paper.init(this);

        assignmentref = FirebaseDatabase.getInstance().getReference().child("Assignments");
        storageReference = FirebaseStorage.getInstance().getReference().child("Assignments");

        assignmentsbrowserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        sendassignmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendassignments();
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_floabtnstudent_assignments);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AssignmentsActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsstudent_assignments);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(AssignmentsActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(AssignmentsActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(AssignmentsActivity.this, StudentsdashboardActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(AssignmentsActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(AssignmentsActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });


    }

    private void openFile() {

        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("file/*");
        startActivityForResult(galleryintent,1);
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            fileuri=data.getData();
        }
        else{
            Toast.makeText(this, "Error  Uploading Please Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AssignmentsActivity.this,AssignmentsActivity.class));
            finish();
        }
    }

    private void sendassignments() {



        if (TextUtils.isEmpty(assignmentstitle.getText().toString())) {

            Toast.makeText(this, " Please typing is Assignments title....", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(assignmentsmassage.getText().toString())) {

            Toast.makeText(this, " Please typing is Assignments massage....", Toast.LENGTH_SHORT).show();
        }
        else if (asssl.equals("Select Assignments Type")) {

            Toast.makeText(this, "Please Select Assignments Type.....", Toast.LENGTH_SHORT).show();
        }
        else if (fileuri == null) {

            Toast.makeText(this, "Please Upload Assignments zip file.....", Toast.LENGTH_SHORT).show();

        }
        else {

            lodingbar.setTitle("Uploading Assignments");
            lodingbar.setMessage("Dear Students, please wait,while we are uploading the Assignments");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();

            final StorageReference fileref = storageReference
                    .child(Prevalite.CurrentonlineUsers.getUsername() + asssl + ".zip");

            uploadTask = fileref.putFile(fileuri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {

                        Uri downloadUri = task.getResult();
                        myuri = downloadUri.toString();

                        Calendar calender = Calendar.getInstance();

                        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                        String discussiondate = currentdate.format(calender.getTime());

                        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                        String discussiontime = currenttime.format(calender.getTime());

                        assadddateandtime = discussiondate + " " + discussiontime;
                        studentname = Prevalite.CurrentonlineUsers.getUsername();
                        studentcourse = Prevalite.CurrentonlineUsers.getCourses();
                        studenttype = Paper.book().read(Prevalite.Usertypekey);
                        asstitle = assignmentstitle.getText().toString();
                        assmassage = assignmentsmassage.getText().toString();

                        HashMap<String, Object> assignmentmap = new HashMap<>();
                        assignmentmap.put("assignmentdateandtime", assadddateandtime);
                        assignmentmap.put("assignmentusername", studentname);
                        assignmentmap.put("assignmentcourse", studentcourse);
                        assignmentmap.put("assignmenttype", studenttype);
                        assignmentmap.put("assignmenttitle", asstitle);
                        assignmentmap.put("assignmentmassage", assmassage);
                        assignmentmap.put("assignmentfile", myuri);

                        assignmentref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!(dataSnapshot.child(studentname+asssl).exists())) {

                                    assignmentref.child(studentname+asssl).updateChildren(assignmentmap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AssignmentsActivity.this, "Sending you Assignments", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(AssignmentsActivity.this, StudentsdashboardActivity.class));
                                                        lodingbar.dismiss();

                                                    } else {

                                                        String message = task.getException().toString();
                                                        Toast.makeText(AssignmentsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        lodingbar.dismiss();

                                                    }
                                                }
                                            });
                                }
                                else {

                                    assignmentref.child(studentname+asssl).updateChildren(assignmentmap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(AssignmentsActivity.this, "Sending you Assignments", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(AssignmentsActivity.this, StudentsdashboardActivity.class));
                                                        lodingbar.dismiss();

                                                    } else {

                                                        String message = task.getException().toString();
                                                        Toast.makeText(AssignmentsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        lodingbar.dismiss();

                                                    }
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                String message = databaseError.toString();
                                Toast.makeText(AssignmentsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        asssl=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, asssl, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.websayuraapp.websayura;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.websayuraapp.websayura.Lecturers.LecturersHomeActivity;
import com.websayuraapp.websayura.Lecturers.LecturersaddclassActivity;
import com.websayuraapp.websayura.Lecturers.LecturersdashboardActivity;
import com.websayuraapp.websayura.Model.Discussion;
import com.websayuraapp.websayura.Model.Joinclass;
import com.websayuraapp.websayura.Prevalite.Prevalite;
import com.websayuraapp.websayura.Viewholder.DiscussionViewHolder;
import com.websayuraapp.websayura.Viewholder.JoinclassViewHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

import static com.websayuraapp.websayura.R.drawable.ic_photo;

public class DiscussionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText discussionet;
    private ImageView discussionsendbtn,discussionuploadiv;
    private String slectedtype;
    private Spinner courses_spinner;
    private String disusername,disdateandtime,distype,discourse,dis_massage;
    private DatabaseReference discussionref,notificationref;
    private RecyclerView discussionlist;
    RecyclerView.LayoutManager layoutManager;

    private Uri imageuri;
    private String myuri="";
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private ProgressDialog lodingbar;
    private BitmapDrawable drawable;
    private Bitmap bitmap;
    private long difference_In_Days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        storageReference = FirebaseStorage.getInstance().getReference().child("Discussion");

        discussionet = findViewById(R.id.discussionet);
        discussionsendbtn = findViewById(R.id.discussionsendbtn);
        discussionuploadiv = findViewById(R.id.discussionuploadimage);

        discussionref = FirebaseDatabase.getInstance().getReference().child("Discussion");
        notificationref = FirebaseDatabase.getInstance().getReference().child("Notification");
        Paper.init(this);

        discussionlist=findViewById(R.id.recyclerdiscussion);
        discussionlist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        discussionlist.setLayoutManager(layoutManager);

        courses_spinner= findViewById(R.id.discussionspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Courses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courses_spinner.setAdapter(adapter);
        courses_spinner.setOnItemSelectedListener(this);
        lodingbar=new ProgressDialog(this);

        discussionsendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddiscussion();
            }
        });

        discussionuploadiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        FloatingActionButton fab = findViewById(R.id.home_floabtndiscussion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(DiscussionActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsdiscussion);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(DiscussionActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(DiscussionActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(DiscussionActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(DiscussionActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(DiscussionActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    private void openGallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
            discussionuploadiv.setImageURI(imageuri);
        }
        else{
            Toast.makeText(this, "Error  Uploading Please Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DiscussionActivity.this,DiscussionActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Discussion> options=
                new FirebaseRecyclerOptions.Builder<Discussion>().setQuery(discussionref.orderByChild("discussiondateandtime"),Discussion.class)
                        .build();
        FirebaseRecyclerAdapter<Discussion, DiscussionViewHolder> adapter=
                new FirebaseRecyclerAdapter<Discussion, DiscussionViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position, @NonNull Discussion model) {


                        Calendar calender = Calendar.getInstance();

                        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                        String discussiondate = currentdate.format(calender.getTime());

                        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                        String discussiontime = currenttime.format(calender.getTime());

                        disdateandtime = discussiondate + " " + discussiontime;

                        findDifference(model.getDiscussiondateandtime(),disdateandtime);

                        if(difference_In_Days<=3){

                            if(model.getDiscussionimage().equals("noimage")) {

                                holder.discussion_usernme.setText(model.getDiscussionusername());
                                holder.discussion_course.setText(model.getDiscussioncourse());
                                holder.discussion_dateandtime.setText(model.getDiscussiondateandtime());
                                holder.discussion_type.setText(model.getDiscussiontype());
                                holder.discussion_massage.setText(model.getDiscussionmassage());
                                holder.cartdiscussionuploadimage.setVisibility(View.INVISIBLE);


                            }
                            else {

                                holder.discussion_usernme.setText(model.getDiscussionusername());
                                holder.discussion_course.setText(model.getDiscussioncourse());
                                holder.discussion_dateandtime.setText(model.getDiscussiondateandtime());
                                holder.discussion_type.setText(model.getDiscussiontype());
                                holder.discussion_massage.setText(model.getDiscussionmassage());
                                Picasso.get().load(model.getDiscussionimage()).into(holder.cartdiscussionuploadimage);

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        FileOutputStream outputStream = null;

                                        drawable = (BitmapDrawable) holder.cartdiscussionuploadimage.getDrawable();
                                        bitmap = drawable.getBitmap();

                                        File sdcard = Environment.getExternalStorageDirectory();
                                        File director = new File(sdcard.getAbsolutePath(), "/Discussionimage");
                                        director.mkdir();

                                        String filename = String.format("%d.jpg", System.currentTimeMillis());
                                        File outfile = new File(director, filename);
                                        Toast.makeText(DiscussionActivity.this, "Image Save Successfully", Toast.LENGTH_SHORT).show();

                                        try {

                                            outputStream = new FileOutputStream(outfile);
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                            outputStream.flush();
                                            outputStream.close();

                                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                            intent.setData(Uri.fromFile(outfile));
                                            sendBroadcast(intent);

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });
                            }

                        }


                    }

                    @NonNull
                    @Override
                    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discussion,viewGroup,false);
                        DiscussionViewHolder holder =new DiscussionViewHolder(view);
                        return holder;
                    }
                };

        discussionlist.setAdapter(adapter);
        adapter.startListening();
    }



    private void senddiscussion() {

        if (TextUtils.isEmpty(discussionet.getText().toString())) {

            Toast.makeText(this, " Please typing is Discussion....", Toast.LENGTH_SHORT).show();
        }

        else if (slectedtype.equals("Select Courses Type")) {

            Toast.makeText(this, "Please Select Courses Type.....", Toast.LENGTH_SHORT).show();
        }

        else {
            if (imageuri != null) {

                lodingbar.setTitle("Uploading Image");
                lodingbar.setMessage("Dear Students, please wait,while we are uploading the Image");
                lodingbar.setCanceledOnTouchOutside(false);
                lodingbar.show();

                final StorageReference fileref = storageReference
                        .child(Prevalite.CurrentonlineUsers.getUsername() + random()+ ".jpg");

                uploadTask = fileref.putFile(imageuri);

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

                            Uri downloadUri =task.getResult();
                            myuri =downloadUri.toString();

                            Calendar calender = Calendar.getInstance();

                            SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                            String discussiondate = currentdate.format(calender.getTime());

                            SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                            String discussiontime = currenttime.format(calender.getTime());

                            disdateandtime = discussiondate + " " + discussiontime;
                            disusername = Prevalite.CurrentonlineUsers.getUsername();
                            discourse = Prevalite.CurrentonlineUsers.getCourses();
                            distype = Paper.book().read(Prevalite.Usertypekey);
                            dis_massage = discussionet.getText().toString();

                            HashMap<String, Object> discussionmap = new HashMap<>();
                            discussionmap.put("discussiondateandtime", disdateandtime);
                            discussionmap.put("discussionusername", disusername);
                            discussionmap.put("discussioncourse", discourse);
                            discussionmap.put("discussiontype", distype);
                            discussionmap.put("discussionmassage", dis_massage);
                            discussionmap.put("discussionimage", myuri);

                            discussionref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!(dataSnapshot.child(disusername + random()).exists())) {

                                        discussionref.child(disusername + random()).updateChildren(discussionmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(DiscussionActivity.this, "Sending you discussion", Toast.LENGTH_SHORT).show();
                                                            discussionet.setText(null);
                                                            discussionuploadiv.setImageResource(ic_photo);
                                                            imageuri = null;
                                                            lodingbar.dismiss();
                                                        } else {

                                                            String message = task.getException().toString();
                                                            Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                            lodingbar.dismiss();

                                                        }
                                                    }
                                                });
                                    } else {

                                        discussionref.child(disusername + random()).updateChildren(discussionmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(DiscussionActivity.this, "Sending you discussion", Toast.LENGTH_SHORT).show();
                                                            discussionet.setText(null);
                                                            discussionuploadiv.setImageResource(ic_photo);
                                                            imageuri = null;
                                                            lodingbar.dismiss();

                                                        } else {

                                                            String message = task.getException().toString();
                                                            Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                            lodingbar.dismiss();

                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    String message = databaseError.toString();
                                    Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                }

                            });

                            HashMap<String, Object> notificationmap = new HashMap<>();
                            notificationmap.put("dateandtime", disdateandtime);
                            notificationmap.put("name", disusername);
                            notificationmap.put("title", slectedtype + " " + distype);
                            notificationmap.put("text", dis_massage);
                            notificationmap.put("image", myuri);

                            notificationref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!(dataSnapshot.child(disusername + random()).exists())) {

                                        notificationref.child(disusername + random()).updateChildren(notificationmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(DiscussionActivity.this, "Sending you notification", Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            String message = task.getException().toString();
                                                            Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                        notificationref.child("mainnotification").updateChildren(notificationmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                        } else {

                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    String message = databaseError.toString();
                                    Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    }
                });
            }
            else {
                lodingbar.setTitle("Sending Messages");
                lodingbar.setMessage("Dear Students, please wait,while we are Sending the Messages");
                lodingbar.setCanceledOnTouchOutside(false);
                lodingbar.show();
                Calendar calender = Calendar.getInstance();

                SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                String discussiondate = currentdate.format(calender.getTime());

                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                String discussiontime = currenttime.format(calender.getTime());

                disdateandtime = discussiondate + " " + discussiontime;
                disusername = Prevalite.CurrentonlineUsers.getUsername();
                discourse = Prevalite.CurrentonlineUsers.getCourses();
                distype = Paper.book().read(Prevalite.Usertypekey);
                dis_massage = discussionet.getText().toString();

                HashMap<String, Object> discussionmap = new HashMap<>();
                discussionmap.put("discussiondateandtime", disdateandtime);
                discussionmap.put("discussionusername", disusername);
                discussionmap.put("discussioncourse", discourse);
                discussionmap.put("discussiontype", distype);
                discussionmap.put("discussionmassage", dis_massage);
                discussionmap.put("discussionimage", "noimage");

                discussionref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child(disusername + random()).exists())) {

                            discussionref.child(disusername + random()).updateChildren(discussionmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DiscussionActivity.this, "Sending you discussion", Toast.LENGTH_SHORT).show();
                                                discussionet.setText(null);
                                                discussionuploadiv.setImageResource(ic_photo);
                                                imageuri = null;
                                                lodingbar.dismiss();
                                            } else {

                                                String message = task.getException().toString();
                                                Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                lodingbar.dismiss();

                                            }
                                        }
                                    });
                        } else {

                            discussionref.child(disusername + random()).updateChildren(discussionmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DiscussionActivity.this, "Sending you discussion", Toast.LENGTH_SHORT).show();
                                                discussionet.setText(null);
                                                discussionuploadiv.setImageResource(ic_photo);
                                                imageuri = null;
                                                lodingbar.dismiss();
                                            } else {

                                                String message = task.getException().toString();
                                                Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                lodingbar.dismiss();

                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String message = databaseError.toString();
                        Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }

                });

                HashMap<String, Object> notificationmap = new HashMap<>();
                notificationmap.put("dateandtime", disdateandtime);
                notificationmap.put("name", disusername);
                notificationmap.put("title", slectedtype + " " + distype);
                notificationmap.put("text", dis_massage);
                notificationmap.put("image", "noimage");

                notificationref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child(disusername + random()).exists())) {

                            notificationref.child(disusername + random()).updateChildren(notificationmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(DiscussionActivity.this, "Sending you notification", Toast.LENGTH_SHORT).show();
                                            } else {

                                                String message = task.getException().toString();
                                                Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                            notificationref.child("mainnotification").updateChildren(notificationmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            } else {

                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String message = databaseError.toString();
                        Toast.makeText(DiscussionActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }

                });
            }

        }
    }

    private static String random() {

        Random r = new Random();
        String bulidstring = null;
        for (int i = 0; i < 5; i++) {
            bulidstring= String.valueOf(r.nextInt());
        }
        return bulidstring;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectedtype=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, slectedtype, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void findDifference(String start_date,
                                  String end_date){
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "MMM dd, yyyy HH:mm:ss");

        try {

            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            long difference_In_Seconds
                    = TimeUnit.MILLISECONDS
                    .toSeconds(difference_In_Time)
                    % 60;

            long difference_In_Minutes
                    = TimeUnit
                    .MILLISECONDS
                    .toMinutes(difference_In_Time)
                    % 60;

            long difference_In_Hours
                    = TimeUnit
                    .MILLISECONDS
                    .toHours(difference_In_Time)
                    % 24;

            difference_In_Days
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    % 365;

            long difference_In_Years
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    / 365l;

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
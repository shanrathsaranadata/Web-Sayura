package com.websayuraapp.websayura.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.websayuraapp.websayura.HomeActivity;
import com.websayuraapp.websayura.LoginActivity;
import com.websayuraapp.websayura.MainActivity;
import com.websayuraapp.websayura.MenuActivity;
import com.websayuraapp.websayura.Model.Discussion;
import com.websayuraapp.websayura.Model.Users;
import com.websayuraapp.websayura.MoreActivity;
import com.websayuraapp.websayura.ProfileActivity;
import com.websayuraapp.websayura.R;
import com.websayuraapp.websayura.Viewholder.DiscussionViewHolder;
import com.websayuraapp.websayura.Viewholder.UsersViewHolder;

import java.util.HashMap;

import io.paperdb.Paper;

public class AdminmaintainusersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner users_spinner;
    private DatabaseReference usersref;
    private RecyclerView userslist;
    private Button anubtn;
    RecyclerView.LayoutManager layoutManager;
    private String slectedtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmaintainusers);

        users_spinner= findViewById(R.id.users_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users_spinner.setAdapter(adapter);
        users_spinner.setOnItemSelectedListener(this);

        userslist=findViewById(R.id.recycleradminmaintainusers);
        userslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        userslist.setLayoutManager(layoutManager);

        usersref = FirebaseDatabase.getInstance().getReference();

        anubtn = findViewById(R.id.admin_addnewuserbtn);

        anubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminmaintainusersActivity.this, AdminaddnewusersActivity.class);
                startActivity(intent);


            }
        });


        FloatingActionButton fab = findViewById(R.id.home_floabtnadminmaintainusers);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminmaintainusersActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationsadminmaintainusers);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(AdminmaintainusersActivity.this, MenuActivity.class));
                        break;

                    case R.id.navigation_logout:
                        Paper.book().destroy();
                        Intent intent=new Intent(AdminmaintainusersActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        startActivity(new Intent(AdminmaintainusersActivity.this, LoginActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(AdminmaintainusersActivity.this, ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(AdminmaintainusersActivity.this, MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectedtype=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, slectedtype, Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(slectedtype) || slectedtype.equals("Select Users Type")){
            Toast.makeText(this, "Please Select Users Type.....", Toast.LENGTH_SHORT).show();
        }
        else {
            maintainusersdetelis();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void maintainusersdetelis(){

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>().setQuery(usersref.child(slectedtype).orderByChild("username"), Users.class)
                        .build();
        FirebaseRecyclerAdapter<Users, UsersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

                        holder.users_usernameet.setText(model.getUsername());
                        holder.users_nameet.setText(model.getName());
                        holder.users_passwordet.setText(model.getPassword());
                        holder.users_emailet.setText(model.getEmail());
                        holder.users_courseset.setText(model.getCourses());

                        holder.users_updatebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "yes",
                                                "no"

                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminmaintainusersActivity.this);
                                builder.setTitle("Do you want to Update this User. Are you");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i==0){

                                            Updateuserinfo(holder.users_usernameet,holder.users_nameet,holder.users_passwordet,holder.users_emailet,holder.users_courseset);
                                            dialogInterface.dismiss();

                                        }

                                        if(i==1){

                                            dialogInterface.dismiss();


                                        }


                                    }
                                });
                                builder.show();
                            }
                        });

                        holder.users_deletebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "yes",
                                                "no"

                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminmaintainusersActivity.this);
                                builder.setTitle("Do you want to Delete this Users. Are you");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i==0){

                                            Deleteuserinfo(model.getUsername());
                                            dialogInterface.dismiss();


                                        }

                                        if(i==1){

                                            dialogInterface.dismiss();


                                        }


                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users, viewGroup, false);
                        UsersViewHolder holder = new UsersViewHolder(view);
                        return holder;
                    }
                };

        userslist.setAdapter(adapter);
        adapter.startListening();
    }

    private void Deleteuserinfo(String username) {

        usersref.child(slectedtype).child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override

             public void onComplete(@NonNull Task<Void> task) {
              Toast.makeText(AdminmaintainusersActivity.this, username+" is Deleted successfully", Toast.LENGTH_SHORT).show();
                maintainusersdetelis();
            }
        });

    }

    private void Updateuserinfo(EditText username, EditText name, EditText password, EditText email, EditText courses) {

        if(username.getText().toString().equals("")){
            Toast.makeText(this, "Write down User Username", Toast.LENGTH_SHORT).show();
        }
        else  if(name.getText().toString().equals("")){
            Toast.makeText(this, "Write down User nme", Toast.LENGTH_SHORT).show();
        }
        else  if(password.getText().toString().equals("")){
            Toast.makeText(this, "Write down User Password", Toast.LENGTH_SHORT).show();
        }
        else  if(email.getText().toString().equals("")){
            Toast.makeText(this, "Write down User Email", Toast.LENGTH_SHORT).show();
        }
        else  if(courses.getText().toString().equals("")){
            Toast.makeText(this, "Write down User Courses", Toast.LENGTH_SHORT).show();
        }
        else{

            HashMap<String,Object> usermap = new HashMap<>();
            usermap.put("username",username.getText().toString());
            usermap.put("name",name.getText().toString());
            usermap.put("password",password.getText().toString());
            usermap.put("email",email.getText().toString());
            usermap.put("courses",courses.getText().toString());

            usersref.child(slectedtype).child(username.getText().toString()).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(AdminmaintainusersActivity.this, username.getText().toString()+" is Update successfully", Toast.LENGTH_SHORT).show();
                        maintainusersdetelis();

                    }

                }
            });

        }

    }
}
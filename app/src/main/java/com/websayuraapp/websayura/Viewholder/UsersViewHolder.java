package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public EditText users_usernameet,users_emailet,users_nameet,users_courseset,users_passwordet;
    public Button users_updatebtn,users_deletebtn;
    public ItemClickListner listner;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);

        users_usernameet = itemView.findViewById(R.id.users_username);
        users_emailet = itemView.findViewById(R.id.users_email);
        users_nameet = itemView.findViewById(R.id.users_name);
        users_courseset =itemView.findViewById(R.id.users_courses);
        users_passwordet =itemView.findViewById(R.id.users_password);
        users_updatebtn=itemView.findViewById(R.id.users_updatebtn);
        users_deletebtn=itemView.findViewById(R.id.users_deletebtn);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

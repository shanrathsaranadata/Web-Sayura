package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

public class ProjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Button projectsbutton;
    public ItemClickListner listner;

    public ProjectsViewHolder(@NonNull View itemView) {
        super(itemView);

        projectsbutton= itemView.findViewById(R.id.allprojects);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

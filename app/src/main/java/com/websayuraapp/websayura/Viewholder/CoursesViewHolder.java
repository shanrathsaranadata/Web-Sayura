package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

public class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView courses_name,courses_fee,courses_level,courses_description,courses_enrolled,courses_duration;
    public ImageView cartcourses_image;
    public ItemClickListner listner;

    public CoursesViewHolder(@NonNull View itemView) {
        super(itemView);

        courses_name = itemView.findViewById(R.id.course_name);
        courses_fee = itemView.findViewById(R.id.course_fee);
        courses_duration = itemView.findViewById(R.id.course_duration);
        courses_level =itemView.findViewById(R.id.course_level);
        courses_enrolled =itemView.findViewById(R.id.course_enrolled);
        courses_description=itemView.findViewById(R.id.course_description);
        cartcourses_image=itemView.findViewById(R.id.course_image);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

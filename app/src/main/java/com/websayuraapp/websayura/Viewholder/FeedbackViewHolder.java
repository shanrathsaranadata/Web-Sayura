package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView feedback_sname,feedback_role,feedback_description;
    public CircleImageView feedback_sprofileimage;
    public ImageView cartfeedback_image;
    public ItemClickListner listner;

    public FeedbackViewHolder(@NonNull View itemView) {
        super(itemView);
        feedback_sname = itemView.findViewById(R.id.feedback_studentname);
        feedback_role = itemView.findViewById(R.id.feedback_studentrole);
        feedback_description =itemView.findViewById(R.id.feedback_description);
        feedback_sprofileimage =itemView.findViewById(R.id.feedbackstudentsimage);
        cartfeedback_image=itemView.findViewById(R.id.feedback_image);
    }

    @Override
    public void onClick(View v) {

    }
    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }
}

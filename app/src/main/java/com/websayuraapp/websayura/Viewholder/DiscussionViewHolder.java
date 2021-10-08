package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscussionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView discussion_usernme,discussion_dateandtime,discussion_type,discussion_course,discussion_massage;
    public CircleImageView cartdiscussion_profile;
    public ImageView cartdiscussionuploadimage;
    public ItemClickListner listner;

    public DiscussionViewHolder(@NonNull View itemView) {
        super(itemView);

        cartdiscussion_profile = itemView.findViewById(R.id.course_name);
        discussion_usernme = itemView.findViewById(R.id.discussion_usernme);
        discussion_dateandtime = itemView.findViewById(R.id.discussion_dateandtime);
        discussion_type =itemView.findViewById(R.id.discussion_type);
        discussion_course =itemView.findViewById(R.id.discussion_course);
        discussion_massage=itemView.findViewById(R.id.discussion_massage);
        cartdiscussionuploadimage = itemView.findViewById(R.id.discussionuploadimagecart);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

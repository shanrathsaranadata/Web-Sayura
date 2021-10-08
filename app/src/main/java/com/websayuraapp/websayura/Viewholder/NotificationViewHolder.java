package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView notification_usernametv,notification_dateandtimetv,notification_titletv,notification_texttv;
    public ImageView cartnotification_image;
    public ItemClickListner listner;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        cartnotification_image = itemView.findViewById(R.id.notification_image);
        notification_usernametv = itemView.findViewById(R.id.notification_name);
        notification_dateandtimetv = itemView.findViewById(R.id.notification_dateandtime);
        notification_titletv =itemView.findViewById(R.id.notification_title);
        notification_texttv =itemView.findViewById(R.id.notification_text);

    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

public class JoinclassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView joinclass_title,joinclass_lecname,joinclass_batch,joinclass_dateandtime,joinclass_massage;
    public Button joinclzbtn;
    public ItemClickListner listner;

    public JoinclassViewHolder(@NonNull View itemView) {
        super(itemView);

        joinclass_title = itemView.findViewById(R.id.joinclass_title);
        joinclass_lecname = itemView.findViewById(R.id.joinclass_lecname);
        joinclass_batch = itemView.findViewById(R.id.joinclass_batch);
        joinclass_dateandtime =itemView.findViewById(R.id.joinclass_dateandtime);
        joinclass_massage =itemView.findViewById(R.id.joinclass_massage);
        joinclzbtn=itemView.findViewById(R.id.joinclassbtncart);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

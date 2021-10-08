package com.websayuraapp.websayura.Viewholder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websayuraapp.websayura.Interface.ItemClickListner;
import com.websayuraapp.websayura.R;

public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Button videosbutton;
    public ItemClickListner listner;

    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);

        videosbutton= itemView.findViewById(R.id.zoomvideosbtns);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}

package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.ImageFullScreenActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AmbulanceModel;
import com.doctor.telemedicine.model.DocumentModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class DocumentLitsAdapterDoctor extends RecyclerView.Adapter<DocumentLitsAdapterDoctor.MyViewHolder> {
    List<DocumentModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        RelativeLayout relative_container;
        ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            imageView = (ImageView) view.findViewById(R.id.image);


        }
    }


    public DocumentLitsAdapterDoctor(List<DocumentModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_document_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DocumentModel movie = list.get(position);
        context = holder.tv_title.getContext();
        holder.tv_title.setText(movie.getTitle());
        Glide.with(context).load(PHOTO_BASE + movie.getPhoto()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ImageFullScreenActivity.class);
                intent.putExtra("link",PHOTO_BASE+movie.getPhoto());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
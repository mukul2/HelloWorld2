package com.doctor.telemedicine.adapter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.BlogDetailActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.BlogModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_BLOG;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class BlogAdapterPatient extends RecyclerView.Adapter<BlogAdapterPatient.MyViewHolder> {
    List<BlogModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_drName, tv_department, tv_body, tv_time, tv_title;
        ImageView circleImageView;
        RelativeLayout relative_container;
        CircleImageView profile;
        RecyclerView recycler_view;
        ImageView blog_cover;


        public MyViewHolder(View view) {
            super(view);
            tv_drName = (TextView) view.findViewById(R.id.tv_drName);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_department = (TextView) view.findViewById(R.id.tv_department);
            tv_body = (TextView) view.findViewById(R.id.tv_body);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            profile = (CircleImageView) view.findViewById(R.id.profile);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            blog_cover = (ImageView) view.findViewById(R.id.blog_cover);


        }
    }


    public BlogAdapterPatient(List<BlogModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BlogModel movie = list.get(position);
        context = holder.tv_drName.getContext();
        if (movie.getDrInfo() != null) {
            holder.tv_drName.setText(movie.getDrInfo().getName());
            Glide.with(context).load(PHOTO_BASE + movie.getDrInfo().getPhoto()).into(holder.profile);


        } else {
            holder.tv_drName.setText("No Name");
            holder.tv_department.setText("No Dept");
            Glide.with(context).load(Color.GRAY).into(holder.profile);


        }
        if ( movie.getDrInfo().getDepartment_info()!=null){
            holder.tv_department.setText(movie.getDrInfo().getDepartment_info().getName());
        }else {
            holder.tv_department.setText("No data");

        }

        holder.tv_title.setText(movie.getTitle());
        holder.tv_body.setText(movie.getBody());
        if (movie.getPhotoInfo().size() > 0) {
            Glide.with(context).load(PHOTO_BASE + movie.getPhotoInfo().get(0).getPhoto()).into(holder.blog_cover);
            // Glide.with(context).load("https://img1.thelist.com/img/gallery/this-is-what-gal-gadot-really-eats-in-a-day/intro-1523885167.jpg").into(holder.blog_cover);

        } else {
            Glide.with(context).load(Color.GRAY).into(holder.blog_cover);
        }

      /*  GallaryAdapterOnline  mAdapter = new GallaryAdapterOnline(context, movie.getPhotoInfo());
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        holder.recycler_view.setLayoutManager(_sGridLayoutManager);
        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
        holder.recycler_view.setAdapter(mAdapter);
        */


        holder.tv_time.setText(DataStore.changeDateformate(movie.getCreatedAt()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_BLOG = movie;

                context.startActivity(new Intent(context, BlogDetailActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
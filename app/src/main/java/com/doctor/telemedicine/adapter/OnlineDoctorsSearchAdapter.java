package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.DoctorsFullProfileView;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.OnlineDoctorsModel;
import com.doctor.telemedicine.model.SearchDoctorModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_DOC;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;

/**
 * Created by mukul on 3/10/2019.
 */


public class OnlineDoctorsSearchAdapter extends RecyclerView.Adapter<OnlineDoctorsSearchAdapter.MyViewHolder> {

    Context context;
    List<OnlineDoctorsModel>list=new ArrayList<>();
    DrClicked drClicked;
    public  interface DrClicked{
        void  onClicked();
    }

    public void setDrClicked(DrClicked d) {
        this.drClicked = d;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist,tv_address,tv_department;
        CircleImageView profile;
        RelativeLayout relative_container;
        LinearLayout linerBody;
        RecyclerView recycler_view;
        ImageView imageCover;


        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_department = (TextView) view.findViewById(R.id.tv_department);
            profile = (CircleImageView) view.findViewById(R.id.profile);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            imageCover = (ImageView) view.findViewById(R.id.imageCover);



        }
    }


    public OnlineDoctorsSearchAdapter(List<OnlineDoctorsModel>data) {
        this.list=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorsModel data = list.get(position);
        context = holder.tv_title.getContext();
        holder.tv_title.setText(data.getName());
     //   holder.tv_department.setText(data.get().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_ONLINE_DOC=data;
                context.startActivity(new Intent(context, DoctorsFullProfileView.class));
            }
        });
        if (data.getPhoto()!=null) {
            Glide.with(context).load(PHOTO_BASE + data.getPhoto()).into(holder.profile);
            Glide.with(context).load(PHOTO_BASE + data.getPhoto()).into(holder.imageCover);
        }
/*        DrOnlineServicesAdapter mAdapter = new DrOnlineServicesAdapter(data.getOnlineDoctorsServiceInfo());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.recycler_view.setLayoutManager(layoutManager);
        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
        holder.recycler_view.setAdapter(mAdapter);
        mAdapter.setSerViceClicked(new DrOnlineServicesAdapter.SerViceClicked() {
            @Override
            public void onServiceClicked() {
                NOW_SHOWING_ONLINE_DOC=data;
                context.startActivity(new Intent(context, DoctorsFullProfileView.class));
            }
        });

 */
        if (data.getDesignation_title()!=null){
            holder.tv_department.setText(data.getDesignation_title());
        }else {
            holder.tv_department.setText("Not Specified");

        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.AmbulanceDetailActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AmbulanceModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_AMBULANCE;

/**
 * Created by mukul on 3/10/2019.
 */


public class AmbulanceLitsAdapterAdmin extends RecyclerView.Adapter<AmbulanceLitsAdapterAdmin.MyViewHolder> {
    List<AmbulanceModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_hospitalName, tv_body, tv_lastDegree, tv_epacialist, tv_address;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            tv_body = (TextView) view.findViewById(R.id.tv_body);


        }
    }


    public AmbulanceLitsAdapterAdmin(List<AmbulanceModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ambulance_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AmbulanceModel movie = list.get(position);
        context = holder.title.getContext();
        holder.title.setText(movie.getTitle());
        holder.tv_body.setText(movie.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_AMBULANCE=movie;
                context.startActivity(new Intent(context, AmbulanceDetailActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;

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

import com.doctor.telemedicine.Activity.DoctorsSwipeActivity;
import com.doctor.telemedicine.Activity.OnlinDoctorsSwipeActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.OnlineDoctorModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.Data.singleDrModel;

/**
 * Created by mukul on 3/10/2019.
 */


public class OnlineDrListAdapter extends RecyclerView.Adapter<OnlineDrListAdapter.MyViewHolder> {

    Context context;
    List<OnlineDoctorModel>list=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist,tv_address;
        ImageView circleImageView;
        RelativeLayout relative_container;
        LinearLayout linerBody;
        RecyclerView recycler_view;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_name);
            tv_epacialist = (TextView) view.findViewById(R.id.tv_epacialist);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);



        }
    }


    public OnlineDrListAdapter(List<OnlineDoctorModel>data) {
        this.list=data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_dr_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorModel movie = list.get(position);
        context = holder.title.getContext();
        holder.title.setText(movie.getName());
        holder.tv_epacialist.setText(movie.getDepartment());

        //DrOnlineServicesAdapter mAdapter = new DrOnlineServicesAdapter(movie.getDrService());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.recycler_view.setLayoutManager(layoutManager);
        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
     //   holder.recycler_view.setAdapter(mAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, OnlinDoctorsSwipeActivity.class);
                i.putExtra("currentIndex",""+position);
                //context.startActivity(new Intent(context, ChamberDetailActivity.class));
                context.startActivity(i);
            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
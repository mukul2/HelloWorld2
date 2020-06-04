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

import com.doctor.telemedicine.Activity.DialingActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.VideoCallModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.CurentCallDr;

/**
 * Created by mukul on 3/10/2019.
 */


public class CurrentlyOnlineDoctorAdapter extends RecyclerView.Adapter<CurrentlyOnlineDoctorAdapter.MyViewHolder> {
    List<VideoCallModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_department,tv_degree;
        ImageView imageCall;



        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_department = (TextView) view.findViewById(R.id.tv_department);
            tv_degree = (TextView) view.findViewById(R.id.tv_degree);
            imageCall = (ImageView) view.findViewById(R.id.imageCall);



        }
    }


    public CurrentlyOnlineDoctorAdapter(List<VideoCallModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_call_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VideoCallModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getDrName());
        holder.tv_department.setText(movie.getSpecialist());
        holder.tv_degree.setText(movie.getLastDegree());
        holder.imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurentCallDr= movie;
                context.startActivity(new Intent(context, DialingActivity.class));

            }
        });



    }




    @Override
    public int getItemCount() {
        return list.size();
    }
}
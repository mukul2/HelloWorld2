package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.EducationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class EducationsAdapterDoctor extends RecyclerView.Adapter<EducationsAdapterDoctor.MyViewHolder> {
    List<EducationInfo>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_body;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_body = (TextView) view.findViewById(R.id.tv_body);



        }
    }


    public EducationsAdapterDoctor(List<EducationInfo> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.education_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final EducationInfo data = list.get(position);
        context = holder.tv_title.getContext();
        holder.tv_title.setText(data.getTitle());
        holder.tv_body.setText(data.getBody());


    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
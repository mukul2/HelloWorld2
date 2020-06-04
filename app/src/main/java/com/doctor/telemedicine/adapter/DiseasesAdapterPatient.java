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
import com.doctor.telemedicine.model.DiseasesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class DiseasesAdapterPatient extends RecyclerView.Adapter<DiseasesAdapterPatient.MyViewHolder> {
    List<DiseasesModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_diseases, tv_started, tv_status, tv_lastDegree, tv_epacialist,tv_address;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_diseases = (TextView) view.findViewById(R.id.tv_diseases);
            tv_started = (TextView) view.findViewById(R.id.tv_started);
            tv_status = (TextView) view.findViewById(R.id.tv_status);



        }
    }


    public DiseasesAdapterPatient(List<DiseasesModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diseases_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DiseasesModel movie = list.get(position);
        context = holder.tv_diseases.getContext();
        holder.tv_diseases.setText(movie.getDiseaseName());
        holder.tv_started.setText(": "+movie.getFirstNoticeDate());
        holder.tv_status.setText(": "+movie.getStatus());


    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
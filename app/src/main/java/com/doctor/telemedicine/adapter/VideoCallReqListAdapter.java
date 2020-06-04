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
import com.doctor.telemedicine.Activity.AmbulanceDetailActivity;
import com.doctor.telemedicine.Activity.ChatActivityCommon;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AmbulanceModel;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.VideoAppointmentModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_AMBULANCE;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

/**
 * Created by mukul on 3/10/2019.
 */


public class VideoCallReqListAdapter extends RecyclerView.Adapter<VideoCallReqListAdapter.MyViewHolder> {
    List<VideoAppointmentModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_name, tv_body, tv_lastDegree, tv_epacialist, tv_address,tv_mark_done;
        ImageView img_profile;


        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_mark_done = (TextView) view.findViewById(R.id.tv_mark_done);
            img_profile = (ImageView) view.findViewById(R.id.img_profile);


        }
    }


    public VideoCallReqListAdapter(List<VideoAppointmentModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_call_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VideoAppointmentModel movie = list.get(position);
        context = holder.tv_date.getContext();
        holder.tv_name.setText(movie.getPatientInfo().getName());
        holder.tv_date.setText(movie.getCreatedAt());
       // Glide.with(context).load(PHOTO_BASE+movie.getPatientInfo().getPhoto()).into(holder.img_profile);
        if (movie.getAppointmentStatus()==0){
            holder.tv_mark_done.setText("Mark as Seved");
        }else {
            holder.tv_mark_done.setText("Served");

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivityCommon.class);
                i.putExtra("partner_id", String.valueOf(movie.getPatientId()));
                i.putExtra("partner_name", movie.getPatientInfo().getName());
                i.putExtra("partner_photo", movie.getPatientInfo().getPhoto());
                context.startActivity(i);
            }
        });
        //change_video_appointment_status_done
        holder.tv_mark_done.setOnClickListener((View view)->{
            Api.getInstance().change_video_appointment_status_done(TOKEN, "" + movie.getId(), new ApiListener.basicApiListener() {
                @Override
                public void onBasicSuccess(StatusMessage response) {
                    holder. tv_mark_done.setText("Served");
                }

                @Override
                public void onBasicApiFailed(String msg) {

                }
            });
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.AmbulanceDetailActivity;
import com.doctor.telemedicine.Activity.NotificationDetailActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AmbulanceModel;
import com.doctor.telemedicine.model.NoticeModel;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_AMBULANCE;
import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_NOTICE;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

/**
 * Created by mukul on 3/10/2019.
 */


public class NotificationLitsAdapter extends RecyclerView.Adapter<NotificationLitsAdapter.MyViewHolder> {
    List<NoticeModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time, tv_hospitalName, tv_body, tv_lastDegree, tv_epacialist, tv_address;
        LinearLayout linearBody;


        public MyViewHolder(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_body = (TextView) view.findViewById(R.id.tv_body);
            linearBody = (LinearLayout) view.findViewById(R.id.linearBody);


        }
    }


    public NotificationLitsAdapter(List<NoticeModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NoticeModel movie = list.get(position);
        context = holder.tv_time.getContext();
        holder.tv_time.setText(movie.getCreatedAt());
        holder.tv_body.setText(movie.getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NOW_SHOWING_NOTICE = movie;
                list.get(position).setSeen(1);
                Api.getInstance().update_notification_status(TOKEN, "" + movie.getId(), new ApiListener.NoticesStatusUpdateListener() {
                    @Override
                    public void onNoticesStatusUpdateSuccess(StatusMessage list) {

                    }

                    @Override
                    public void onNoticesStatusUpdateFailed(String msg) {

                    }
                });

                context.startActivity(new Intent(context, NotificationDetailActivity.class));

            }
        });
        if (movie.getSeen() == 0) {
            holder.linearBody.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLite));
        } else {
            holder.linearBody.setBackgroundColor(Color.WHITE);

        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
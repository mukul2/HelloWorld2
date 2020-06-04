package com.doctor.telemedicine.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModelNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class ConfirmedAppointmentAdapterDoctor extends RecyclerView.Adapter<ConfirmedAppointmentAdapterDoctor.MyViewHolder> {
    List<AppointmentModelNew> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_problem, tv_date, tv_serial;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_serial = (TextView) view.findViewById(R.id.tv_serial);


        }
    }


    public ConfirmedAppointmentAdapterDoctor(List<AppointmentModelNew> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirmed_appointment_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModelNew movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getName());
        holder.tv_serial.setText("" + movie.getId());
        holder.tv_date.setText(movie.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = doForMe.showDialog(context, R.layout.appointment_detail_dialog);
                ((TextView) dialog.findViewById(R.id.tv_name)).setText(movie.getName());
                ((TextView) dialog.findViewById(R.id.tv_problems)).setText(movie.getProblems());
                ((TextView) dialog.findViewById(R.id.tv_date)).setText(movie.getDate());
                ((TextView) dialog.findViewById(R.id.tv_serial)).setText("" + movie.getId());

                TextView tv_option_left = ((TextView) dialog.findViewById(R.id.tv_option_left));
                tv_option_left.setText("Cancel");
                tv_option_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                TextView tv_option_right = ((TextView) dialog.findViewById(R.id.tv_option_right));
                tv_option_right.setText("Call Patient");
                tv_option_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        String phone = movie.getPhone();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        context.startActivity(intent);


                    }
                });
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
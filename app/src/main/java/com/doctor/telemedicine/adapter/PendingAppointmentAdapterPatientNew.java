package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.ConfirmedAppointmentDetailActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.appointmentModel;

/**
 * Created by mukul on 3/10/2019.
 */


public class PendingAppointmentAdapterPatientNew extends RecyclerView.Adapter<PendingAppointmentAdapterPatientNew.MyViewHolder> {
    List<AppointmentModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_address, tv_appointmentfor, tv_date,tv_viewDetails,tv_serial,tv_ref;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_appointmentfor = (TextView) view.findViewById(R.id.tv_appointmentfor);
            tv_date = (TextView) view.findViewById(R.id.tv_date);

            tv_viewDetails = (TextView) view.findViewById(R.id.tv_viewDetails);
            tv_ref = (TextView) view.findViewById(R.id.tv_ref);

        }
    }


    public PendingAppointmentAdapterPatientNew(List<AppointmentModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_pending_item_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getDrName());
        holder.tv_ref.setText(movie.getAppointment_id());
        holder.tv_address.setText(movie.getAddress());
        holder.tv_appointmentfor.setText(movie.getAppointmentFor());
        holder.tv_date.setText(movie.getDate());
        holder.tv_viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     appointmentModel=movie;
             //   context.startActivity(new Intent(context, ConfirmedAppointmentDetailActivity.class));
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
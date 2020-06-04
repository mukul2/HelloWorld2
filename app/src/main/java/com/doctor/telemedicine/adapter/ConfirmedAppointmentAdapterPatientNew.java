package com.doctor.telemedicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.ConfirmedAppointmentDetailActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.StatusResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.appointmentModel;

/**
 * Created by mukul on 3/10/2019.
 */


public class ConfirmedAppointmentAdapterPatientNew extends RecyclerView.Adapter<ConfirmedAppointmentAdapterPatientNew.MyViewHolder>  {
    List<AppointmentModel>list=new ArrayList<>();

    Context context;
    int triggeredItem = 0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_address, tv_appointmentfor, tv_date,tv_viewDetails,tv_cancel,tv_serial;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_appointmentfor = (TextView) view.findViewById(R.id.tv_appointmentfor);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_viewDetails = (TextView) view.findViewById(R.id.tv_viewDetails);
            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            tv_serial = (TextView) view.findViewById(R.id.tv_serial);


        }
    }


    public ConfirmedAppointmentAdapterPatientNew(List<AppointmentModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_confirmed_item_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_serial.setText("#"+movie.getAppointment_id()   );
        holder.tv_name.setText(movie.getDrName());
        holder.tv_address.setText(movie.getAddress());
        holder.tv_appointmentfor.setText(movie.getAppointmentFor());
        holder.tv_date.setText(changeDateformate(movie.getDate()));
        holder.tv_viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   appointmentModel=movie;
               // context.startActivity(new Intent(context, ConfirmedAppointmentDetailActivity.class));
            }
        });
        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, movie.getAppointment_id(), Toast.LENGTH_SHORT).show();
                changeState(movie.getAppointment_id(),position);
            }
        });


    }
    public void changeState(String appointment_id, int pos) {
        MyProgressBar.with(context);
        triggeredItem = pos;
      //  Api.getInstance().changeStatus(appointment_id, ""+Data.STATUS_CANCEL, this);

    }
//    @Override
//    public void onAppointmentChangeSuccess(StatusResponse status) {
//        MyProgressBar.dismiss();
//        if (status.getStatus()) {
//            MyDialog.getInstance().with((Activity) context)
//                    .message("This appointment has been canceled")
//                    .autoBack(false)
//                    .autoDismiss(false)
//                    .show();
//            // list.remove(triggeredItem);
//            if (removeItem(triggeredItem)) {
//                notifyItemRemoved(triggeredItem);
//                notifyItemRangeChanged(triggeredItem, getItemCount());
//            }
//
//        } else {
//            MyDialog.getInstance().with((Activity) context)
//                    .message("Failed")
//                    .autoBack(false)
//                    .autoDismiss(false)
//                    .show();
//        }
//
//    }

    public boolean removeItem(int position) {
        if (list.size() >= position + 1) {
            list.remove(position);
            return true;
        }
        return false;
    }




    private String changeDateformate(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd");
        return targetFormat.format(sourceDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
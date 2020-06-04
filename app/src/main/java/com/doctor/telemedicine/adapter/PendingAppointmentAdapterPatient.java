package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.ConfirmedAppointmentDetailActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModelNew;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.appointmentModel;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

/**
 * Created by mukul on 3/10/2019.
 */


public class PendingAppointmentAdapterPatient extends RecyclerView.Adapter<PendingAppointmentAdapterPatient.MyViewHolder> {
    List<AppointmentModelNew> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_address, tv_appointmentfor, tv_date, tv_serial, tv_cancel;
        CircleImageView image;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_appointmentfor = (TextView) view.findViewById(R.id.tv_appointmentfor);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_serial = (TextView) view.findViewById(R.id.tv_ref_id);

            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            image = (CircleImageView) view.findViewById(R.id.image);

        }
    }


    public PendingAppointmentAdapterPatient(List<AppointmentModelNew> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_pending_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModelNew movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_serial.setText("# "+movie.getId());
        if (movie != null) {
            if (movie.getDr_info()!=null&&movie.getDr_info().getPhoto() != null) {
                Glide.with(context).load(PHOTO_BASE + movie.getDr_info().getPhoto()).into(holder.image);
            }
            if (movie != null && movie.getDr_info() != null && movie.getDr_info().getName() != null) {
                holder.tv_name.setText("" + movie.getDr_info().getName());
            } else {
                holder.tv_name.setText("No name");

            }
            holder.tv_address.setText(movie.getChamber_information().getAddress());
            holder.tv_appointmentfor.setText(movie.getName());
            holder.tv_date.setText(movie.getDate());

            holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDialog.getInstance().with(context).yesNoConfirmation(new MyDialog.confirmListener() {
                        @Override
                        public void onDialogClicked(boolean result) {
                            if (result == true) {
                                MyProgressBar.with(context).show();
                                Api.getInstance().changeStatus(TOKEN, "" + movie.getId(), "3", new ApiListener.appointmentStateChangeListener() {
                                    @Override
                                    public void onAppointmentChangeSuccess(StatusMessage list) {
                                        MyProgressBar.dismiss();
                                        if (list.getStatus() == true) {
                                            if (removeItem(position)) {
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, getItemCount());
//            }
                                            }

                                        }
                                    }

                                    @Override
                                    public void onPppointmentChangeFailed(String msg) {
                                        MyProgressBar.dismiss();
                                        Toast.makeText(context, "Cancel failed.Try again later", Toast.LENGTH_SHORT).show();


                                    }
                                });

                            } else {

                            }

                        }
                    }, "Do you really want to cancel this appointment?");
                }
            });
        }


    }

    public boolean removeItem(int position) {
        if (list.size() >= position + 1) {
            list.remove(position);
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
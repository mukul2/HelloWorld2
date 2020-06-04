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
import com.doctor.telemedicine.Activity.PrescriptionGivingActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModelNew;
import com.doctor.telemedicine.model.PrescriptionRequestModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.REQUEST_TO_PRESCRIBE;

/**
 * Created by mukul on 3/10/2019.
 */


public class PrescriptionRequestAdapterDoctor extends RecyclerView.Adapter<PrescriptionRequestAdapterDoctor.MyViewHolder> {
    List<PrescriptionRequestModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_problems, tv_name;
        ImageView circleImageView;
        RelativeLayout relative_container;
        CircleImageView image;


        public MyViewHolder(View view) {
            super(view);
            tv_problems = (TextView) view.findViewById(R.id.tv_problems);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            image = (CircleImageView) view.findViewById(R.id.image);


        }
    }


    public PrescriptionRequestAdapterDoctor(List<PrescriptionRequestModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_request_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PrescriptionRequestModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getPatientInfo().getName());
        holder.tv_problems.setText(movie.getProblem());
        Glide.with(context).load(PHOTO_BASE + movie.getPatientInfo().getPhoto()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REQUEST_TO_PRESCRIBE=movie;
                Intent i=new Intent(context, PrescriptionGivingActivity.class);
                i.putExtra("type","prescriptionRequest");
                context.startActivity(i);



            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
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
import com.doctor.telemedicine.Activity.ChatActivityCommon;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.SubscriptionsModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class MySubscribeddAdapterDoctor extends RecyclerView.Adapter<MySubscribeddAdapterDoctor.MyViewHolder> {
    List<SubscriptionsModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_dr_name, tv_duration, tv_expires;
        ImageView img_profile;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_dr_name = (TextView) view.findViewById(R.id.tv_dr_name);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            // tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_expires = (TextView) view.findViewById(R.id.tv_expires);

            img_profile = (ImageView) view.findViewById(R.id.img_profile);


        }
    }


    public MySubscribeddAdapterDoctor(List<SubscriptionsModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscriptions_item_patients, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SubscriptionsModel movie = list.get(position);
        context = holder.tv_dr_name.getContext();
        holder.tv_dr_name.setText(movie.getDrInfo().getName());
        holder.tv_duration.setText(""+movie.getNumberOfMonths()+" Months");
        holder.tv_expires.setText("Expires : "+movie.getEnds());
        Glide.with(context).load(PHOTO_BASE+movie.getDrInfo().getPhoto()).into(holder.img_profile);
//        String time = "";
//        for (int i = 0; i < movie.getDays().size(); i++) {
//            time += movie.getDays().get(i).getDay() + "  " + movie.getDays().get(i).getTime() + "\n";
//        }
//        holder.tv_time.setText(time);

        holder.itemView.setOnClickListener((View view)->{
            Intent i = new Intent(context, ChatActivityCommon.class);
            i.putExtra("partner_id", String.valueOf(movie.getPatientId()));
            i.putExtra("partner_name", movie.getPatientInfo().getName());
            i.putExtra("partner_photo", movie.getPatientInfo().getPhoto());
            context.startActivity(i);

        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.PaymentModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.CURRENCY_USD;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class PaymentListDoctorAdapter extends RecyclerView.Adapter<PaymentListDoctorAdapter.MyViewHolder> {
    List<PaymentModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rate, tv_name, tv_reason, tv_lastDegree, tv_epacialist, tv_date;
        ImageView img_profile;


        public MyViewHolder(View view) {
            super(view);
            tv_rate = (TextView) view.findViewById(R.id.tv_rate);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_reason = (TextView) view.findViewById(R.id.tv_reason);
            img_profile = (ImageView) view.findViewById(R.id.img_profile);


        }
    }


    public PaymentListDoctorAdapter(List<PaymentModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_item_doctor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PaymentModel movie = list.get(position);
        context = holder.tv_rate.getContext();
        holder.tv_rate.setText(""+movie.getFees()+CURRENCY_USD);
        holder.tv_name.setText(movie.getPatientInfo().getName());
        holder.tv_reason.setText(movie.getServiceDetails());
        holder.tv_date.setText(movie.getCreatedAt());
        Glide.with(context).load(PHOTO_BASE+movie.getPatientInfo().getPhoto()).into(holder.img_profile);



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
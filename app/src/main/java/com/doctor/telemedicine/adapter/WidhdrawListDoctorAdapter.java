package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.PaymentModel;
import com.doctor.telemedicine.model.WidthdrawModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.CURRENCY_USD;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class WidhdrawListDoctorAdapter extends RecyclerView.Adapter<WidhdrawListDoctorAdapter.MyViewHolder> {
    List<WidthdrawModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rate, tv_status,tv_date;


        public MyViewHolder(View view) {
            super(view);
            tv_rate = (TextView) view.findViewById(R.id.tv_rate);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_date = (TextView) view.findViewById(R.id.tv_date);



        }
    }


    public WidhdrawListDoctorAdapter(List<WidthdrawModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widhdraw_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final WidthdrawModel movie = list.get(position);
        context = holder.tv_rate.getContext();
        holder.tv_rate.setText(""+movie.getAmount()+CURRENCY_USD);
        holder.tv_date.setText(""+movie.getCreated_at());
        String status = "Pending";
        if (movie.getStatus()==1){
            status = "Compleated";
        }else if (movie.getStatus()==2){
            status = "Refused";
        }
        holder.tv_status.setText(status);




    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
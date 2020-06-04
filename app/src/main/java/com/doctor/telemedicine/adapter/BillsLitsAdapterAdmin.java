package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.BillItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class BillsLitsAdapterAdmin extends RecyclerView.Adapter<BillsLitsAdapterAdmin.MyViewHolder> {
    List<BillItem> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_description, tv_unit, tv_body, tv_rate, tv_total, tv_address;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_rate = (TextView) view.findViewById(R.id.tv_rate);
            tv_total = (TextView) view.findViewById(R.id.tv_total);


        }
    }


    public BillsLitsAdapterAdmin(List<BillItem> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_bill_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BillItem movie = list.get(position);
        context = holder.tv_unit.getContext();
        holder.tv_description.setText(movie.getDescription());
        holder.tv_unit.setText(""+movie.getUnit());
        holder.tv_rate.setText(""+movie.getRate()+" BDT");
        holder.tv_total.setText(""+movie.getTotal()+" BDT");



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.MedicineInfo;
import com.doctor.telemedicine.model.PrescriptionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class MedicinesListAdapter extends RecyclerView.Adapter<MedicinesListAdapter.MyViewHolder> {
    List<MedicineInfo> list = new ArrayList<>();

    Context context;
    public   ClickListener clickListener;

    public void setClickListener(ClickListener l) {
        this.clickListener = l;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_medicineName, tv_date, tv_time, tv_duration, tv_serial, tv_doses,tv_b_a_meal;
        ImageView circleImageView;
        RelativeLayout relative_container;
        RecyclerView recycler_view;


        public MyViewHolder(View view) {
            super(view);
            tv_medicineName = (TextView) view.findViewById(R.id.tv_medicineName);
            tv_doses = (TextView) view.findViewById(R.id.tv_doses);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            tv_serial = (TextView) view.findViewById(R.id.tv_serial);
            tv_b_a_meal = (TextView) view.findViewById(R.id.tv_b_a_meal);
//

        }
    }

    public  interface  ClickListener{
          void  onClicked();
    }


    public MedicinesListAdapter(List<MedicineInfo> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MedicineInfo date = list.get(position);
       context = holder.tv_medicineName.getContext();
       holder.tv_medicineName.setText(date.getMedicineNameInfo().getName());

       String duration=""+date.getDurationLength()+"  ";
       if (date.getDurationType().equals("d")){
           duration+="Days";

       }else if (date.getDurationType().equals("w")){
           duration+="Weeks";

       }else if (date.getDurationType().equals("m")){
           duration+="Months";

       }
       if (date.getIsAfterMeal()==1){
           holder.tv_b_a_meal.setText("After Meal");
       }else {
           holder.tv_b_a_meal.setText("Before Meal");

       }
       holder.tv_duration.setText(duration);
       holder.tv_serial.setText(""+(position+1));
       holder.tv_doses.setText(""+date.getDose());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


              // clickListener.onClicked();



           }
       });




    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
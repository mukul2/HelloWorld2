package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.PrescriptionMedicineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class PrescriptionGivingAdapterDoctor extends RecyclerView.Adapter<PrescriptionGivingAdapterDoctor.MyViewHolder> {
    List<PrescriptionMedicineModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_lenght, tv_time, tv_dose, tv_epacialist, tv_address, tv_b_a_meal;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lenght = (TextView) view.findViewById(R.id.tv_lenght);
            tv_dose = (TextView) view.findViewById(R.id.tv_dose);
            tv_b_a_meal = (TextView) view.findViewById(R.id.tv_b_a_meal);


        }
    }


    public PrescriptionGivingAdapterDoctor(List<PrescriptionMedicineModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_creating_medicine_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PrescriptionMedicineModel movie = list.get(position);
        context = holder.tv_lenght.getContext();
        holder.tv_name.setText(movie.getName());
        String lenth = "" + movie.getDurationLength();
        if (movie.getDurationType().equals("w")) {
            lenth += " weeks";
        } else if (movie.getDurationType().equals("m")) {
            lenth += " month";
        } else if (movie.getDurationType().equals("d")) {
            lenth += " days";
        }
        holder.tv_lenght.setText(lenth);
        holder.tv_dose.setText(movie.getDose());
        if (movie.getIsAfterMeal() == 1) {
            holder.tv_b_a_meal.setText("After Meal");
        } else {
            holder.tv_b_a_meal.setText("Before Meal");


        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
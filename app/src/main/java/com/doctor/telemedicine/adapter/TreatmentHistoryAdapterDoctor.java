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
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.TreatmentHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class TreatmentHistoryAdapterDoctor extends RecyclerView.Adapter<TreatmentHistoryAdapterDoctor.MyViewHolder> {
    List<TreatmentHistoryModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_dr_name, tv_pa_name, tv_comment, tv_date;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_dr_name = (TextView) view.findViewById(R.id.tv_dr_name);
            tv_pa_name = (TextView) view.findViewById(R.id.tv_pa_name);
            tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            tv_date = (TextView) view.findViewById(R.id.tv_date);


        }
    }


    public TreatmentHistoryAdapterDoctor(List<TreatmentHistoryModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.treatment_history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TreatmentHistoryModel movie = list.get(position);
        context = holder.tv_dr_name.getContext();
        holder.tv_dr_name.setText("Treatment By : "+movie.getDrName());
       // holder.tv_pa_name.setText(movie.getPatientName());
        holder.tv_pa_name.setVisibility(View.GONE);
        holder.tv_comment.setText("Comment : "+movie.getComment());
        holder.tv_date.setText("Treatment Date : "+DataStore.changeDateformate(movie.getPosted()));
//        String time = "";
//        for (int i = 0; i < movie.getDays().size(); i++) {
//            time += movie.getDays().get(i).getDay() + "  " + movie.getDays().get(i).getTime() + "\n";
//        }
//        holder.tv_time.setText(time);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
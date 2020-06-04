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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class AppointmentAdapterPatient extends RecyclerView.Adapter<AppointmentAdapterPatient.MyViewHolder> {
    List<AppointmentModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist,tv_address;
        ImageView circleImageView;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_name);
            tv_hospitalName = (TextView) view.findViewById(R.id.tv_hospitalName);
            // tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_lastDegree = (TextView) view.findViewById(R.id.tv_lastDegree);
            tv_epacialist = (TextView) view.findViewById(R.id.tv_epacialist);
            tv_address = (TextView) view.findViewById(R.id.tv_address);


        }
    }


    public AppointmentAdapterPatient(List<AppointmentModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModel movie = list.get(position);
        context = holder.title.getContext();
        holder.title.setText(movie.getDrName());
        holder.tv_hospitalName.setText(movie.getAddress());
        holder.tv_epacialist.setText(movie.getDate());
        holder.tv_address.setText(movie.getPhone());
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
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.VisitActivityDr;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.AppointmentModel2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class SearchAdapterDoctor extends RecyclerView.Adapter<SearchAdapterDoctor.MyViewHolder> {
    List<AppointmentModel2> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist, tv_address, tv_serial;
        ImageView circleImageView;
        RelativeLayout relative_container;
        LinearLayout lin1, lin2, lin3;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_name);
            tv_serial = (TextView) view.findViewById(R.id.tv_serial);

            lin1 = (LinearLayout) view.findViewById(R.id.lin1);
            lin2 = (LinearLayout) view.findViewById(R.id.lin2);
            lin3 = (LinearLayout) view.findViewById(R.id.lin3);


        }
    }

    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();


    }


    public SearchAdapterDoctor(List<AppointmentModel2> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_search_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppointmentModel2 movie = list.get(position);
        context = holder.title.getContext();
        if (!movie.getId().equals("0")) {
            holder.lin1.setVisibility(View.VISIBLE);
            holder.lin2.setVisibility(View.VISIBLE);
            holder.lin3.setVisibility(View.GONE);
            holder.title.setText(movie.getAppointmentFor());
            holder.tv_serial.setText(movie.getId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Data.drServingModel = movie;
                    context.startActivity(new Intent(context, VisitActivityDr.class));

                }
            });
        } else {
            holder.lin1.setVisibility(View.GONE);
            holder.lin2.setVisibility(View.GONE);
            holder.lin3.setVisibility(View.VISIBLE);

        }

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
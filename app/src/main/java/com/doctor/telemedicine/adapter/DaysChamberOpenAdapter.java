package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.BookingActivityNew;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.DaysChamberOpenModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukul on 3/10/2019.
 */


public class DaysChamberOpenAdapter extends RecyclerView.Adapter<DaysChamberOpenAdapter.MyViewHolder> {

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_day;
        ImageView circleImageView;
        RelativeLayout relative_container;
        CardView card;


        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_day = (TextView) view.findViewById(R.id.tv_day);
            card = (CardView) view.findViewById(R.id.card);


        }
    }


    public DaysChamberOpenAdapter( ) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.days_select_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DaysChamberOpenModel movie = BookingActivityNew.allDays.get(position);
        context = holder.tv_date.getContext();
        holder.tv_date.setText(""+movie.getDate());
        holder.tv_day.setText(DataStore.convertToWeekDay(""+movie.getDay()));
        if (movie.isSelected()){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.tv_date.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tv_day.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tv_date.setTextColor(Color.parseColor("#CCCCCC"));
            holder.tv_day.setTextColor(Color.parseColor("#CCCCCC"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               for (int i=0;i<BookingActivityNew.allDays.size();i++){
                   BookingActivityNew.allDays.get(i).setSelected(false);

               }
                BookingActivityNew.allDays.get(position).setSelected(true);
               notifyDataSetChanged();
            }
        });



    }



    @Override
    public int getItemCount() {
        return BookingActivityNew.allDays.size();
    }
}
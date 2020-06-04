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
import com.doctor.telemedicine.model.CallHistoryPatient;
import com.doctor.telemedicine.model.DaysTimeModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.convertToWeekDay;

/**
 * Created by mukul on 3/10/2019.
 */


public class ChamberDaysListAdapter extends RecyclerView.Adapter<ChamberDaysListAdapter.MyViewHolder> {
    List<DaysTimeModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_start,tv_end_time,tv_day;

        public MyViewHolder(View view) {
            super(view);
            tv_start = (TextView) view.findViewById(R.id.tv_start);
            tv_end_time = (TextView) view.findViewById(R.id.tv_end);
            tv_day = (TextView) view.findViewById(R.id.tv_day);




        }
    }


    public ChamberDaysListAdapter(List<DaysTimeModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chamber_days_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DaysTimeModel data = list.get(position);
        context = holder.tv_start.getContext();
        holder.tv_day.setText(convertToWeekDay(data.getDayName()));
        holder.tv_start.setText(data.getStartTime());
        holder.tv_end_time.setText(data.getEndTime());





    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
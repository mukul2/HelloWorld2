package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.DrListActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DoctorModel;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.downloadedDoctors;

/**
 * Created by mukul on 3/10/2019.
 */


public class DrListAdapter extends RecyclerView.Adapter<DrListAdapter.MyViewHolder> {
    List<DoctorModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;



        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);



        }
    }


    public DrListAdapter(List<DoctorModel> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitals_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DoctorModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getDrName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
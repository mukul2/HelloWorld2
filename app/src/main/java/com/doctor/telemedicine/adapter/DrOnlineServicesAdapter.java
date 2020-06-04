package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.DrListActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.DrService;
import com.doctor.telemedicine.model.OnlineDoctorsServiceInfo;
import com.doctor.telemedicine.model.SpecialistNameCount;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.DataStore.CLICKED_TITLE;

/**
 * Created by mukul on 3/10/2019.
 */


public class DrOnlineServicesAdapter extends RecyclerView.Adapter<DrOnlineServicesAdapter.MyViewHolder> {
    List<OnlineDoctorsServiceInfo>list=new ArrayList<>();

    Context context;
    SerViceClicked serViceClicked;
    public  interface SerViceClicked{
        public  void onServiceClicked();
    }

    public void setSerViceClicked(SerViceClicked clicked) {
        this.serViceClicked = clicked;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;



        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);




        }
    }


    public DrOnlineServicesAdapter(List<OnlineDoctorsServiceInfo> lists ) {
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_single_service_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorsServiceInfo movie = list.get(position);
        context = holder.tv_name.getContext();
       holder.tv_name.setText(movie.getServiceNameInfo().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serViceClicked.onServiceClicked();
            }
        });
        if (movie.getStatus() == 1){

           // holder.itemView.setVisibility(View.VISIBLE);
        }else {
          //  list.remove(position);
          //  notifyDataSetChanged();
         //   holder.itemView.setVisibility(View.GONE);

        }



    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.DrListActivity;
import com.doctor.telemedicine.Activity.DrListGridActivity;
import com.doctor.telemedicine.Activity.OnlineDocListActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DepartmentModel;
import com.doctor.telemedicine.model.DeptModel;
import com.doctor.telemedicine.model.DoctorModel;
import com.doctor.telemedicine.model.SpecialistNameCount;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.DataStore.CLICKED_TITLE;
import static com.doctor.telemedicine.Data.DataStore.downloadedDoctors;

/**
 * Created by mukul on 3/10/2019.
 */


public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.MyViewHolder> {
    List<DeptModel> list = new ArrayList<>();
    DeptSelectListsner deptSelectListsner;

    public void setDeptSelectListsner(DeptSelectListsner deptSelectListsner) {
        this.deptSelectListsner = deptSelectListsner;
    }

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        CardView card;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            card = (CardView) view.findViewById(R.id.card);


        }
    }


    public DepartmentsAdapter(List<DeptModel> lists) {
        this.list = lists;

    }

    public DepartmentsAdapter(List<DeptModel> lists, DeptSelectListsner listsner) {
        this.list = lists;
        this.deptSelectListsner = listsner;

    }

    public interface DeptSelectListsner {
        public void onSelected(int i);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospitals_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DeptModel movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(movie.getName());
        holder.card.setCardBackgroundColor(Color.parseColor(Data.getColorCode(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MyProgressBar.with(context).show();
                // CLICKED_TITLE = movie.getName();
                if (TYPE_OF_ACTIVITY.equals("review")) {
                    context.startActivity(new Intent(context, DrListGridActivity.class));

                } else if (TYPE_OF_ACTIVITY.equals("OnlineDoc")) {
                    Intent i = new Intent(context, OnlineDocListActivity.class);
                    i.putExtra("depID", "" + movie.getId());
                    context.startActivity(i);

                } else if (TYPE_OF_ACTIVITY.equals("recheck")) {
                    deptSelectListsner.onSelected(movie.getId());

                } else {
                    Intent intent = new Intent(context, DrListActivity.class);
                    intent.putExtra("depID", movie.getId());
                    context.startActivity(intent);

                }
//                Api.getInstance().searchDoctor("", "", movie.getName(), "", "", new ApiListener.doctorSearchListener() {
//                    @Override
//                    public void onSearchSuccess(List<DoctorModel> list) {
//                        MyProgressBar.dismiss();
//                        if (list!=null) {
//                            searchResult.clear();
//                            searchResult = list;
//                            context.startActivity(new Intent(context, DrListActivity.class));
//                        }else {
//                            Toast.makeText(context, "Network error.Please try again", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onSuccessFailed(String msg) {
//
//                    }
//                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
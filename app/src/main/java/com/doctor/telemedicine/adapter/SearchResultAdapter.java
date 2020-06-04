package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.Activity.ChamberDetailActivity;
import com.doctor.telemedicine.Activity.DoctorsSwipeActivity;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.DoctorModel;

import java.util.List;

import static com.doctor.telemedicine.Data.Data.days;
import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.Data.singleDrModel;

/**
 * Created by mukul on 3/10/2019.
 */


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist,tv_address;
        ImageView circleImageView;
        RelativeLayout relative_container;
        LinearLayout linerBody;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_name);
            tv_hospitalName = (TextView) view.findViewById(R.id.tv_hospitalName);
            // tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_lastDegree = (TextView) view.findViewById(R.id.tv_lastDegree);
            tv_epacialist = (TextView) view.findViewById(R.id.tv_epacialist);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            linerBody = (LinearLayout) view.findViewById(R.id.linerBody);


        }
    }


    public SearchResultAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DoctorModel movie = searchResult.get(position);
        context = holder.title.getContext();
        holder.title.setText(movie.getDrName());
        holder.tv_hospitalName.setText(movie.getHospitalName());
        holder.tv_epacialist.setText(movie.getSpecialist()+" spacialist");
        holder.tv_address.setText(movie.getAddress());
//        String time = "";
//        for (int i = 0; i < movie.getDays().size(); i++) {
//            time += movie.getDays().get(i).getDay() + "  " + movie.getDays().get(i).getTime() + "\n";
//        }
//        holder.tv_time.setText(time);
        holder.tv_lastDegree.setText(movie.getLastDegree());
        holder.linerBody.setOnClickListener((View v) -> detailView(position,movie.getDays(),movie));


    }

    private void detailView(int position, List<Day> days, DoctorModel movie) {
        singleDrModel= movie;
       // Data.days.clear();
      //  Data.days.addAll(days);
        Intent i=new Intent(context, DoctorsSwipeActivity.class);
        i.putExtra("currentIndex",""+position);
        //context.startActivity(new Intent(context, ChamberDetailActivity.class));
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }
}
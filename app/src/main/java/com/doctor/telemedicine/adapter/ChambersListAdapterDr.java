package com.doctor.telemedicine.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.doctor.telemedicine.Activity.BookingActivityNew;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.model.AppointmentModel;
import com.doctor.telemedicine.model.Chamber;
import com.doctor.telemedicine.model.ChamberInfo;
import com.doctor.telemedicine.model.DaysTimeModel;
import com.doctor.telemedicine.model.DrChamberResponse;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Data.Data.CHAMBER_TO_BOOK;

/**
 * Created by mukul on 3/10/2019.
 */


public class ChambersListAdapterDr extends RecyclerView.Adapter<ChambersListAdapterDr.MyViewHolder> {
    List<ChamberInfo> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_location, tv_sittingCount, tv_fees, tv_book,tv_follow_up_fees;
        RecyclerView recycler_view;

        public MyViewHolder(View view) {
            super(view);
            tv_sittingCount = (TextView) view.findViewById(R.id.tv_sittingCount);
            tv_location = (TextView) view.findViewById(R.id.tv_location);
            tv_fees = (TextView) view.findViewById(R.id.tv_fees);
            tv_book = (TextView) view.findViewById(R.id.tv_book);
            tv_follow_up_fees = (TextView) view.findViewById(R.id.tv_follow_up_fees);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);


        }
    }


    public ChambersListAdapterDr(List<ChamberInfo> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chambers_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChamberInfo movie = list.get(position);
        context = holder.tv_location.getContext();
        holder.tv_location.setText(movie.getAddress());
        holder.tv_fees.setText("" + movie.getFee() + " TK");
        holder.tv_follow_up_fees.setText("" + movie.getFollowUpFee() + " TK");
        if (movie.getChamberDays() != null && movie.getChamberDays().size() == 0) {
            holder.tv_sittingCount.setText("No date is added");
        } else {
            List<DaysTimeModel> models = new ArrayList<>();
            for (int i = 0; i < movie.getChamberDays().size(); i++) {
                models.add(new DaysTimeModel("" + movie.getChamberDays().get(i).getDay(), movie.getChamberDays().get(i).getStartTime(), movie.getChamberDays().get(i).getEndTime()));
            }
            ChamberDaysListAdapter mAdapter = new ChamberDaysListAdapter(models);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            holder.recycler_view.setLayoutManager(mLayoutManager);
            holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL, false);
            holder.recycler_view.setAdapter(mAdapter);
            holder.recycler_view.addItemDecoration(dividerItemDecoration);


//            holder.tv_sittingCount.setVisibility(View.VISIBLE);
//            String detail="";
//            for (int i=0;i<movie.getSitingdays().size();i++){
//                detail+=DataStore.convertToWeekDay(movie.getSitingdays().get(i).getDay())+" "+movie.getSitingdays().get(i).getStartTime()+"-"+movie.getSitingdays().get(i).getEndTime();
//                if (i!=movie.getSitingdays().size()-1){
//                    detail+="\n";
//                }
//            }
//            holder.tv_sittingCount.setText(detail);
            SessionManager sessionManager = new SessionManager(context);
            if (sessionManager.getUserType().equals("d")) {
                holder.tv_book.setVisibility(View.GONE);
            } else {
                holder.tv_book.setVisibility(View.VISIBLE);
                holder.tv_book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CHAMBER_TO_BOOK=movie;
                        context.startActivity(new Intent(context, BookingActivityNew.class));
                    }
                });
            }


        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
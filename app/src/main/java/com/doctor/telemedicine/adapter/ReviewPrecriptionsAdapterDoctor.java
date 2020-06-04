package com.doctor.telemedicine.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.PrescriptionReviewReplyActivity;
import com.doctor.telemedicine.Activity.PrescriptionReviewSendingActivity;
import com.doctor.telemedicine.Activity.PrescriptionViewForReviewActivity;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.GeneralListener;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.model.PrescriptionReviewModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_PRESCRIPTION;
import static com.doctor.telemedicine.Data.DataStore.PRESCRIPTION_VIEW_TYPE;
import static com.doctor.telemedicine.Data.DataStore.REVIEW_MODEL;

/**
 * Created by mukul on 3/10/2019.
 */


public class ReviewPrecriptionsAdapterDoctor extends RecyclerView.Adapter<ReviewPrecriptionsAdapterDoctor.MyViewHolder> {
    List<PrescriptionReviewModel> list = new ArrayList<>();
    prescriptionSelectListener pListener;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_DrName, tv_date, tv_time, tv_lastDegree, tv_epacialist, tv_address,tv_comment,tv_reply;
        ImageView circleImageView,img_reviewd;
        RelativeLayout relative_container;
        RecyclerView recycler_view;
        CircleImageView proPic;


        public MyViewHolder(View view) {
            super(view);
            tv_DrName = (TextView) view.findViewById(R.id.tv_DrName);
            tv_reply = (TextView) view.findViewById(R.id.tv_reply);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            proPic = (CircleImageView) view.findViewById(R.id.proPic);
            img_reviewd = (ImageView) view.findViewById(R.id.img_reviewd);


        }
    }
    public interface prescriptionSelectListener{
         void  onPrescriptionSelected(PrescriptionModel data);
    }


    public ReviewPrecriptionsAdapterDoctor(List<PrescriptionReviewModel > lists) {
        this.list = lists;

    }
    public ReviewPrecriptionsAdapterDoctor(List<PrescriptionReviewModel > lists, prescriptionSelectListener lis) {
        this.list = lists;
        this.pListener = lis;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recheck_request_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PrescriptionReviewModel  date = list.get(position);
        context = holder.tv_DrName.getContext();
        holder.tv_DrName.setText(date.getPatientInfo().getName());
        holder.tv_date.setText(DataStore.changeDateformate(date.getCreatedAt()));
        Glide.with(context).load(PHOTO_BASE+date.getPatientInfo().getPhoto()).into(holder.proPic);

        //write recycler here
        MedicinesListAdapter mAdapter = new MedicinesListAdapter(date.getMedicineInfo());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.recycler_view.setLayoutManager(mLayoutManager);
        holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
        holder.recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        holder.recycler_view.setAdapter(mAdapter);
       /*
        holder.tv_reply.setClickListener(new MedicinesListAdapter.ClickListener() {
            @Override
            public void onClicked() {
               // GeneralListener.needRefresh.doRefresh(position);
                Toast.makeText(context, "clicked review", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, PrescriptionReviewReplyActivity.class));


            }
        });
        */
        if (date.getPatient_comment()!=null){
            holder.tv_comment.setText(date.getPatient_comment());
        }else {
            holder.tv_comment.setText("NO comment");

        }
        if (date.getIsReviewed()==0){
            holder.tv_reply.setText("Reply");
        }else {
            holder.tv_reply.setText("View");

        }

        holder.tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                REVIEW_MODEL=date;
                context.startActivity(new Intent(context, PrescriptionViewForReviewActivity.class));

            }
        });

        if (date.getIsReviewed()==1){
            holder.img_reviewd.setVisibility(View.VISIBLE);
        }else {
            holder.img_reviewd.setVisibility(View.GONE);

        }




    }

    public void show_dialog(PrescriptionModel date) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.prescription_dialog);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_DrName = (TextView) dialog.findViewById(R.id.tv_DrName);
        TextView tv_date = (TextView) dialog.findViewById(R.id.tv_date);
        ImageView imgForward = (ImageView) dialog.findViewById(R.id.imgForward);
        RecyclerView recycler_view = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        tv_DrName.setText(date.getDrInfo().getName());
        tv_date.setText(DataStore.changeDateformate(date.getCreatedAt()));

        //write recycler here
        MedicinesListAdapter mAdapter = new MedicinesListAdapter(date.getMedicineInfo());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_PRESCRIPTION=date;
                Intent i=new Intent(context, PrescriptionReviewSendingActivity.class);
                i.putExtra("prescriptionID",""+date.getId());
              //  context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
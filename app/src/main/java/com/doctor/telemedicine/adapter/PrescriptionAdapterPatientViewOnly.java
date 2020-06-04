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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.PrescriptionDetailViewPatientActivity;
import com.doctor.telemedicine.Activity.PrescriptionReviewSendingActivity;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_PRESCRIPTION;
import static com.doctor.telemedicine.Data.DataStore.PRESCRIPTION_VIEW_TYPE;

/**
 * Created by mukul on 3/10/2019.
 */


public class PrescriptionAdapterPatientViewOnly extends RecyclerView.Adapter<PrescriptionAdapterPatientViewOnly.MyViewHolder> {
    List<PrescriptionModel> list = new ArrayList<>();
    prescriptionSelectListener pListener;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_DrName, tv_date;
        CircleImageView proPic;
        ImageView prescriptionImage;


        public MyViewHolder(View view) {
            super(view);
            tv_DrName = (TextView) view.findViewById(R.id.tv_DrName);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            proPic = (CircleImageView) view.findViewById(R.id.proPic);
            prescriptionImage = (ImageView) view.findViewById(R.id.prescriptionImage);


        }
    }

    public interface prescriptionSelectListener {
        void onPrescriptionSelected(PrescriptionModel data);
    }


    public PrescriptionAdapterPatientViewOnly(List<PrescriptionModel> lists) {
        this.list = lists;

    }

    public PrescriptionAdapterPatientViewOnly(List<PrescriptionModel> lists, prescriptionSelectListener lis) {
        this.list = lists;
        this.pListener = lis;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_item_patient_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PrescriptionModel date = list.get(position);
        context = holder.tv_DrName.getContext();
        holder.tv_date.setText(DataStore.changeDateformate(date.getCreatedAt()));
        if (date.getDrInfo() != null && date.getDrInfo().getName() != null) {
            holder.tv_DrName.setText(date.getDrInfo().getName());
            Glide.with(context).load(PHOTO_BASE + date.getDrInfo().getPhoto()).into(holder.proPic);
        } else {
            holder.tv_DrName.setText("No Doctor Name Selected");
        }

        if (date.getAttachment() != null && date.getAttachment().size() > 0 && date.getAttachment().get(0) != null && date.getAttachment().get(0).getFile() != null) {

            Glide.with(context).load(PHOTO_BASE + date.getAttachment().get(0).getFile()).into(holder.prescriptionImage);

        } else {
            holder.prescriptionImage.setAlpha(0.0f);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_PRESCRIPTION = date;
                context.startActivity(new Intent(context, PrescriptionDetailViewPatientActivity.class));
            }
        });

//        //write recycler here
//        if (date.getMedicineInfo() != null) {
//            MedicinesListAdapter mAdapter = new MedicinesListAdapter(date.getMedicineInfo());
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//            holder.recycler_view.setLayoutManager(mLayoutManager);
//            holder.recycler_view.setItemAnimator(new DefaultItemAnimator());
//            holder.recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
//            holder.recycler_view.setAdapter(mAdapter);
//            holder.recycler_view.setClickable(false);
//            holder.recycler_view.setEnabled(false);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (PRESCRIPTION_VIEW_TYPE.equals("review")) {
//                     //   pListener.onPrescriptionSelected(date);
//
//
//                    } else if (PRESCRIPTION_VIEW_TYPE.equals("justView")) {
//                    //    show_dialog(date);
//
//                    }
//                }
//            });
//        /*    mAdapter.setClickListener(new MedicinesListAdapter.ClickListener() {
//                @Override
//                public void onClicked() {
//                    if (PRESCRIPTION_VIEW_TYPE.equals("review")) {
//                        pListener.onPrescriptionSelected(date);
//
//
//                    } else if (PRESCRIPTION_VIEW_TYPE.equals("justView")) {
//                        show_dialog(date);
//
//                    }
//                }
//            });
//            */
//        } else {
//
//
//        }
//
//        if (date.getDrInfo() != null) {
//            holder.tv_DrName.setText(date.getDrInfo().getName());
//            Glide.with(context).load(PHOTO_BASE + date.getDrInfo().getPhoto()).into(holder.proPic);
//            if (date.getDiseases_name() != null) {
//                holder.tv_diseases_name.setText(date.getDiseases_name());
//            }
//            holder.linearPrescription.setVisibility(View.VISIBLE);
//            holder.img_click.setVisibility(View.GONE);
//            holder.title.setVisibility(View.GONE);
//
//        } else {
//            holder.linearPrescription.setVisibility(View.GONE);
//            holder.img_click.setVisibility(View.VISIBLE);
//            holder.title.setVisibility(View.VISIBLE);
//            holder.title.setText(date.getDiseases_name());
//            if (date.getAttachment()!=null&&date.getAttachment().size()>0){
//                Glide.with(context).load(PHOTO_BASE+date.getAttachment().get(0).getFile()).into(holder.img_click);
//            }
//        }


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
                NOW_SHOWING_PRESCRIPTION = date;
                Intent i = new Intent(context, PrescriptionReviewSendingActivity.class);
                i.putExtra("prescriptionID", "" + date.getId());
                //  context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
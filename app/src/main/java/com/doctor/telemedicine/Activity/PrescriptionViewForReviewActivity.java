package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.MedicinesListAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.DataStore.REVIEW_MODEL;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;

public class PrescriptionViewForReviewActivity extends BaseActivity implements  ApiListener.singlePrescriptionDownloadListener {

    @BindView(R.id.tv_DrName)
    TextView tv_DrName;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_review_reply)
    TextView tv_review_reply;
    @BindView(R.id.tv_patient_comment)
    TextView tv_patient_comment;
    @BindView(R.id.tv_no_pres_came_yet)
    TextView tv_no_pres_came_yet;
    @BindView(R.id.linearOLDPrescriptionPreparedByDoc)
    LinearLayout linearOLDPrescriptionPreparedByDoc;
    @BindView(R.id.tv_old_pres_doc_not_specified)
    TextView tv_old_pres_doc_not_specified;


    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_diseases_name)
    TextView tv_diseases_name;
    @BindView(R.id.proPic)
    CircleImageView proPic;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view_for_review);
        ButterKnife.bind(this);
        //singlePrescriptionDownload
        if (REVIEW_MODEL.getNew_prescription_id()!=null&&REVIEW_MODEL.getNew_prescription_id()>0) {
            Api.getInstance().singlePrescriptionDownload(TOKEN,""+REVIEW_MODEL.getNew_prescription_id(),this);
        }
        if (REVIEW_MODEL.getOldPrescriptionId()!=null&&REVIEW_MODEL.getOldPrescriptionId()>0) {
            Api.getInstance().singlePrescriptionDownload(TOKEN, "" + REVIEW_MODEL.getOldPrescriptionId(), new ApiListener.singlePrescriptionDownloadListener() {
                @Override
                public void onPrescriptionDownloadSuccess(PrescriptionModel list) {
                    tv_diseases_name.setText(list.getDiseases_name());
                    if (list.getDrInfo() == null) {
                        if (list.getAttachment() != null && list.getAttachment().size() > 0 && list.getAttachment().get(0).getFile() != null) {
                            Glide.with(context).load(PHOTO_BASE + list.getAttachment().get(0).getFile()).into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(context,ImageFullScreenActivity.class);
                                    intent.putExtra("link",PHOTO_BASE + list.getAttachment().get(0).getFile());
                                    startActivity(intent);
                                }
                            });
                        }else {
                            //  Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
                        }
                        linearOLDPrescriptionPreparedByDoc.setVisibility(View.GONE);
                        tv_old_pres_doc_not_specified.setVisibility(View.VISIBLE);

                    } else {
                        linearOLDPrescriptionPreparedByDoc.setVisibility(View.VISIBLE);
                        tv_old_pres_doc_not_specified.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPrescriptionDownloaFailed(String msg) {

                }
            });
        }
        tv_patient_comment.setText(REVIEW_MODEL.getPatient_comment());
        tv_DrName.setText(REVIEW_MODEL.getDrInfo().getName());
        tv_date.setText(DataStore.changeDateformate(REVIEW_MODEL.getCreatedAt()));

        if (REVIEW_MODEL.getIsReviewed()==0){
           // tv_no_pres_came_yet.setVisibility(View.VISIBLE);
        }else {
           // tv_no_pres_came_yet.setVisibility(View.GONE);
            tv_review_reply.setText(REVIEW_MODEL.getDr_comment());

        }
        if (REVIEW_MODEL.getIsReviewed()==1&&REVIEW_MODEL.getNew_prescription_id()==null){
            tv_no_pres_came_yet.setText("No Medicine Prescribed");
        }
        if (REVIEW_MODEL.getMedicineInfo().size()>0){
            Toast.makeText(context, "new Prescripton Came", Toast.LENGTH_SHORT).show();

            MedicinesListAdapter mAdapter = new MedicinesListAdapter(REVIEW_MODEL.getMedicineInfo());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
            recycler_view.setAdapter(mAdapter);



        }else {
            Toast.makeText(context, "No Prescription came", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onPrescriptionDownloadSuccess(PrescriptionModel data) {

        // holder.tv_department.setText(date.getDrInfo().getDepartment_info().getName());

        //write recycler here
        MedicinesListAdapter mAdapter = new MedicinesListAdapter(data.getMedicineInfo());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        Glide.with(context).load(PHOTO_BASE+data.getDrInfo().getPhoto()).into(proPic);
        if (data.getDiseases_name()!=null){

        }


    }

    @Override
    public void onPrescriptionDownloaFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }



    public void cancelRequest(View view) {
    }

    public void reply(View view) {
        startActivity(new Intent(this,PrescriptionReviewReplyActivity.class));

    }
}

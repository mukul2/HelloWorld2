package com.doctor.telemedicine.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.adapter.DepartmentsAdapter;
import com.doctor.telemedicine.adapter.PrescriptionAdapterDoctor;
import com.doctor.telemedicine.adapter.PrescriptionAdapterForReviewSendingByPatient;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.DeptModel;
import com.doctor.telemedicine.model.PrescriptionModel;
import com.doctor.telemedicine.model.StatusMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_PRESCRIPTION;
import static com.doctor.telemedicine.Data.DataStore.PRESCRIPTION_VIEW_TYPE;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PrescriptionReviewSendingActivity extends BaseActivity implements ApiListener.prescriptionPostListener {
    Context context = this;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.ed_comment)
    EditText ed_comment;

    @BindView(R.id.image)
    CircleImageView image;
    PrescriptionModel SELECTEDPRESCRIPTION;
    String paymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_review_sending);
        ButterKnife.bind(this);


        //NOW_SHOWING_ONLINE_DOC
        Glide.with(context).load(PHOTO_BASE + NOW_SHOWING_ONLINE_DOC.getPhoto()).into(image);
        tv_name.setText(NOW_SHOWING_ONLINE_DOC.getName());
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            paymentInfo = (String) b.get("paymentInfo");
        } else {
            onBackPressed();
        }

    }

    public void back(View view) {
        onBackPressed();
    }

//    public void pickDoctor(View view) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.select_department_dialog);
//        dialog.show();
//        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        Api.getInstance().getDepList(TOKEN, new ApiListener.DeptDownloadListener() {
//            @Override
//            public void onDepartmentDownloadSuccess(List<DeptModel> list) {
//                TYPE_OF_ACTIVITY = "recheck";
//                DepartmentsAdapter mAdapter = new DepartmentsAdapter(list, new DepartmentsAdapter.DeptSelectListsner() {
//                    @Override
//                    public void onSelected(int i) {
//
//                    }
//                });
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//                recyclerView.setLayoutManager(mLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setAdapter(mAdapter);
//
//            }
//
//            @Override
//            public void onDepartmentDownloadFailed(String msg) {
//
//            }
//        });
//    }

    public void ChoosePrescription(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_department_dialog);
        dialog.show();
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Api.getInstance().getPresCriptionsByPatient(TOKEN, USER_ID, "patient", new ApiListener.PresCriptionDownloadListenerPatient() {
            @Override
            public void onPrescriptionDownloadSuccess(List<PrescriptionModel> data) {
                PRESCRIPTION_VIEW_TYPE = "review";
                PrescriptionAdapterForReviewSendingByPatient mAdapter = new PrescriptionAdapterForReviewSendingByPatient(data, new PrescriptionAdapterForReviewSendingByPatient.prescriptionSelectListener() {
                    @Override
                    public void onPrescriptionSelected(PrescriptionModel data) {
                        dialog.dismiss();
                        SELECTEDPRESCRIPTION = data;
                        tv_status.setText("Prescription is selected");
                        tv_status.setTextColor(Color.parseColor("#28B463"));

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(_sGridLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onPrescriptionDownloadFailed(String msg) {

            }
        });

    }

    public void submit(View view) {
        if (SELECTEDPRESCRIPTION != null) {
            String comment = ed_comment.getText().toString().trim();
            if (comment.length() > 0)
                MyProgressBar.with(context);
            Api.getInstance().postReview(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), "" + SELECTEDPRESCRIPTION.getId(), comment, paymentInfo, "1", this);

        }
    }

    @Override
    public void onPrescriptionPostSuccess(StatusMessage response) {
        MyProgressBar.dismiss();
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        if (response.getStatus() == true) {
            startActivity(new Intent(this, PatientHomeActivity.class));
            finishAffinity();
        }
    }

    @Override
    public void onPrescriptionPostFailed(String msg) {
        MyProgressBar.dismiss();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}

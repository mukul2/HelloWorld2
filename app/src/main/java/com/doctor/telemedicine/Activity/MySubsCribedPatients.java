package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.MySubscribeddAdapterDoctor;
import com.doctor.telemedicine.adapter.MySubscriptionsAdapterPatient;
import com.doctor.telemedicine.adapter.ScheduleAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.SubscriptionsModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class MySubsCribedPatients extends BaseActivity implements ApiListener.SubscriptionListDownlaodListener {
    Context context = this ;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subs_cribed_patients);
        ButterKnife.bind(this);
        Api.getInstance().get_subscription_list(TOKEN,"doctor",USER_ID,this);
    }

    @Override
    public void onSubscriptionListDownlaodSuccess(List<SubscriptionsModel> data) {
      MySubscribeddAdapterDoctor mAdapter = new MySubscribeddAdapterDoctor(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onSubscriptionListDownlaodFailed(String msg) {

    }
}

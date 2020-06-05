package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DrOnlineServicesAdapter;
import com.doctor.telemedicine.adapter.DrOnlineServicesAdapterBigItem;
import com.doctor.telemedicine.adapter.SkillAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.OnlineDoctorsServiceInfo;
import com.doctor.telemedicine.model.SubscriptionViewResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Activity.DoctorsFullProfileView.SKILLS;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;


public class PatientDoctorsOnlineServiceFragment extends Fragment implements ApiListener.SubscriptionViewListener {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    public static boolean IS_CHAT_SUBSCRIBED = false;
    public static boolean IS_VIDEO_CALL_SUBSCRIBED = false;
    public static boolean IS_1_SUBSCRIBED = false;
    public static boolean IS_3_SUBSCRIBED = false;
    public static boolean IS_6_SUBSCRIBED = false;
    public static boolean IS_12_SUBSCRIBED = false;


    public static PatientDoctorsOnlineServiceFragment newInstance() {
        PatientDoctorsOnlineServiceFragment fragment = new PatientDoctorsOnlineServiceFragment();
        return fragment;
    }

    public PatientDoctorsOnlineServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        IS_CHAT_SUBSCRIBED = false;
        IS_VIDEO_CALL_SUBSCRIBED = false;
        IS_1_SUBSCRIBED = false;
        IS_3_SUBSCRIBED = false;
        IS_6_SUBSCRIBED = false;
        IS_12_SUBSCRIBED = false;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        IS_CHAT_SUBSCRIBED = false;
        IS_VIDEO_CALL_SUBSCRIBED = false;
        IS_1_SUBSCRIBED = false;
        IS_3_SUBSCRIBED = false;
        IS_6_SUBSCRIBED = false;
        IS_12_SUBSCRIBED = false;
        super.onResume();
        Api.getInstance().check_subscriptions(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.online_servicec_fragment, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);

        //DrConfirmedActivity\

        Api.getInstance().check_subscriptions(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), this);


        return v;
    }

    private void initRecyclerView() {
//        List<OnlineDoctorsServiceInfo> LIST =  NOW_SHOWING_ONLINE_DOC.getOnlineDoctorsServiceInfo();
//        for(int k = 0; k<NOW_SHOWING_ONLINE_DOC.getOnlineDoctorsServiceInfo().size();k++){
//            if (NOW_SHOWING_ONLINE_DOC.getOnlineDoctorsServiceInfo().get(k).getStatus()==1){
//                LIST.add(NOW_SHOWING_ONLINE_DOC.getOnlineDoctorsServiceInfo().get(k));
//            }
//        }

        DrOnlineServicesAdapterBigItem mAdapter = new DrOnlineServicesAdapterBigItem(NOW_SHOWING_ONLINE_DOC.getOnlineDoctorsServiceInfo());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);


    }


    @Override
    public void onSubscriptionViewSuccess(SubscriptionViewResponse data) {
        if (data != null) {
            if (data.getChat() > 0) IS_CHAT_SUBSCRIBED = true;
            if (data.getVideo() > 0) IS_VIDEO_CALL_SUBSCRIBED = true;
            if (data.getOne() > 0) IS_1_SUBSCRIBED = true;
            if (data.getThree() > 0) IS_3_SUBSCRIBED = true;
            if (data.getSix() > 0) IS_6_SUBSCRIBED = true;
            if (data.getYear() > 0) IS_12_SUBSCRIBED = true;
            initRecyclerView();
        }


    }

    @Override
    public void onSubscriptionViewFailed(String msg) {

    }
}

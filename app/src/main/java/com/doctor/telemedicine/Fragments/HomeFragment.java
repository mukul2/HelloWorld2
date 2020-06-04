package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.doctor.telemedicine.Activity.AmbulanceActivity;
import com.doctor.telemedicine.Activity.ChatListActivity;
import com.doctor.telemedicine.Activity.EnjoyPregnancyDrListActivity;
import com.doctor.telemedicine.Activity.GuideLineActivity;
import com.doctor.telemedicine.Activity.OnlineDoctorsActivity;
import com.doctor.telemedicine.Activity.PersonalPhysicianAcrivity;
import com.doctor.telemedicine.Activity.RequestReviewActivityPatient;
import com.doctor.telemedicine.Activity.SpecialistActivity;
import com.doctor.telemedicine.Activity.SubscriptionActivityPatient;
import com.doctor.telemedicine.Activity.VideoCallAppointmentList;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;


public class HomeFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.cardChember)
    CardView cardChember;
    @BindView(R.id.cardOnline)
    CardView cardOnline;

    @BindView(R.id.cardChat)
    CardView cardChat;
    @BindView(R.id.cardGuide)
    CardView cardGuide;
    @BindView(R.id.cardVideoAppointment)
    CardView cardVideoAppointment;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
        cardChember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SpecialistActivity.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatListActivity.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });

        cardVideoAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoCallAppointmentList.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
    //GuideLineActivity


    @OnClick(R.id.cardOnline)
    public void cardGuide() {
        startActivity(new Intent(context, OnlineDoctorsActivity.class));
    }

    @OnClick(R.id.cardGuide)
    public void openGuideline() {
        startActivity(new Intent(context, GuideLineActivity.class));
    }

    @OnClick(R.id.cardChat)
    public void openChatList() {

        //startActivity(new Intent(context, DrChatActivity.class));
    }

    @OnClick(R.id.cardSubscription)
    public void openSubscriptions() {
        startActivity(new Intent(context, SubscriptionActivityPatient.class));
    }

    @OnClick(R.id.cardAmbulance)
    public void openAmbulance() {
        startActivity(new Intent(context, AmbulanceActivity.class));
    }


}

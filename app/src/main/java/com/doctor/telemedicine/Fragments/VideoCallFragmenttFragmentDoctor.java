package com.doctor.telemedicine.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Activity.CallReceiveActivity;
import com.doctor.telemedicine.Activity.DialingActivity;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.CallLogHistoryAdapter;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.CurrentlyOnlineDoctorAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.CallHistoryPatient;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.VideoCallModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoCallFragmenttFragmentDoctor extends Fragment implements  ApiListener.doctorCallLogListener  {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.switch_online)
    Switch switch_online;
    SessionManager sessionManager;
    String USER_ID;
    ApiListener.doctorOnlineStatusChangeListener listener;
    SinchClient sinchClient;
    Call call;

    public VideoCallFragmenttFragmentDoctor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_video_call_doctor, container, false);
        context=v.getContext();
        sessionManager=new SessionManager(context);
        USER_ID=sessionManager.getUserId();

        ButterKnife.bind(this,v);
       // changeDrOnlineStatus
        switch_online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               // MyProgressBar.with(context);
                if (b){


                    Api.getInstance().changeDrOnlineStatus(USER_ID,"1",listener);
                }else {
                    Api.getInstance().changeDrOnlineStatus(USER_ID,"0",listener);
                }
            }
        });
        listener=new ApiListener.doctorOnlineStatusChangeListener() {
            @Override
            public void ondoctorOnlineStatusChangeSuccess(StatusMessage statusMessage) {
               // MyProgressBar.dismiss();
                Toast.makeText(context, statusMessage.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void ondoctorOnlineStatusChangeFailed(String msg) {
               // MyProgressBar.dismiss();

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        };
        askPermissions();
        Api.getInstance().downloadCallLogDoctor(USER_ID,this);



        return v;
    }

    @Override
    public void onDoctorCallLogSuccess(List<CallHistoryPatient> list) {
    /*    CallLogHistoryAdapter mAdapter = new CallLogHistoryAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        */

    }

    @Override
    public void onDoctorCallLogFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            SinchCons.incomingCallInstance=incomingCall;
            SinchCons.callSnichClient=sinchClient;
            Toast.makeText(context, "Incomming call deteted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(context, CallReceiveActivity.class));

        }
    }
    private void askPermissions() {
        Dexter.withActivity((Activity)context)
                .withPermissions(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            //RECEIVER_ID
                            //CALLER_ID
                            sinchClient = Sinch.getSinchClientBuilder()
                                    .context(context)
                                    .userId(USER_ID)
                                    .applicationKey(SinchCons.applicationKey)
                                    .applicationSecret(SinchCons.applicationSecret)
                                    .environmentHost(SinchCons.environmentHost)
                                    .build();
                            sinchClient.startListeningOnActiveConnection();

                            sinchClient.setSupportCalling(true);
                            sinchClient.start();
                            sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                            // sinchClient.getCallClient().addCallClientListener(new Video);




                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

}

package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import android.os.Bundle;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.Fragments.VideoCallFragmenttFragmentDoctor;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.CallLogHistoryAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.CallHistoryPatient;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.VideoCallHistoryModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;

public class VideoCallActivityDr extends AppCompatActivity implements  ApiListener.VideoCallHistoryDownloadListenerPatient, ServiceConnection {
    Context context=this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.switch_online)
    Switch switch_online;
    SessionManager sessionManager;
    String USER_ID;
    ApiListener.doctorOnlineStatusChangeListener listener;
    SinchClient sinchClient;
    private SinchService.SinchServiceInterface mSinchServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_dr);
        sessionManager=new SessionManager(context);
        USER_ID=sessionManager.getUserId();

        ButterKnife.bind(this);
        // changeDrOnlineStatus
        switch_online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // MyProgressBar.with(context);
                if (b==true){
                    sessionManager.set_isCallEnabled(true);

                askPermissions();

                }else {
                    sessionManager.set_isCallEnabled(false);

                }
            }
        });

        switch_online.setChecked(sessionManager.get_isCallEnabled());
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
      //  askPermissions();
        Api.getInstance().getVideoCallSummery(TOKEN,USER_ID,"doctor",this);
    }
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SinchService.MESSAGE_PERMISSIONS_NEEDED:
                    Bundle bundle = msg.getData();
                    String requiredPermission = bundle.getString(SinchService.REQUIRED_PERMISSION);
                    ActivityCompat.requestPermissions(VideoCallActivityDr.this, new String[]{requiredPermission}, 0);
                    break;
            }
        }
    });

    private void bindService() {
        Intent serviceIntent = new Intent(this, SinchService.class);
        serviceIntent.putExtra(SinchService.MESSENGER, messenger);
        getApplicationContext().bindService(serviceIntent, VideoCallActivityDr.this, BIND_AUTO_CREATE);
    }
    private void askPermissions() {
        Dexter.withActivity(VideoCallActivityDr.this)
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
                            //loginClicked();
                            sessionManager.set_isCallEnabled(true);
                            bindService();


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

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onVideoCallHistoryDownloadSuccess(List<VideoCallHistoryModel> data) {
        CallLogHistoryAdapter mAdapter = new CallLogHistoryAdapter(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onVideoCallHistoryDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}

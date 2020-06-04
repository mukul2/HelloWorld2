package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.Data;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.model.VideoCallModel;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;

public class DialingActivity extends AppCompatActivity {
    @BindView(R.id.tv_dialing)
    TextView tv_dialing;
    @BindView(R.id.imageReject)
    ImageView imageReject;
    SinchClient sinchClient;
    Call call;
    Context context=this;
    SessionManager sessionManager;
    String USER_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialing);
        ButterKnife.bind(this);
        tv_dialing.setText("Dialing " + NOW_SHOWING_ONLINE_DOC.getName());
        sessionManager=new SessionManager(this);
        USER_ID=sessionManager.getUserId();
        askPermissions();


    }
    public void call(String drId) {
        //CALLER_ID
        //RECEIVER_ID

        if (call == null) {
            call = sinchClient.getCallClient().callUserVideo(drId);
            imageReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call.hangup();
                    onBackPressed();
                }
            });

        }
       // call.addCallListener(new SinchCallListener());
        call.addCallListener(new VideoCallListener() {
            @Override
            public void onVideoTrackAdded(Call call) {
                VideoController vc = sinchClient.getVideoController();
                View myPreview = vc.getLocalView();
                View remoteView = vc.getRemoteView();


                LayoutInflater inflater = getLayoutInflater();
                ViewGroup main = (ViewGroup) findViewById(R.id.relativeView);
                ViewGroup tv_myView = (ViewGroup) findViewById(R.id.tv_myView);
                main.addView(remoteView, 0);
                tv_myView.addView(myPreview, 0);


            }

            @Override
            public void onVideoTrackPaused(Call call) {

            }

            @Override
            public void onVideoTrackResumed(Call call) {

            }

            @Override
            public void onCallProgressing(Call call) {

            }

            @Override
            public void onCallEstablished(Call call) {

            }

            @Override
            public void onCallEnded(Call call) {
                startActivity(new Intent(context,PatientHomeActivity.class));
                finishAffinity();

            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }
        });
    }
    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            call=incomingCall;
            Toast.makeText(context, "Incomming call deteted", Toast.LENGTH_SHORT).show();

        }
    }

    private void askPermissions() {
        Dexter.withActivity(this)
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

                            sinchClient.addSinchClientListener(new SinchClientListener() {
                                @Override
                                public void onClientStarted(SinchClient sinchClient) {
                                    call(""+NOW_SHOWING_ONLINE_DOC.getId());
                                }

                                @Override
                                public void onClientStopped(SinchClient sinchClient) {

                                }

                                @Override
                                public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {

                                }

                                @Override
                                public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {

                                }

                                @Override
                                public void onLogMessage(int i, String s, String s1) {

                                }
                            });


                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            onBackPressed();
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

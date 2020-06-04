package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.AudioPlayer;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;

public class VoiceCallDialActivity extends VoiceCallBaseActivity{
    Context context=this;
    AudioPlayer audioManager;
    Call call;
    String mCallId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call_dial);
        audioManager=new AudioPlayer(context);
       // askPermissions();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }
    @Override
    protected void onServiceConnected() {

       // mCallButton.setEnabled(true);
      //  callButtonClicked();
    }

    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

//    private void callButtonClicked() {
//        String userName = ""+NOW_SHOWING_ONLINE_DOC.getId();
//        if (userName.isEmpty()) {
//            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        try {
//            Call call = getSinchServiceInterface().callUser(userName);
//            if (call == null) {
//                // Service failed for some reason, show a Toast and abort
//                Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
//                        + "placing a call.", Toast.LENGTH_LONG).show();
//                return;
//            }
//            String callId = call.getCallId();
//            Intent callScreen = new Intent(this, CallScreenActivity.class);
//            callScreen.putExtra(SinchService.CALL_ID, callId);
//            startActivity(callScreen);
//        } catch (MissingPermissionException e) {
//            ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
//        }
//
//    }





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
                            Toast.makeText(context, "started", Toast.LENGTH_SHORT).show();
                            // do you work now
                            //RECEIVER_ID
                            //CALLER_ID
//                            sinchClient = Sinch.getSinchClientBuilder()
//                                    .context(context)
//                                    .userId(DataStore.USER_ID)
//                                    .applicationKey(SinchCons.applicationKey)
//                                    .applicationSecret(SinchCons.applicationSecret)
//                                    .environmentHost(SinchCons.environmentHost)
//                                    .build();
//                            sinchClient.startListeningOnActiveConnection();
//
//                            sinchClient.setSupportMessaging(true);
//                            sinchClient.setSupportCalling(true);
//                            sinchClient.setSupportMessaging(true);
//                            sinchClient.setSupportActiveConnectionInBackground(true);
//
//                            sinchClient.start();
//                         //   sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
//                            // sinchClient.getCallClient().addCallClientListener(new Video);
//                            sinchClient.addSinchClientListener(new SinchClientListener() {
//                                @Override
//                                public void onClientStarted(SinchClient sinchClient) {
//                                    call(""+NOW_SHOWING_ONLINE_DOC.getId());
//
//                                }
//
//                                @Override
//                                public void onClientStopped(SinchClient sinchClient) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(VoiceCallDialActivity.this, "4 ", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//
//                                }
//
//                                @Override
//                                public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(VoiceCallDialActivity.this,sinchError.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//
//
//                                }
//
//                                @Override
//                                public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(VoiceCallDialActivity.this, "1 ", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//
//
//                                }
//
//                                @Override
//                                public void onLogMessage(int i, String s, String s1) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            //Toast.makeText(VoiceCallDialActivity.this, "log ", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//
//                                }
//                            });




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
//
//    private void call(String s) {
//        CallClient callClient = sinchClient.getCallClient();
//         call = callClient.callUser(s);
//
//        call.addCallListener(new CallListener() {
//            @Override
//            public void onCallProgressing(Call call) {
//                audioManager.playProgressTone();
//                sinchClient .getAudioController().enableSpeaker();
//
//            }
//
//            @Override
//            public void onCallEstablished(Call call) {
//                audioManager.stopProgressTone();
//
//                Toast.makeText(VoiceCallDialActivity.this, "call stablished", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCallEnded(Call call) {
//                call.hangup();
//                audioManager.stopProgressTone();
//                setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
//                onBackPressed();
//
//
//
//            }
//
//            @Override
//            public void onShouldSendPushNotification(Call call, List<PushPair> list) {
//                Toast.makeText(context, "here", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
    public void hangup(View view) {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();


       }

    }


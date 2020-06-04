package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.R;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

import static com.doctor.telemedicine.Data.Data.CALL_TYPE_AUDIO;
import static com.doctor.telemedicine.Data.Data.CALL_TYPE_VIDEO;
import static com.doctor.telemedicine.Data.Data.TYPE_OF_CALL;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class NewDialingActivity extends VoiceCallBaseActivity implements SinchService.StartFailedListener {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dialing);
        //askPermissions();
//        getApplicationContext().bindService(new Intent(this, SinchService.class), this,
//                BIND_AUTO_CREATE);


    }

    private void askPermissions() {
        Dexter.withActivity(NewDialingActivity.this)
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
                            loginClicked();


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

    @Override
    protected void onServiceConnected() {
        //mLoginButton.setEnabled(true);
        Toast.makeText(context, "service connected", Toast.LENGTH_SHORT).show();
        getSinchServiceInterface().setStartListener(this);
        askPermissions();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStarted() {

        Toast.makeText(context, "came here 11", Toast.LENGTH_SHORT).show();


        if (TYPE_OF_CALL.equals(CALL_TYPE_AUDIO)) {
            openVoiceCallActivity();
            Toast.makeText(context, "audio call", Toast.LENGTH_SHORT).show();
        } else if (TYPE_OF_CALL.equals(CALL_TYPE_VIDEO)) {
            openVideoCallActivity();
        }
    }

    private void loginClicked() {
        String userName = USER_ID;

        Toast.makeText(context, "came here", Toast.LENGTH_SHORT).show();

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
        } else {
            if (TYPE_OF_CALL.equals(CALL_TYPE_AUDIO)) {
                openVoiceCallActivity();
            } else if (TYPE_OF_CALL.equals(CALL_TYPE_VIDEO)) {
                openVideoCallActivity();
            }
        }
    }

    private void openVoiceCallActivity() {
     /*   if (NOW_SHOWING_ONLINE_DOC.getId()!=null) {

            try {
                Call call = getSinchServiceInterface().callUser("" + NOW_SHOWING_ONLINE_DOC.getId());
                if (call == null) {
                    // Service failed for some reason, show a Toast and abort
                    Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                            + "placing a call.", Toast.LENGTH_LONG).show();
                    return;
                }
                String callId = call.getCallId();
                Intent callScreen = new Intent(this, VoiceDialActivity.class);
                callScreen.putExtra(SinchService.CALL_ID, callId);
                startActivity(callScreen);
                finish();

            } catch (MissingPermissionException e) {
                ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
            }
        }else {
            Toast.makeText(context, "recp id null", Toast.LENGTH_SHORT).show();
        }

      */
    }

    private void openVideoCallActivity() {


        Call call = getSinchServiceInterface().callUserVideo("" + NOW_SHOWING_ONLINE_DOC.getId());
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, VideoCallUIActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
        finish();


    }
}

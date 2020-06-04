package com.doctor.telemedicine.Activity;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.AudioPlayer;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.StatusMessage;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallState;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.doctor.telemedicine.Data.Data.NOW_HITTING_SERVICE;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class CallScreenActivity extends VoiceCallBaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();
    static final String ADDED_LISTENER = "addedListener";
    static final String VIEWS_TOGGLED = "viewsToggled";

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    private boolean mAddedListener = false;
    private boolean mLocalVideoViewAdded = false;
    private boolean mRemoteVideoViewAdded = false;

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;
    boolean mToggleVideoViewPositions = false;
    ImageView partnerPHOTO;
    Context context = this;
    ImageView switcheCamera, loudSpeaker;

    int LOUD_SPEAKER = 0;
    int MINI_SPEAKER = 1;
    int SPEAKER_MODE = 0;

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
        savedInstanceState.putBoolean(VIEWS_TOGGLED, mToggleVideoViewPositions);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);
        mToggleVideoViewPositions = savedInstanceState.getBoolean(VIEWS_TOGGLED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callscreen);
        //NOW_SHOWING_ONLINE_DOC

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = findViewById(R.id.callDuration);
        mCallerName = findViewById(R.id.remoteUser);
        mCallState = findViewById(R.id.callState);
        partnerPHOTO = findViewById(R.id.partnerPHOTO);
        loudSpeaker = findViewById(R.id.loudSpeaker);
        switcheCamera = findViewById(R.id.switcheCamera);
        ImageView endCallButton = findViewById(R.id.hangupButton);

        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });

        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if (!mAddedListener) {
                call.addCallListener(new SinchCallListener());
                mAddedListener = true;
            }
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }

        updateUI();
    }

    private void updateUI() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            // mCallerName.setText(call.getRemoteUserId());

            Glide.with(context).load(PHOTO_BASE +getIntent().getStringExtra("USER_PHOTO")).into(partnerPHOTO);

            if (SESSION_MANAGER.getUserType().equals("p")) {
                //this is outgoing cal
                mCallerName.setText( getIntent().getStringExtra("DOCTOR_NAME"));
            } else {
                //this is incomming
                String USER_NAME = getIntent().getStringExtra("USER_NAME");
                String USER_PHOTO = getIntent().getStringExtra("USER_PHOTO");

                String DOCTOR_NAME = getIntent().getStringExtra("DOCTOR_NAME");
                String PATIENT_NAME = getIntent().getStringExtra("PATIENT_NAME");
                mCallerName.setText(getIntent().getStringExtra("PATIENT_NAME"));
                // Glide.with(context).load(PHOTO_BASE + USER_PHOTO).into(partnerPHOTO);

            }


            mCallState.setText(call.getState().toString());
            if (call.getDetails().isVideoOffered()) {
                if (call.getState() == CallState.ESTABLISHED) {
                    setVideoViewsVisibility(true, true);
                } else {
                    setVideoViewsVisibility(true, false);
                }
            }
        } else {
            setVideoViewsVisibility(false, false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mDurationTask.cancel();
        mTimer.cancel();
        removeVideoViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
        updateUI();
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }

        if (SESSION_MANAGER.getUserType().equals("d")) {
            startActivity(new Intent(this, HomeActivityDrActivity.class));

        } else {
            startActivity(new Intent(this, PatientHomeActivity.class));

        }
        finishAffinity();


    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallDuration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    private ViewGroup getVideoView(boolean localView) {
        if (mToggleVideoViewPositions) {
            localView = !localView;
        }
        return (ViewGroup) (localView ? findViewById(R.id.localVideo) : findViewById(R.id.remoteVideo));
    }

    private void addLocalView() {
        if (mLocalVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewGroup localView = getVideoView(true);
                    localView.addView(vc.getLocalView());
                    localView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vc.toggleCaptureDevicePosition();
                        }
                    });
                    mLocalVideoViewAdded = true;
                    vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
                }
            });
        }
    }

    private void addRemoteView() {
        final VideoController vc = getSinchServiceInterface().getVideoController();

        if (mRemoteVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        loudSpeaker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPEAKER_MODE == LOUD_SPEAKER) {
                    setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
                    SPEAKER_MODE = MINI_SPEAKER;
                } else {
                    setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                    AudioController audioController = getSinchServiceInterface().getAudioController();
                    audioController.enableSpeaker();
                    SPEAKER_MODE = LOUD_SPEAKER;
                }

            }
        });
        switcheCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                vc.toggleCaptureDevicePosition();
            }
        });
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewGroup remoteView = getVideoView(false);
                    remoteView.addView(vc.getRemoteView());
                    remoteView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeVideoViews();
                            mToggleVideoViewPositions = !mToggleVideoViewPositions;
                            addRemoteView();
                            addLocalView();
                        }
                    });
                    mRemoteVideoViewAdded = true;
                    vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
                }
            });
        }
    }


    private void removeVideoViews() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((ViewGroup) (vc.getRemoteView().getParent())).removeView(vc.getRemoteView());
                    ((ViewGroup) (vc.getLocalView().getParent())).removeView(vc.getLocalView());
                    mLocalVideoViewAdded = false;
                    mRemoteVideoViewAdded = false;
                }
            });
        }
    }

    private void setVideoViewsVisibility(final boolean localVideoVisibile, final boolean remoteVideoVisible) {
        if (getSinchServiceInterface() == null)
            return;
        if (mRemoteVideoViewAdded == false) {
            addRemoteView();
        }
        if (mLocalVideoViewAdded == false) {
            addLocalView();
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();

        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vc.getLocalView().setVisibility(localVideoVisibile ? View.VISIBLE : View.GONE);
                    vc.getRemoteView().setVisibility(remoteVideoVisible ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    private class SinchCallListener implements VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
            Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            if (SESSION_MANAGER.getUserType().equals("p")&& call.getDetails().getDuration()>0) {
                Calendar calendar = Calendar.getInstance();
                endCall();
             /*   Api.getInstance().postCallRecord(TOKEN, USER_ID, call.getRemoteUserId(), "" + System.currentTimeMillis(), "" + call.getDetails().getDuration(), USER_ID,NOW_HITTING_SERVICE,NOW_SHOWING_ONLINE_DOC.getName(), new ApiListener.VideoCallPostListener() {
                    @Override
                    public void onVideoCallPostSuccess(StatusMessage response) {


                        endCall();
                    }

                    @Override
                    public void onVideoCallPostFailed(String msg) {

                    }
                });

              */

            } else {
                endCall();
            }

        }

        @Override
        public void onCallEstablished(Call call) {


            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = getSinchServiceInterface().getAudioController();
            audioController.enableSpeaker();
            if (call.getDetails().isVideoOffered()) {
                setVideoViewsVisibility(true, true);
            }
            Log.d(TAG, "Call offered video: " + call.getDetails().isVideoOffered());
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {

        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {

        }
    }
}

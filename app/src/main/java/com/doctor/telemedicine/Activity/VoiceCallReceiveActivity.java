package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.AudioPlayer;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.SinchCons.callSnichClient;
import static com.doctor.telemedicine.Data.SinchCons.incomingCallInstance;

public class VoiceCallReceiveActivity extends AppCompatActivity {
    Context context = this;

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;
    String callerID;
    @BindView(R.id.tv_duration)
    TextView tv_duration;

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            VoiceCallReceiveActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call_receive);
        ButterKnife.bind(this);

        callSnichClient.getAudioController().enableSpeaker();
        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();

        callerID = incomingCallInstance.getCallId();
        incomingCallInstance.addCallListener(new CallListener() {
            @Override
            public void onCallProgressing(Call call) {


            }

            @Override
            public void onCallEstablished(Call call) {
                if (mAudioPlayer!=null){
                    mAudioPlayer.stopRingtone();
                }
                Toast.makeText(context, "call received", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCallEnded(Call call) {
                Toast.makeText(context, "call ened", Toast.LENGTH_SHORT).show();
                if (mAudioPlayer!=null){
                    mAudioPlayer.stopRingtone();
                }
                call.hangup();

                onBackPressed();

            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }
        });
    }

    public void reject(View view) {
        incomingCallInstance.hangup();
        if (mAudioPlayer != null) {
            mAudioPlayer.stopRingtone();

        }
        if (mDurationTask != null) {
            mDurationTask.cancel();

        }
        if (mTimer != null) {
            mTimer.cancel();

        }
        onBackPressed();
    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = incomingCallInstance;
        if (call != null) {
            tv_duration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    public void connect(View view) {
        incomingCallInstance.answer();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);


    }
}

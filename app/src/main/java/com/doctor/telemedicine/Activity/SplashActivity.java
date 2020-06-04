package com.doctor.telemedicine.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.SessionManager;

import java.util.Calendar;

import static com.doctor.telemedicine.Data.Data.TYPE_DOCTOR;
import static com.doctor.telemedicine.Data.Data.TYPE_PATIENT;


public class SplashActivity extends Activity {
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(SplashActivity.this);

        setUpStatusbar();
        View mSplashImage = findViewById(R.id.splash);
        TextView mSplashText = findViewById(R.id.splashText);
        Animation splashAnimImage = AnimationUtils.loadAnimation(this, R.anim.splash_anim_img);
        Animation splashAnimText = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        mSplashText.startAnimation(splashAnimText);
        mSplashImage.startAnimation(splashAnimImage);

        splashAnimImage.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (sessionManager.getLoggedIn()) {

                    {
                        if (sessionManager.getUserType().equals(TYPE_DOCTOR)){
                            startActivity(new Intent(SplashActivity.this, HomeActivityDrActivity.class));
                            finish();
                        }else   if (sessionManager.getUserType().equals(TYPE_PATIENT)){
                            startActivity(new Intent(SplashActivity.this, PatientHomeActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }



                    }

                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        String startMonth,endMonth;
        Calendar calendar = Calendar.getInstance();
        startMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
        calendar.add(Calendar.MONTH,1);
        endMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
       // Toast.makeText(this, ""+startMonth+"\n"+endMonth, Toast.LENGTH_SHORT).show();


    }


    private void setUpStatusbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
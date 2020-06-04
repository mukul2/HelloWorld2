package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.StatusMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.confirm_new_password)
    EditText confirm_new_password;
    String mobile;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setUpStatusbar();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mobile = bundle.getString("mobile");

        }
    }

    public void setUpStatusbar() {
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

    public void back(View view) {
        onBackPressed();
    }

    public void confirm(View view) {
        String newPass = new_password.getText().toString().trim();
        String newPassConfirm = confirm_new_password.getText().toString().trim();
        if (newPass.length() > 5) {
            if (newPassConfirm.length() > 5) {
                if (newPass.equals(newPassConfirm)) {
                    MyProgressBar.with(context);
                    Api.getInstance().updatePassword(mobile, newPass, new ApiListener.updatePassword() {
                        @Override
                        public void onUpdatePasswordSuccess(StatusMessage data) {
                            if (data.getStatus() == true) {
                                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context,LoginActivity.class));
                                finishAffinity();
                            }

                        }

                        @Override
                        public void onUpdatePasswordFailed(String msg) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        }


    }
}

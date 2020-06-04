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

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.VERIFICATION_PHONE_NUMBER;

public class ForgotePasswordPhoneVerificationActivity extends AppCompatActivity {
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth firebaseAuth;
    @BindView(R.id.ed_code_number)
    EditText ed_code_number;
    String mVerificationId;
    Context context = this;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgote_password_phone_verification);
        ButterKnife.bind(this);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            number = (String) b.get("number");
            firebaseAuth = FirebaseAuth.getInstance();
            MyProgressBar.with(context);
            request_sms_varification(number);


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

    private void request_sms_varification(String phoneNumber) {
        //   progressDial.show();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {


                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                MyProgressBar.dismiss();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(ForgotePasswordPhoneVerificationActivity.this, "Invalid request", Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(ForgotePasswordPhoneVerificationActivity.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();

                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;


            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(


                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        final FirebaseUser user = task.getResult().getUser();

                        // presentActivity();
                        // Intent i = new Intent(PhonVarificationActivity.this, SignUpActivity.class);
                        //   i.putExtra("code", credential.getSmsCode());
                        //  i.putExtra("number", ed_number.getText().toString().trim());
                        // i.putExtra("fb_id", user.getUid());
                        // CachedData.CODE= credential.getSmsCode();
                        //  CachedData.NUMBER=ed_number.getText().toString().trim().replace("+","");
                        //  CachedData.fb_id=user.getUid();


                        // startActivity(i);
                        // finish();
                        MyProgressBar.dismiss();

                        Toast.makeText(this, "Successfully verified", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ChangePasswordActivity.class);
                        intent.putExtra("mobile",number);
                        startActivity(intent);

                    } else {
                        //  Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            //  MyUtils.getInstance().buildSnackMessage(snackView,"Invalid code !!!",LENGTH_LONG).show();
                            // Toasty.error(Phone_Varification_Activity.this,)
                            //  Toasty.error(PhonVarificationActivity.this,"Invalid code !!!",Toast.LENGTH_LONG,true).show();
                        }
                        // pDialog.hide();
                    }
                });
    }


    public void compare_pin(View view) {
        if (ed_code_number.getText().toString().trim().length() > 3) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, ed_code_number.getText().toString().trim());
            signInWithPhoneAuthCredential(credential);
        }
    }

    public void resend(View view) {
        MyProgressBar.with(context);
        request_sms_varification(number);

    }

    public void back(View view) {
        onBackPressed();
    }
}

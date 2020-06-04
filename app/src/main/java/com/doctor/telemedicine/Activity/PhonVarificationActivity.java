package com.doctor.telemedicine.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.Success;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.VERIFICATION_PHONE_NUMBER;


public class PhonVarificationActivity extends AppCompatActivity implements ApiListener.successListener {
    @BindView(R.id.ed_number)
    EditText ed_number;
    @BindView(R.id.tv_wait)
    TextView tv_wait;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth firebaseAuth;
    @BindView(R.id.btn_card)
    CardView btn_card;
    String mVerificationId;
    @BindView(R.id.linear_body1)
    LinearLayout linear_body1;

    @BindView(R.id.linear_body2)
    LinearLayout linear_body2;
    @BindView(R.id.ed_code_number)
    EditText ed_code_number;
    ProgressDialog progressDial;
    //PhoneInputLayout phoneInputLayout;
    // PhoneEditText phoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phon_varification);
        ButterKnife.bind(this);
        progressDial = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        initListener();
        // phoneInputLayout = (PhoneInputLayout) findViewById(R.id.phone_input_layout);
        //   phoneEditText = (PhoneEditText) findViewById(R.id.edit_text);
        // phoneInputLayout.setDefaultCountry("bd");
        //phoneInputLayout.getEditText().setBackgroundColor(Color.BLACK);


    }

    private void initListener() {
        ed_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numberValidate(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void numberValidate(CharSequence s) {

        if (s.length() == 11) {
            {
                if (s.charAt(0) == '0') {
                    if (s.charAt(1) == '1') {
                        if (Integer.parseInt(String.valueOf(s.charAt(2))) > 2) {
                            ed_number.setTextColor(getResources().getColor(R.color.colorPrimary));
                            btn_card.setClickable(true);
                            btn_card.setAlpha(1.0f);
                            ed_number.setText("88" + s.toString());


                        } else {
                            btn_card.setClickable(false);
                            btn_card.setAlpha(0.5f);
                        }
                    } else {
                        btn_card.setClickable(false);
                        btn_card.setAlpha(0.5f);
                    }
                } else {
                    btn_card.setClickable(false);
                    btn_card.setAlpha(0.5f);
                }

            }

        } else if (s.length() == 13) {

            if (s.charAt(0) == '8') {
                if (s.charAt(1) == '8') {
                    if (s.charAt(2) == '0') {
                        if (s.charAt(3) == '1') {
                            if (Integer.parseInt(String.valueOf(s.charAt(4))) > 2) {
                                ed_number.setTextColor(getResources().getColor(R.color.colorPrimary));
                                btn_card.setClickable(true);
                                btn_card.setAlpha(1.0f);


                            } else {
                                btn_card.setClickable(false);
                                btn_card.setAlpha(0.5f);
                            }
                        } else {
                            btn_card.setClickable(false);
                            btn_card.setAlpha(0.5f);
                        }


                    } else {
                        btn_card.setClickable(false);
                        btn_card.setAlpha(0.5f);
                    }
                } else {
                    btn_card.setClickable(false);
                    btn_card.setAlpha(0.5f);
                }
            } else {
                btn_card.setClickable(false);
                btn_card.setAlpha(0.5f);
            }
        } else {
            btn_card.setClickable(false);
            btn_card.setAlpha(0.5f);
        }


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

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(PhonVarificationActivity.this, "Invalid request", Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(PhonVarificationActivity.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();

                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                tv_wait.setText("A pin is sending to " + phoneNumber + " Enter The Varification Code You Have Received Through SMS");
                ed_number.setEnabled(false);
                VERIFICATION_PHONE_NUMBER = phoneNumber;

                progressDial.dismiss();
                linear_body1.setVisibility(View.GONE);
                linear_body2.setVisibility(View.VISIBLE);


            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(


                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    public void varify(View view) {
        // request_sms_varification();\

        if (ed_number.getText().toString().trim().charAt(0) != '+') {
            // ed_number.setText("+" + ed_number.getText().toString().trim());
            String number="+" + ed_number.getText().toString().trim();
            progressDial.setMessage("Please wait");
            progressDial.show();
            Api.getInstance().checkMobileNumber(number, new ApiListener.NumberUniqueCheckListener() {
                @Override
                public void onNumberUniqueCheckSuccess(StatusMessage status) {
                    progressDial.dismiss();
                    if (status.getStatus()==true){
                        request_sms_varification("+" + ed_number.getText().toString().trim());

                    }else {
                        MyDialog.getInstance().with(PhonVarificationActivity.this)
                                .message(status.getMessage())
                                .autoBack(false)
                                .autoDismiss(false)
                                .show();
                    }

                }

                @Override
                public void onNumberUniqueCheckFailed(String msg) {
                    progressDial.dismiss();

                }
            });



        }

        //   check_if_unique(ed_number.getText().toString().trim().replace("+",""));


    }


    private boolean check_if_unique(String trim) {
        boolean status = false;

        // Api.getInstance().checkPhone(trim,this);


        return status;
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        final FirebaseUser user = task.getResult().getUser();

                        // presentActivity();
                        progressDial.dismiss();
                        Intent i = new Intent(PhonVarificationActivity.this, SignUpActivity.class);
                        //   i.putExtra("code", credential.getSmsCode());
                        //  i.putExtra("number", ed_number.getText().toString().trim());
                        // i.putExtra("fb_id", user.getUid());
                        // CachedData.CODE= credential.getSmsCode();
                        //  CachedData.NUMBER=ed_number.getText().toString().trim().replace("+","");
                        //  CachedData.fb_id=user.getUid();


                        startActivity(i);
                        finish();

                        Toast.makeText(this, "Successfully verified", Toast.LENGTH_SHORT).show();

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
        progressDial.show();
        if (ed_code_number.getText().toString().trim().length() > 3) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, ed_code_number.getText().toString().trim());
            signInWithPhoneAuthCredential(credential);
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void Onsuccess(Success response) {
        progressDial.dismiss();

        if (response.isSuccess()) {

            request_sms_varification(ed_number.getText().toString().trim());

        } else {
            MyDialog.getInstance().with(this).autoDismiss(false).autoBack(false).message("Number is not available").show();

        }


    }

    @Override
    public void Onfailed(String message) {
        progressDial.dismiss();

        MyDialog.getInstance().with(this).autoDismiss(false).autoBack(false).message("Error on connecting server.Please try again").show();

    }

    public void resend(View view) {
        progressDial.setMessage("Please wait");
        progressDial.show();
        if (ed_number.getText().toString().trim().charAt(0) != '+') {
            // ed_number.setText("+" + ed_number.getText().toString().trim());
            request_sms_varification("+" + ed_number.getText().toString().trim());


        }
    }
}

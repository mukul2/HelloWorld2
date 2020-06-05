package com.doctor.telemedicine.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.doctor.telemedicine.R;

import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentAddResponse;
import com.doctor.telemedicine.model.StatusMessage;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.doctor.telemedicine.Data.Data.PAY_PAL_CLIENT_ID;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class ProjectPaypalPaymentActivity extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7777;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAY_PAL_CLIENT_ID);


    String amount = "";
    String type;
    Context context = this;
    String startMonth,endMonth;
    Calendar calendar;
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_paypal_payment);

        //

        calendar = Calendar.getInstance();
        startMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));

        //start paypal service

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            int j = (int) b.get("credit");
            type = (String) b.get("type");
            amount = "" + j;
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
            processPayment();
        }

    }

    private void processPayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Service Fees", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {

                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        Log.i("mkl", paymentDetails);
//                        startActivity(new Intent(this, PaymentDetails.class)
//                                .putExtra("Payment Details", paymentDetails)
//                                .putExtra("Amount", amount));


                        if (type.equals("prescriptionRequest"))
                            processToThePrescriptionRequest(paymentDetails);
                        if (type.equals("videoAppointment"))
                            processToTheAddVideoAppointment(paymentDetails);
                        if (type.equals("prescriptionReview"))
                            processToThePrescriptioReview(paymentDetails);
                        if (type.equals("chat"))
                            processToTheChatRequest(paymentDetails);
                        if (type.equals("1MonthSubscription"))
                            processToThe1MonthSubscriptionRequest(paymentDetails);
                        if (type.equals("3MonthSubscription"))
                            processToThe3MonthSubscriptionRequest(paymentDetails);
                        if (type.equals("6MonthSubscription"))
                            processToThe6MonthSubscriptionRequest(paymentDetails);
                        if (type.equals("12MonthSubscription"))
                            processToThe12MonthSubscriptionRequest(paymentDetails);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("mkl", e.getLocalizedMessage());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    private void processToThe1MonthSubscriptionRequest(String paymentDetails) {
        calendar.add(Calendar.MONTH,1);
        endMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "1", startMonth, endMonth,amount, new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }
    private void processToThe6MonthSubscriptionRequest(String paymentDetails) {
        calendar.add(Calendar.MONTH,6);
        endMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "6", startMonth, endMonth, amount,new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }
    private void processToThe3MonthSubscriptionRequest(String paymentDetails) {
        calendar.add(Calendar.MONTH,3);
        endMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "3", startMonth, endMonth,amount, new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }
    private void processToThe12MonthSubscriptionRequest(String paymentDetails) {
        calendar.add(Calendar.MONTH,12);
        endMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));
        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "12", startMonth, endMonth, amount,new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }
    private void processToTheChatRequest(String paymentDetails) {
        Api.getInstance().addChatRequest(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails,amount, new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, ChatActivityCommon.class);
                i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                context.startActivity(i);
                finish();


            }

            @Override
            public void onAppointmentPOStFailed(String msg) {

            }
        });
    }

    private void processToThePrescriptionRequest(String paymentDetails) {

        Api.getInstance().add_payment_info_only(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), amount, "Prescription request", new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Intent i = new Intent(getBaseContext(), PrescriptonRequestMakingActivity.class);
                i.putExtra("paymentInfo", paymentDetails);
                i.putExtra("amount", amount);
                //paymentInfo
                startActivity(i);
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }

    private void doNothing() {
        Toast.makeText(this, "Nothing to do", Toast.LENGTH_SHORT).show();
    }

    private void processToTheAddVideoAppointment(String paymentDetails) {
        Api.getInstance().addVideoAppointmentInfo(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "1",amount, new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {

                Toast.makeText(ProjectPaypalPaymentActivity.this, "Appointment ID  " + data.getAppointmentId(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onAppointmentPOStFailed(String msg) {
                Toast.makeText(ProjectPaypalPaymentActivity.this, msg, Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void processToThePrescriptioReview(String paymentDetails) {

        Api.getInstance().add_payment_info_only(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), amount, "Prescription Review", new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Intent intent = new Intent(context, PrescriptionReviewSendingActivity.class);

                intent.putExtra("paymentInfo", paymentDetails);
                intent.putExtra("doctorID", "" + NOW_SHOWING_ONLINE_DOC.getId());
                intent.putExtra("partnerName", NOW_SHOWING_ONLINE_DOC.getName());
                intent.putExtra("partnerPhoto", NOW_SHOWING_ONLINE_DOC.getPhoto());
                intent.putExtra("amount",amount);
                context.startActivity(intent);
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });





    }
}

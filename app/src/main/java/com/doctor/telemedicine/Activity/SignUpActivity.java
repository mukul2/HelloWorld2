package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.BasicByDrResponse;
import com.doctor.telemedicine.model.DepartmentModel;
import com.doctor.telemedicine.model.SignUpResponse;
import com.doctor.telemedicine.model.StatusId;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.StatusResponse;
import com.doctor.telemedicine.model.TestModel;
import com.doctor.telemedicine.model.testSelectedModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.doctor.telemedicine.Data.DataStore.VERIFICATION_PHONE_NUMBER;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_email)
    EditText ed_email;
    @BindView(R.id.ed_password)
    EditText ed_password;
    Uri resultUri;
    Context context = this;
    SessionManager sessionManager;
    String DOCTOR = "d";
    String PATIENT = "p";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        ed_phone.setText(VERIFICATION_PHONE_NUMBER);
        ed_phone.setEnabled(false);

    }

    private void askPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            openCamera();
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

    private void openCamera() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void PickImage(View view) {
        askPermission();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    public void openDoctorRegister(View view) {
        startActivity(new Intent(this, DoctorsRegister.class));
    }

    public void Register(View view) {
        if (resultUri != null) {

            File f = new File(resultUri.getPath());
            MultipartBody.Part photo = null;
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
            String name = ed_name.getText().toString().trim();
            String phone = ed_phone.getText().toString().trim();
            String email = ed_email.getText().toString().trim();
            String password = ed_password.getText().toString().trim();
            if (name.length() > 0) {
                if (phone.length() > 0) {
                    if (email.length() > 0) {
                        if (password.length() > 0) {
                            MyProgressBar.with(context);
                            Api.getInstance().patientSignUp(c_m_b(name),
                                    c_m_b("0"),
                                    c_m_b("p"),
                                    c_m_b(password),
                                    c_m_b(email),
                                    c_m_b(phone),
                                    null,
                                    photo, new ApiListener.PatientSignUPListener() {
                                        @Override
                                        public void onPatientSignUPSuccess(SignUpResponse status) {
                                            MyProgressBar.dismiss();
                                            if (status.getStatus()==true) {
                                                sessionManager.setuserId("" + status.getUserInfo().getId());
                                                sessionManager.setLoggedIn(true);
                                                sessionManager.setuserName(status.getUserInfo().getName());
                                                sessionManager.setuserType(status.getUserInfo().getUserType());
                                                sessionManager.setToken("Bearer " + status.getAccess_token());
                                                sessionManager.set_userPhoto(status.getUserInfo().getPhoto());
                                                sessionManager.set_userMobile(status.getUserInfo().getPhone());
                                                sessionManager.set_userEmail(status.getUserInfo().getEmail());
                                                sessionManager.set_userdisplay(status.getUserInfo().getDesignation_title());
                                                sessionManager.set_userPass(ed_password.getText().toString().trim());

                                                Toast.makeText(context, status.getMessage(), Toast.LENGTH_LONG).show();

                                                if (status.getUserInfo().getUserType().equals(DOCTOR)) {
                                                    startActivity(new Intent(context, HomeActivityDrActivity.class));
                                                    finishAffinity();
                                                } else if (status.getUserInfo().getUserType().equals(PATIENT)) {
                                                    startActivity(new Intent(context, PatientHomeActivity.class));
                                                    finishAffinity();

                                                } else {
                                                    Toast.makeText(context, "Unknown usertype", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                showError(status.getMessage());
                                            }

                                        }

                                        @Override
                                        public void onPatientSignUPSuccessFailed(String msg) {
                                            MyProgressBar.dismiss();
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                        }
                                    });

                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Add Photo first", Toast.LENGTH_SHORT).show();
        }


    }

    private void showError(String msg) {
        MyDialog.getInstance().with(context)
                .message(msg)
                .autoBack(false)
                .autoDismiss(false)
                .show();
    }

    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }

}

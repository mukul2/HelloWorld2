package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.ProfileUpdateResponse;
import com.doctor.telemedicine.model.StatusMessage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class PatientPersonalInfoActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_display_title)
    TextView tv_display_title;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.liner_dr_only)
    LinearLayout liner_dr_only;

    @BindView(R.id.image)
    ImageView image;
    Context context = this;
    Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_info);
        ButterKnife.bind(this);
        setUpStatusbar();
        tv_display_title.setText(SESSION_MANAGER.get_userdisplay());
        tv_email.setText(SESSION_MANAGER.get_userEmail());
        tv_name.setText(SESSION_MANAGER.getUserName());
        tv_phone.setText(SESSION_MANAGER.get_userMobile());
        Glide.with(PatientPersonalInfoActivity.this).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(image);
        if (SESSION_MANAGER.getUserType().equals("p")){
            liner_dr_only.setVisibility(View.GONE);
        }else {
            liner_dr_only.setVisibility(View.VISIBLE);
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

    public void showNameEditDialog(View view) {
        Dialog dialog = doForMe.showDialog(context, R.layout.change_user_name_dialog);
        EditText newName = (EditText) dialog.findViewById(R.id.ed_name);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
        newName.setText(SESSION_MANAGER.getUserName());

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=newName.getText().toString().trim();
                if (name.length()>0){
                    dialog.dismiss();
                    MyProgressBar.with(context);
                    Api.getInstance().updateProfile(TOKEN, c_m_b(USER_ID), c_m_b(name), null,null, new ApiListener.PRofileUpdateListenerPatient() {
                        @Override
                        public void onPRofileUpdateSuccess(ProfileUpdateResponse data) {
                            MyProgressBar.dismiss();

                            if (data.getStatus()==true){
                                SESSION_MANAGER.setuserName(name);
                                tv_name.setText(name);
                            }

                        }

                        @Override
                        public void onPRofileUpdateFailed(String msg) {
                            MyProgressBar.dismiss();


                        }
                    });

                }

            }
        });

    }
    public void showTitleEditDialog(View view) {
        Dialog dialog = doForMe.showDialog(context, R.layout.change_user_name_dialog);
        EditText newName = (EditText) dialog.findViewById(R.id.ed_name);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
        newName.setText(SESSION_MANAGER.get_userdisplay());

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=newName.getText().toString().trim();
                if (name.length()>0){
                    dialog.dismiss();
                    MyProgressBar.with(context);
                    Api.getInstance().updateProfile(TOKEN, c_m_b(USER_ID),null, c_m_b(name), null, new ApiListener.PRofileUpdateListenerPatient() {
                        @Override
                        public void onPRofileUpdateSuccess(ProfileUpdateResponse data) {
                            MyProgressBar.dismiss();

                            if (data.getStatus()==true){
                                SESSION_MANAGER.set_userdisplay(name);
                                tv_display_title.setText(name);
                            }

                        }

                        @Override
                        public void onPRofileUpdateFailed(String msg) {
                            MyProgressBar.dismiss();


                        }
                    });

                }

            }
        });

    }
    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
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
               // image.setImageURI(resultUri);
                try{
                    uploadPhoto(resultUri);
                }catch (Exception e){
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadPhoto(Uri u) {
        if (u != null) {

            File f = new File(resultUri.getPath());
            MultipartBody.Part photo = null;
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);

            MyProgressBar.with(context);
            Api.getInstance().updateProfile(TOKEN, c_m_b(USER_ID),null,null, photo, new ApiListener.PRofileUpdateListenerPatient() {
                @Override
                public void onPRofileUpdateSuccess(ProfileUpdateResponse data) {
                    MyProgressBar.dismiss();
                    Gson gson = new Gson();

                    Toast.makeText(context,gson.toJson(data),Toast.LENGTH_LONG).show();

                    if (data.getStatus()==true){
                       SESSION_MANAGER.set_userPhoto(data.getPhoto());
                        Glide.with(PatientPersonalInfoActivity.this).load(PHOTO_BASE + data.getPhoto()).into(image);

                    }

                }

                @Override
                public void onPRofileUpdateFailed(String msg) {
                    MyProgressBar.dismiss();
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

                }
            });
        } else {
            Toast.makeText(this, "Add Photo first", Toast.LENGTH_SHORT).show();
        }
    }
}

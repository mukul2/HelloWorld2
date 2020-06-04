package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.BlogCategoryNameID;
import com.doctor.telemedicine.model.StatusMessage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class AddBlogActivity extends AppCompatActivity implements ApiListener.BlogCategoryDownloadListener {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.image)
    ImageView image;
    List<BlogCategoryNameID> DATAS = new ArrayList<>();
    String selectedCATEGORY_ID;
    Uri resultUri;
    @BindView(R.id.ed_youtube)
    EditText ed_youtube;
    @BindView(R.id.ed_title)
    EditText ed_title;
    @BindView(R.id.ed_body)
    EditText ed_body;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);
        ButterKnife.bind(this);
        Api.getInstance().BlogCategoryNameID(TOKEN, this);
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
                image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onBlogCategoryDownloadSuccess(List<BlogCategoryNameID> list) {
        DATAS = list;
        List<String> types = new ArrayList<>();
        types.add("Select Type");
        for (int i = 0; i < list.size(); i++) {
            types.add(list.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    selectedCATEGORY_ID=""+DATAS.get(i-1).getId();
                }else {
                    selectedCATEGORY_ID=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onBlogCategoryDownloadFailed(String msg) {

    }
    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }

    public void submit(View view) {
        String title=ed_title.getText().toString().trim();
        String body=ed_body.getText().toString().trim();
        String youtube=ed_youtube.getText().toString().trim();
        if (selectedCATEGORY_ID!=null){
            if (title.length()>0){
                if (body.length()>0){
                    if (youtube.length()>0 ||resultUri!=null){
                        MyProgressBar.with(context);

                        MultipartBody.Part photo = null;
                        if (resultUri!=null) {
                            File f = new File(resultUri.getPath());
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
                            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
                        }
                        Api.getInstance().blogPostWithPhoto(TOKEN, c_m_b(USER_ID), c_m_b(body), c_m_b(selectedCATEGORY_ID), c_m_b(title), c_m_b(youtube), photo, new ApiListener.BlogPostListener() {
                            @Override
                            public void onBlogPostSuccess(StatusMessage response) {
                                MyProgressBar.dismiss();
                                onBackPressed();
                            }

                            @Override
                            public void onBlogPostFailed(String msg) {
                                MyProgressBar.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();



                            }
                        });


                    }
                }
            }
        }

    }
}

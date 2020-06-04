package com.doctor.telemedicine.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.Activity.PatientPersonalInfoActivity;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.doForMe;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.ProfileUpdateResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrBasicInfoProfileFragment extends Fragment {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_change_photo)
    TextView tv_change_photo;

    @BindView(R.id.image)
    ImageView image;
    Context context;
    Uri resultUri;
    View view;


    public DrBasicInfoProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dr_basic_info_profile, container, false);
        context = view.getContext();
        ButterKnife.bind(this, view);


        tv_email.setText(SESSION_MANAGER.get_userEmail());
        tv_name.setText(SESSION_MANAGER.getUserName());
        tv_phone.setText(SESSION_MANAGER.get_userMobile());
        Glide.with(context).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(image);
        //Glide.with(context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Gal_Gadot_by_Gage_Skidmore_3.jpg/220px-Gal_Gadot_by_Gage_Skidmore_3.jpg").into(image);
        Toast.makeText(context, SESSION_MANAGER.get_userPhoto(), Toast.LENGTH_SHORT).show();

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameEditDialog();
            }
        });

        tv_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    public void showNameEditDialog() {
        Dialog dialog = doForMe.showDialog(context, R.layout.change_user_name_dialog);
        EditText newName = (EditText) dialog.findViewById(R.id.ed_name);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
        newName.setText(SESSION_MANAGER.getUserName());

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newName.getText().toString().trim();
                if (name.length() > 0) {
                    dialog.dismiss();
                    MyProgressBar.with(context);
                    Api.getInstance().updateProfile(TOKEN, c_m_b(USER_ID), c_m_b(name), null, null, new ApiListener.PRofileUpdateListenerPatient() {
                        @Override
                        public void onPRofileUpdateSuccess(ProfileUpdateResponse data) {
                            MyProgressBar.dismiss();

                            if (data.getStatus() == true) {
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

    public void showTitleEditDialog() {
        Dialog dialog = doForMe.showDialog(context, R.layout.change_user_name_dialog);
        EditText newName = (EditText) dialog.findViewById(R.id.ed_name);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
        newName.setText(SESSION_MANAGER.getUserName());

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newName.getText().toString().trim();
                if (name.length() > 0) {
                    dialog.dismiss();
                    MyProgressBar.with(context);
                    Api.getInstance().updateProfile(TOKEN, c_m_b(USER_ID), null,c_m_b(name),  null, new ApiListener.PRofileUpdateListenerPatient() {
                        @Override
                        public void onPRofileUpdateSuccess(ProfileUpdateResponse data) {
                            MyProgressBar.dismiss();

                            if (data.getStatus() == true) {
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

    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }

}

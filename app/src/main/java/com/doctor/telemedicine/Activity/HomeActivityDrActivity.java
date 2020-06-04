package com.doctor.telemedicine.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.fxn.pix.Pix;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.doctor.telemedicine.Data.SinchCons;
import com.doctor.telemedicine.Data.sharedPhotoListener;
import com.doctor.telemedicine.Fragments.AppointmentsFragment;
import com.doctor.telemedicine.Fragments.BlogFragmentDoctor;
import com.doctor.telemedicine.Fragments.BlogFragmentPatient;
import com.doctor.telemedicine.Fragments.DrBasicInfoProfileFragment;
import com.doctor.telemedicine.Fragments.HomeFragment;
import com.doctor.telemedicine.Fragments.HomeFragmentDr;
import com.doctor.telemedicine.Fragments.HomeFragmentDrTwo;
import com.doctor.telemedicine.Fragments.MedicineFragment;
import com.doctor.telemedicine.Fragments.NotificationFragmentPatient;
import com.doctor.telemedicine.Fragments.ProfileFragment;
import com.doctor.telemedicine.Fragments.SettingFragmentDr;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.LoginResponse;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Activity.SinchService.CALL_ID;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.Data.USER_ENABLED;
import static com.doctor.telemedicine.Data.Data.bottom_nav;
import static com.doctor.telemedicine.Data.Data.itemView;
import static com.doctor.telemedicine.Data.Data.menuView;
import static com.doctor.telemedicine.Data.Data.notificationBadge;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.USER_TYPE;
import static com.doctor.telemedicine.Data.SinchCons.callSnichClient;
import static com.doctor.telemedicine.Data.SinchCons.incomingCallInstance;

public class HomeActivityDrActivity extends VoiceCallBaseActivity implements SinchService.StartFailedListener {
    Context context = this;
    Resources resources;
    int primaryClr, another;


    int anotherColorText = Color.GRAY;

    String DOCTOR = "d";
    String PATIENT = "p";

    SessionManager sessionManager;

    SinchClient sinchClient;
    Call call;
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    @BindView(R.id.bottom)
    BottomNavigationView bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dr);
        setUpStatusbar();
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        SESSION_MANAGER = sessionManager;
        USER_ID = sessionManager.getUserId();
        TOKEN = sessionManager.getToken();
        USER_TYPE = "d";
        MyProgressBar.with(context);
        FirebaseMessaging.getInstance().subscribeToTopic(USER_ID);
        Api.getInstance().loginUser(sessionManager.get_userEmail(), sessionManager.get_userPass(), new ApiListener.LoginUserListener() {
            @Override
            public void onUserLoginSuccess(LoginResponse status) {
                MyProgressBar.dismiss();
                if (status.getStatus()) {
                    sessionManager.setuserId("" + status.getUserInfo().getId());
                    sessionManager.setLoggedIn(true);
                    sessionManager.setuserName(status.getUserInfo().getName());
                    sessionManager.setuserType(status.getUserInfo().getUserType());
                    sessionManager.setToken("Bearer " + status.getAccessToken());
                    sessionManager.set_userPhoto(status.getUserInfo().getPhoto());
                    sessionManager.set_userMobile(status.getUserInfo().getPhone());
                    sessionManager.set_userEmail(status.getUserInfo().getEmail());
                    sessionManager.set_userdisplay(status.getUserInfo().getDesignation_title());
                    // sessionManager.set_userPass(ed_password.getText().toString().trim());
                    if (status.getUserInfo().getStatus() == 0) {
                        sessionManager.set_userStatus(false);
                        USER_ENABLED = false;
                    } else {
                        sessionManager.set_userStatus(true);
                        USER_ENABLED = true;
                    }
                    Toast.makeText(context, status.getMessage(), Toast.LENGTH_LONG).show();

                    initHomePage();

                    if (status.getUserInfo().getUserType().equals(DOCTOR)) {
                        //  startActivity(new Intent(context, HomeActivityDrActivity.class));
                        // finishAffinity();
                    } else if (status.getUserInfo().getUserType().equals(PATIENT)) {
                        startActivity(new Intent(context, PatientHomeActivity.class));
                        finishAffinity();

                    } else {
                        Toast.makeText(context, "Unknown usertype", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    MyDialog.getInstance().with(context)
                            .message("Wrong mobile or password")
                            .autoBack(false)
                            .autoDismiss(false)
                            .show();
                    finishAffinity();
                }
            }

            @Override
            public void onUserLoginFailed(String msg) {
                MyProgressBar.dismiss();
                Toast.makeText(context, "Network Error.Try again later", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
        SessionManager sessionManager = new SessionManager(this);
        String user_id = sessionManager.getUserId();
        if (!user_id.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(user_id);
        } else {
        }
    }

    @Override
    public void onStarted() {
        Toast.makeText(context, "started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

    }

    private void initHomePage() {
        SESSION_MANAGER = sessionManager;
        USER_ID = sessionManager.getUserId();
        TOKEN = sessionManager.getToken();
        getColorManagement();



        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottom.getMenu().getItem(0).setChecked(true);
                    bottom.getMenu().getItem(1).setChecked(false);
                    bottom.getMenu().getItem(2).setChecked(false);
                    bottom.getMenu().getItem(3).setChecked(false);
                    bottom.getMenu().getItem(4).setChecked(false);

                } else if (position == 1) {

                    bottom.getMenu().getItem(0).setChecked(false);
                    bottom.getMenu().getItem(1).setChecked(true);
                    bottom.getMenu().getItem(2).setChecked(false);
                    bottom.getMenu().getItem(3).setChecked(false);
                    bottom.getMenu().getItem(4).setChecked(false);

                } else if (position == 2) {
                    bottom.getMenu().getItem(0).setChecked(false);
                    bottom.getMenu().getItem(1).setChecked(false);
                    bottom.getMenu().getItem(2).setChecked(true);
                    bottom.getMenu().getItem(3).setChecked(false);
                    bottom.getMenu().getItem(4).setChecked(false);

                } else if (position == 3) {
                    bottom.getMenu().getItem(0).setChecked(false);
                    bottom.getMenu().getItem(1).setChecked(false);
                    bottom.getMenu().getItem(2).setChecked(false);
                    bottom.getMenu().getItem(3).setChecked(true);
                    bottom.getMenu().getItem(4).setChecked(false);

                } else if (position == 4) {
                    bottom.getMenu().getItem(0).setChecked(false);
                    bottom.getMenu().getItem(1).setChecked(false);
                    bottom.getMenu().getItem(2).setChecked(false);
                    bottom.getMenu().getItem(3).setChecked(false);
                    bottom.getMenu().getItem(4).setChecked(true);

                }
                bottom.getMenu().getItem(position).setChecked(true);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {

                        vpPager.setCurrentItem(0);

                        return true;
                    }
                    case R.id.notification: {

                        vpPager.setCurrentItem(1);
                        return true;
                    }
                    case R.id.profile: {
                        vpPager.setCurrentItem(2);
                        return true;
                    }
                    case R.id.blog: {
                        vpPager.setCurrentItem(3);
                        return true;
                    }
                    case R.id.setting: {
                        vpPager.setCurrentItem(4);
                        return true;
                    }
                }
                return false;
            }
        });
        // addBadgeView();
        bottom_nav = bottom;
        menuView = (BottomNavigationMenuView) bottom_nav.getChildAt(0);
        itemView = (BottomNavigationItemView) menuView.getChildAt(1);
        notificationBadge = LayoutInflater.from(context).inflate(R.layout.noti_badge, menuView, false);
        setupViewPager(vpPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragmentDr(), "TWO");
        adapter.addFragment(new NotificationFragmentPatient(), "TWO");
        adapter.addFragment(new HomeFragmentDrTwo(), "ONE");
        adapter.addFragment(new BlogFragmentPatient(), "ONE");
        adapter.addFragment(new SettingFragmentDr(), "ONE");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 77) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            sharedPhotoListener.pListener.onPicSelected(returnValue);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
             /*  resultUri = result.getUri();
                imageView.setImageURI(resultUri);
                */

                sharedPhotoListener.pListenerUri.onPicSelected(result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void getColorManagement() {
        resources = context.getResources();
        primaryClr = resources.getColor(R.color.colorPrimary);
        another = Color.GRAY;
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
}

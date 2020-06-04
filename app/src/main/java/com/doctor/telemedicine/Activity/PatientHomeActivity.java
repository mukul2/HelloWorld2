package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.os.IBinder;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.doctor.telemedicine.Fragments.AppointmentsFragment;
import com.doctor.telemedicine.Fragments.BlogFragmentPatient;
import com.doctor.telemedicine.Fragments.HomeFragment;
import com.doctor.telemedicine.Fragments.MedicineFragment;
import com.doctor.telemedicine.Fragments.NotificationFragmentPatient;
import com.doctor.telemedicine.Fragments.ProfileFragment;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.CustomDrawerButton;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.BasicInfoModel;
import com.doctor.telemedicine.model.SpecialistNameCount;
import com.sinch.android.rtc.SinchError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.SESSION_MANAGER;
import static com.doctor.telemedicine.Data.Data.bottom_nav;
import static com.doctor.telemedicine.Data.Data.itemView;
import static com.doctor.telemedicine.Data.Data.menuView;
import static com.doctor.telemedicine.Data.Data.notificationBadge;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.USER_TYPE;

public class PatientHomeActivity extends VoiceCallBaseActivity implements ApiListener.basicInfoDownloadListener, SinchService.StartFailedListener {
    Context context = this;
    Resources resources;
    int primaryClr, another;

    @BindView(R.id.profilePic)
    CircleImageView profilePic;

    @BindView(R.id.tv_name)
    TextView tv_name;

    TextView tv_4;
    int anotherColorText = Color.GRAY;

    @BindView(R.id.bottom)
    BottomNavigationView bottom;
    @BindView(R.id.vpPager)
    ViewPager vpPager;
    // LinearLayout.LayoutParams enable = new LinearLayout.LayoutParams(30, 30);
    //  LinearLayout.LayoutParams disbale = new LinearLayout.LayoutParams(25, 25);
    public static List<String> HOSPITALS = new ArrayList<>();
    public static List<SpecialistNameCount> SPECIALIST = new ArrayList<>();
    SessionManager sesMan;

    @BindView(R.id.customDrawer)
    CustomDrawerButton customDrawerButton;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        ButterKnife.bind(this);


        sesMan = new SessionManager(this);

        TOKEN = sesMan.getToken();
        USER_ID = sesMan.getUserId();
        SESSION_MANAGER = sesMan;

        USER_TYPE = "p";
        getColorManagement();

        FirebaseMessaging.getInstance().subscribeToTopic(USER_ID);

        Api.getInstance().downloadBasicInfo(this);
        setUpStatusbar();

        customDrawerButton.setDrawerLayout(drawer_layout);
        customDrawerButton.getDrawerLayout().addDrawerListener(customDrawerButton);
        customDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawerButton.changeState();
            }
        });
        Glide.with(PatientHomeActivity.this).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(profilePic);
        //Glide.with(PatientHomeActivity.this).load("https://appointmentbd.com/frontend/user/1561548940.jpg").into(profilePic);
        tv_name.setText(SESSION_MANAGER.getUserName());
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PatientPersonalInfoActivity.class));
            }
        });
        setupViewPager(vpPager);
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
                    case R.id.account: {
                        vpPager.setCurrentItem(2);
                        return true;
                    }
                    case R.id.appointment: {
                        vpPager.setCurrentItem(3);
                        return true;
                    }
                    case R.id.blog: {
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


    }

    private void addBadgeView() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1);

        View notificationBadge = LayoutInflater.from(this).inflate(R.layout.noti_badge, menuView, false);

        itemView.addView(notificationBadge);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "TWO");
        adapter.addFragment(new NotificationFragmentPatient(), "TWO");
        adapter.addFragment(new ProfileFragment(), "ONE");
        adapter.addFragment(new AppointmentsFragment(), "ONE");
        adapter.addFragment(new BlogFragmentPatient(), "ONE");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(context, "failed " + error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStarted() {
        Toast.makeText(context, "started", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(context, "service connected", Toast.LENGTH_SHORT).show();
        getSinchServiceInterface().setStartListener(this);
        SessionManager sessionManagr = new SessionManager(this);
        String id = sessionManagr.getUserId();
        if (!id.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(id);
        } else {
        }

    }

    @Override
    protected void onServiceDisconnected() {
        super.onServiceDisconnected();
        Toast.makeText(context, "disconnected", Toast.LENGTH_SHORT).show();
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
    protected void onRestart() {
        super.onRestart();
        Glide.with(PatientHomeActivity.this).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(profilePic);
        //Glide.with(PatientHomeActivity.this).load("https://appointmentbd.com/frontend/user/1561548940.jpg").into(profilePic);
        tv_name.setText(SESSION_MANAGER.getUserName());
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
    public void onBasicInfoDownloadSuccess(BasicInfoModel data) {
        if (data != null) {
            HOSPITALS = data.getHospitalList();
            SPECIALIST = data.getSpacialist();

        } else {
            // onBackPressed();
            // Toast.makeText(this, "Something is not right.Try again later", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBasicInfoDownloadFailed(String msg) {
        //onBackPressed();
        //  Toast.makeText(this, "Something is not right.Try again later", Toast.LENGTH_SHORT).show();


    }


    private void getColorManagement() {
        resources = context.getResources();
        primaryClr = resources.getColor(R.color.colorPrimary);
        another = Color.GRAY;
    }



 /*   @Override
    public void onClick(View view) {
        Fragment selectedFragment = null;
        switch (view.getId()) {
            case R.id.linerhomebutton:
                selectedFragment = HomeFragment.newInstance();
                break;
            case R.id.linerMedicineButton:
                selectedFragment = BlogFragmentPatient.newInstance();
                do_0_0_0_1();
                break;
            case R.id.linerAppointmentButton:
                selectedFragment = AppointmentsFragment.newInstance();
                do_0_0_1_0();
                break;
            case R.id.linerProfileButton:
                selectedFragment = ProfileFragment.newInstance();
                do_0_1_0_0();
                break;

        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();

    }
    */

    public void logout(View view) {
        FirebaseMessaging.getInstance().subscribeToTopic(USER_ID);
        sesMan.setLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public void click_1(View view) {
        customDrawerButton.changeState();
        startActivity(new Intent(this, OnlineDoctorsActivity.class));
    }

    public void click_2(View view) {
        customDrawerButton.changeState();
        startActivity(new Intent(this, SpecialistActivity.class));

    }

    public void click_3(View view) {
        customDrawerButton.changeState();
    }

    public void click_4(View view) {
        customDrawerButton.changeState();
        // startActivity(new Intent(this,DrChatActivity.class));

    }

    public void click_5(View view) {
        customDrawerButton.changeState();
    }

    public void click_6(View view) {
        customDrawerButton.changeState();
    }

    public void click_7(View view) {
        customDrawerButton.changeState();

    }

    public void click_video(View view) {
        customDrawerButton.changeState();
        startActivity(new Intent(this, VideoCallHistoryPatientActivity.class));
    }

    public void openSupport(View view) {
        //PublicAskByPatient

        startActivity(new Intent(this, PublicAskByPatient.class));
    }
}

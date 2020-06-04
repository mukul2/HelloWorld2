package com.doctor.telemedicine.Activity;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.doctor.telemedicine.Fragments.AppointmentsListFragment;
import com.doctor.telemedicine.Fragments.AppointmentsListPatient;
import com.doctor.telemedicine.Fragments.NotificationFragment;
import com.doctor.telemedicine.Fragments.VideoCallFragmenttFragment;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.CustomDrawerButton;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.ListenerPatientsData.PatientALLDataDownloadListener;
import static com.doctor.telemedicine.Data.ListenerPatientsData.PatientNotificationDataDownloadListener;

public class HomeActivity extends AppCompatActivity
{
    SessionManager sessionManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    CustomDrawerButton customDrawerButton;
    DrawerLayout drawer;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        sessionManager=new SessionManager(this);
        tv_user_name.setText(sessionManager.getUserName());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.parseColor("#E6E6E6"), Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setupWithViewPager(viewPager);

        drawer= (DrawerLayout)findViewById(R.id.drawer_layout);
        customDrawerButton = (CustomDrawerButton)findViewById(R.id.customDrawer);
        customDrawerButton.setDrawerLayout( drawer );
        customDrawerButton.getDrawerLayout().addDrawerListener( customDrawerButton );
        customDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawerButton.changeState();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppointmentsListPatient(), "Appointments");
        adapter.addFragment(new NotificationFragment(), "Recom.");
        adapter.addFragment(new VideoCallFragmenttFragment(), "Video Call");
        viewPager.setAdapter(adapter);
    }

    public void FindDoctorActivity(View view) {
        startActivity(new Intent(this, FindDoctorActivity.class));
    }


    public void logout(View view) {
        sessionManager.setLoggedIn(false);
        startActivity(new Intent(this,LoginActivity.class));
        finishAffinity();
    }




    public void history(View view) {
        startActivity(new Intent(this,HistoryActivity.class));

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
}

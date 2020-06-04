package com.doctor.telemedicine.Activity;

import android.content.Context;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.Data.lis;
import com.doctor.telemedicine.Fragments.AppointmentsListFragment;
import com.doctor.telemedicine.Fragments.NewAppointListFragment;
import com.doctor.telemedicine.Fragments.NotificationFragment;
import com.doctor.telemedicine.Fragments.VideoCallFragmenttFragmentDoctor;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.CustomDrawerButton;
import com.doctor.telemedicine.Utils.MyDialog;
import com.doctor.telemedicine.Utils.MyProgressBar;
import com.doctor.telemedicine.Utils.SessionManager;
import com.doctor.telemedicine.adapter.ConfirmedAppointmentAdapterDoctor;
import com.doctor.telemedicine.adapter.SearchAdapterDoctor;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AppointmentModel2;
import com.doctor.telemedicine.model.AppointmentResponse;
import com.doctor.telemedicine.model.BasicInfoModel;
import com.doctor.telemedicine.model.SpacialistModel;
import com.doctor.telemedicine.model.StatusMessage;
import com.doctor.telemedicine.model.TestModel;
import com.doctor.telemedicine.model.testSelectedModel;

import android.content.Intent;
import android.graphics.Color;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.spacialist;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.lis.Confirmedlistener;
import static com.doctor.telemedicine.Data.lis.Pendinglistener;

public class DoctorHomeActivity extends AppCompatActivity implements
        ApiListener.basicInfoDownloadListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    CustomDrawerButton customDrawerButton;
    DrawerLayout drawer;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.searchDr_recycler)
    RecyclerView searchDr_recycler;
    SessionManager sessionManager;
    int count = 0;
    Context context = this;
    SearchAdapterDoctor mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        sessionManager = new SessionManager(this);
        ButterKnife.bind(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.parseColor("#E6E6E6"), Color.WHITE);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setupWithViewPager(viewPager);

        //drayer setup
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        customDrawerButton = (CustomDrawerButton) findViewById(R.id.customDrawer);
        customDrawerButton.setDrawerLayout(drawer);
        customDrawerButton.getDrawerLayout().addDrawerListener(customDrawerButton);
        customDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawerButton.changeState();
            }
        });
        tv_user_name.setText(sessionManager.getUserName());
        USER_ID = sessionManager.getUserId();



        count = 0;
        Api.getInstance().downloadBasicInfo(this);
        init_search();
    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    private void init_search() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String id = "";
                String key=ed_search.getText().toString().trim();
                String patient_name="";
                if (isNumeric(key)){
                    id=key;
                    patient_name="";

                }else {
                    id="";
                    patient_name=key;

                }

                if ((id.trim().length()+patient_name.trim().length()) > 0 ) {
                  //  Toast.makeText(context, ""+(id.trim().length()+patient_name.trim().length()), Toast.LENGTH_SHORT).show();
                    Api.getInstance().searchAppointment(id, USER_ID, patient_name, new ApiListener.appointmentSearchListener() {
                        @Override
                        public void onAppointmentSearchSuccess(List<AppointmentModel2> data) {
                            mAdapter = new SearchAdapterDoctor(data);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            searchDr_recycler.setLayoutManager(mLayoutManager);
                            searchDr_recycler.setItemAnimator(new DefaultItemAnimator());
                            //searchDr_recycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

                            searchDr_recycler.setAdapter(mAdapter);
                        }

                        @Override
                        public void onAppointmentSearchFailed(String msg) {
                            Toast.makeText(DoctorHomeActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                   // Toast.makeText(context, "clr list", Toast.LENGTH_SHORT).show();
                    if (mAdapter==null){

                    }else {
                        mAdapter.clearAdapter();

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBasicInfoDownloadSuccess(BasicInfoModel data) {
        count++;


        spacialist.clear();
        for (int i = 0; i < data.getSpacialist().size(); i++) {
           // spacialist.add(new SpacialistModel(data.getSpacialist().get(i), false));
        }


    }

    @Override
    public void onBasicInfoDownloadFailed(String msg) {
        count++;


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppointmentsListFragment(), "Confirmed");
        adapter.addFragment(new NewAppointListFragment(), "Pending");
        adapter.addFragment(new VideoCallFragmenttFragmentDoctor(), "Video Call");
        viewPager.setAdapter(adapter);
    }

    public void OpenDrPersonalInfoActivity(View view) {
        startActivity(new Intent(this, DrPersonalInfoActivity.class));

    }





    public void logout(View view) {
        sessionManager.setLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public void openChamberActivity(View view) {
        customDrawerButton.changeState();
        startActivity(new Intent(this, DrChamberListActivity.class));
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
    public void onBackPressed() {
        MyProgressBar.with(DoctorHomeActivity.this);
        Api.getInstance().changeDrOnlineStatus(USER_ID, "0", new ApiListener.doctorOnlineStatusChangeListener() {
            @Override
            public void ondoctorOnlineStatusChangeSuccess(StatusMessage statusMessage) {
                MyProgressBar.dismiss();
                finish();
            }

            @Override
            public void ondoctorOnlineStatusChangeFailed(String msg) {
                MyProgressBar.dismiss();
                finish();

            }
        });

    }
}

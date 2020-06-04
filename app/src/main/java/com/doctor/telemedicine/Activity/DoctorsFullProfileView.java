package com.doctor.telemedicine.Activity;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.doctor.telemedicine.Fragments.PatientDoctorsChamberFragment;
import com.doctor.telemedicine.Fragments.PatientDoctorsEducationFragment;
import com.doctor.telemedicine.Fragments.PatientDoctorsOnlineServiceFragment;
import com.doctor.telemedicine.Fragments.PatientDoctorsSkillFragment;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.ChamberInfo;
import com.doctor.telemedicine.model.DrEduChInfoModel;
import com.doctor.telemedicine.model.EducationInfo;
import com.doctor.telemedicine.model.SkillInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;
import static com.doctor.telemedicine.Data.Data.TYPE_OF_ACTIVITY;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_DOC;
import static com.doctor.telemedicine.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;

public class DoctorsFullProfileView extends BaseActivity implements ApiListener.drChamberEduSkillDownloadListener {
    @BindView(R.id.profile)
    ImageView profile;
    @BindView(R.id.imgSmall)
    CircleImageView imgSmall;
    @BindView(R.id.dr_name)
    TextView dr_name;
    @BindView(R.id.specialist)
    TextView specialist;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public static List<SkillInfo> SKILLS = new ArrayList<>();
    public static List<EducationInfo> EDUCATION = new ArrayList<>();
    public static List<ChamberInfo> CHAMBERLIST = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_full_profile_view);
        ButterKnife.bind(this);
        if (TYPE_OF_ACTIVITY.equals("OnlineDoc")) {
            if (NOW_SHOWING_ONLINE_DOC != null && (NOW_SHOWING_ONLINE_DOC.getPhoto() != null)) {
                Glide.with(DoctorsFullProfileView.this).load(PHOTO_BASE + NOW_SHOWING_ONLINE_DOC.getPhoto()).into(profile);
                Glide.with(DoctorsFullProfileView.this).load(PHOTO_BASE + NOW_SHOWING_ONLINE_DOC.getPhoto()).into(imgSmall);
                dr_name.setText(NOW_SHOWING_ONLINE_DOC.getName());
                specialist.setText(NOW_SHOWING_ONLINE_DOC.getDesignation_title());

                Api.getInstance().getEduSKillChamber(TOKEN, "" + NOW_SHOWING_ONLINE_DOC.getId(), this);
            } else {
                Toast.makeText(context, "Error occured null problem", Toast.LENGTH_SHORT).show();
            }

        } else if (TYPE_OF_ACTIVITY.equals("ChamberDoc")) {

            if (NOW_SHOWING_DOC != null && (NOW_SHOWING_DOC.getPhoto() != null)) {
                Glide.with(DoctorsFullProfileView.this).load(PHOTO_BASE + NOW_SHOWING_DOC.getPhoto()).into(profile);
                Glide.with(DoctorsFullProfileView.this).load(PHOTO_BASE + NOW_SHOWING_DOC.getPhoto()).into(imgSmall);
                dr_name.setText(NOW_SHOWING_DOC.getName());
                if (NOW_SHOWING_DOC != null && NOW_SHOWING_DOC.getDesignation_title() != null) {
                    if (NOW_SHOWING_DOC.getDesignation_title().length() > 0) {
                        specialist.setText(NOW_SHOWING_DOC.getDesignation_title());

                    } else {
                        specialist.setText("Not Given");

                    }
                } else {
                    specialist.setText("Not Given");

                }

                Api.getInstance().getEduSKillChamber(TOKEN, "" + NOW_SHOWING_DOC.getId(), this);
            } else {
                Toast.makeText(context, "Error occured null problem", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onChamberEduSkillDownloadSuccess(DrEduChInfoModel list) {
        Gson gson = new Gson();
        SKILLS = list.getSkillInfo();
        EDUCATION = list.getEducationInfo();
        CHAMBERLIST = list.getChamberInfo();
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);

        int selectedColor = context.getResources().getColor(R.color.black);
        int normal = context.getResources().getColor(R.color.textText);
        tabs.setTabTextColors(normal, selectedColor);


        // Toast.makeText(context, list.ge, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onChamberEduSkillDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (TYPE_OF_ACTIVITY.equals("OnlineDoc")) {
            adapter.addFragment(new PatientDoctorsOnlineServiceFragment(), "Services");

        } else {
            adapter.addFragment(new PatientDoctorsChamberFragment(), "Chamber");

        }
        adapter.addFragment(new PatientDoctorsEducationFragment(), "Education");
        adapter.addFragment(new PatientDoctorsSkillFragment(), "Skill");

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

    public void back(View view) {
        onBackPressed();
    }
}

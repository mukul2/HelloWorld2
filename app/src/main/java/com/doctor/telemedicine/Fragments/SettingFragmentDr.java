package com.doctor.telemedicine.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.ChamberInfo;
import com.doctor.telemedicine.model.DrEduChInfoModel;
import com.doctor.telemedicine.model.EducationInfo;
import com.doctor.telemedicine.model.EducationSkillModel;
import com.doctor.telemedicine.model.SkillInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.DataStore.EDUCATIONSKILLMODEL;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;


public class SettingFragmentDr extends Fragment implements ApiListener.drChamberEduSkillDownloadListener {
    View v;
    Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static List<SkillInfo> SKILLS = new ArrayList<>();
    public static List<EducationInfo> EDUCATION = new ArrayList<>();
    public static List<ChamberInfo> CHAMBERLIST = new ArrayList<>();


    public static SettingFragmentDr newInstance() {
        SettingFragmentDr fragment = new SettingFragmentDr();
        return fragment;
    }

    public SettingFragmentDr() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dr_fragment_two, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        int selectedColor = context.getResources().getColor(R.color.black);
        int normal = context.getResources().getColor(R.color.textText);
        tabLayout.setTabTextColors(normal, selectedColor);
        Api.getInstance().getEduSKillChamber(TOKEN, USER_ID, this);


        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OnlineFragmentDr(), "Online Services");
        adapter.addFragment(new DrDocumentsFragment(), "Documents");


        viewPager.setAdapter(adapter);
    }


    @Override
    public void onChamberEduSkillDownloadSuccess(DrEduChInfoModel list) {
        Gson gson = new Gson();
        SKILLS = list.getSkillInfo();
        EDUCATION = list.getEducationInfo();
        CHAMBERLIST = list.getChamberInfo();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onChamberEduSkillDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

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

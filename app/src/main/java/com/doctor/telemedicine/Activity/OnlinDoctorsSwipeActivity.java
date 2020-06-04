package com.doctor.telemedicine.Activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.DrOnlineServicesAdapter;
import com.doctor.telemedicine.adapter.ScheduleAdapter;
import com.doctor.telemedicine.model.DoctorModel;


import java.util.ArrayList;
import java.util.List;

import static com.doctor.telemedicine.Activity.OnlineDoctorsActivity.DOCTORS_LIST;
import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.Data.singleDrModel;

public class OnlinDoctorsSwipeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    int counter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlin_doctors_swipe);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String j = (String) b.get("currentIndex");
            counter = Integer.parseInt(j);
            mViewPager.setCurrentItem(counter);
            //setTitle((String) b.get("category_name"));
            //  toolbar.setTitle((String) b.get("category_name"));
        }

    }


    public void next(View view) {
        counter++;
        if (counter == searchResult.size())
            counter = 0;
        mViewPager.setCurrentItem(counter);

    }

    public void prev(View view) {
        counter--;
        if (counter == -1)
            counter = searchResult.size() - 1;
        mViewPager.setCurrentItem(counter);
    }

    public void back(View view) {
        onBackPressed();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         *
         * @param sectionNumber
         * @param title
         * @param image
         */
        public static PlaceholderFragment newInstance(String sectionNumber, String title, String image) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, "1");
            args.putString("position", sectionNumber);
            args.putString("image", sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_online_dr_info, container, false);
            TextView dr_name = (TextView) rootView.findViewById(R.id.dr_name);
            TextView specialist = (TextView) rootView.findViewById(R.id.specialist);
            RecyclerView recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            dr_name.setText(DOCTORS_LIST.get(Integer.parseInt(getArguments().getString("position"))).getName());
            specialist.setText(DOCTORS_LIST.get(Integer.parseInt(getArguments().getString("position"))).getDepartment());
         //   DrOnlineServicesAdapter mAdapter = new DrOnlineServicesAdapter(DOCTORS_LIST.get(Integer.parseInt(getArguments().getString("position"))).getDrService());
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(rootView.getContext(), RecyclerView.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
        //    recycler_view.setAdapter(mAdapter);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance("" + position, "", "");
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return DOCTORS_LIST.size();
        }
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Text Copied", Toast.LENGTH_SHORT).show();
    }

    public void shareText(String text) {
        String shareBody = text;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }
}

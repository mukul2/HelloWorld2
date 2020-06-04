package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ScheduleAdapter;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.DoctorModel;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doctor.telemedicine.Data.Data.searchResult;
import static com.doctor.telemedicine.Data.Data.singleDrModel;

public class DoctorsSwipeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    int counter;
    private ViewPager mViewPager;
    public static   List<DoctorModel>LIST_DOCTORS=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_swipe);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        setDoctors();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String j = (String) b.get("currentIndex");
            counter=Integer.parseInt(j);
            mViewPager.setCurrentItem(0);
            //setTitle((String) b.get("category_name"));
            //  toolbar.setTitle((String) b.get("category_name"));
        }
        setUpStatusbar();
    }

    private void setDoctors() {
        DoctorModel model=new DoctorModel();
        model.setDrName("Mukul 1");
        model.setAddress("Fatullah Narayanganj");
        model.setCity("Dhaka");
        model.setLastDegree("MBBS");
        model.setVisitFee("500");
        model.setSpecialist("Heart");
        model.setPhoto("https://i.ebayimg.com/images/g/Lc8AAOSw42ta3qor/s-l640.jpg");
        List<Day>days=new ArrayList<>();
        days.add(new Day("1","10:40 AM"));


        model.setDays(days);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);

    }


    public  void setUpStatusbar(){
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


    public void next(View view) {
        counter++;
        if (counter == LIST_DOCTORS.size())
            counter = 0;
        mViewPager.setCurrentItem(counter);

    }

    public void prev(View view) {
        counter--;
        if (counter == -1)
            counter =LIST_DOCTORS.size()-1;
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
         *  @param sectionNumber
         * @param title
         * @param image
         */
        public static PlaceholderFragment newInstance(String sectionNumber, String title, String image) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, "1");
            args.putString("position", sectionNumber);
            args.putString("image", image);

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
            TextView dr_name = (TextView) rootView.findViewById(R.id.dr_name);
            TextView specialist = (TextView) rootView.findViewById(R.id.specialist);
            RecyclerView recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            dr_name.setText(LIST_DOCTORS.get(Integer.parseInt(getArguments().getString("position"))).getDrName());
            specialist.setText(LIST_DOCTORS.get(Integer.parseInt(getArguments().getString("position"))).getSpecialist());
            singleDrModel=LIST_DOCTORS.get(Integer.parseInt(getArguments().getString("position")));
            ScheduleAdapter mAdapter = new ScheduleAdapter();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
            StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            recycler_view.setLayoutManager(_sGridLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
          //  Toast.makeText(, "", Toast.LENGTH_SHORT).show();

            recycler_view.setAdapter(mAdapter);
            //  textView.setText(getArguments().getInt(ARG_SECTION_NUMBER));
            //textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
            //section_title.setText(getArguments().getString("title"));
            //profile
            CircleImageView profile = (CircleImageView) rootView.findViewById(R.id.profile);

           Glide.with(rootView.getContext()).load(getArguments().getString("image")).into(profile);

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
            return PlaceholderFragment.newInstance(""+position,LIST_DOCTORS.get(position).getSpecialist(),LIST_DOCTORS.get(position).getPhoto());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return LIST_DOCTORS.size();
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
    public  void shareText(String text){
        String shareBody = text;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }
}

package com.doctor.telemedicine.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;

import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.doctor.telemedicine.Data.DataStore;
import com.doctor.telemedicine.Fragments.EducationFragmentForPatient;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.adapter.ScheduleAdapter;
import com.doctor.telemedicine.model.Day;
import com.doctor.telemedicine.model.DoctorModel;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class SwipeNewActivity extends AppCompatActivity {
    SwipeStack swipeStack;

    public static   List<DoctorModel>LIST_DOCTORS=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_new);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);

        setUpStatusbar();
        setDoctors();

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
        days.add(new Day("3","12:00 AM"));


        model.setDays(days);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);
        LIST_DOCTORS.add(model);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.e("Width", "" + width);
        Log.e("height", "" + height);
        Toast.makeText(this, "Width" + width+"height" + height, Toast.LENGTH_SHORT).show();
        int centerW=width/2;
        int centerH=height/2;

        swipeStack.setAdapter(new SwipeStackAdapter(LIST_DOCTORS));
        swipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {


            }



            @Override
            public void onViewSwipedToRight(int position) {



            }

            @Override
            public void onStackEmpty() {
                Toast.makeText(SwipeNewActivity.this, "Empty", Toast.LENGTH_SHORT).show();

            }
        });


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

    public void back(View view) {
        onBackPressed();
    }

    public void swipeLeft(View view) {
        swipeStack.swipeTopViewToLeft();

    }

    public void swipeRight(View view) {
        swipeStack.swipeTopViewToRight();

    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<DoctorModel> mData;
        Context context;

        public SwipeStackAdapter(List<DoctorModel> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public DoctorModel getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.card, parent, false);
            //tv_book
            DoctorModel model=mData.get(position);
            TextView tv_book = (TextView) convertView.findViewById(R.id.tv_book);
            TextView tv_days_detail = (TextView) convertView.findViewById(R.id.tv_days_detail);
            context=tv_book.getContext();
            init_tabs(convertView);
            tv_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            String value="";
            if (model.getDays().size()>0){
                for (int i=0;i<model.getDays().size();i++){
                    value+= DataStore.convertToWeekDay(model.getDays().get(i).getDay()) +" "+model.getDays().get(i).getTime();
                    if (i<(model.getDays().size()-1)){
                        value+="\n";
                    }
                }
            }
            tv_days_detail.setText(value);

          //  TabLayout tabs = (TabLayout ) convertView.findViewById(R.id.tabs);
           // ViewPager viewpager = (ViewPager  ) convertView.findViewById(R.id.viewpager);
           // setupViewPager(viewpager);
            //tabs.setupWithViewPager(viewpager);
//            RecyclerView recycler_view = (RecyclerView) convertView.findViewById(R.id.recycler_view);
//            ScheduleAdapter mAdapter = new ScheduleAdapter(model.getDays());
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//            StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
//                    StaggeredGridLayoutManager.VERTICAL);
//            recycler_view.setLayoutManager(mLayoutManager);
//            recycler_view.setItemAnimator(new DefaultItemAnimator());
//            recycler_view.setAdapter(mAdapter);
            return convertView;
        }
        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new EducationFragmentForPatient(), "Education");
            adapter.addFragment(new EducationFragmentForPatient(), "Skill");
            adapter.addFragment(new EducationFragmentForPatient(), "Chamber");
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
    }

    private void init_tabs(View convertView) {

        TextView tv_body = (TextView) convertView.findViewById(R.id.tv_body);


        TextView tv_one = (TextView) convertView.findViewById(R.id.tv_one);
        TextView tv_two = (TextView) convertView.findViewById(R.id.tv_two);
        TextView tv_three = (TextView) convertView.findViewById(R.id.tv_three);

        View v_1 = (View) convertView.findViewById(R.id.v_1);
        View v_2 = (View) convertView.findViewById(R.id.v_2);
        View v_3 = (View) convertView.findViewById(R.id.v_3);

        tv_one.setTextColor(Color.BLACK);
        tv_two.setTextColor(Color.GRAY);
        tv_three.setTextColor(Color.GRAY);

        v_1.setBackgroundColor(Color.BLACK);
        v_2.setBackgroundColor(Color.WHITE);
        v_3.setBackgroundColor(Color.WHITE);
//

        tv_body.setText("General Physician (All or any common diseases and health issues)"+"\n"+"MBBS, CCD (Birdem).");

        tv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SwipeNewActivity.this, "edu", Toast.LENGTH_SHORT).show();
                tv_one.setTextColor(Color.BLACK);
                tv_two.setTextColor(Color.GRAY);
                tv_three.setTextColor(Color.GRAY);

                v_1.setBackgroundColor(Color.BLACK);
                v_2.setBackgroundColor(Color.WHITE);
                v_3.setBackgroundColor(Color.WHITE);

                tv_body.setText("General Physician (All or any common diseases and health issues)"+"\n"+"MBBS, CCD (Birdem).");




            }
        });
        tv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_one.setTextColor(Color.GRAY);
                tv_two.setTextColor(Color.BLACK);
                tv_three.setTextColor(Color.GRAY);

                v_1.setBackgroundColor(Color.WHITE);
                v_2.setBackgroundColor(Color.BLACK);
                v_3.setBackgroundColor(Color.WHITE);

                tv_body.setText("General Physician, Medicine, Diabetics & Child Specialist \n" +
                        "Worked as Residential Medical Officer at Apollo Hospitals Dhaka for 2.5 yrs in Orthopedic and Neurosurgery Department. CMU Certificate Course on Diabetology.Certificate Course on Medical Ultrasound.");



            }
        });
        tv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SwipeNewActivity.this, "chamber", Toast.LENGTH_SHORT).show();

                tv_one.setTextColor(Color.GRAY);
                tv_two.setTextColor(Color.GRAY);
                tv_three.setTextColor(Color.BLACK);

                v_1.setBackgroundColor(Color.WHITE);
                v_2.setBackgroundColor(Color.WHITE);
                v_3.setBackgroundColor(Color.BLACK);

                tv_body.setText( "Chamber 1"+"\n"+"Medinova Pharmacy, Shahjahanpur, (Near to Amtola Mosque), Khilgaon, Dhaka"+"\n"+"Chamber 2"+"\n"+"Khan Draug House, College Super Market, Lohajang , Sadar, Munshiganj");



            }
        });

    }
}

package com.doctor.telemedicine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.telemedicine.Fragments.AllCollectionFragment;
import com.doctor.telemedicine.Fragments.AllWidthdrawFragment;
import com.doctor.telemedicine.Fragments.MySubscriptionsFragment;
import com.doctor.telemedicine.Fragments.SearchNewFragments;
import com.doctor.telemedicine.R;
import com.doctor.telemedicine.Utils.GeneralListener;
import com.doctor.telemedicine.adapter.BillsLitsAdapterAdmin;
import com.doctor.telemedicine.adapter.PaymentListDoctorAdapter;
import com.doctor.telemedicine.api.Api;
import com.doctor.telemedicine.api.ApiListener;
import com.doctor.telemedicine.model.AllCollectionWithdraModel;
import com.doctor.telemedicine.model.PaymentModel;
import com.doctor.telemedicine.widgets.DividerItemDecoration;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.CURRENCY_USD;
import static com.doctor.telemedicine.Data.DataStore.TOKEN;
import static com.doctor.telemedicine.Data.DataStore.USER_ID;
import static com.doctor.telemedicine.Data.DataStore.USER_TYPE;

public class PaymentsHistoryActivity extends BaseActivity {

    Context context = this;
    @BindView(R.id.tv_total_collection)
    TextView tv_total_collection;
    @BindView(R.id.tv_total_width)
    TextView tv_total_width;
    @BindView(R.id.tv_remaining)
    TextView tv_remaining;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    public  static  AllCollectionWithdraModel ALL_COLLECTION_WIDTHDRAWL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_history);
        ButterKnife.bind(this);

        String USER_TYPE_;
        if (USER_TYPE.equals("d")) {
            USER_TYPE_ = "doctor";
        } else {
            USER_TYPE_ = "patient";
        }
        Api.getInstance().get_payment_list(TOKEN, USER_ID, USER_TYPE_, new ApiListener.PaymentListDownloadListener() {
            @Override
            public void onPaymentListDownloadSuccess(AllCollectionWithdraModel response) {
                tv_total_collection.setText(""+response.getTotal_bill()+ CURRENCY_USD);
                tv_total_width.setText(""+response.getAll_widthdraw()+ CURRENCY_USD);
                tv_remaining.setText(""+(response.getTotal_bill()-response.getAll_widthdraw())+ CURRENCY_USD);
                ALL_COLLECTION_WIDTHDRAWL = response ;
                setupViewPager(viewpager);
                int selectedColor=context.getResources().getColor(R.color.black);
                int normal=context.getResources().getColor(R.color.textText);
                tabs.setTabTextColors(normal,selectedColor);
                tabs.setupWithViewPager(viewpager);
                //Toast.makeText(context, ""+response.getTotal_bill(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onPaymentListDownloadFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllCollectionFragment(), "Collections");
        adapter.addFragment(new AllWidthdrawFragment(), "Withdrawal");
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

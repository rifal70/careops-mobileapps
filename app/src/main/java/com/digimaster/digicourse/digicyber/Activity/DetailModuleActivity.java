package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.DetailModuleFragment;
import com.digimaster.digicourse.digicyber.Fragment.TopicFragment;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class  DetailModuleActivity extends AppCompatActivity {

    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    String passing_module_id, passing_tit, passing_module_img, passing_module_title, passTotalAction, passTotalFinished;
    ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String title, author, totTopics, totAction;
    TextView tvJudulDetailCourse, tvAuthorDetailCourse, tvCompletedModules, tvAction2Go;
    RelativeLayout rvHeaderDetailCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        tvJudulDetailCourse = findViewById(R.id.tvJudulDetailCourse);
        tvAuthorDetailCourse = findViewById(R.id.tvAuthorDetailCourse);
        tvCompletedModules = findViewById(R.id.tvCompletedModules);
        tvAction2Go = findViewById(R.id.tvAction2Go);
        rvHeaderDetailCourse = findViewById(R.id.rvHeaderDetailCourse);

        try {
            passing_module_id = pref.getString(PrefContract.module_id, "");

            passTotalAction = pref.getString(PrefContract.tot_action, "");
            passTotalFinished = pref.getString(PrefContract.tot_finished, "");

            passing_module_title = pref.getString(PrefContract.module_title, "");
            passing_module_img = pref.getString(PrefContract.module_img, "");

            Picasso.with(this).load(getResources().getString(R.string.base_url_asset_quiz) + "module/image/" + passing_module_img)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            rvHeaderDetailCourse.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        } catch (AppException e) {
            e.printStackTrace();
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            if (bundle.getString("title") != null || bundle.getString("author") != null
                    || bundle.getString("topic") != null || bundle.getString("action") != null) {
                title = bundle.getString("title");
                author = bundle.getString("author");
                totTopics = bundle.getString("topic");
                totAction = bundle.getString("action");

                tvAuthorDetailCourse.setText("Created By " + author);

            }
        }

        tvJudulDetailCourse.setText(passing_module_title);

        //actionnya get per topic karena beda2 anying aswwuu auww
        tvCompletedModules.setText("Completed "+passTotalFinished+" of " + passTotalAction + " Total Activities");

        //tvAction2Go.setVisibility(View.VISIBLE);
        //tvAction2Go.setText((Integer.parseInt(passTotalAction) - Integer.parseInt(passTotalFinished))+" Actions to go");

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#073674"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#073674"));

        //Log.d("logggPassKocag", "onCreate: "+passing);

        Toolbar toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle(passing_tit);

    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopicFragment(), "Topics");
        adapter.addFragment(new DetailModuleFragment(), "Details");
//            adapter.addFragment(new MyCertificateFragment(), "MY CERTIFICATES");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return new TopicFragment();
                case 1:
                    return new DetailModuleFragment();

                default:
                    return new TopicFragment();
            }
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

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position == 0) {
                new TopicFragment(); //Refresh what you need on this fragment
            } else if (position == 1) {
                new DetailModuleFragment();
            }
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_bar, menu);
        //MenuItem item = menu.findItem(R.id.star_button);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}

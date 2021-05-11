package com.digimaster.digicourse.digicyber.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Adapter.DetailLibraryViewPagerAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.DetailCourseHalamanAchievement;
import com.digimaster.digicourse.digicyber.Fragment.ListModuleHalamanAchievement;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TitleCourseHalamanAchievement extends AppCompatActivity  {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    BaseApiService mApiService;
    SecuredPreference pref;
    String courseid,passing_tit,passing_img;
    TextView title,writer,count;
    RelativeLayout rvHeaderDetailCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_library_baru);
        tabLayout =  findViewById(R.id.tl_detaillib);
        appBarLayout =  findViewById(R.id.appbardetaillib);
        viewPager = findViewById(R.id.vp_detaillib);
        DetailLibraryViewPagerAdapter adapter = new DetailLibraryViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new ListModuleHalamanAchievement(),"Modules");
        adapter.AddFragment(new DetailCourseHalamanAchievement(),"Details");
        rvHeaderDetailCourse = findViewById(R.id.rv_header);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mApiService = UtilsApi.getAPIService();
        title = findViewById(R.id.tv_titlecoursetitle);
        writer = findViewById(R.id.tv_writercoursetitle);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);


        loadDetailOnsite();


        Toolbar toolbar = findViewById(R.id.modtb);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_bar, menu);


        return true;
    }

    private void loadDetailOnsite() {


        try {
            courseid =pref.getString(PrefContract.cour_id, "");
            passing_tit = pref.getString(PrefContract.title, "");
            passing_img = pref.getString(PrefContract.cour_image, "");

            Picasso.with(this).load(getResources().getString(R.string.base_url_asset_quiz) + "course/image/" + passing_img)
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
        mApiService.getTitlecourse(courseid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {

                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                JSONArray userId = jsonRESULTS.getJSONArray("course_title:");
                                JSONObject obj = userId.getJSONObject(0);
                                //passingTRYJUARDI

                                String id= obj.getString("course_id");

                                String writ = obj.getString("institut_name");
                                String judul = obj.getString("course_name");
                                String total = obj.getString("Total_Module");

                                title.setText(judul);
                                writer.setText(writ);
                                writer.setTextSize(12);


                            } catch (JSONException| IOException e) {
                                e.printStackTrace();
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });






    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
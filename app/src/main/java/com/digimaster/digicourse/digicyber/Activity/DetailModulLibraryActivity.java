package com.digimaster.digicourse.digicyber.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.digimaster.digicourse.digicyber.Fragment.DetailsTopicsLibraryDetails;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.DetailLibraryViewPagerAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.DetailsModulLibraryDetails;
//import com.digimaster.digicourse.digicyber.Fragment.DetailsTopicsLibraryDetails;
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

public class DetailModulLibraryActivity extends AppCompatActivity  {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    BaseApiService mApiService;
    SecuredPreference pref;
    String moduleid,modulename,passing_module_title,passing_module_img;
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
        adapter.AddFragment(new DetailsTopicsLibraryDetails(),"Topics");
        adapter.AddFragment(new DetailsModulLibraryDetails(),"Details");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mApiService = UtilsApi.getAPIService();
        title = findViewById(R.id.tv_titlecoursetitle);
        writer = findViewById(R.id.tv_writercoursetitle);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        rvHeaderDetailCourse = findViewById(R.id.rv_header);


        try {
            moduleid =pref.getString(PrefContract.module_id, "");
            modulename =pref.getString(PrefContract.module_title, "");

            pref.put(PrefContract.module_id,moduleid);
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
        mApiService.getTitleModul(moduleid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(DetailModulLibraryActivity.this,modulename, Toast.LENGTH_SHORT).show();
                            try {

                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                JSONArray userId = jsonRESULTS.getJSONArray("module_title:");
                                JSONObject obj = userId.getJSONObject(0);
                                //passingTRYJUARDI

                                String id= obj.getString("module_id");

                                String writ = obj.getString("institut_name");

                                String total = obj.getString("Total_Action");

                                title.setText(modulename);
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




    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
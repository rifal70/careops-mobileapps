package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.SoalAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.MateriItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class eAssessActivity extends AppCompatActivity {

    FloatingActionButton playDemo;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<MateriItem> dataItemList = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    SoalAdapter mMyBabAdapter;
    String passing, passing_tit, passing_img;
    ImageView ivKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            passing = pref.getString(PrefContract.cour_id, "");
            passing_tit = pref.getString(PrefContract.title, "");
            passing_img = pref.getString(PrefContract.img_url, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        //Log.d("logggPassKocag", "onCreate: "+passing);

        //ivKosong = findViewById(R.id.iv_header_kosong);
        Picasso.with(this).load(getResources().getString(R.string.base_url_asset) + "image/" + passing_img).into(ivKosong);


        Toolbar toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle(passing_tit);


    }


    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
//        Intent intent  = new Intent(CourseActivity.this, HomeActivity.class);
//        startActivity(intent);
//        finish();
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

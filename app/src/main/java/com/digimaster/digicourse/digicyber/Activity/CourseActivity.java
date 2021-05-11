package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ModuleAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.MateriItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    FloatingActionButton playDemo;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<MateriItem> dataItemList = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    ModuleAdapter mModuleAdapter;
    String passing, passing_tit, passing_img;
    ImageView ivKosong;
    Button btnFeedBack;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            passing = pref.getString(PrefContract.cour_id, "");
            passing_tit = pref.getString(PrefContract.title, "");
            passing_img = pref.getString(PrefContract.img_url,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        //Log.d("logggPassKocag", "onCreate: "+passing);

        //ivKosong = findViewById(R.id.iv_header_kosong);



        //Picasso.with(this).load("https://assets.digicourse.id/image/digicom/"+passing_img).into(ivKosong);

//        playDemo = findViewById(R.id.playContent);
//        playDemo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CourseActivity.this, ShowDemo.class);
//                startActivity(intent);
//            }
//        });

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle(passing_tit);


//        mRecyclerView = findViewById(R.id.rvCourse);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //loadCourseSKUY();
    }

//    private void loadCourseSKUY() {
//
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
//        mApiService.getOnlineDetail("online/get_detail_course/"+passing).enqueue(new Callback<DetailCourseResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<DetailCourseResponse> call, @NonNull Response<DetailCourseResponse> response) {
//                if (response.isSuccessful()) {
//                    loading.dismiss();
//                    //Log.d("logggDetailCourRes", "" + response.body());
//
//                    List<MateriItem> materiItemList = response.body().getMateri();
//
//                    mMyBabAdapter = new MyBabAdapter(CourseActivity.this, materiItemList, genProductAdapterListener());
//                    mRecyclerView.setAdapter(mMyBabAdapter);
//
//                    mMyBabAdapter.notifyDataSetChanged();
//
//                    //Log.d("logggS", "response beres");
//                } else {
//                    loading.dismiss();
//                    Toast.makeText(CourseActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<DetailCourseResponse> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(CourseActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


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

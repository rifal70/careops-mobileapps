package com.digimaster.digicourse.digicyber.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AchievementCourseAdapter;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.DataItem;
import com.digimaster.digicourse.digicyber.Model.LibraryItem;
import com.digimaster.digicourse.digicyber.Model.LibraryResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementCourseActivity extends AppCompatActivity {
    protected String token;
    protected String userid;
    protected AlertDialog loading;
    protected SecuredPreference pref;
    protected Context context;
    protected AchievementCourseAdapter achievementCourseAdapter;
    protected BaseApiService mApiService;
    protected RecyclerView rvDosen;
    protected RecyclerView.LayoutManager layoutManager;
    protected RelativeLayout relaCourse;
    protected List<DataItem> androidItemList = new ArrayList<>();
    protected LinearLayout  linearLayoutMyCour;
    protected View alertDialogView;
    protected SwipeRefreshLayout mySwipeCourse;
    protected ActionBar actionBar;
    List<LibraryItem> onlineItemList = new ArrayList<>();
    AchievementCourseAdapter onlineAdapter;
    protected boolean mToolBarNavigationListenerIsRegistered = false;
    Toolbar toolbar;
    String passing_firstname,passing_photo,passing_institution,passing_position,passing_lastname;
    TextView tvNamauser,tvPosition,tvInstitution;
    CircleImageView iv_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_courses);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDosen = findViewById(R.id.rvDosen);
        relaCourse = findViewById(R.id.rv_course_item);
        rvDosen.setHasFixedSize(true);
        rvDosen.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDosen.setLayoutManager(layoutManager);
        linearLayoutMyCour = findViewById(R.id.linearMyCourse);
        mySwipeCourse = findViewById(R.id.swiperefreshMyCourse);
        tvPosition = findViewById(R.id.tv_position);
        tvNamauser = findViewById(R.id.tv_namauser);
        iv_profile = findViewById(R.id.profile_image);
        tvInstitution =findViewById(R.id.tv_institution);
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("Achievement");


        alertDialogView = LayoutInflater.from(this).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(this).setView(alertDialogView).create();



        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            token =  pref.getString(PrefContract.PREF_TOKEN,"");
            userid = pref.getString(PrefContract.user_id,"");

            passing_firstname = pref.getString(PrefContract.first_name, "");
            passing_photo = pref.getString(PrefContract.photo, "");
            passing_institution = pref.getString(PrefContract.institution, "");
            passing_position = pref.getString(PrefContract.position, "");
            passing_lastname = pref.getString(PrefContract.last_name, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        tvNamauser.setText(passing_firstname+ " " + passing_lastname);
        tvPosition.setText(passing_position);
        tvInstitution.setText(passing_institution);
//        tvName.setText(name);
//        tvRole.setText(role);
        if(!passing_photo.equals("")){
            Picasso.with(this)
                    .load(getResources().getString(R.string.base_url_asset_profile)+passing_photo)
                    .into(iv_profile);
        }

        mySwipeCourse.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        loadJSON();
                    }
                }
        );

        loadJSON();

    }

    private void loadJSON(){
        loading.show();
        loading.getWindow().setLayout(250, 250);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getLibraryach(userid).enqueue(new Callback<LibraryResponse>() {
            @Override
            public void onResponse(@NonNull Call<LibraryResponse> call, @NonNull Response<LibraryResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN",""+response.body());
                    List<LibraryItem> dataItemList = response.body().getLibrary();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    onlineAdapter = new AchievementCourseAdapter(AchievementCourseActivity.this, dataItemList, genProductAdapterListener());
                    rvDosen.setAdapter(onlineAdapter);
                    onlineAdapter.notifyDataSetChanged();

                    //Log.d("logggS","response beres");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);

                    }
                    Toast.makeText(AchievementCourseActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LibraryResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                    mySwipeCourse.setRefreshing(false);

                }
                //Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
}

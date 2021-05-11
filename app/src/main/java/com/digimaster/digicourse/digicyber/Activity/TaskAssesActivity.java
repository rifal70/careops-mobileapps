package com.digimaster.digicourse.digicyber.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.TaskAssAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.DataItem;
import com.digimaster.digicourse.digicyber.Model.TaskAssItem;
import com.digimaster.digicourse.digicyber.Model.TaskAssResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaskAssesActivity extends AppCompatActivity {
    String token;
    String userid;
    AlertDialog loading;
    SecuredPreference pref;
    Context context;
    TaskAssAdapter taskAssAdapter;
    BaseApiService mApiService;
    RecyclerView rvDosen;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relaCourse;
    List<DataItem> androidItemList = new ArrayList<>();
    LinearLayout linearLayoutMyCour;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;
    Toolbar toolbar;


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

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = this;

        alertDialogView = LayoutInflater.from(this).inflate
                (R.layout.loading_layout, null);

        loading = new AlertDialog.Builder(this).setView(alertDialogView).create();


        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            token = pref.getString(PrefContract.PREF_TOKEN, "");
            userid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("Assessment Task");

        mySwipeCourse.setOnRefreshListener(
                this::loadAssessment
        );

        loadAssessment();

    }


    private void loadAssessment() {
        loading.show();
        loading.getWindow().setLayout(250, 250);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.ambilTaskAss(userid).enqueue(new Callback<TaskAssResponse>() {
            @Override
            public void onResponse(@NonNull Call<TaskAssResponse> call, @NonNull Response<TaskAssResponse> response) {
                if (response.isSuccessful()) {

                    String jsonRESULTS = response.body().getMessage();
                    //Log.d("logggMSG", "onResponse: "+jsonRESULTS);
                    //String pesanError = jsonRESULTS.getString("message");
                    if (jsonRESULTS.equals("Berhasil mengambil seluruh data")) {
                        //Log.d("logggN", "" + response.body());
                        List<TaskAssItem> taskAssItemList = response.body().getTaskAss();
                        if (taskAssItemList.size() == 0) {
                            rvDosen.setVisibility(View.GONE);
                            relaCourse.setVisibility(View.VISIBLE);
                        } else {
                            relaCourse.setVisibility(View.GONE);
                            rvDosen.setVisibility(View.VISIBLE);
                            taskAssAdapter = new TaskAssAdapter(TaskAssesActivity.this, taskAssItemList, genProductAdapterListener());
                            rvDosen.setAdapter(taskAssAdapter);
                            taskAssAdapter.notifyDataSetChanged();

                            //Log.d("logggS", "response beres");
                            loading.dismiss();
                            mySwipeCourse.setRefreshing(false);
                        }
                    } else {
                        rvDosen.setVisibility(View.GONE);
                        relaCourse.setVisibility(View.VISIBLE);
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);

//                        Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Silahkan Login", Snackbar.LENGTH_LONG);
//                        View sbView = snackbar.getView();
//                        sbView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_alert));
//                        snackbar.show();
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                } else {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Please login first", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskAssResponse> call, Throwable t) {
                loading.dismiss();
//                Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Bad Connection", Snackbar.LENGTH_LONG);
//                View sbView = snackbar.getView();
//                sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
//                snackbar.show();
                rvDosen.setVisibility(View.GONE);
                relaCourse.setVisibility(View.VISIBLE);
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
    public void onResume() {
        super.onResume();
        // put your code here...
    }


}

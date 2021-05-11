package com.digimaster.digicourse.digicyber.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ListGroupAdminAdapter;
import com.digimaster.digicourse.digicyber.Adapter.MyScoresAdapter;
import com.digimaster.digicourse.digicyber.Adapter.SimpleSectionedRecyclerViewAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ScoreResponse;
import com.digimaster.digicourse.digicyber.Model.ScoreUserItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScoreAdminActivity extends AppCompatActivity {

    Context mContext;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    SecuredPreference pref;
    String[] sCheeseStrings;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ListGroupAdminAdapter listGroupAdminAdapter;
    ProgressDialog loading;
    BaseApiService mApiService;
    String user_id, cour_id, name, phone;
    RelativeLayout rela_score, rela_cert;
    TextView tvName, tvRole, tvBeloman, tvBeres;
    CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score_admin);

        mRecyclerView = findViewById(R.id.rv_certi);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Your RecyclerView.Adapter

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rela_score = findViewById(R.id.rvRootMyScore);
        rela_cert = findViewById(R.id.rela_cert);

        tvName = findViewById(R.id.tvNameScore);
        tvRole = findViewById(R.id.tvTitleScore);
        tvBeloman = findViewById(R.id.tvBeloman);
        tvBeres = findViewById(R.id.tvBeres);

        circleImageView = findViewById(R.id.imageView7);
        circleImageView.setImageResource(R.drawable.profile);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("user_id")!= null || bundle.getString("user_name")!=null
                || bundle.getString("user_phone")!=null) {
            user_id = bundle.getString("user_id");
            name = bundle.getString("user_name");
            phone = bundle.getString("user_phone");

            tvName.setText(name);
            tvRole.setText(phone);
        }

        Toolbar toolbar = findViewById(R.id.detail_toolbar_app_bar_atasnya_doank);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Member Score Detail");

        try {
            cour_id = pref.getString(PrefContract.cour_id, "");


        } catch (AppException e) {
            e.printStackTrace();
        }



        if (user_id.equals("") || user_id.equals(null)) {
            mRecyclerView.setVisibility(View.GONE);
            rela_score.setVisibility(View.VISIBLE);
        } else {
            loadCert();
        }
    }

    private void loadCert() {


        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getScoreUser(user_id).enqueue(new Callback<ScoreResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScoreResponse> call, @NonNull Response<ScoreResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggSEKUD", "" + response.body());
                    List<ScoreUserItem> scoreUserItemList = response.body().getScoreUser();
                    String beres = response.body().getTaskFinish();
                    String beloman = response.body().getTaskUnfinish();

                    tvBeloman.setText(beloman);
                    tvBeres.setText(beres);

                    if (scoreUserItemList.size() == 0) {
                        mRecyclerView.setVisibility(View.GONE);
                        rela_cert.setVisibility(View.VISIBLE);
                    } else {
                        rela_cert.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                        mAdapter = new MyScoresAdapter(MyScoreAdminActivity.this, scoreUserItemList, genProductAdapterListener());
                        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                                SimpleSectionedRecyclerViewAdapter(MyScoreAdminActivity.this, R.layout.section, R.id.section_text, mAdapter);
                        mSectionedAdapter.setSections(sections.toArray(dummy));

                        //Apply this adapter to the RecyclerView
                        mRecyclerView.setAdapter(mSectionedAdapter);
                        //initDataIntent(androidItems);
                        mAdapter.notifyDataSetChanged();
                    }
                    //rvDosen.setAdapter(orderAdapter);
                    Log.d("logggS", "response beres");
                    loading.dismiss();
                } else {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(rela_cert, "Please login first", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                    snackbar.show();
                }
                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                //String pesan = jsonRESULTS.getString("success");
            }

            @Override
            public void onFailure(Call<ScoreResponse> call, Throwable t) {
                loading.dismiss();
                Snackbar snackbar = Snackbar.make(rela_cert, "Bad Connection", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                snackbar.show();
            }
        });
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

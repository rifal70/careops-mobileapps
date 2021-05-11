package com.digimaster.digicourse.digicyber.Activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.HistoryAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.RegisteredEventItem;
import com.digimaster.digicourse.digicyber.Model.RegisteredEventResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistory;
    BaseApiService mApiService;
    HistoryAdapter historyAdapter;
    SecuredPreference pref;
    String uid;
    GifImageView gifImageViewHis;
    RelativeLayout rvRootActHis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        gifImageViewHis = findViewById(R.id.gif_load_history);
        rvRootActHis = findViewById(R.id.rvRootActHis);
        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new GridLayoutManager(this,1));
        rvHistory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        Toolbar toolbar = findViewById(R.id.detail_toolbar_app_bar_atasnya_doank);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Request History");

        try {
            uid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loadHistoryu(uid);
    }

    private void loadHistoryu(String id){
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gifImageViewHis.setVisibility(View.VISIBLE);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getRegisteredEvent(id).enqueue(new Callback<RegisteredEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisteredEventResponse> call, @NonNull Response<RegisteredEventResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<RegisteredEventItem> historyRequestItemList = response.body().getRegisteredEvent();

                    historyAdapter = new HistoryAdapter(HistoryActivity.this, historyRequestItemList, genProductAdapterListener());
                    rvHistory.setAdapter(historyAdapter);
                    historyAdapter.notifyDataSetChanged();

                    gifImageViewHis.setVisibility(View.GONE);



                } else {
                    gifImageViewHis.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(rvRootActHis, "Gagal mengambil data", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(HistoryActivity.this, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<RegisteredEventResponse> call, Throwable t) {
                gifImageViewHis.setVisibility(View.GONE);
                Toast.makeText(HistoryActivity.this, "Tidak ada history event.", Toast.LENGTH_SHORT).show();
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

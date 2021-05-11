package com.digimaster.digicourse.digicyber.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.OnsiteItem;
import com.digimaster.digicourse.digicyber.Model.OnsiteResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    String token, userid;
    SecuredPreference pref;
    Context context;
    BaseApiService mApiService;
    RecyclerView rvWishlist;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button btnBrowse;
    AlertDialog loading;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvWishlist = findViewById(R.id.rvWishlist);
        rvWishlist.setHasFixedSize(true);
        rvWishlist.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWishlist.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        mySwipeCourse = findViewById(R.id.swiperefreshEvent);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = this;
        btnBrowse = findViewById(R.id.btn_browse_wishlist);

        alertDialogView = LayoutInflater.from(this).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(this).setView(alertDialogView).create();

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("Event");

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            token =  pref.getString(PrefContract.PREF_TOKEN,"");
            userid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        mySwipeCourse.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Log.i("logggASDAS", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        initItem();
                    }
                }
        );


        //loadJSON();
        initItem();
    }

    private void initItem() {
        if (loading != null && loading.isShowing()) {

        }
        else{
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getOnsiteCour().enqueue(new Callback<OnsiteResponse>() {
            @Override
            public void onResponse(@NonNull Call<OnsiteResponse> call, @NonNull Response<OnsiteResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN",""+response.body());
                    List<OnsiteItem> onsiteItemList = response.body().getOnsite();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();

                    //recyclerCurr.setAdapter(adapter_r);
                    onsiteAdapter = new OnsiteAdapter(EventActivity.this, onsiteItemList, genProductAdapterListener());
                    rvWishlist.setAdapter(onsiteAdapter);
                    onsiteAdapter.notifyDataSetChanged();

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
                    Toast.makeText(EventActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnsiteResponse> call, Throwable t) {
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
        getMenuInflater().inflate(R.menu.history, menu);
        //MenuItem item = menu.findItem(R.id.star_button);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            //getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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


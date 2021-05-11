package com.digimaster.digicourse.digicyber.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.NewsAdapter;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.NewsItem;
import com.digimaster.digicourse.digicyber.Model.NewsResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    String token, userid;
    SecuredPreference pref;
    Context context;
    NewsAdapter newsAdapter;
    BaseApiService mApiService;
    RecyclerView rvWishlist;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NewsItem> androidItemList = new ArrayList<>();
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
        newsAdapter = new NewsAdapter(this, androidItemList, genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
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
        toolbar.setTitle("Announcement");

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
                        loadJSON();
                    }
                }
        );


        loadJSON();
    }

    private void loadJSON(){
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rvWishlist)
                .adapter(newsAdapter)
                .load(R.layout.news_item)
                .color(R.color.light_transparent)
                .duration(1000)
                .angle(30)
                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getNews().enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggSCS", "response berhasil");
                    List<NewsItem> dataItemList = response.body().getNews();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    newsAdapter = new NewsAdapter(NewsActivity.this, dataItemList, genProductAdapterListener());
                    rvWishlist.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                    mySwipeCourse.setRefreshing(false);

                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    loading.dismiss();
                    mySwipeCourse.setRefreshing(false);

                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                mySwipeCourse.setRefreshing(false);

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
}


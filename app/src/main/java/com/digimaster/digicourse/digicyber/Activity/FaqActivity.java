package com.digimaster.digicourse.digicyber.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.FaqAdapter;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Adapter.faqcategoryadapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.FaqCatItem;
import com.digimaster.digicourse.digicyber.Model.FaqItem;
import com.digimaster.digicourse.digicyber.Model.FaqResponse;
import com.digimaster.digicourse.digicyber.Model.Faqcategoryresponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {
    String token, userid,searchval;
    SecuredPreference pref;
    Context context;
    faqcategoryadapter faqcategoryadapter;
    FaqAdapter faqAdapter;
    BaseApiService mApiService;
    RecyclerView rvFaq,rvFaqcat;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    List<FaqItem> androidItemList = new ArrayList<>();
    List<FaqCatItem> categoryitemlist = new ArrayList<>();
    Button btnBrowse,searchby;
    AlertDialog loading;
    View alertDialogView;
    MaterialSearchView searchView;
    Toolbar toolbar;
    LinearLayout faqby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        rvFaq = findViewById(R.id.rvFaq);
        rvFaqcat = findViewById(R.id.rv_faqby);
        rvFaq.setHasFixedSize(true);
        rvFaqcat.setHasFixedSize(true);
        rvFaq.setLayoutManager(mLayoutManager);
        rvFaqcat.setLayoutManager(mLayoutManager2);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFaq.setLayoutManager(layoutManager);
        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFaqcat.setLayoutManager(layoutManager2);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        faqAdapter = new FaqAdapter(this, androidItemList, genProductAdapterListener());
        faqcategoryadapter = new faqcategoryadapter(this,categoryitemlist,genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
        faqby = findViewById(R.id.faqcategory);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = this;
        btnBrowse = findViewById(R.id.btn_browse_wishlist);

        alertDialogView = LayoutInflater.from(this).inflate
                (R.layout.loading_layout, null);

        loading = new AlertDialog.Builder(this).setView(alertDialogView).create();
//faqby.setVisibility(View.GONE);
        try {
            token = pref.getString(PrefContract.PREF_TOKEN, "");
            userid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

//
//        Bundle bundle = this.getIntent().getExtras();
//        searchval = bundle.getString("pdf");
//        Toast.makeText(context,searchval, Toast.LENGTH_SHORT).show();
//        faqcategoryadapter faqcategoryadapter = new faqcategoryadapter(btncat, new ClickListener() {
//            @Override public void onPositionClicked(int position) {
//                // callback performed on click
//            }
//
//            @Override public void onLongClicked(int position) {
//                // callback performed on click
//            }
//        });
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            if (bundle.getString("cat") != null ) {
                searchval = bundle.getString("cat");
                mApiService.getFaq3(searchval).enqueue(new Callback<FaqResponse>() {
                    @Override
                    public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                        if (response.isSuccessful()) {
                            //Log.d("logggSCS", "response berhasil");
                            List<FaqItem> dataItemList = response.body().getFaq();

                            //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                            faqAdapter = new FaqAdapter(FaqActivity.this, dataItemList, genProductAdapterListener());
                            rvFaq.setAdapter(faqAdapter);
                            faqAdapter.notifyDataSetChanged();

                            loading.dismiss();

                        } else {
                            //Log.d("logggResGagal", "respon gagal");
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<FaqResponse> call, Throwable t) {
                        //Log.d("logggGGL", "GAGAL");
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
            }
        }
        else {
            mApiService.getFaq2().enqueue(new Callback<FaqResponse>() {
                @Override
                public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                    if (response.isSuccessful()) {
                        //Log.d("logggSCS", "response berhasil");
                        List<FaqItem> dataItemList = response.body().getFaq();

                        //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                        faqAdapter = new FaqAdapter(FaqActivity.this, dataItemList, genProductAdapterListener());
                        rvFaq.setAdapter(faqAdapter);
                        faqAdapter.notifyDataSetChanged();

                        loading.dismiss();

                    } else {
                        //Log.d("logggResGagal", "respon gagal");
                        loading.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<FaqResponse> call, Throwable t) {
                    //Log.d("logggGGL", "GAGAL");
                    //Log.e("debug", "onFailure: ERROR > " + t.toString());
                    loading.dismiss();
                }
            });
        }
//        searchby =  findViewById(R.id.btncategory);
//
//        searchby.setOnClickListener(view -> {
//            Toast.makeText(context,"ea", Toast.LENGTH_SHORT).show();
//        });

        //searchby.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {

        //      searchval= searchby.getText().toString();
        //  Toast.makeText(context,"ea", Toast.LENGTH_SHORT).show();
//                mApiService.getFaq3(searchval).enqueue(new Callback<FaqResponse>() {
//                    @Override
//                    public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
//                        if (response.isSuccessful()) {
//                            //Log.d("logggSCS", "response berhasil");
//                            List<FaqItem> dataItemList = response.body().getFaq();
//
//                            //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
//                            faqAdapter = new FaqAdapter(FaqActivity.this, dataItemList, genProductAdapterListener());
//                            rvFaq.setAdapter(faqAdapter);
//                            faqAdapter.notifyDataSetChanged();
//
//                            loading.dismiss();
//
//                        } else {
//                            //Log.d("logggResGagal", "respon gagal");
//                            loading.dismiss();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<FaqResponse> call, Throwable t) {
//                        //Log.d("logggGGL", "GAGAL");
//                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
//                    }
//      /          });
        //   }
        //  });


        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("FAQ");



        searchView = findViewById(R.id.search_view);

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rvFaq)
                .adapter(faqAdapter)
                .load(R.layout.news_item)
                .color(R.color.light_transparent)
                .duration(1000)
                .angle(30)
                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        // mApiService.getFaq(filter).enqueue(new Callback<FaqResponse>() {

        //initItem();


        mApiService.getfaqcategory().enqueue(new Callback<Faqcategoryresponse>() {
            @Override
            public void onResponse(Call<Faqcategoryresponse> call, Response<Faqcategoryresponse> response) {
                if (response.isSuccessful()) {
                    //Log.d("logggSCS", "response berhasil");
                    List<FaqCatItem> dataItemList2 = response.body().getFaqCat();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    faqcategoryadapter = new faqcategoryadapter(FaqActivity.this, dataItemList2, genProductAdapterListener());
                    rvFaqcat.setAdapter(faqcategoryadapter);
                    faqcategoryadapter.notifyDataSetChanged();

                    loading.dismiss();

                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    loading.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Faqcategoryresponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });

    }




    private void loadJSON(String teks) {
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//        final SkeletonScreen skeletonScreen = Skeleton.bind(rvFaq)
//                .adapter(faqAdapter)
//                .load(R.layout.news_item)
//                .color(R.color.light_transparent)
//                .duration(1000)
//                .angle(30)
//                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        // mApiService.getFaq(filter).enqueue(new Callback<FaqResponse>() {
        mApiService.getFaq(teks).enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful()) {
                    //Log.d("logggSCS", "response berhasil");
                    List<FaqItem> dataItemList = response.body().getFaq();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    faqAdapter = new FaqAdapter(FaqActivity.this, dataItemList, genProductAdapterListener());
                    rvFaq.setAdapter(faqAdapter);
                    faqAdapter.notifyDataSetChanged();

                    loading.dismiss();

                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    loading.dismiss();
                }

            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
                Toast.makeText(context, "Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.faq, menu);
        //MenuItem item = menu.findItem(R.id.star_button);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (item != null) {
            searchView = (SearchView) item.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String teks) {
                //Here u can get the value "query" which is entered in the search box.
                loadJSON(teks);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String teks) {
                if (teks.equals("")){
                    //  faqby.setVisibility(View.GONE);
                    faqby.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqby.setVisibility(View.GONE);
                    // faqby.setVisibility(View.VISIBLE);
                    loadJSON(teks);
                }
                return false;
            }

        });




        return true;




    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            faqby.setVisibility(View.VISIBLE);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}


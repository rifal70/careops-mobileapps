package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.DetailLibraryAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.LibraryDetailItem;
import com.digimaster.digicourse.digicyber.Model.LibraryDetailResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLibActivity extends AppCompatActivity  {
    private FloatingActionButton playDemo;
    List<LibraryDetailItem> dataItemList = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    String passing, passingAuth, passingTit, passingImg, passingPrice, passType, passMail, passPhone, passFullname, passCek, vid, mtrl_id,
            passingDesc,
            uid,cour_id,
            vouc;
    DetailLibraryAdapter detailLibraryAdapter;
    RecyclerView rv_spec;
    RecyclerView.LayoutManager layoutManager;
    ImageView ivHeader, ivOucher;
    TextView price, tvDesc, tv_jml_vid, tv_jml_quiz, tv_expire, tv_completion, tv_author;
    LikeButton loveBtn;
    Button btnApply;
    CardView cv1,cv2,cv3,cv4,cv5;
    NestedScrollView cv69, nestedScrollViewAsli;
    LinearLayout lilaDet;
    RelativeLayout rvDetailItem;
    Toolbar toolbar;
    TextView someTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            //passing = pref.getString(PrefContract.id_detail, "");
            passingAuth = pref.getString(PrefContract.author, "");
            passingTit = pref.getString(PrefContract.title, "");
            passingImg = pref.getString(PrefContract.img_url, "");
            passingPrice = pref.getString(PrefContract.price, "");
            passType = pref.getString(PrefContract.cour_type, "");
            passMail = pref.getString(PrefContract.email, "");
            passPhone = pref.getString(PrefContract.phone, "");
            passFullname = pref.getString(PrefContract.USER_FULL_NAME, "");
            passCek = pref.getString(PrefContract.check_login, "false");
            passingDesc = pref.getString(PrefContract.desc,"");
            vouc = pref.getString(PrefContract.voucher,"");

            uid = pref.getString(PrefContract.user_id,"");
            cour_id = pref.getString(PrefContract.cour_id,"");
            mtrl_id = pref.getString(PrefContract.material_id,"");


        } catch (AppException e) {
            e.printStackTrace();
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.getString("id_detail") != null || bundle.getString("price") != null ||
                    bundle.getString("cour_id") != null) {
                passing = bundle.getString("id_detail");
                passingPrice = bundle.getString("price");
                cour_id = bundle.getString("id_detail");

            } else {
                //Log.d("logggError", "bundle error");
            }
        }


        //Log.d("logggDetilValuePosition", ""+passingPrice);

        cv1 = findViewById(R.id.cv1_app_bar_detail);
        //cv2 = findViewById(R.id.cv2_app_bar_detail);
        cv3 = findViewById(R.id.cv3_app_bar_detail);
        cv4 = findViewById(R.id.cv4_app_bar_detail);
        cv5 = findViewById(R.id.cv5_app_bar_detail);
        cv69 = findViewById(R.id.cv69_app_bar_detail);
        nestedScrollViewAsli = findViewById(R.id.nested_scro_app_bar_detail);
        lilaDet = findViewById(R.id.LiLa_Det_Item);
        rvDetailItem = findViewById(R.id.rvDetail_item);
        tvDesc =findViewById(R.id.tv_desc_detail_item);
        tv_jml_vid = findViewById(R.id.tv_hour_app_bar_detail);
        tv_jml_quiz = findViewById(R.id.tv_quiz_app_bar_detail);
        tv_completion = findViewById(R.id.tv_completion_app_bar_detail);
        tv_expire = findViewById(R.id.tv_expire_app_bar_detail);
        tv_author = findViewById(R.id.tv_author_app_bar_detail);

        //ivOucher = findViewById(R.id.ivOucher_detail_item);

//        rv_spec = findViewById(R.id.rvCourse);
//        rv_spec.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //BANGSAT HARUSNYA VERTICAL SI ASUUUUUUUU
        rv_spec.setLayoutManager(layoutManager);
        detailLibraryAdapter = new DetailLibraryAdapter(DetailLibActivity.this, dataItemList, genProductAdapterListener());
        rv_spec.setAdapter(detailLibraryAdapter);
        detailLibraryAdapter.notifyDataSetChanged();

        //ivHeader = findViewById(R.id.iv_header_kosong);
        price = findViewById(R.id.tvPriceAsli);

        Picasso.with(DetailLibActivity.this).load(getResources().getString(R.string.base_url_asset) +"library/image/"+ passingImg).into(ivHeader);

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(passingTit);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));


      loadDetail();

    }

    public void loadDetail(){
//        if (loading != null && loading.isShowing()) {
//
//        }
//        else{
//            loading.show();
//            loading.getWindow().setLayout(250, 250);
//        }

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.getString("id_detail") != null || bundle.getString("price") != null ||
                    bundle.getString("cour_id") != null) {
                passing = bundle.getString("id_detail");
                passingPrice = bundle.getString("price");
                cour_id = bundle.getString("id_detail");

            } else {
                //Log.d("logggError", "bundle error");
            }
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getLibDetail(passing).enqueue(new Callback<LibraryDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<LibraryDetailResponse> call, @NonNull Response<LibraryDetailResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN",""+response.body());
                    List<LibraryDetailItem> dataItemList = response.body().getLibraryDetail();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    detailLibraryAdapter = new DetailLibraryAdapter(DetailLibActivity.this, dataItemList, genProductAdapterListener());
                    rv_spec.setAdapter(detailLibraryAdapter);
                    detailLibraryAdapter.notifyDataSetChanged();

                    //Log.d("logggS","response beres");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }
                    Toast.makeText(DetailLibActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LibraryDetailResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(DetailLibActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void regis_onlineskuy(String status) {
        String slagslug = null;
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        try {
             slagslug = pref.getString(PrefContract.slug,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.get("price") != null) {
                passingPrice = bundle.getString("price");
                cour_id = bundle.getString("id_detail");
            }
        }


        //Log.d("ngews", " "+uid+" "+passFullname+" "+cour_id+" "+status+" "+passingPrice+" "+slagslug);
        mApiService.requestRegisOnline(uid, passFullname, cour_id, status, passingPrice, slagslug)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();


                            //Log.d("logggBerhasil", "onResponse: Berhasil");
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Daftar berhasil")) {

                                    JSONObject objReq = jsonRESULTS.getJSONObject("request");
                                    String nama_rek = objReq.getString("nama_rek");
                                    String price = objReq.getString("total");
                                    String stat = objReq.getString("status");

//                                    pref.put(PrefContract.nama_rekening, nama_rek);
//                                    //pref.put(PrefContract.price, price);
//                                    pref.put(PrefContract.status_pembayaran, stat);

                                    Toast.makeText(DetailLibActivity.this, "Terimakasih telah mendaftar. Silahkan menyelesaikan pembayaran" +
                                            "", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DetailLibActivity.this, SimpleTabsActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(DetailLibActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                                    //Log.d("logggGagal", "onResponse: Gagal");

                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
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

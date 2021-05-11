package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailOnsiteItemActivity extends AppCompatActivity {
    private FloatingActionButton playDemo;
    ImageView img_head;
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    String passing, passingAuth, passingTit, passingImg, passingPrice, passType, passCek, vid, mtrl_id;
    ImageView ivHeader;
    TextView price, venue, desc;
    String uid,cour_id;
    LikeButton loveBtn;
    RelativeLayout rvDetailOnsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_onsite_item);

//        ButterKnife.bind(this);
        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        ivHeader = findViewById(R.id.iv_header_det);
        price = findViewById(R.id.price_onsite);
        venue = findViewById(R.id.tvVenue);
        desc = findViewById(R.id.tv_desc);
        loveBtn = findViewById(R.id.star_buttonOnsite);
        rvDetailOnsite = findViewById(R.id.rv_detail_onsite_item);
        playDemo = findViewById(R.id.playDemo_app_bar_onsite

        );


        try {
            //passing = pref.getString(PrefContract.id_detail, "");
            passingAuth = pref.getString(PrefContract.author, "");
            passingTit = pref.getString(PrefContract.title, "");
            passingImg = pref.getString(PrefContract.img_url, "");
            passingPrice = pref.getString(PrefContract.price, "");
            passType = pref.getString(PrefContract.cour_type, "");
            uid = pref.getString(PrefContract.user_id,"");
            cour_id = pref.getString(PrefContract.cour_id,"");
            passCek = pref.getString(PrefContract.check_login, "false");

            mtrl_id = pref.getString(PrefContract.material_id,"");

        } catch (AppException e) {
            e.printStackTrace();
        }


        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("id_detail")!= null)
        {
            passing = bundle.getString("id_detail");
        }else{
            Log.d("logggError", "bundle error");
        }

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle(passingTit);
        Picasso.with(DetailOnsiteItemActivity.this).load(getResources().getString(R.string.base_url_asset) +"event/image/"+ passingImg).into(ivHeader);
        //price.setText("IDR "+passingPrice);

        Button btnApply = findViewById(R.id.btnApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passCek.equals("false")) {
                    Snackbar snackbar = Snackbar.make(rvDetailOnsite, "Sorry, please login first", Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(DetailOnsiteItemActivity.this, R.color.red_alert));
                    snackbar.show();
                } else{
                    regisskuy();
                }
            }
        });



        loadDetailOnsite();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_bar, menu);
        //MenuItem item = menu.findItem(R.id.star_button);

        return true;
    }

    private void loadDetailOnsite() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getOnsiteDetail("onsite/get_detail/"+passing).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggDetailCourRes", "" + response.body());

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray userId = jsonRESULTS.getJSONArray("onsite");

                        //Log.d("logggVidUrlPre", ""+userId);

                        JSONObject obj = userId.getJSONObject(0);
                        String cor_desc = obj.getString("desc");
                        String cor_ven = obj.getString("place");
                        String uid = obj.getString("event_id");


                        pref.put(PrefContract.cour_id, uid);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            desc.setText(Html.fromHtml(cor_desc, Html.FROM_HTML_MODE_COMPACT));
                        }else{
                            desc.setText((Html.fromHtml(cor_desc)));

                        }

                        venue.setText(cor_ven);


                    } catch (JSONException | IOException | AppException e) {
                        e.printStackTrace();
                    }

                    loading.dismiss();
                    //Log.d("logggS", "response beres");

                } else {
                    loading.dismiss();
                    Toast.makeText(DetailOnsiteItemActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailOnsiteItemActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void regisskuy() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        mApiService.requestRegisOnsite(""+uid, cour_id, passingTit)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Daftar berhasil")) {

                                    Toast.makeText(DetailOnsiteItemActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                                    //PAYMENTTT
                                    Intent intent = new Intent(DetailOnsiteItemActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(DetailOnsiteItemActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

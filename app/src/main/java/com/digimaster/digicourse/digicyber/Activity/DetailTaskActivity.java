package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailTaskActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_detail_task);

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

//        playDemo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    pref.put(PrefContract.material_id,mtrl_id);
//                    vid = pref.getString(PrefContract.vid_url_pre,"");
//                } catch (AppException e) {
//                    e.printStackTrace();
//                }
//
//                if(vid.equals("")) {
//
//                    Toast.makeText(DetailTaskActivity.this, "Ups no available video!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Intent intent = new Intent(DetailTaskActivity.this, ShowPreview.class);
//                    intent.putExtra("test2", vid);
//
//                    DetailTaskActivity.this.startActivity(intent);
//                }
//            }
//        });

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
        Picasso.with(DetailTaskActivity.this).load(getResources().getString(R.string.base_url_asset) +"image/digicom/"+ passingImg).into(ivHeader);
        //price.setText("IDR "+passingPrice);

        Button btnApply = findViewById(R.id.btnApply);

        //someTextView.setText("IDR 4.000.000.000");


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        final EditText editText = new EditText(DetailTaskActivity.this);
                        editText.setHint("Put your insight");
                        editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);


                        new SweetAlertDialog(DetailTaskActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Before you finish...")
                                .setConfirmText("Finish")
                                .setCustomView(editText)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        //submit_task();
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .show();

            }
        });

//        loveBtn.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//                //Toast.makeText(DetailOnsiteItemActivity.this, "THANKS FOR LOVING ME", Toast.LENGTH_SHORT).show();
//                love();
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//                //Toast.makeText(DetailOnsiteItemActivity.this, "THANKS FOR UNLOVE ME", Toast.LENGTH_SHORT).show();
//                unlove();
//            }
//        });

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
                        String cor_desc = obj.getString("course_desc");
                        String cor_ven = obj.getString("course_venue");
                        String uid = obj.getString("course_id");
                        String vid_url_pre = obj.getString("course_vid");



                        try {
                            pref.put(PrefContract.vid_url_pre,vid_url_pre);
                        } catch (AppException e) {
                            e.printStackTrace();
                        }

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
                    //loadlagi();
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailTaskActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailTaskActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadlagi() {
        //buat api tentang love, bukan deng api buat ngambil status dan id buat set lovenya di detailitemact
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getLoveStat(uid,cour_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggDetailCourRes", "" + response.body());

                    JSONObject jsonRESULTS = null;
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        String code = jsonRESULTS.getString("status");
                        if(code.equals("100")) {

                            JSONArray pesanError = jsonRESULTS.getJSONArray("data");
                            JSONObject kunci = pesanError.getJSONObject(0);
                            String stat = kunci.getString("status");

                            if (stat.equals("1")) {
                                loveBtn.setLiked(true);
                            } else if (stat.equals("0")) {
                                loveBtn.setLiked(false);
                            }
                        }else{
                            loveBtn.setLiked(false);
                        }

                        //Log.d("logggAPIBARU", ""+stat);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                    Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailTaskActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailTaskActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unlove() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.hateMe(uid,cour_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    loading.dismiss();
                    //Log.d("logggDetailCourRes", "" + response.body());

                    JSONObject jsonRESULTS = null;
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        Boolean pesanError = jsonRESULTS.getBoolean("success");
                        if (pesanError.equals(true)) {
                            Toast.makeText(DetailTaskActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailTaskActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailTaskActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailTaskActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void love() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.loveMe(uid,cour_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggDetailCourRes", "" + response.body());

                    JSONObject jsonRESULTS = null;
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        Boolean pesanError = jsonRESULTS.getBoolean("success");
                        if (pesanError.equals(true)) {
                            Toast.makeText(DetailTaskActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailTaskActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                   // Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailTaskActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailTaskActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

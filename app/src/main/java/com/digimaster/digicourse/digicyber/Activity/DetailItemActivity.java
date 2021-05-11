package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.digimaster.digicourse.digicyber.Adapter.ContentAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.MateriItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.digimaster.digicourse.digicyber.util.SdkConfig;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailItemActivity extends AppCompatActivity implements TransactionFinishedCallback {
    private FloatingActionButton playDemo;
    List<MateriItem> dataItemList = new ArrayList<>();
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    String passing, passingAuth, passingTit, passingImg, passingPrice, passType, passMail, passPhone, passFullname, passCek, vid, mtrl_id,
            passingDesc,
            uid,cour_id,
            vouc;
    ContentAdapter contentAdapter;
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
        setContentView(R.layout.activity_detail_item);

        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            //passing = pref.getString(PrefContract.id_detail, "");
            passingAuth = pref.getString(PrefContract.author, "");
            passingTit = pref.getString(PrefContract.title, "");
            //passingImg = pref.getString(PrefContract.img_url, "");
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

        rv_spec = findViewById(R.id.rv_spec);
        rv_spec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //BANGSAT HARUSNYA VERTICAL SI ASUUUUUUUU
        rv_spec.setLayoutManager(layoutManager);
        contentAdapter = new ContentAdapter(this, dataItemList, genProductAdapterListener());
        rv_spec.setAdapter(contentAdapter);
        contentAdapter.notifyDataSetChanged();

        ivHeader = findViewById(R.id.iv_header);
        price = findViewById(R.id.tvPriceAsli);


        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle(passingTit);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));

        loveBtn = findViewById(R.id.star_button);
        btnApply = findViewById(R.id.btnApply);
        //someTextView = findViewById(R.id.strike_text);
        //someTextView.setText("IDR 2.000.000.000");
        //someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        playDemo = findViewById(R.id.playDemo);
//        playDemo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    //put(PrefContract.material_id,mtrl_id);
//                    vid = pref.getString(PrefContract.vid_url_pre,"");
//                } catch (AppException e) {
//                    e.printStackTrace();
//                }
//
//                Intent intent = new Intent(DetailItemActivity.this, ShowPreview.class);
//                intent.putExtra("test2", vid);
//
//                DetailItemActivity.this.startActivity(intent);
//            }
//        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passCek.equals("false")){
                    Snackbar snackbar = Snackbar.make(rvDetailItem, "Sorry, please login first", Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(DetailItemActivity.this, R.color.red_alert));
                    snackbar.show();
                }else {
                        regis_onlineskuy("settlement");
                }
            }
        });

        loveBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //Toast.makeText(DetailItemActivity.this, "THANKS FOR LOVING ME", Toast.LENGTH_SHORT).show();
                love();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                //Toast.makeText(DetailItemActivity.this, "THANKS FOR UNLOVE ME", Toast.LENGTH_SHORT).show();
                unlove();
            }
        });

//        ivOucher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText editText = new EditText(DetailItemActivity.this);
//                editText.setText(vouc);
//
//                new SweetAlertDialog(DetailItemActivity.this, SweetAlertDialog.NORMAL_TYPE)
//                        .setTitleText("Silahkan masukkan kode voucher")
//                        .setConfirmText("Confirm")
//                        .setCancelText("Cancel")
//                        .setCustomView(editText)
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                               cek_voucher(editText.getText().toString());
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
//            }
//        });


        //loadDetail();
        initMidtransSdk();
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
                    //Log.d("logggDetailCourRes", "" + response.body());

                    JSONObject jsonRESULTS = null;
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        Boolean pesanError = jsonRESULTS.getBoolean("success");
                        if (pesanError.equals(true)) {
                            Toast.makeText(DetailItemActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailItemActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailItemActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailItemActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void love() {
        //buat api tentang love, bukan deng api buat ngambil status dan id buat set lovenya di detailitemact
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
                            Toast.makeText(DetailItemActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            loveBtn.setEnabled(true);
                        } else {
                            Toast.makeText(DetailItemActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                    //Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailItemActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailItemActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void loadDetail() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getResources().getString(R.string.base_url))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
//        mApiService.getOnlineDetail("online/get_detail_course/"+passing).enqueue(new Callback<DetailCourseResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<DetailCourseResponse> call, @NonNull Response<DetailCourseResponse> response) {
//                if (response.isSuccessful()) {
//
//
//                    Integer jsonRESULTS = response.body().getCode();
//                    String title2 = response.body().getTitle();
//                    String img2 = response.body().getTitle();
//
//
//                    Picasso.with(DetailItemActivity.this).load(getResources().getString(R.string.base_url_asset) +"image/carefast/" + img2).into(ivHeader);
//
//                    //Log.d("logggTitleNyaApa", "onResponse: "+title2);
//
//
//                    //Log.d("logggPrintArray", "onResponse: "+img2);
//
//
//                    try {
//
//                        pref.put(PrefContract.title,"");
//
//                    } catch (AppException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(jsonRESULTS.equals(200)) {
//
//                        btnApply.setVisibility(View.GONE);
//                        cv1.setVisibility(View.GONE);
//                        //cv2.setVisibility(View.GONE);
//                        cv3.setVisibility(View.GONE);
//                        cv4.setVisibility(View.GONE);
//                        cv5.setVisibility(View.GONE);
//                        lilaDet.setVisibility(View.GONE);
//                        nestedScrollViewAsli.setVisibility(View.GONE);
//                        cv69.setVisibility(View.VISIBLE);
//
//                    }else {
//                        String desc2 = response.body().getTitle();
//
//                        String author2 = response.body().getAuthor();
//
//                        //price.setText("IDR "+price2);
//
//                        //tvDesc.setText(desc2);
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            tvDesc.setText(Html.fromHtml(desc2, Html.FROM_HTML_MODE_COMPACT));
//                        }else{
//                            tvDesc.setText((Html.fromHtml(desc2)));
//
//                        }
//
//                        tv_author.setText("Created by "+author2);
//
//                        List<MateriItem> androidItems = response.body().getMateri();
//
//                        contentAdapter = new ContentAdapter(DetailItemActivity.this, androidItems, genProductAdapterListener());
//                        rv_spec.setAdapter(contentAdapter);
//
//
//                        contentAdapter.notifyDataSetChanged();
//
//                    }
//
//                    //Log.d("logggS", "response beres");
//                    loading.dismiss();
//                    loadlagi();
//                } else {
//                    loading.dismiss();
//                    Toast.makeText(DetailItemActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<DetailCourseResponse> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(DetailItemActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void loadlagi() {
        //buat api tentang love, bukan deng api buat ngambil status dan id buat set lovenya di detailitemact
        //loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getLoveStat(uid,cour_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {

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

                        loading.dismiss();
                        //Log.d("logggAPIBARU", ""+stat);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                    //Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(DetailItemActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailItemActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMidtransSdk() {
        String client_key = SdkConfig.MERCHANT_CLIENT_KEY;
        String base_url = SdkConfig.MERCHANT_BASE_CHECKOUT_URL;

        CreditCard creditCardOptions = new CreditCard();
        // Set to true if you want to save card to Snap
        creditCardOptions.setSaveCard(false);
        // Set to true to save card token as `one click` token
        creditCardOptions.setSecure(false);
        // Set bank name when using MIGS channel
        creditCardOptions.setBank(BankType.BCA);
        // Set MIGS channel (ONLY for BCA, BRI and Maybank Acquiring bank)
        creditCardOptions.setChannel(CreditCard.MIGS);
        // Set Credit Card Options
        initTransactionRequest().setCreditCard(creditCardOptions);
        // Set transaction request into SDK instance
        //#00ccbc
        UIKitCustomSetting setting = MidtransSDK.getInstance().getUIKitCustomSetting();
        setting.setSkipCustomerDetailsPages(true);
        MidtransSDK.getInstance().setUIKitCustomSetting(setting);

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(DetailItemActivity.this) // context is mandatory
                .setTransactionFinishedCallback(DetailItemActivity.this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url
                .enableLog(true) // enable sdk log
                .setUIkitCustomSetting(setting)
                .setColorTheme(new CustomColorTheme("#00ccbc", "#00ccbc", "#00ccbc")) // will replace theme on snap theme on MAP
                .buildSDK();

        //MidtransSDK.getInstance().startPaymentUiFlow(MidtransPaymentActivity.this, PaymentMethod.BANK_TRANSFER_BCA);
        //Log.d("logggInitMidtransSDK", "initMidtransSdk: harusnya beres");
    }

    private TransactionRequest initTransactionRequest() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.get("price") != null) {
                passingPrice = bundle.getString("price");
            }
        }
        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(String.valueOf(System.currentTimeMillis()), Double.parseDouble(passingPrice));

        //set customer details
        transactionRequestNew.setCustomerDetails(initCustomerDetails());


        // set item details
        ItemDetails itemDetails = new ItemDetails("1", Double.parseDouble(passingPrice), 1, passingTit);

        // Add item details into item detail list.
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);


        // Create creditcard options for payment
        CreditCard creditCard = new CreditCard();

        creditCard.setSaveCard(false); // when using one/two click set to true and if normal set to  false

//        this methode deprecated use setAuthentication instead
//        creditCard.setSecure(true); // when using one click must be true, for normal and two click (optional)

        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_3DS);

        // noted !! : channel migs is needed if bank type is BCA, BRI or MyBank
        creditCard.setChannel(CreditCard.MIGS); //set channel migs
        creditCard.setBank(BankType.BCA); //set spesific acquiring bank

        transactionRequestNew.setCreditCard(creditCard);

        return transactionRequestNew;
    }

    private CustomerDetails initCustomerDetails() {

        //define customer detail (mandatory for coreflow)
        CustomerDetails mCustomerDetails = new CustomerDetails();
        mCustomerDetails.setPhone(passPhone);
        mCustomerDetails.setFirstName(passFullname);
        mCustomerDetails.setEmail(passMail);
        return mCustomerDetails;
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            TransactionResponse a = result.getResponse();
            // Log.d("logggTransaction", "onTransactionFinished: "+a);
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    regis_onlineskuy(result.getResponse().getTransactionStatus());
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();

                    break;
                case TransactionResult.STATUS_PENDING:
                    regis_onlineskuy(result.getResponse().getTransactionStatus());
                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();

                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }

   /*
    }*/

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


        Log.d("ngews", " "+uid+" "+passFullname+" "+cour_id+" "+status+" "+passingPrice+" "+slagslug);
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

                                    pref.put(PrefContract.nama_rekening, nama_rek);
                                    pref.put(PrefContract.price, price);
                                    pref.put(PrefContract.status_pembayaran, stat);

                                    Toast.makeText(DetailItemActivity.this, "Terimakasih telah mendaftar. Silahkan menyelesaikan pembayaran" +
                                            "", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DetailItemActivity.this, SimpleTabsActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(DetailItemActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                                    //Log.d("logggGagal", "onResponse: Gagal");

                                }

                            } catch (JSONException | IOException | AppException e) {
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

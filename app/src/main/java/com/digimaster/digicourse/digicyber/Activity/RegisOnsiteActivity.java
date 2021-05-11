package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RegisOnsiteActivity extends AppCompatActivity implements TransactionFinishedCallback {
    TextView qty, qty_berubah, price_static, nama_course;
    Button btnnext, btnAdd, btnMin;
    TextInputEditText name, email, voc_code, name1, name2, name3, name4;
    RelativeLayout rv1;


    String error_all_field;
    Integer i =1;
    BaseApiService mApiService;
    ProgressDialog loading;
    SecuredPreference pref;
    String uid, passingPrice, cour_id, passMail, passPhone, passFullname, passingTit, passCek;
    Double hargaCourse;
    UIKitCustomSetting uiKitCustomSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_onsite);
        qty_berubah = findViewById(R.id.qty);
        qty = findViewById(R.id.tv_qty);
        btnnext =  findViewById(R.id.btn_next);
        rv1 = findViewById(R.id.rv_1_regis_onsite);
        name = findViewById(R.id.et_name);
        //email = findViewById(R.id.etMail);
        //voc_code = findViewById(R.id.etVcode);
        error_all_field = getResources().getString(R.string.error_field_kosong);
        btnAdd = findViewById(R.id.btnAddQty);
        btnMin = findViewById(R.id.btnMinQty);
        price_static = findViewById(R.id.tv_price_static);
        nama_course = findViewById(R.id.tv_nama_kursus_regis_onsite);
        name1 = findViewById(R.id.etNama_peserta_1);
        name2 = findViewById(R.id.etNama_peserta_2);
        name3 = findViewById(R.id.etNama_peserta_3);
        name4 = findViewById(R.id.etNama_peserta_4);


        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);


        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        //final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        try {
            uid = pref.getString(PrefContract.user_id, "");
            Log.d("logggUID", "onCreate: "+uid);
            passingPrice = pref.getString(PrefContract.price, "");
            cour_id = pref.getString(PrefContract.cour_id, "");
            passMail = pref.getString(PrefContract.email, "");
            passPhone = pref.getString(PrefContract.phone, "");
            passFullname = pref.getString(PrefContract.USER_FULL_NAME, "");
            passingTit = pref.getString(PrefContract.title,"");
            passCek = pref.getString(PrefContract.check_login, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Log.d("logggMail", "onCreate: "+passMail);
        Log.d("logggFullname", "onCreate: "+passFullname);


        hargaCourse = Double.valueOf(passingPrice);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        final DecimalFormat decimalFormat = new DecimalFormat("Rp ###,###,###,###", symbols);

        qty.setText(decimalFormat.format(Double.valueOf(passingPrice)));
        price_static.setText(decimalFormat.format(Double.valueOf(passingPrice)));
        nama_course.setText(passingTit);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                i = Integer.valueOf(qty_berubah.getText().toString());
                if(i<=3) {
                    i++;
                }else{
                    i = 4;
                }
                qty_berubah.setText(i.toString());
                qty.setText(String.valueOf(decimalFormat.format(i*hargaCourse)));
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                i = Integer.valueOf(qty_berubah.getText().toString());
                i--;
                if (i < 1){
                    i = 1;
                }
                qty_berubah.setText(i.toString());
                qty.setText(String.valueOf(decimalFormat.format(i*hargaCourse)));
            }
        });


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().matches("") || name1.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(rv1, ""+error_all_field, Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(RegisOnsiteActivity.this, R.color.red_alert));
                    snackbar.show();
                }
                else{
                        MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
                        MidtransSDK.getInstance().startPaymentUiFlow(RegisOnsiteActivity.this);
                        //regisskuy();
                }
            }
        });

        //initMidtransSdk();
    }

    private void regisskuy() {
//        String nama_penyamun;
//
//        if(name2.getText().toString().equals("")){
//            nama_penyamun = name1.getText().toString();
//        }else {
//
//            if(name3.getText().toString().equals("")){
//                nama_penyamun = ""+name1.getText().toString()+","+name2.getText().toString();
//           }
//           else{
//               if(name4.getText().toString().equals("")){
//                   nama_penyamun = ""+name1.getText().toString()+","+name2.getText().toString()+","+name3.getText().toString();
//               }
//               else{
//                   nama_penyamun = ""+name1.getText().toString()+","+name2.getText().toString()+","+name3.getText().toString()+","
//                           +name4.getText().toString();
//               }
//           }
//        }
//
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//
//        mApiService.requestRegisOnsite(""+uid, nama_penyamun, "DIGIAZIEG",
//                String.valueOf(i), cour_id, String.valueOf((i*hargaCourse)))
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()) {
//
//
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                //String pesan = jsonRESULTS.getString("success");
//                                String pesanError = jsonRESULTS.getString("message");
//                                if (pesanError.equals("Daftar berhasil")) {
//
//                                    Toast.makeText(RegisOnsiteActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
//                                    //PAYMENTTT
//                                    Intent intent = new Intent(RegisOnsiteActivity.this, SimpleTabsActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    Log.d("logggIntent", "Berhasil intent");
//                                    finish();
//                                } else {
//                                    loading.dismiss();
//                                    Toast.makeText(RegisOnsiteActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            loading.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
//                    }
//                });
//    }
//
//    private void initMidtransSdk() {
//        String client_key = SdkConfig.MERCHANT_CLIENT_KEY;
//        String base_url = SdkConfig.MERCHANT_BASE_CHECKOUT_URL;
//
//        CreditCard creditCardOptions = new CreditCard();
//        // Set to true if you want to save card to Snap
//        creditCardOptions.setSaveCard(false);
//        // Set to true to save card token as `one click` token
//        creditCardOptions.setSecure(false);
//        // Set bank name when using MIGS channel
//        creditCardOptions.setBank(BankType.BCA);
//        // Set MIGS channel (ONLY for BCA, BRI and Maybank Acquiring bank)
//        creditCardOptions.setChannel(CreditCard.MIGS);
//        // Set Credit Card Options
//        initTransactionRequest().setCreditCard(creditCardOptions);
//        // Set transaction request into SDK instance
//        //#00ccbc
////        UIKitCustomSetting uisetting = new UIKitCustomSetting();
////        uisetting.setShowPaymentStatus(true);
////        uisetting.setSkipCustomerDetailsPages(true);
////        uisetting.setEnabledAnimation(true);
//        UIKitCustomSetting setting = MidtransSDK.getInstance().getUIKitCustomSetting();
//        setting.setSkipCustomerDetailsPages(true);
//        MidtransSDK.getInstance().setUIKitCustomSetting(setting);
//
//        SdkUIFlowBuilder.init()
//                .setClientKey(client_key) // client_key is mandatory
//                .setContext(RegisOnsiteActivity.this) // context is mandatory
//                .setTransactionFinishedCallback(RegisOnsiteActivity.this) // set transaction finish callback (sdk callback)
//                .setMerchantBaseUrl(base_url) //set merchant url
//                .enableLog(true) // enable sdk log
//                .setUIkitCustomSetting(setting)
//                .setColorTheme(new CustomColorTheme("#00ccbc", "#00ccbc", "#00ccbc")) // will replace theme on snap theme on MAP
//                .buildSDK();
//
//        //MidtransSDK.getInstance().startPaymentUiFlow(MidtransPaymentActivity.this, PaymentMethod.BANK_TRANSFER_BCA);
//        Log.d("logggInitMidtransSDK", "initMidtransSdk: harusnya beres");
    }

    private TransactionRequest initTransactionRequest() {
        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(System.currentTimeMillis() + "", Double.parseDouble(passingPrice));

        //set customer details
        transactionRequestNew.setCustomerDetails(initCustomerDetails());


        // set item details
        ItemDetails itemDetails = new ItemDetails(cour_id, Double.parseDouble(passingPrice), i,
                passingTit);

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
        //Log.d("logggCustomerDtls", "initCustomerDetails: "+passMail);
        return mCustomerDetails;
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            TransactionResponse a = result.getResponse();
            Log.d("logggTransaction", "onTransactionFinished: "+a);
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    regisskuy();
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();

                    break;
                case TransactionResult.STATUS_PENDING:
                    regisskuy();
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


}

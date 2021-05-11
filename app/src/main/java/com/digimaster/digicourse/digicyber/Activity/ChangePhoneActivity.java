package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digimaster.digicourse.digicyber.Contract.PrefContract.email;
import static com.digimaster.digicourse.digicyber.Contract.PrefContract.user_id;

public class ChangePhoneActivity extends AppCompatActivity {
CountryCodePicker ccpbaru;
TextView phone1;
    SecuredPreference pref;
    Button save;
    Toolbar toolbar;
    ProgressDialog loading;
    BaseApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Change Phone Number");

        pref = new SecuredPreference(ChangePhoneActivity.this, PrefContract.PREF_EL);





ccpbaru = findViewById(R.id.ccpbaru);
        phone1= findViewById(R.id.etphonebaru1);
ccpbaru.registerPhoneNumberTextView(phone1);
        save= findViewById(R.id.savegantiphone);

        String passing_firstname;
        mApiService = UtilsApi.getAPIService();


        phone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ccpbaru.getSelectedCountryNameCode().equals("ID")) {
                    if (s.toString().length() == 1) {
                        if (s.toString().startsWith("0")) {
                            s.clear();
                        }
                    } else {
                        if (s.toString().startsWith("0")) {
                            s.clear();
                            Toast.makeText(ChangePhoneActivity.this, "0 is not required", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        try {
            passing_firstname = pref.getString(user_id, "");

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ccpbaru.isValid()) {


                    /////bawah ini apus
                    mApiService.changePhone(passing_firstname, ccpbaru.getFullNumber())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(ChangePhoneActivity.this, "Nomor Berhasil diganti", Toast.LENGTH_SHORT).show();
                                        pref.clear();
                                        try {

                                            pref.put(PrefContract.check_login, "false");
                                            Log.d("logggOut", "berhasil logout");
                                            startActivity(new Intent(ChangePhoneActivity.this, LoginActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            //sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);

                                            finish();
                                            logout();
                                        } catch (AppException e) {
                                            e.printStackTrace();
                                        }





                                    } else {
                                        Log.d("logggOut", "gagal");
                                    }
                                }@Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("debug", "onFailure: ERROR > " + t.toString());

                                }
                            });



                }
                    else {
                        Toast.makeText(ChangePhoneActivity.this, "Format nomor salah.", Toast.LENGTH_SHORT).show();
                    }

                }
            });






        } catch (AppException e) {

        }
















    }


    private void logout() {


        mApiService.requestLogout(email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            JSONObject jsonRESULTS = null;
                            try {
                                jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("successfuly logout")) {

                                    pref.clear();
                                    pref.put(PrefContract.check_login, "false");

                                    String success_message = jsonRESULTS.getString("message");

                                    Toast.makeText(ChangePhoneActivity.this, success_message, Toast.LENGTH_LONG).show();

                                    //Log.d("logggOut", "berhasil logout");
                                    startActivity(new Intent(ChangePhoneActivity.this, LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    ChangePhoneActivity.this.finish();


                                } else {

                                    Toast.makeText(ChangePhoneActivity.this, "Gagal Logout", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(ChangePhoneActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

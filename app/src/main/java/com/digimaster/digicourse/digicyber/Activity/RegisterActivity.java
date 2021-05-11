package com.digimaster.digicourse.digicyber.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText fName, lName, email, password, alias, phone, address, school;
    Button btnRegis;
    ToggleSwitch genderSwitch;
    Spinner spin;
    SecuredPreference pref;
    String uid;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);


        fName = findViewById(R.id.etFname);
        lName = findViewById(R.id.etLname);

        phone = findViewById(R.id.etPhone);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        alias = findViewById(R.id.etAlias);


        btnRegis = findViewById(R.id.btnRegisRegis);
        ccp = findViewById(R.id.ccp);

        ccp.registerPhoneNumberTextView(phone);

        phone.setHint("input phone number");


        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ccp.getSelectedCountryNameCode().equals("ID")) {
                    if (s.toString().length() == 1) {
                        if (s.toString().startsWith("0")) {
                            s.clear();
                        }
                    } else {
                        if (s.toString().startsWith("0")) {
                            s.clear();
                            Toast.makeText(RegisterActivity.this, "0 is not required", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ccp.isValid()) {
                    if (isEmailValid(email.getText().toString())) {
                        register();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Wrong e-mail format.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Format nomor salah.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void register() {
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);


        try {
            uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }


        mApiService.registerRequest(ccp.getFullNumber(), fName.getText().toString(), lName.getText().toString()
                , alias.getText().toString(), password.getText().toString(), email.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                if (pesanError.equals("200")) {

                                    Toast.makeText(RegisterActivity.this,
                                            "Register Success. Please check your e-mail for confirmation.",
                                            Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();

                                    // Log.d("logggIntent", "Berhasil put");
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Something happened.",
                                            Toast.LENGTH_SHORT).show();
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
//        }else{
//            loading.dismiss();
//            Toast.makeText(getActivity(), "Please fill the right email", Toast.LENGTH_SHORT).show();
//        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

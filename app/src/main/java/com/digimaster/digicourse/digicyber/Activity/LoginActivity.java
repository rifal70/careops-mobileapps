package com.digimaster.digicourse.digicyber.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.FeaturedFragment;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    FragmentActivity mContext;
    BaseApiService mApiService;
    ProgressDialog loading;
    SecuredPreference pref;
    EditText phonenumber, pass;
    TextView forgetpass;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        mApiService = UtilsApi.getAPIService();


        phonenumber = findViewById(R.id.etPhone);
        pass = findViewById(R.id.etAddress);
        forgetpass = findViewById(R.id.tvForgetPass);
        ccp = findViewById(R.id.ccp);
        ccp.registerPhoneNumberTextView(phonenumber);

        phonenumber.setHint("input phone number");

        phonenumber.addTextChangedListener(new TextWatcher() {
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
                            Toast.makeText(LoginActivity.this, "0 is not required", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        Button button = findViewById(R.id.btnLogin);
//        Button btnLogin2 = findViewById(R.id.btnLogin2);

        TextView txtRegister = findViewById(R.id.txtRegister);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ccp.isValid()) {
                    loading = ProgressDialog.show(LoginActivity.this, null, "Please Wait...", true, false);
                    login();
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();

                }
            }
        });

//        btnLogin2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                loading = ProgressDialog.show(LoginActivity.this, null, "Harap Tunggu...", true, false);
//                login_admin();
//            }
//        });


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void login (){

        //Log.d("logggIntent", "Berhasil method login");
        mApiService.loginRequest(ccp.getFullNumber(), pass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Logged In Successfully")) {
                                    JSONArray userId = jsonRESULTS.getJSONArray("user");
                                    JSONObject obj = userId.getJSONObject(0);
                                    //passingTRYJUARDI

                                    String fullname = obj.getString("first_name");

                                    String email = obj.getString("email");
                                    String id = obj.getString("id");
                                    String phone = obj.getString("phone");
                                    String photo = obj.getString("photo");
                                    String first_name = obj.getString("first_name");
                                    String last_name = obj.getString("last_name");
                                    String institution = obj.getString("institution");
                                    String position = obj.getString("position");
                                    String nickname = obj.getString("nickname");


                                    //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
                                    //Log.d("logggIntent", "Berhasil abis Toast");
                                    pref.put(PrefContract.USER_FULL_NAME, fullname);

                                    pref.put(PrefContract.email, email);
                                    pref.put(PrefContract.phone, phone);
                                    pref.put(PrefContract.photo, photo);
                                    pref.put(PrefContract.check_login, "true");
                                    pref.put(PrefContract.user_id, id);
//                                    pref.put(PrefContract.role, level);
                                    pref.put(PrefContract.first_name,first_name);
                                    pref.put(PrefContract.last_name,last_name);
                                    pref.put(PrefContract.username, nickname);
                                    pref.put(PrefContract.institution,institution);
                                    pref.put(PrefContract.position,position);

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //Log.d("logggIntent", "Berhasil put");
                                    new FeaturedFragment();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | AppException | IOException e) {
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


}

package com.digimaster.digicourse.digicyber.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPassActivity extends AppCompatActivity {
    Button btnForgetPass;
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget_pass);

        mApiService = UtilsApi.getAPIService();

        email = findViewById(R.id.etPhone);
        btnForgetPass = findViewById(R.id.btnForgetPass);

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void register () {



        mApiService.forgetPass(email.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ForgetPassActivity.this, "Email Sudah Terkirim", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

//                                String pesanError = jsonRESULTS.getString("status");
//                                    if (pesanError.equals("success")) {
////                                    String userId = jsonRESULTS.getJSONObject("data").getString("user_id");
////                                    String token = jsonRESULTS.getString("token");
//
//                                        //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
//                                        //Log.d("logggIntent", "Berhasil abis Toast");
////                                    pref.put(PrefContract.PREF_TOKEN, token);
////                                    pref.put(PrefContract.user_id, userId);
////                                    pref.put(PrefContract.check_login, "true");

                                Toast.makeText(ForgetPassActivity.this,response.body().string(), Toast.LENGTH_SHORT).show();

//                                        Intent intent = new Intent(ForgetPassActivity.this, HomeActivity.class);
//                                        startActivity(intent);
//                                        finish();


                                //finish();
//                                    } else {
//                                        Toast.makeText(ForgetPassActivity.this, "Reset password gagal.", Toast.LENGTH_SHORT).show();
//                                    }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
//                                displayExceptionMessage(e.getMessage());
//                                Toast.makeText(ForgetPassActivity.this, "cek kembali email anda", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ForgetPassActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());

                    }
                });




    }
    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
//    @SuppressLint("StaticFieldLeak")
//    public void register () {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://digicourse.id/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        BaseApiService service = retrofit.create(BaseApiService.class);
//
//        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
//
//
//        if(isValidEmail(email.getText().toString())) {
//            service.forgetPass("auth/reset_password/"+email.getText().toString())
//                    .enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if (response.isSuccessful()) {
//                                loading.dismiss();
//
//                                try {
//                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//
//                                    String pesanError = jsonRESULTS.getString("status");
//                                    if (pesanError.equals("success")) {
////                                    String userId = jsonRESULTS.getJSONObject("data").getString("user_id");
////                                    String token = jsonRESULTS.getString("token");
//
//                                        //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
//                                        //Log.d("logggIntent", "Berhasil abis Toast");
////                                    pref.put(PrefContract.PREF_TOKEN, token);
////                                    pref.put(PrefContract.user_id, userId);
////                                    pref.put(PrefContract.check_login, "true");
//
//                                        Toast.makeText(ForgetPassActivity.this, "E-mail reset telah dikirim!", Toast.LENGTH_SHORT).show();
//
//                                        Intent intent = new Intent(ForgetPassActivity.this, HomeActivity.class);
//                                        startActivity(intent);
//                                        finish();
//
//
//                                        //finish();
//                                    } else {
//                                        Toast.makeText(ForgetPassActivity.this, "Reset password gagal.", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } catch (JSONException | IOException e) {
//                                    e.printStackTrace();
//                                }
//                                //if (jsonRESULTS.getString("message").equals("Successfully login.")) {
//                                // Jika login berhasil maka data nama yang ada di response API
//                                // akan diparsing ke activity selanjutnya.
//
//                                            /*} else {
//                                                // Jika login gagal
//                                                String error = jsonRESULTS.getString("message");
//                                                //loading.dismiss();
//                                                Toast.makeText(mContext, "USERNAME / PASSWORD SALAH", Toast.LENGTH_LONG).show();
//                                            }*/
//                            } else {
//                                loading.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Log.e("debug", "onFailure: ERROR > " + t.toString());
//                            loading.dismiss();
//                        }
//                    });
//        }else{
//            loading.dismiss();
//            Toast.makeText(ForgetPassActivity.this, "Please fill the right email", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}

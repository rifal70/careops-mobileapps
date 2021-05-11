package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digimaster.digicourse.digicyber.Contract.PrefContract.email;
import static com.digimaster.digicourse.digicyber.Contract.PrefContract.user_id;

public class ChangePasswordActivity extends AppCompatActivity {
    SecuredPreference pref;
    Button save;
    Toolbar toolbar;
    BaseApiService mApiService;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_change_password);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Change Password");

        pref = new SecuredPreference(ChangePasswordActivity.this, PrefContract.PREF_EL);
        save= findViewById(R.id.savegantipassword);
        EditText pass1 = findViewById(R.id.etpasswordbaru1);
        EditText pass2 = findViewById(R.id.etpasswordbaru2);
String passing_firstname;
        mApiService = UtilsApi.getAPIService();
        try {
            passing_firstname = pref.getString(user_id, "");

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(pass1.getText().toString().length()>7){

                        if (pass1.getText().toString().equals(pass2.getText().toString())){

                            /////bawah ini apus
                            mApiService.changePass(passing_firstname, pass1.getText().toString())
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(ChangePasswordActivity.this, "Password Berhasil diganti", Toast.LENGTH_SHORT).show();

                                               logout();

                                        } else {
                                                Log.d("logggOut", "gagal");
                                        }
                                        }@Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.e("debug", "onFailure: ERROR > " + t.toString());

                                        }
                                    });
                            ///atasini apus
                        }
                        else{
                            Toast.makeText(ChangePasswordActivity.this, "Confirmasi Password tidak tepat", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this,"Password Minimal 8 Character", Toast.LENGTH_SHORT).show();}
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

                                    Toast.makeText(ChangePasswordActivity.this, success_message, Toast.LENGTH_LONG).show();

                                    //Log.d("logggOut", "berhasil logout");
                                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    ChangePasswordActivity.this.finish();


                                } else {

                                    Toast.makeText(ChangePasswordActivity.this, "Gagal Logout", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(ChangePasswordActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}

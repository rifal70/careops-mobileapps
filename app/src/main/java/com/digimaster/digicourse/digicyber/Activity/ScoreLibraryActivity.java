package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ScoreLibraryActivity extends AppCompatActivity {
    Button btnBacktoCou;
    SecuredPreference pref;
    String skor, uid, material_id, cour_id, name, waktu_awal, waktu_akhir, cour_name;
    TextView tvSkor, tvSucc, tvTap;
    BaseApiService mApiService;
    ProgressDialog loading;
    ImageView ivChest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        //loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("mtrl_id")!= null)
        {
            material_id  = bundle.getString("mtrl_id");
            waktu_akhir = bundle.getString("waktu_akhir");
        }

        try {
            skor = pref.getString(PrefContract.score, "");
            uid = pref.getString(PrefContract.user_id,"");

            cour_id = pref.getString(PrefContract.cour_id,"");
            cour_name = pref.getString(PrefContract.cour_name,"");
            name = pref.getString(PrefContract.USER_FULL_NAME,"");
            waktu_awal = pref.getString(PrefContract.waktu_mulai,"");
//            Log.d("logggMaterId", "Material id: "+material_id);
//            Log.d("logggUid", "User id: "+uid);
//            Log.d("logggSkor", "Skor id: "+skor);
        } catch (AppException e) {
            e.printStackTrace();
        }

        ivChest = findViewById(R.id.iVchest);
        tvSkor = findViewById(R.id.tvSkor);
        tvSucc = findViewById(R.id.tvSucc);
        tvSkor.setText(skor);
        tvSkor.setVisibility(View.INVISIBLE);

        btnBacktoCou = findViewById(R.id.btnBack2Cou);

        btnBacktoCou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreLibraryActivity.this, DetailLibraryBaru.class)
                        .setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        //final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        ivChest = findViewById(R.id.iVchest);
        //ivChest.startAnimation(animShake);


    }

    private void submit_task(String submission, String task_id, String task_name) {

        try {
            uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loading = ProgressDialog.show(ScoreLibraryActivity.this, null, "Harap Tunggu...", true, false);

        mApiService.submitTask(uid,task_id, material_id, task_name, submission)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Submit feedback berhasil")) {
                                    loading.dismiss();

                                    Toast.makeText(ScoreLibraryActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();

                                    //PAYMENTTT
//                                    Intent intent = new Intent(ScoreActivity.this, HomeActivity.class);
//                                    startActivity(intent);

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(ScoreLibraryActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
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
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }


    private void submitSkor() {
//        Log.d("logggIntent", "Berhasil metho" +
//                "'" +
//                "d login");
        mApiService.submitScore(uid, material_id, cour_id, skor, waktu_awal, waktu_akhir,"1")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Integer pesanError = jsonRESULTS.getInt("code");
                                if (pesanError.equals(100)) {
//                                    GABISA NGISI 2X\

                                    pref.put(PrefContract.score_cp,0);
                                    pref.put(PrefContract.score,0);

                                    //ambil progressnya udah ngerjain berapa course dari berapa

//                                    String fullname = obj.getString("fullname");
//                                    String email = obj.getString("email");

//
//                                    //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
//                                    Log.d("logggIntent", "Berhasil abis Toast");
//                                    pref.put(PrefContract.USER_FULL_NAME, fullname);
//                                    pref.put(PrefContract.email, email);
//                                    pref.put(PrefContract.check_login, "true");
//                                    Toast.makeText(ScoreActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(ScoreActivity.this, CourseActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                }
                                else if (pesanError.equals(101)){
                                    pref.put(PrefContract.score_cp,0);
                                    pref.put(PrefContract.score,0);
                                    submitCerti();
                                }
                                else {
                                    Toast.makeText(ScoreLibraryActivity.this, "Failed to save score", Toast.LENGTH_SHORT).show();
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

    private void submitCerti() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        mApiService.submitCert(uid, name, cour_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Integer pesanError = jsonRESULTS.getInt("code");
                                if (pesanError.equals(100)) {
//
                                    tvSucc.setVisibility(View.VISIBLE);
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                }else {
//                                    Toast.makeText(ScoreActivity.this, "Failed to generate certificate",
//                                            Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            loading.dismiss();
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
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
//        Intent intent = new  Intent(ScoreActivity.this, eAssessActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();

        super.onBackPressed();
    }
}

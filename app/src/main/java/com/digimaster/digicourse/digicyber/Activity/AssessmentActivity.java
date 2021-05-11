package com.digimaster.digicourse.digicyber.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.QuizActionAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem;
import com.digimaster.digicourse.digicyber.Model.QuizResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.paginate.Paginate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class AssessmentActivity extends AppCompatActivity implements Paginate.Callbacks {
    private static final int FILE_SELECT_CODE = 0;

    SimpleExoPlayer player;
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    QuizActionAdapter quizAdapter;
    private RecyclerView rvAssess;
    String mtrl_id, uid, cour_id, sakral, lastSco, quiz_topic_id, quiz_action_id;
    RelativeLayout rlAss;
    Toolbar toolbar;
    File file;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (player == null || player.equals(null)){
                player.stop();
                finish();
            }else{
                player.stop();
                finish();
            }
            return true;
        }
//        player.stop();
//        finish();
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            uid = pref.getString(PrefContract.user_id, "");
            cour_id = pref.getString(PrefContract.cour_id, "");


        } catch (AppException e) {
            e.printStackTrace();
        }

//        toolbar = findViewById(R.id.detail_toolbar_kosong);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//        toolbar.setTitle("Content");


        Button backButton = (Button)this.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == null || player.equals(null)){
                    player.stop();
                    finish();
                }else{
                    player.stop();
                    finish();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("quiz_topic_id") != null || bundle.getString("sakral") != null) {
                quiz_topic_id = bundle.getString("quiz_topic_id");
                quiz_action_id = bundle.getString("quiz_action_id");
                sakral = bundle.getString("sakral");

                //Log.d("logggAssessSakral", "sakralAssess : "+sakral);


                try {
                    pref.put(PrefContract.sakral, sakral);
                } catch (AppException e) {
                    e.printStackTrace();
                }

            }
        }

        //Log.d("MTRL_ID_FUCK", ""+mtrl_id);

        rlAss = findViewById(R.id.rlAssAct);
        rvAssess = findViewById(R.id.rvAssess);
        rvAssess.setHasFixedSize(true);
        rvAssess.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        //rvAssess.scrollToPosition(0);

        loadSoalSkuy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(AssessmentActivity.this, "Result OK", Toast.LENGTH_SHORT).show();
                // Get the Uri of the selected file
                Uri uri = data.getData();

                Log.d("logggFileAwal", "File Uri: " + uri.toString());
                // Get the path
                String path = uri.getPath();
//                try {
//                    path = FileUtils.getPath(this, uri);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }

                Log.d("logggPath", "Path : " + path);


                Toast.makeText(AssessmentActivity.this, "" + path, Toast.LENGTH_SHORT).show();
                //Log.d("logggFile", "File Path: " + path);
                // Get the file instance
                if (path != null) {
                    file = new File(path);
                }

                Log.d("logggFile", "File Uri: " + file);

                add(file, path, uri);

                // Initiate the upload
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("logggFile", "File Uri failed: " + file);
                Toast.makeText(this, "Select file failed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    @SuppressLint("StaticFieldLeak")
    public void add(File file, String path, Uri uri) {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);


        RequestBody reqFile = RequestBody.create(path, MediaType.parse("text/plain"))   ;

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

//        Log.d("logggReqBody", "reqfile: " + fileExt);

        Log.d("logggBody", "add: " + body);

        RequestBody uidM = null;
        try {
            uidM = RequestBody.create(pref.getString(PrefContract.user_id, ""), MediaType.parse("multipart/form-data"));
        } catch (AppException e) {
            e.printStackTrace();
        }

        mApiService.submitFileActivity(uidM, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                String pesan = jsonRESULTS.getString("message");
                                if (pesanError.equals("200")) {


                                    Toast.makeText(AssessmentActivity.this, "Upload file success.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AssessmentActivity.this, "" + pesan, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                            Toast.makeText(AssessmentActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR FUNCTION ADD > " + t.toString());
                        loading.dismiss();
                    }
                });
        //        }else{
//            loading.dismiss();
//            Toast.makeText(getActivity(), "Please fill the right email", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }


    private void loadSoalSkuy() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getAllQuiz(quiz_topic_id, quiz_action_id).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggDetailCourRes", "" + response.body());

                    List<AssessmentQuizItem> androidItems = response.body().getAssessmentQuiz();

                    if (androidItems.size() <= 0) {
                        loading.dismiss();

                        Toast.makeText(AssessmentActivity.this, "No quiz available yet.", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(AssessmentActivity.this, DetailModuleActivity.class)
                                        .setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    } else {
                        quizAdapter = new QuizActionAdapter(AssessmentActivity.this, androidItems, genProductAdapterListener());
                        rvAssess.setAdapter(quizAdapter);

                        //setupPagination();
                        quizAdapter.notifyDataSetChanged();

                        loading.dismiss();
                        //Log.d("logggS", "response beres");
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(AssessmentActivity.this, "Error 404", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AssessmentActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_assessment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_bar, menu);
        //MenuItem item = menu.findItem(R.id.star_button);

        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }


    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

    }


}

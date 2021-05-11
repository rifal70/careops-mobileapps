package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.IndoAdapter;
import com.digimaster.digicourse.digicyber.Adapter.InterAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.IndoResponse;
import com.digimaster.digicourse.digicyber.Model.InterResponse;
import com.digimaster.digicourse.digicyber.Model.KurikulumIndoItem;
import com.digimaster.digicourse.digicyber.Model.KurikulumInterItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.nex3z.togglebuttongroup.button.CircularToggle;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrDetailActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    String[] sCheeseStrings;
    ProgressDialog loading;
    BaseApiService mApiService;
    SecuredPreference pref;
    InterAdapter interAdapter;
    IndoAdapter indoAdapter;
    String tipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar_app_bar_atasnya_doank);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //toolbar.setTitle("Choose grade and subject");

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        mRecyclerView = findViewById(R.id.rv_curr_detail);

        //sCheeseStrings = new String[]{"guru 1", "guru 2", "guru 3","guru 4","guru 5","guru 6 seksyen lain"};

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        mAdapter = new SubjectAdapter(this,sCheeseStrings);
//        mRecyclerView.setAdapter(mAdapter);


        /*final SingleSelectToggleGroup single = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                Toast.makeText(CurrDetailActivity.this, "Hari "+single.getTag(), Toast.LENGTH_SHORT).show();

                loadDetail();
            }

        });*/

        final CircularToggle satu = findViewById(R.id.choice_1);
        final CircularToggle dua = findViewById(R.id.choice_2);
        final CircularToggle tiga = findViewById(R.id.choice_3);
        final CircularToggle empat = findViewById(R.id.choice_4);
        final CircularToggle lima = findViewById(R.id.choice_5);
        final CircularToggle enam = findViewById(R.id.choice_6);

        /*label buat yang multi ex : math,scie, soci*/
        final LabelToggle math = findViewById(R.id.lt_math);
        final LabelToggle scie = findViewById(R.id.lt_scie);
        final LabelToggle soci = findViewById(R.id.lt_soci);

        try {
            tipe = pref.getString(PrefContract.course_cat,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


        if(tipe.equals("5")) {
            math.setText("Matematika");
            scie.setText("IPA");
            soci.setText("IPS");

            satu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","1");
                }
            });
            dua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","2");
                }
            });
            tiga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","3");
                }
            });
            empat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","4");
                }
            });
            lima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","5");
                }
            });
            enam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetail("","6");
                }
            });
        }else{
            math.setText("Maths");
            scie.setText("Science");
            soci.setText("Social Sciencce");

            satu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","1");
                }
            });
            dua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","2");
                }
            });
            tiga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","3");
                }
            });
            empat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","4");
                }
            });
            lima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","5");
                }
            });
            enam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDetailInter("","6");
                }
            });
        }



/*
        label toogle click listerner
*/
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(math.isChecked()){
                        String mathS = math.getText().toString();
                        //Log.d("loggggToogleClick", "onClick: " + mathS);
                    if(tipe.equals("5")) {
                        loadDetail(mathS,"");
                    }
                    else if(tipe.equals("6")){
                        loadDetailInter(mathS,"");
                    }

                }

            }
        });

        scie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scie.isChecked()){
                    String scieS = scie.getText().toString();
                    //Log.d("loggggToogleClick", "onClick: "+scieS);
                    if(tipe.equals("5")) {
                        loadDetail(scieS,"");
                    }
                    else if(tipe.equals("6")){
                        loadDetailInter(scieS,"");
                    }
                }
            }
        });

        soci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soci.isChecked()){
                    String sociS = soci.getText().toString();
                    //Log.d("loggggToogleClick", "onClick: "+sociS);
                    if(tipe.equals("5")) {
                        loadDetail(sociS,"");
                    }
                    else if(tipe.equals("6")){
                        loadDetailInter(sociS,"");
                    }
                }
            }
        });




        if(tipe.equals("5")){
            loadDetail("","");
        }
        else if (tipe.equals("6")){
            loadDetailInter("","");
        }
        else{
            Toast.makeText(this, "Something went wrong :/", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadDetail(String filter, String kelas) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getIndoCour(filter,kelas).enqueue(new Callback<IndoResponse>() {
            @Override
            public void onResponse(@NonNull Call<IndoResponse> call, @NonNull Response<IndoResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggDetailCourRes", "" + response.body());

                    List<KurikulumIndoItem> androidItems = response.body().getKurikulumIndo();

                    indoAdapter = new IndoAdapter(CurrDetailActivity.this, androidItems, genProductAdapterListener());
                    mRecyclerView.setAdapter(indoAdapter);


                    indoAdapter.notifyDataSetChanged();

                    //Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(CurrDetailActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<IndoResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CurrDetailActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDetailInter(String filter, String kelas) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getInterCour(filter,kelas).enqueue(new Callback<InterResponse>() {
            @Override
            public void onResponse(@NonNull Call<InterResponse> call, @NonNull Response<InterResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggDetailCourRes", "" + response.body());

                    List<KurikulumInterItem> androidItems = response.body().getKurikulumInter();

                    interAdapter = new InterAdapter(CurrDetailActivity.this, androidItems, genProductAdapterListener());
                    mRecyclerView.setAdapter(interAdapter);


                    interAdapter.notifyDataSetChanged();

                    //Log.d("logggS", "response beres");
                } else {
                    loading.dismiss();
                    Toast.makeText(CurrDetailActivity.this, "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<InterResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CurrDetailActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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

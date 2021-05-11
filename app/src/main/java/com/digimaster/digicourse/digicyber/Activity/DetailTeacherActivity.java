package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.SchedulerAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.JsonMemberPackageItem;
import com.digimaster.digicourse.digicyber.Model.PackageResponse;
import com.digimaster.digicourse.digicyber.Model.ScheduleItem;
import com.digimaster.digicourse.digicyber.Model.ScheduleResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTeacherActivity extends AppCompatActivity {

    private String nama, bidang, harga, teach_id, uid, schedule_id, area, telp;
    TextView tvNama,tvBidang,tvHarga,tvArea, tvTelp;
    Button btnRequest;
    BaseApiService mApiService;
    ProgressDialog loading;
    List<ScheduleItem> scheduleItemList;
    Spinner spinner, spinnerTime;
    SchedulerAdapter schedulerAdapter;
    RecyclerView rvSpecial;
    SecuredPreference pref;
    ArrayAdapter<String> adapter, adapterTime;
    String dayOfTheWeek, dayDateEnd, scheduleTime, scheduleDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_teacher);

        Toolbar toolbar = findViewById(R.id.detail_toolbar_app_bar_atasnya_doank);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Detail Teacher");

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        tvNama = findViewById(R.id.tvNamaTeacher);
        tvBidang = findViewById(R.id.tvTeachAbility);
        tvHarga = findViewById(R.id.tvPriceTeach);
        tvArea = findViewById(R.id.tvTeachArea);
        tvTelp = findViewById(R.id.tvTelpTeach);

        btnRequest = findViewById(R.id.btnRequestPrivate);
        rvSpecial = findViewById(R.id.rvSpecialBtmShid);

        rvSpecial.setHasFixedSize(true);
        rvSpecial.setLayoutManager(new GridLayoutManager(this,2));
        rvSpecial.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        spinner = findViewById(R.id.spinndaSchedule);

        if(getIntent().getStringExtra("teach_name") != null) {
            nama = getIntent().getStringExtra("teach_name");
            tvNama.setText(nama);

        }

        if(getIntent().getStringExtra("teach_bidang") != null) {
            bidang = getIntent().getStringExtra("teach_bidang");
            tvBidang.setText(bidang);
        }

        if(getIntent().getStringExtra("teach_price") != null) {
            harga = getIntent().getStringExtra("teach_price");

            tvHarga.setText("Rp "+harga);

        }

        if(getIntent().getStringExtra("teach_id") != null) {
            teach_id = getIntent().getStringExtra("teach_id");

        }

        if(getIntent().getStringExtra("teach_address") != null) {
            area = getIntent().getStringExtra("teach_address");

            tvArea.setText(area);

        }

        if(getIntent().getStringExtra("teach_phone") != null) {
            telp = getIntent().getStringExtra("teach_phone");

            tvTelp.setText(telp);

        }

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // set the peek height
        bottomSheetBehavior.setPeekHeight(160);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //getSchedule();
        getPackage();

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPrivate();
            }
        });

}

    private void getSchedule() {
        mApiService.getTeacherSchedule(""+getIntent().getStringExtra("teach_id")).enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    final List<ScheduleItem> scheduleItemList = response.body().getSchedule();

                    List<String> listSpinner = new ArrayList<>();
                    for (int i = 0; i < scheduleItemList.size(); i++){

                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

                        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                        try {
                            Date date = format.parse(""+scheduleItemList.get(i).getStartDate());
                            Date dateEnd = format.parse(""+scheduleItemList.get(i).getEndDate());

                            dayOfTheWeek = sdf.format(date);
                            dayDateEnd = sdf.format(dateEnd);


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        listSpinner.add(dayOfTheWeek+" "+scheduleItemList.get(i).getStartDate()+" - "+dayDateEnd+" "+scheduleItemList.get(i).getEndDate()+" Time : "
                                +scheduleItemList.get(i).getStartTime()+" - "+scheduleItemList.get(i).getEndTime());
                        adapter = new ArrayAdapter<>(DetailTeacherActivity.this,
                                R.layout.layout_teke, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                try {
                                    pref.put(PrefContract.schedule_id,""+scheduleItemList.get(i).getScheduleId());
                                    pref.put(PrefContract.schedule_date,""+dayOfTheWeek+" "+scheduleItemList.get(i).getStartDate()+" - "+dayDateEnd+" "+scheduleItemList.get(i).getEndDate());
                                    pref.put(PrefContract.schedule_time,""+scheduleItemList.get(i).getStartTime()+" - "+scheduleItemList.get(i).getEndTime());

                                } catch (AppException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }



                } else {


                }
            }


            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Toast.makeText(DetailTeacherActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPackage() {
        mApiService.getPackage(""+getIntent().getStringExtra("teach_id")).enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(@NonNull Call<PackageResponse> call, @NonNull Response<PackageResponse> response) {
                if (response.isSuccessful()) {

                    final List<JsonMemberPackageItem> jsonMemberPackageItemList = response.body().getJsonMemberPackage();

                    List<String> listSpinner = new ArrayList<>();
                    for (int i = 0; i < jsonMemberPackageItemList.size(); i++) {

                        listSpinner.add(jsonMemberPackageItemList.get(i).getPackageName());

                        adapter = new ArrayAdapter<>(DetailTeacherActivity.this,
                                R.layout.layout_teke, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                try {
//                                    pref.put(PrefContract.schedule_id,""+scheduleItemList.get(i).getScheduleId());
//                                    pref.put(PrefContract.schedule_date,""+dayOfTheWeek+" "+scheduleItemList.get(i).getStartDate()+" - "+dayDateEnd+" "+scheduleItemList.get(i).getEndDate());
//                                    pref.put(PrefContract.schedule_time,""+scheduleItemList.get(i).getStartTime()+" - "+scheduleItemList.get(i).getEndTime());
//
//                                } catch (AppException e) {
//                                    e.printStackTrace();
//                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                Toast.makeText(DetailTeacherActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void requestPrivate() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        try {
            uid = pref.getString(PrefContract.user_id,"");
            schedule_id = pref.getString(PrefContract.schedule_id,"");
            scheduleTime = pref.getString(PrefContract.schedule_time,"");
            scheduleDate = pref.getString(PrefContract.schedule_date,"");

        } catch (AppException e) {
            e.printStackTrace();
        }

        //Log.d("logggIsiisiRequest", " +"+uid+" +"+getIntent().getStringExtra("teach_id")+" + "+getIntent().getStringExtra("teach_name")+" + "+spinner.getSelectedItem().toString());

        mApiService.requestTeacher(""+uid,""+getIntent().getStringExtra("teach_id"),""+getIntent().getStringExtra("teach_name"),
                schedule_id,scheduleDate, scheduleTime,"0")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Submit request berhasil")) {

                                    loading.dismiss();
                                    Toast.makeText(DetailTeacherActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();

                                    pref.put(PrefContract.schedule_id,"");
                                    pref.put(PrefContract.schedule_date,"");
                                    pref.put(PrefContract.schedule_time,"");

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(DetailTeacherActivity.this, "" + pesanError, Toast.LENGTH_SHORT).show();
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

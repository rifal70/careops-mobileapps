package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ListMemberAdminAdapter;
import com.digimaster.digicourse.digicyber.Adapter.TeacherAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ListMemberAdminItem;
import com.digimaster.digicourse.digicyber.Model.ListMemberAdminResponse;
import com.digimaster.digicourse.digicyber.Model.TeachersItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMemberGroupAdminActivity extends AppCompatActivity {
    RecyclerView rvTeach;
    ProgressDialog loading;
    SecuredPreference pref;
    TeacherAdapter teacherAdapter;
    ListMemberAdminAdapter listMemberAdminAdapter;
    BaseApiService mApiService;
    List<TeachersItem> teachersItemList = new ArrayList<>();
    RelativeLayout rvFindguru;
    Context context;
    GifImageView gif_find_teacher;
    String uid, role, group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_member_group_admin);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            if (bundle.getString("group_id") != null) {
                group_id = bundle.getString("group_id");
            }
        }

        Toolbar toolbar = findViewById(R.id.detail_toolbar_app_bar_atasnya_doank);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("List Member");

        rvTeach = findViewById(R.id.rvTeacher);

        rvTeach.setHasFixedSize(true);
        rvTeach.setLayoutManager(new LinearLayoutManager(this));
        rvTeach.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rvFindguru = findViewById(R.id.rvFindGuru);

        gif_find_teacher = findViewById(R.id.gif_load_fragment_find_teacher);

        try {
            uid = pref.getString(PrefContract.user_id, "");
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loadAdmin();
    }

    private void loadAdmin() {
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gif_find_teacher.setVisibility(View.VISIBLE);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getListMemberAdmin(group_id).enqueue(new Callback<ListMemberAdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListMemberAdminResponse> call, @NonNull Response<ListMemberAdminResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<ListMemberAdminItem> listMemberAdminItems = response.body().getListMemberAdmin();

                    listMemberAdminAdapter = new ListMemberAdminAdapter(ListMemberGroupAdminActivity.this, listMemberAdminItems, genProductAdapterListener());
                    rvTeach.setAdapter(listMemberAdminAdapter);
                    listMemberAdminAdapter.notifyDataSetChanged();

                    //Log.d("logggS", "response beres");
                    gif_find_teacher.setVisibility(View.GONE);

                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                } else {
                    gif_find_teacher.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(rvFindguru, "Gagal mengambil data", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ListMemberAdminResponse> call, Throwable t) {
                gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(ListMemberGroupAdminActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return (view, position) -> {

        };
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

}

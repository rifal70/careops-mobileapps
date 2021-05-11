package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ListGroupAdminAdapter;
import com.digimaster.digicourse.digicyber.Adapter.TeacherAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminItem;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminResponse;
import com.digimaster.digicourse.digicyber.Model.ListGroupItem;
import com.digimaster.digicourse.digicyber.Model.ListGroupResponse;
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



public class MyGroupActivity extends AppCompatActivity {

    RecyclerView rvTeach;
    ProgressDialog loading;
    SecuredPreference pref;
    TeacherAdapter teacherAdapter;
    ListGroupAdminAdapter listGroupAdminAdapter;
    BaseApiService mApiService;
    List<TeachersItem> teachersItemList = new ArrayList<>();
    RelativeLayout rvFindguru, relaPpl;
    Context context;
    GifImageView gif_find_teacher;
    String uid, role;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);

        rvTeach = findViewById(R.id.rvTeacher);
        relaPpl = findViewById(R.id.rela_my_ppl);

        rvTeach.setHasFixedSize(true);
        rvTeach.setLayoutManager(new LinearLayoutManager(this));
        rvTeach.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rvFindguru = findViewById(R.id.rvFindGuru);


        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("My Group");

        try {
            uid = pref.getString(PrefContract.user_id, "");
            role = pref.getString(PrefContract.role, "false");
        } catch (
                AppException e) {
            e.printStackTrace();
        }


        gif_find_teacher = findViewById(R.id.gif_load_fragment_find_teacher);

        if (role.equals("admin")) {
            loadAdmin("");
        } else {
            loadJSON("");
        }

    }

    private void loadJSON(String filter) {
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gif_find_teacher.setVisibility(View.VISIBLE);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getListGroup(uid).enqueue(new Callback<ListGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListGroupResponse> call, @NonNull Response<ListGroupResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<ListGroupItem> listGroupItems = response.body().getListGroup();

                    if (listGroupItems.size() == 0) {
                        gif_find_teacher.setVisibility(View.GONE);
                        rvTeach.setVisibility(View.GONE);
                        relaPpl.setVisibility(View.VISIBLE);

                    } else {
                        teacherAdapter = new TeacherAdapter(MyGroupActivity.this, listGroupItems, genProductAdapterListener());
                        rvTeach.setAdapter(teacherAdapter);
                        teacherAdapter.notifyDataSetChanged();
                        //Log.d("logggS", "response beres");
                        gif_find_teacher.setVisibility(View.GONE);
                    }


                } else {
                    gif_find_teacher.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(rvFindguru, "Gagal mengambil data", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    relaPpl.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onFailure(Call<ListGroupResponse> call, Throwable t) {
                gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(MyGroupActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                relaPpl.setVisibility(View.VISIBLE);

            }
        });
    }

    private void loadAdmin(String filter) {
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gif_find_teacher.setVisibility(View.VISIBLE);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getListGroupAdmin(uid).enqueue(new Callback<ListGroupAdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListGroupAdminResponse> call, @NonNull Response<ListGroupAdminResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<ListGroupAdminItem> listGroupItems = response.body().getListGroupAdmin();

                    listGroupAdminAdapter = new ListGroupAdminAdapter(MyGroupActivity.this, listGroupItems, genProductAdapterListener());
                    rvTeach.setAdapter(listGroupAdminAdapter);
                    listGroupAdminAdapter.notifyDataSetChanged();

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
                    relaPpl.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onFailure(Call<ListGroupAdminResponse> call, Throwable t) {
                gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(MyGroupActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                relaPpl.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plus, menu);
        //MenuItem item = menu.findItem(R.id.star_button);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_join) {

            Intent intent = new Intent(this, RequestActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    private AdapterOnItemClickListener genProductAdapterListener() {
        return (view, position) -> {

        };
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

}

package com.digimaster.digicourse.digicyber.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.RequestAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.GroupItem;
import com.digimaster.digicourse.digicyber.Model.GroupResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {
    String token, userid;
    ProgressDialog loading;
    SecuredPreference pref;
    Context context;
    RequestAdapter requestAdapter;
    BaseApiService mApiService;
    RecyclerView rvRTH;
    RecyclerView.LayoutManager layoutManager;
    List<GroupItem> groupItemList = new ArrayList<>();
    Button btnBrowse;
    EditText etPhone;
    ImageView ivPhone;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRTH = findViewById(R.id.rvGroup);
        rvRTH.setHasFixedSize(true);
        rvRTH.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRTH.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        requestAdapter = new RequestAdapter(this, groupItemList, genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
        etPhone = findViewById(R.id.editTextPhoneGroup);
        ivPhone = findViewById(R.id.imageView3);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = this;
        btnBrowse = findViewById(R.id.btn_browse_wishlist);

        try {
            pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.detail_toolbar_kosong);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("Request Group");

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        try {
            token =  pref.getString(PrefContract.PREF_TOKEN,"");
            userid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON();
            }
        });



    }

    private void loadJSON(){
        loading = ProgressDialog.show(RequestActivity.this, null, "Harap Tunggu...", true, false);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rvRTH)
                .adapter(requestAdapter)
                .load(R.layout.activity_wishlist_adapter)
                .color(R.color.light_transparent)
                .duration(1000)
                .angle(30)
                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();

        mApiService.getGroup("onsite/check_group/"+etPhone.getText().toString()).enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupResponse> call, @NonNull Response<GroupResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    ivPhone.setVisibility(View.GONE);
                    //Log.d("logggN",""+response.body());
                    List<GroupItem> androidItems = response.body().getGroup();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    skeletonScreen.hide();
                    requestAdapter = new RequestAdapter(RequestActivity.this, androidItems, genProductAdapterListener());
                    rvRTH.setAdapter(requestAdapter);
                    //initDataIntent(androidItems);
                    requestAdapter.notifyDataSetChanged();
                    //rvDosen.setAdapter(orderAdapter);
                    //Log.d("logggS","response beres");
                } else {
                    loading.dismiss();
                    ivPhone.setVisibility(View.VISIBLE);
                    skeletonScreen.hide();
                    Toast.makeText(RequestActivity.this, "Something happened, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                loading.dismiss();
                ivPhone.setVisibility(View.VISIBLE);
                skeletonScreen.hide();
                Toast.makeText(RequestActivity.this, "There's no available group yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.plus, menu);
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

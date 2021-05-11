package com.digimaster.digicourse.digicyber.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ListGroupAdminAdapter;
import com.digimaster.digicourse.digicyber.Adapter.MyScoresAdapter;
import com.digimaster.digicourse.digicyber.Adapter.SimpleSectionedRecyclerViewAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ScoreResponse;
import com.digimaster.digicourse.digicyber.Model.ScoreUserItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyAchievementFragment extends Fragment {

    Context mContext;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    SecuredPreference pref;
    String[] sCheeseStrings;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ListGroupAdminAdapter listGroupAdminAdapter;
    ProgressDialog loading;
    BaseApiService mApiService;
    String userid, cour_id, name, role;
    RelativeLayout rela_score, rela_cert;
    TextView tvName, tvRole, tvBeloman, tvBeres;
    CircleImageView circleImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_score2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "CERTI", Toast.LENGTH_SHORT).show();
        mRecyclerView = view.findViewById(R.id.list_score);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //Your RecyclerView.Adapter

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rela_score = view.findViewById(R.id.rvRootMyScore);
        rela_cert = view.findViewById(R.id.rela_cert);

        tvName = view.findViewById(R.id.tvNameScore);
        tvRole = view.findViewById(R.id.tvTitleScore);
        tvBeloman = view.findViewById(R.id.tvBeloman);
        tvBeres = view.findViewById(R.id.tvBeres);

//        circleImageView = view.findViewById(R.id.imageView7);
//        circleImageView.setImageResource(R.drawable.profile);


        try {
            userid = pref.getString(PrefContract.user_id, "");
            cour_id = pref.getString(PrefContract.cour_id, "");
            name = pref.getString(PrefContract.USER_FULL_NAME, "");
            role = pref.getString(PrefContract.role, "");

        } catch (AppException e) {
            e.printStackTrace();
        }

//        tvName.setText(name);
//        tvRole.setText(role);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //Log.i(tag, "onKey Back listener is working!!!");
                    //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        //Apply this adapter to the RecyclerView
        if (userid.equals("") || userid.equals(null)) {
            mRecyclerView.setVisibility(View.GONE);
            rela_score.setVisibility(View.VISIBLE);
        } else {
            loadAchieve();
        }
    }


    private void loadAchieve() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getScoreUser(userid).enqueue(new Callback<ScoreResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScoreResponse> call, @NonNull Response<ScoreResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggSEKUD", "" + response.body());
                    List<ScoreUserItem> scoreUserItemList = response.body().getScoreUser();
                    if (scoreUserItemList.size() == 0) {
                        mRecyclerView.setVisibility(View.GONE);
                        rela_cert.setVisibility(View.VISIBLE);
                    } else {
                        rela_cert.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        String beres = response.body().getTaskFinish();
                        String beloman = response.body().getTaskUnfinish();

                        tvBeloman.setText(beloman);
                        tvBeres.setText(beres);


                        //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                        mAdapter = new MyScoresAdapter(getActivity(), scoreUserItemList, genProductAdapterListener());
                        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                                SimpleSectionedRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, mAdapter);
                        mSectionedAdapter.setSections(sections.toArray(dummy));

                        //Apply this adapter to the RecyclerView
                        mRecyclerView.setAdapter(mSectionedAdapter);
                        //initDataIntent(androidItems);
                        mAdapter.notifyDataSetChanged();
                    }
                    //rvDosen.setAdapter(orderAdapter);
                    Log.d("logggS", "response beres");
                    loading.dismiss();
                } else {
                    loading.dismiss();
//                    Snackbar snackbar = Snackbar.make(rela_cert, "Please login first", Snackbar.LENGTH_LONG);
//                    View sbView = snackbar.getView();
                    //    sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                    //snackbar.show();
                }
                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                //String pesan = jsonRESULTS.getString("success");
            }

            @Override
            public void onFailure(Call<ScoreResponse> call, Throwable t) {
                loading.dismiss();
                Snackbar snackbar = Snackbar.make(rela_cert, "Bad Connection", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                snackbar.show();
            }
        });
    }


    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}

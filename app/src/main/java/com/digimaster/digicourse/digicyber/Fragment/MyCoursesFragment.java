package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.TaskLibraryAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.DataItem;
import com.digimaster.digicourse.digicyber.Model.TaskLibItem;
import com.digimaster.digicourse.digicyber.Model.TaskLibResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCoursesFragment extends Fragment {
    String token;
    String userid;
    AlertDialog loading;
    SecuredPreference pref;
    Context context;
    TaskLibraryAdapter taskLibraryAdapter;
    BaseApiService mApiService;
    RecyclerView rvDosen;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relaCourse;
    List<DataItem> androidItemList = new ArrayList<>();
    LinearLayout  linearLayoutMyCour;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_courses, container, false);

        showBackButton();

        return inflater.inflate(R.layout.fragment_my_courses, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvDosen = view.findViewById(R.id.rvDosen);
        relaCourse = view.findViewById(R.id.rv_course_item);
        rvDosen.setHasFixedSize(true);
        rvDosen.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDosen.setLayoutManager(layoutManager);
        linearLayoutMyCour = view.findViewById(R.id.linearMyCourse);
        mySwipeCourse = view.findViewById(R.id.swiperefreshMyCourse);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = getActivity();

        alertDialogView = LayoutInflater.from(getContext()).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(getContext()).setView(alertDialogView).create();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.i(tag, "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
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

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        try {
            token =  pref.getString(PrefContract.PREF_TOKEN,"");
            userid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                loadJSON();
            }
        }, 900);

        mySwipeCourse.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        loadJSON();
                    }
                }
        );


        super.onViewCreated(view, savedInstanceState);
    }

    public void showBackButton() {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }


    private void loadJSON(){
        loading.show();
        loading.getWindow().setLayout(250, 250);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.ambilTaskLib(userid).enqueue(new Callback<TaskLibResponse>() {
            @Override
            public void onResponse(@NonNull Call<TaskLibResponse> call, @NonNull Response<TaskLibResponse> response) {
                if (response.isSuccessful()) {

                    String jsonRESULTS = response.body().getMessage();
                    //Log.d("logggMSG", "onResponse: "+jsonRESULTS);
                    //String pesanError = jsonRESULTS.getString("message");
                    if (jsonRESULTS.equals("Berhasil mengambil seluruh data")) {
                        //Log.d("logggN", "" + response.body());
                        List<TaskLibItem> androidItems = response.body().getTaskLib();
                        if(androidItems.size() == 0) {
                            rvDosen.setVisibility(View.GONE);
                            relaCourse.setVisibility(View.VISIBLE);
                        }
                        else {
                            relaCourse.setVisibility(View.GONE);
                            rvDosen.setVisibility(View.VISIBLE);
                            taskLibraryAdapter = new TaskLibraryAdapter(getActivity(), androidItems, genProductAdapterListener());
                            rvDosen.setAdapter(taskLibraryAdapter);
                            taskLibraryAdapter.notifyDataSetChanged();

                            //Log.d("logggS", "response beres");
                            loading.dismiss();
                            mySwipeCourse.setRefreshing(false);
                        }
                    }

                    else{
                        rvDosen.setVisibility(View.GONE);
                        relaCourse.setVisibility(View.VISIBLE);
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);

//                        Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Silahkan Login", Snackbar.LENGTH_LONG);
//                        View sbView = snackbar.getView();
//                        sbView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_alert));
//                        snackbar.show();
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                } else {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Please login first", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskLibResponse> call, Throwable t) {
                loading.dismiss();
//                Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Bad Connection", Snackbar.LENGTH_LONG);
//                View sbView = snackbar.getView();
//                sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
//                snackbar.show();
                rvDosen.setVisibility(View.GONE);
                relaCourse.setVisibility(View.VISIBLE);
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

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
    }


}

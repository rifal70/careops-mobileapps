package com.digimaster.digicourse.digicyber.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.CourseAdapter;
import com.digimaster.digicourse.digicyber.Adapter.TaskAssAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseByUidItem;
import com.digimaster.digicourse.digicyber.Model.CourseResponse;
import com.digimaster.digicourse.digicyber.Model.DataItem;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class  TaskFragment extends Fragment {
    RelativeLayout rlMerah, rlKuning;
    AlertDialog loading;
    SecuredPreference pref;
    Context context;
    TaskAssAdapter taskAssAdapter;
    CourseAdapter courseAdapter;

    BaseApiService mApiService;
    RecyclerView rvTask;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relaTask,relaTask2;
    List<DataItem> androidItemList = new ArrayList<>();
    LinearLayout linearLayoutMyCour;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;
    Toolbar toolbar;
    String token, userid;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("Task");

        return inflater.inflate(R.layout.fragment_my_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        relaTask = view.findViewById(R.id.rela_my_task);
        relaTask2 = view.findViewById(R.id.rela_my_task2);

        rvTask = view.findViewById(R.id.rvTaskGabung);
        rvTask.setHasFixedSize(true);
        rvTask.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTask.setLayoutManager(layoutManager);
        linearLayoutMyCour = view.findViewById(R.id.linearMyCourse);
        mySwipeCourse = view.findViewById(R.id.swiperefreshMyCourse);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = getActivity();

        alertDialogView = LayoutInflater.from(getActivity()).inflate
                (R.layout.loading_layout, null);

        loading = new AlertDialog.Builder(getActivity()).setView(alertDialogView).create();


        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        try {
            token = pref.getString(PrefContract.PREF_TOKEN, "");
            userid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        mySwipeCourse.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTask();
            }
        });

        loadTask();
    }

    private void loadTask(){
        loading.show();
        loading.getWindow().setLayout(250, 250);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getCourseById(userid).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CourseResponse> call, @NonNull Response<CourseResponse> response) {
                if (response.isSuccessful()) {

                    //String jsonRESULTS = response.body().getMessage();
                    //Log.d("logggMSG", "onResponse: "+jsonRESULTS);
                    //String pesanError = jsonRESULTS.getString("message");
//                    if (jsonRESULTS.equals("Berhasil mengambil seluruh data")) {
                        //Log.d("logggN", "" + response.body());
                        List<CourseByUidItem> androidItems = response.body().getCourseByUid();
                        if(androidItems.size() == 0) {
                            loading.dismiss();
                            mySwipeCourse.setRefreshing(false);
                            rvTask.setVisibility(View.GONE);
                            relaTask.setVisibility(View.VISIBLE);
                            relaTask2.setVisibility(View.VISIBLE);
                        }
                        else {
                            relaTask.setVisibility(View.GONE);
                            relaTask2.setVisibility(View.GONE);
                            rvTask.setVisibility(View.VISIBLE);
                            courseAdapter = new CourseAdapter(getActivity(), androidItems, genProductAdapterListener());
                            rvTask.setAdapter(courseAdapter);
                            courseAdapter.notifyDataSetChanged();

                            //Log.d("logggS", "response beres");
                            loading.dismiss();
                            mySwipeCourse.setRefreshing(false);
                        }
//                    }
//
//                    else{
//                        rvTask.setVisibility(View.GONE);
//                        relaTask.setVisibility(View.VISIBLE);
//                        loading.dismiss();
//                        mySwipeCourse.setRefreshing(false);
//
//                    }


                } else {
                    loading.dismiss();
                    mySwipeCourse.setRefreshing(false);
                    Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Please login first", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                loading.dismiss();
                mySwipeCourse.setRefreshing(false);

                rvTask.setVisibility(View.GONE);
                relaTask.setVisibility(View.VISIBLE);
                relaTask2.setVisibility(View.VISIBLE);

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

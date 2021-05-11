package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AchievementAdapter;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.AchievementItem;
import com.digimaster.digicourse.digicyber.Model.AchievementResponse;
import com.digimaster.digicourse.digicyber.Model.TopicDetailItem;
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


public class ListTopicHalamanAchievement extends Fragment {
    String token, userid;
    SecuredPreference pref;
    Context context;
    BaseApiService mApiService;
    RecyclerView rvModules;
    AchievementAdapter achievementAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button btnBrowse;
    AlertDialog loading;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;
    String passing_module_id;
    List<TopicDetailItem> topicDetailItemList = new ArrayList<>();
    RelativeLayout relaCourse;

    public ListTopicHalamanAchievement() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_modules, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvModules = view.findViewById(R.id.rvFragModules);
        rvModules.setHasFixedSize(true);
        relaCourse = view.findViewById(R.id.rv_course_item);
        rvModules.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvModules.setLayoutManager(layoutManager);
        //   achievementAdapter = new AchievementAdapter(getActivity(), topicDetailItemList, genProductAdapterListener());
//        mySwipeCourse = view.findViewById(R.id.swiperefreshFragModule);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = getActivity();

        alertDialogView = LayoutInflater.from(getContext()).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(getContext()).setView(alertDialogView).create();

        try {
            pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


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

            passing_module_id = pref.getString(PrefContract.module_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

//        mySwipeCourse.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        //Log.i("logggASDAS", "onRefresh called from SwipeRefreshLayout");
//
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
//                        initItem();
//                    }
//                }
//        );


        //loadJSON();
        initItem();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }


    private void initItem() {
        if (loading != null && loading.isShowing()) {

        }
        else{
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getAchievementById(userid,passing_module_id).enqueue(new Callback<AchievementResponse>() {
            @Override
            public void onResponse(@NonNull Call<AchievementResponse> call, @NonNull Response<AchievementResponse> response) {
                if (response.isSuccessful()) {

                    List<AchievementItem> achievementItemList = response.body().getAchievement();

                    if(achievementItemList.size() == 0) {
                        rvModules.setVisibility(View.GONE);
                        relaCourse.setVisibility(View.VISIBLE);

                        loading.dismiss();
                    }
                    else {
                        //        relaCourse.setVisibility(View.GONE);
                        rvModules.setVisibility(View.VISIBLE);
                        achievementAdapter = new AchievementAdapter(getActivity(), achievementItemList, genProductAdapterListener());
                        rvModules.setAdapter(achievementAdapter);
                        achievementAdapter.notifyDataSetChanged();

                        //Log.d("logggS", "response beres");
                        loading.dismiss();
                        //  mySwipeCourse.setRefreshing(false);
                    }

//                        Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Silahkan Login", Snackbar.LENGTH_LONG);
//                        View sbView = snackbar.getView();
//                        sbView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_alert));
//                        snackbar.show();
                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                } else {
                    loading.dismiss();
//                    Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Please login first", Snackbar.LENGTH_LONG);
//                    View sbView = snackbar.getView();
//                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
//                    snackbar.show();
//                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AchievementResponse> call, Throwable t) {
                loading.dismiss();
//                Snackbar snackbar = Snackbar.make(linearLayoutMyCour, "Bad Connection", Snackbar.LENGTH_LONG);
//                View sbView = snackbar.getView();
//                sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
//                snackbar.show();
                rvModules.setVisibility(View.GONE);
                relaCourse.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        //getActivity().getMenuInflater().inflate(R.menu.history, menu);
        menu.clear();


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}

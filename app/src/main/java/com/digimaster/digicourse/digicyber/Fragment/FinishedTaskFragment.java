package com.digimaster.digicourse.digicyber.Fragment;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.FinishedTaskAdapter;
import com.digimaster.digicourse.digicyber.Adapter.SimpleSectionedRecyclerViewAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.FinishedTaskItem;
import com.digimaster.digicourse.digicyber.Model.FinishedTaskResponse;
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

public class FinishedTaskFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    String[] sCheeseStrings;
    ProgressDialog loading;
    SecuredPreference pref;
    Context context;
    BaseApiService mApiService;
    String userid;
    RelativeLayout rvScore, rvRoot;
    Handler h = new Handler();
    int delay = 5000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    SwipeRefreshLayout mySwipeScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_score, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "SCORE", Toast.LENGTH_SHORT).show();
        mRecyclerView = view.findViewById(R.id.list_score);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        rvScore = view.findViewById(R.id.rv_score_item);
        rvRoot = view.findViewById(R.id.rvScore_root);
        mySwipeScore = view.findViewById(R.id.swiperefresh);
        try {
            userid = pref.getString(PrefContract.user_id,"");
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


       /* final Handler handler = new Handler();
        final int delay = 2000; //milliseconds*/

       /* handler.postDelayed(new Runnable(){
            public void run(){
                loadJSON();
                handler.postDelayed(this, delay);
            }
        }, delay);*/
       if(userid.equals("")  || userid.equals(null)){
           mRecyclerView.setVisibility(View.GONE);
           rvScore.setVisibility(View.VISIBLE);
       }else {
           loadJSON();
       }

        mySwipeScore.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        //Log.i("logggASDAS", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        loadJSON();
                    }
                }
        );
    }

    private void loadJSON(){

        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getFinishTask(userid).enqueue(new Callback<FinishedTaskResponse>() {
            @Override
            public void onResponse(@NonNull Call<FinishedTaskResponse> call, @NonNull Response<FinishedTaskResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggSEKUD", "" + response.body());
                    List<FinishedTaskItem> androidItems = response.body().getFinishedTask();

                    if(androidItems.size() == 0){
                        mRecyclerView.setVisibility(View.GONE);
                        rvScore.setVisibility(View.VISIBLE);
                    }
                    else {
                        rvScore.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                        mAdapter = new FinishedTaskAdapter(getActivity(), androidItems, genProductAdapterListener());
                        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                        //Sections
                        //TENTUIN PER MATERI APA PERBAB SEKSYENNYA
                        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Score per material"));
                        //sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,"Bab 2 "));
                        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                                SimpleSectionedRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, mAdapter);
                        mSectionedAdapter.setSections(sections.toArray(dummy));

                        //Apply this adapter to the RecyclerView
                        mRecyclerView.setAdapter(mSectionedAdapter);
                        //initDataIntent(androidItems);
                        mAdapter.notifyDataSetChanged();
                        mySwipeScore.setRefreshing(false);
                  }
                    //rvDosen.setAdapter(orderAdapter);
                    //Log.d("logggS", "response beres");
                    //loading.dismiss();
                } else {
                    //loading.dismiss();
                    mySwipeScore.setRefreshing(false);

                    Snackbar snackbar = Snackbar.make(rvRoot, "Please login first", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red_alert));
                    snackbar.show();
                }
                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                //String pesan = jsonRESULTS.getString("success");
            }

            @Override
            public void onFailure(Call<FinishedTaskResponse> call, Throwable t) {
                mySwipeScore.setRefreshing(false);

                //loading.dismiss();
//                Snackbar snackbar = Snackbar.make(rvRoot, "Bad Connection", Snackbar.LENGTH_LONG);
//                View sbView = snackbar.getView();
//                sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red_alert));
//                snackbar.show();
                mRecyclerView.setVisibility(View.GONE);
                rvScore.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        h.postDelayed( runnable = new Runnable() {
//            public void run() {
//                //do something
//                loadJSON();
//                h.postDelayed(runnable, delay);
//            }
//        }, delay);

        super.onResume();
        //Log.e("Frontales","resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        h.removeCallbacks(runnable);
        //Log.e("Frontales","Pause");

    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }
}

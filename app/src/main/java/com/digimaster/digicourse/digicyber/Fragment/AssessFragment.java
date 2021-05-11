package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.CourseAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.LibraryItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;


public class AssessFragment extends Fragment {
    String token, userid;
    AlertDialog loading;
    SecuredPreference pref;
    Context context;
    CourseAdapter courseAdapter;
    BaseApiService mApiService;
    RecyclerView recyclerAss;
    RecyclerView.LayoutManager layoutAss;
    List<LibraryItem> onlineItemList = new ArrayList<>();
    Button btnDate;
    View alertDialogView;
    ExpandableLayout expandableLayout;
    SwipeRefreshLayout mySwipeLib;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("E-Assessment");
        return inflater.inflate(R.layout.fragment_assess, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerAss = view.findViewById(R.id.rvAssessXXX);
        recyclerAss.setHasFixedSize(true);
        layoutAss = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerAss.setLayoutManager(layoutAss);
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
        mySwipeLib = view.findViewById(R.id.swiperefreshAss);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        alertDialogView = LayoutInflater.from(getContext()).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(getContext()).setView(alertDialogView).create();

        context = getActivity();
        btnDate = view.findViewById(R.id.btnDate);



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
        } catch (AppException e) {
            e.printStackTrace();
        }

        mySwipeLib.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        //loadAssess();
                    }
                }
        );

        //loadAssess();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

//    private void loadAssess() {
//        if (loading != null && loading.isShowing()) {
//
//        } else {
//
//            loading.show();
//            loading.getWindow().setLayout(250, 250);
//        }
//        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
//        mApiService.getAssess().enqueue(new Callback<EAssessmentResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<EAssessmentResponse> call, @NonNull Response<EAssessmentResponse> response) {
//                if (response.isSuccessful()) {
//
//                    //Log.d("logggN",""+response.body());
//                    List<AssessmentItem> dataItemList = response.body().getAssessment();
//                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
//                    courseAdapter = new CourseAdapter(getActivity(), dataItemList, genProductAdapterListener());
//                    recyclerAss.setAdapter(courseAdapter);
//                    courseAdapter.notifyDataSetChanged();
//
//                    //Log.d("logggS","response beres");
//                    if (loading != null && loading.isShowing()) {
//                        loading.dismiss();
//                        mySwipeLib.setRefreshing(false);
//
//                    }
//                } else {
//                    if (loading != null && loading.isShowing()) {
//                        loading.dismiss();
//                        mySwipeLib.setRefreshing(false);
//
//                    }
//                    Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EAssessmentResponse> call, Throwable t) {
//                if (loading != null && loading.isShowing()) {
//                    loading.dismiss();
//                    mySwipeLib.setRefreshing(false);
//
//                }
//                //Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

}
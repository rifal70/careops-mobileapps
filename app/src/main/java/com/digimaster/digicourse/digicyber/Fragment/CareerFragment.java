package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.LibraryAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.LibraryItem;
import com.digimaster.digicourse.digicyber.Model.LibraryResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CareerFragment extends Fragment {
    String token, userid;
    AlertDialog loading;
    SecuredPreference pref;
    Context context;
    LibraryAdapter onlineAdapter;
    BaseApiService mApiService;
    RecyclerView rvWishlist;
    RecyclerView.LayoutManager layoutManager;
    List<LibraryItem> onlineItemList = new ArrayList<>();
    Button btnDate;
    View alertDialogView;
    ExpandableLayout expandableLayout;
    SwipeRefreshLayout mySwipeLib;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("Career Path");
        return inflater.inflate(R.layout.fragment_career, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        alertDialogView = LayoutInflater.from(getContext()).inflate
                (R.layout.loading_layout,null);

        loading =  new AlertDialog.Builder(getContext()).setView(alertDialogView).create();

        context = getActivity();



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


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    public void loadOnCourse(){
        if (loading != null && loading.isShowing()) {

        }
        else{
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getLibrary().enqueue(new Callback<LibraryResponse>() {
            @Override
            public void onResponse(@NonNull Call<LibraryResponse> call, @NonNull Response<LibraryResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN",""+response.body());
                    List<LibraryItem> dataItemList = response.body().getLibrary();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    onlineAdapter = new LibraryAdapter(getActivity(), dataItemList, genProductAdapterListener());
                    rvWishlist.setAdapter(onlineAdapter);
                    onlineAdapter.notifyDataSetChanged();

                    //Log.d("logggS","response beres");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeLib.setRefreshing(false);
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeLib.setRefreshing(false);

                    }
                    Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LibraryResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                    mySwipeLib.setRefreshing(false);

                }
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
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
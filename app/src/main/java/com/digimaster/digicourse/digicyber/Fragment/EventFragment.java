package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HistoryActivity;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.OnsiteItem;
import com.digimaster.digicourse.digicyber.Model.OnsiteResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {
    String token, userid;
    SecuredPreference pref;
    Context context;
    BaseApiService mApiService;
    RecyclerView rvWishlist;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button btnBrowse;
    AlertDialog loading;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("Event");
        return inflater.inflate(R.layout.fragment_wishlist, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvWishlist = view.findViewById(R.id.rvWishlist);
        rvWishlist.setHasFixedSize(true);
        rvWishlist.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvWishlist.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        mySwipeCourse = view.findViewById(R.id.swiperefreshEvent);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = getActivity();
        btnBrowse = view.findViewById(R.id.btn_browse_wishlist);

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
        } catch (AppException e) {
            e.printStackTrace();
        }

        mySwipeCourse.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Log.i("logggASDAS", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        initItem();
                    }
                }
        );


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
        mApiService.getOnsiteCour().enqueue(new Callback<OnsiteResponse>() {
            @Override
            public void onResponse(@NonNull Call<OnsiteResponse> call, @NonNull Response<OnsiteResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN",""+response.body());
                    List<OnsiteItem> onsiteItemList = response.body().getOnsite();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();

                    //recyclerCurr.setAdapter(adapter_r);
                    onsiteAdapter = new OnsiteAdapter(getActivity(), onsiteItemList, genProductAdapterListener());
                    rvWishlist.setAdapter(onsiteAdapter);
                    onsiteAdapter.notifyDataSetChanged();

                    //Log.d("logggS","response beres");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);
                    }
                    Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnsiteResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                    mySwipeCourse.setRefreshing(false);
                }
                //Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        //getActivity().getMenuInflater().inflate(R.menu.history, menu);
        menu.clear();

        inflater.inflate(R.menu.history, menu);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(getActivity(), HistoryActivity.class);
            startActivity(intent);
            //getActivity().finish();
            return true;
        }

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

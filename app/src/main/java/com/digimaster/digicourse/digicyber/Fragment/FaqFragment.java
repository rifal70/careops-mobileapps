package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.FaqAdapter;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.FaqItem;
import com.digimaster.digicourse.digicyber.Model.FaqResponse;
import com.digimaster.digicourse.digicyber.Model.OnsiteItem;
import com.digimaster.digicourse.digicyber.Model.OnsiteResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FaqFragment extends Fragment {
    String token, userid;
    SecuredPreference pref;
    Context context;
    FaqAdapter faqAdapter;
    BaseApiService mApiService;
    RecyclerView rvFaq;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<FaqItem> androidItemList = new ArrayList<>();
    Button btnBrowse;
    AlertDialog loading;
    View alertDialogView;
    MaterialSearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("Frequently Asked Questions");
        return inflater.inflate(R.layout.fragment_faq, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvFaq = view.findViewById(R.id.rvFaq);
        rvFaq.setHasFixedSize(true);
        rvFaq.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFaq.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        faqAdapter = new FaqAdapter(getActivity(), androidItemList, genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());

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

        searchView = view.findViewById(R.id.search_view);

        loadJSON("");
        //initItem();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    private void loadJSON(String filter){
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rvFaq)
                .adapter(faqAdapter)
                .load(R.layout.news_item)
                .color(R.color.light_transparent)
                .duration(1000)
                .angle(30)
                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getFaq(filter).enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggSCS", "response berhasil");
                    List<FaqItem> dataItemList = response.body().getFaq();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    faqAdapter = new FaqAdapter(getActivity(), dataItemList, genProductAdapterListener());
                    rvFaq.setAdapter(faqAdapter);
                    faqAdapter.notifyDataSetChanged();


                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    loading.dismiss();
                }

            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });

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
                    rvFaq.setAdapter(onsiteAdapter);
                    onsiteAdapter.notifyDataSetChanged();

                    //Log.d("logggS","response beres");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }
                    Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnsiteResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
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

        inflater.inflate(R.menu.faq, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (item != null) {
            searchView = (SearchView) item.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                loadJSON(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //This is your adapter that will be filtered

                return false;
            }
        });

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
        if (id == R.id.action_search) {

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

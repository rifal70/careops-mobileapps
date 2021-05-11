package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.digimaster.digicourse.digicyber.Adapter.NewsAdapter;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.NewsItem;
import com.digimaster.digicourse.digicyber.Model.NewsResponse;
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


public class NewsFragment extends Fragment {
    String token, userid;
    SecuredPreference pref;
    Context context;
    NewsAdapter newsAdapter;
    BaseApiService mApiService;
    RecyclerView rvWishlist;
    OnsiteAdapter onsiteAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NewsItem> androidItemList = new ArrayList<>();
    Button btnBrowse;
    AlertDialog loading;
    View alertDialogView;
    SwipeRefreshLayout mySwipeCourse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("Announcement");
        return inflater.inflate(R.layout.fragment_wishlist, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvWishlist = view.findViewById(R.id.rvWishlist);
        rvWishlist.setHasFixedSize(true);
        rvWishlist.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvWishlist.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        newsAdapter = new NewsAdapter(getActivity(), androidItemList, genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
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
                        loadJSON();
                    }
                }
        );


        loadJSON();
        //initItem();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    private void loadJSON(){
            loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
            final SkeletonScreen skeletonScreen = Skeleton.bind(rvWishlist)
                    .adapter(newsAdapter)
                    .load(R.layout.news_item)
                    .color(R.color.light_transparent)
                    .duration(1000)
                    .angle(30)
                    .show();
            //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
            mApiService.getNews().enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();
                        //Log.d("logggSCS", "response berhasil");
                        List<NewsItem> dataItemList = response.body().getNews();

                        //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                        newsAdapter = new NewsAdapter(getActivity(), dataItemList, genProductAdapterListener());
                        rvWishlist.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();

                        mySwipeCourse.setRefreshing(false);

                    } else {
                        //Log.d("logggResGagal", "respon gagal");
                        loading.dismiss();
                        mySwipeCourse.setRefreshing(false);

                    }

                }

        @Override
        public void onFailure(Call<NewsResponse> call, Throwable t) {
            //Log.d("logggGGL", "GAGAL");
            //Log.e("debug", "onFailure: ERROR > " + t.toString());
            loading.dismiss();
            mySwipeCourse.setRefreshing(false);

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

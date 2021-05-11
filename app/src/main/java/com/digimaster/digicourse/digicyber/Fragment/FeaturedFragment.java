package com.digimaster.digicourse.digicyber.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.digimaster.digicourse.digicyber.Activity.AchievementCourseActivity;
import com.digimaster.digicourse.digicyber.Activity.EventActivity;
import com.digimaster.digicourse.digicyber.Activity.FaqActivity;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Activity.MyGroupActivity;
import com.digimaster.digicourse.digicyber.Activity.NewsActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.CourseAdapter;
import com.digimaster.digicourse.digicyber.Adapter.FeaturedAdapter;
import com.digimaster.digicourse.digicyber.Adapter.LibraryAdapt2;
import com.digimaster.digicourse.digicyber.Adapter.NewsLatestAdapter;
import com.digimaster.digicourse.digicyber.Adapter.OnsiteAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseByUidItem;
import com.digimaster.digicourse.digicyber.Model.CourseResponse;
import com.digimaster.digicourse.digicyber.Model.LibraryItem;
import com.digimaster.digicourse.digicyber.Model.LibraryResponse;
import com.digimaster.digicourse.digicyber.Model.NewsItem;
import com.digimaster.digicourse.digicyber.Model.NewsResponse;
import com.digimaster.digicourse.digicyber.Model.OnsiteItem;
import com.digimaster.digicourse.digicyber.Model.OnsiteResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import qdx.bezierviewpager_compile.BezierRoundView;
import qdx.bezierviewpager_compile.vPage.BezierViewPager;
import qdx.bezierviewpager_compile.vPage.CardPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teke on 19/02/18.
 */

public class FeaturedFragment extends Fragment {
    List<Object> listImages;

    RecyclerView recyclerView, recyclerOnline, recyclerOffline, recyclerAss, recyclerTask;
    RecyclerView.LayoutManager layoutManager, layoutOnline, layoutOffline, layoutAss, layoutTask;

    FeaturedAdapter adapter;
    OnsiteAdapter onsiteAdapter;
    LibraryAdapt2 onlineAdapter;
    NewsLatestAdapter newsAdapter;
    ImageView imgkosong;

    CourseAdapter courseAdapter;

    BaseApiService mApiService;
    SecuredPreference pref;
    AlertDialog loading;

    BezierViewPager viewPager;
    BezierRoundView roundView;
    CardPagerAdapter cardPagerAdapter;
    View alertDialogView;
    CardView cardViewFake;
    HorizontalStepView horizontalStepView;
    TextView tvGreet;

    String uid, lvl, name, role, nickname;
    LinearLayout ll_ppl, ll_faq, ll_event, ll_career, ll_announce, ll_score,lvkosong;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("CareOps");
        return inflater.inflate(R.layout.fragment_featured, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alertDialogView = LayoutInflater.from(getContext()).inflate
                (R.layout.loading_layout, null);

        loading = new AlertDialog.Builder(getContext()).setView(alertDialogView).create();

        //.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.loading);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);

        viewPager = view.findViewById(R.id.viewPager);
        roundView = view.findViewById(R.id.round_view);
        cardViewFake = view.findViewById(R.id.cardFake);

        buildImageList();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerOnline = view.findViewById(R.id.recyclerOnline);
        recyclerOnline.setHasFixedSize(true);
        layoutOnline = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerOnline.setLayoutManager(layoutOnline);

        recyclerOffline = view.findViewById(R.id.recyclerOffline);
        recyclerOffline.setHasFixedSize(true);
        layoutOffline = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerOffline.setLayoutManager(layoutOffline);

        recyclerTask = view.findViewById(R.id.recyclerTask);
        recyclerTask.setHasFixedSize(true);
        layoutTask = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTask.setLayoutManager(layoutTask);

        horizontalStepView = view.findViewById(R.id.step_view);

        lvkosong = view.findViewById(R.id.kosong);
        ll_event = view.findViewById(R.id.ll_event);
        ll_ppl = view.findViewById(R.id.ll_ppl);
        ll_faq = view.findViewById(R.id.ll_faq);
        ll_career = view.findViewById(R.id.ll_lvl);
        ll_announce = view.findViewById(R.id.ll_announce);
        ll_score = view.findViewById(R.id.ll_score);

        try {
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }

        ll_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
            }
        });

        ll_ppl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyGroupActivity.class);
                startActivity(intent);
            }
        });

        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
            }
        });

        ll_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new CareerFragment();
                replaceFragment(fragment);
            }
        });

        ll_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        ll_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;

                if (role.equals("admin")) {
                    fragment = new ListGroupScoreAdmin();
                    replaceFragment(fragment);

                }else{
                    Intent myIntent = new Intent(getActivity(), AchievementCourseActivity.class);
                    getActivity().startActivity(myIntent);
                }
            }
        });

        try {
            uid = pref.getString(PrefContract.user_id, "");
            name = pref.getString(PrefContract.USER_FULL_NAME, "");
            nickname = pref.getString(PrefContract.username, "");

        } catch (AppException e) {
            e.printStackTrace();
        }


        loadLibrary();
        initItem();
        initItem();
        loadTask();
        //loadAssess();
        //loadTryOut();
        //getLv();

        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        try {
            lvl = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        tvGreet = view.findViewById(R.id.tvGreeting);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(nickname.equals("")){
            nickname = name;
        }

        if (timeOfDay >= 0 && timeOfDay < 12) {
            tvGreet.setText("Good Morning " + nickname);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            tvGreet.setText("Good Afternoon " + nickname);
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            tvGreet.setText("Good Evening " + nickname);
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            tvGreet.setText("Good Night " + nickname);
        }


        loadNews();
    }


    //Annoounnnceee
    private void loadNews() {
        if (loading != null && loading.isShowing()) {

        } else {
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getNews().enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggSCS", "response berhasil");
                    List<NewsItem> dataItemList = response.body().getNews();

                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    newsAdapter = new NewsLatestAdapter(getActivity(), dataItemList, genProductAdapterListener());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }
                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    if (loading != null && loading.isShowing()) {
                        loading.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }


            }
        });

    }



    public void buildImageList() {
        if (loading != null && loading.isShowing()) {

        } else {
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }


        //Banneeeerrr
        //Log.d("logggIntent", "Berhasil method login");
        mApiService.getBanners().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    //Log.d("logggSCS", "response berhasil");
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        JSONArray image = jsonRESULTS.getJSONArray("banner");
                        listImages = new ArrayList<>();

                        if (image.length() >= 1) {
                            for (int i = 0; i < image.length(); i++) {
                                JSONObject gambar = image.getJSONObject(i);
                                // Log.d("loggggetJsonObjI",""+image.getJSONObject(i));
                                String gambir = gambar.getString("image");

                                if (isAdded()) {
                                    listImages.add(getResources().getString(R.string.base_url_asset_banner) + gambir);
                                }
                            }
                        } else {
                            listImages.add("https://admin.digimaster.id/assets/img/course/DigiMaster-PHP.jpg");
                        }

                        cardPagerAdapter = new CardPagerAdapter(getActivity());
                        cardPagerAdapter.addImgUrlList(listImages);


                        viewPager.setAdapter(cardPagerAdapter);
                        roundView.attach2ViewPage(viewPager);
                        //This will scroll page-by-page so that you can view scroll happening


                        /*After setting the adapter use the timer */
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == 4) {
                                    currentPage = 0;
                                }
                                viewPager.setCurrentItem(currentPage++, true);
                            }
                        };

                        timer = new Timer(); // This will create a new Thread
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);


                        buidlbuildimage();

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //Log.d("logggResGagal", "respon gagal");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("logggGGL", "GAGAL");
                //Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });

    }

    private void buidlbuildimage() {
        loading.dismiss();

    }


    //Libraryyyyyy
    public void loadLibrary() {
        if (loading != null && loading.isShowing()) {

        } else {
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
                    onlineAdapter = new LibraryAdapt2(getActivity(), dataItemList, genProductAdapterListener());
                    recyclerOnline.setAdapter(onlineAdapter);
                    onlineAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<LibraryResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
                //Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //INITASKKKK

    public void loadTask(){
        if (loading != null && loading.isShowing()) {

        } else {
            loading.show();
            loading.getWindow().setLayout(250, 250);
        }
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getCourseById(uid).enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CourseResponse> call, @NonNull Response<CourseResponse> response) {
                if (response.isSuccessful()) {
                    List<CourseByUidItem> androidItems = response.body().getCourseByUid();
                    if(androidItems.size() == 0) {
                        loading.dismiss();
                        recyclerTask.setVisibility(View.GONE);
                        lvkosong.setVisibility(View.VISIBLE);
                    }
                    else {
                        lvkosong.setVisibility(View.GONE);
                        recyclerTask.setVisibility(View.VISIBLE);
                        courseAdapter = new CourseAdapter(getActivity(), androidItems, genProductAdapterListener());
                        recyclerTask.setAdapter(courseAdapter);
                        courseAdapter.notifyDataSetChanged();

                        //Log.d("logggS", "response beres");
                        loading.dismiss();
                    }
                } else {
                    if (loading != null && loading.isShowing()) {
                        recyclerTask.setVisibility(View.GONE);
                        lvkosong.setVisibility(View.VISIBLE);
                        loading.dismiss();
                    }
                    Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
                //Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }




    //initeheven
    private void initItem() {
        if (loading != null && loading.isShowing()) {

        } else {
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
                    recyclerOffline.setAdapter(onsiteAdapter);
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


    private AdapterOnItemClickListener genProductAdapterListener() {
        return new AdapterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_area, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

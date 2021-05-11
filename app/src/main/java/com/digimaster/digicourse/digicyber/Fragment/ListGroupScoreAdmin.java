package com.digimaster.digicourse.digicyber.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.ListGroupScoreAdminAdapter;
import com.digimaster.digicourse.digicyber.Adapter.TeacherAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminItem;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminResponse;
import com.digimaster.digicourse.digicyber.Model.TeachersItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carios on 19/02/18.
 */


public class ListGroupScoreAdmin extends Fragment {
    RecyclerView rvTeach;
    ProgressDialog loading;
    SecuredPreference pref;
    TeacherAdapter teacherAdapter;
    ListGroupScoreAdminAdapter listGroupAdminAdapter;
    BaseApiService mApiService;
    List<TeachersItem> teachersItemList = new ArrayList<>();
    RelativeLayout rvFindguru;
    Context context;
    GifImageView gif_find_teacher;
    String uid, role;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((HomeActivity) getActivity())
                .setActionBarTitle("Score");

        return inflater.inflate(R.layout.fragment_list_group_admin, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rvTeach = view.findViewById(R.id.rvTeacher);

        rvTeach.setHasFixedSize(true);
        rvTeach.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTeach.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rvFindguru = view.findViewById(R.id.rvFindGuru);

        setHasOptionsMenu(true);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        try {
            uid = pref.getString(PrefContract.user_id, "");
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }


        gif_find_teacher = view.findViewById(R.id.gif_load_fragment_find_teacher);


        loadAdmin("");

        super.onViewCreated(view, savedInstanceState);
    }


    private void loadAdmin(String filter) {
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gif_find_teacher.setVisibility(View.VISIBLE);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getListGroupAdmin(uid).enqueue(new Callback<ListGroupAdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListGroupAdminResponse> call, @NonNull Response<ListGroupAdminResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<ListGroupAdminItem> listGroupItems = response.body().getListGroupAdmin();

                    listGroupAdminAdapter = new ListGroupScoreAdminAdapter(getActivity(), listGroupItems, genProductAdapterListener());
                    rvTeach.setAdapter(listGroupAdminAdapter);
                    listGroupAdminAdapter.notifyDataSetChanged();

                    //Log.d("logggS", "response beres");
                    gif_find_teacher.setVisibility(View.GONE);

                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");

                } else {
                    gif_find_teacher.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(rvFindguru, "Gagal mengambil data", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.red_alert));
                    snackbar.show();
                    //Toast.makeText(getActivity(), "Gagal mengambil data product", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ListGroupAdminResponse> call, Throwable t) {
                gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        //getActivity().getMenuInflater().inflate(R.menu.history, menu);
        menu.clear();

        if (role.equals("admin")) {

        } else {
            inflater.inflate(R.menu.plus, menu);
        }

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
        if (id == R.id.action_join) {
            Fragment fragment = null;
            fragment = new RequestFragment();
            replaceFragment(fragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_area, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private AdapterOnItemClickListener genProductAdapterListener() {
        return (view, position) -> {

        };
    }

}

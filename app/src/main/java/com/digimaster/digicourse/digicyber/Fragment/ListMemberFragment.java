package com.digimaster.digicourse.digicyber.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
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
import com.digimaster.digicourse.digicyber.Adapter.ListMemberAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.MemberItem;
import com.digimaster.digicourse.digicyber.Model.MemberResponse;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carios on 19/02/18.
 */


public class ListMemberFragment extends Fragment {
    RecyclerView rvTeach;
    ProgressDialog loading;
    SecuredPreference pref;
    ListMemberAdapter
            teacherAdapter;
    BaseApiService mApiService;
    RelativeLayout  rvFindguru;
    Context context;
    GifImageView gif_find_teacher;
    String task_id, task_type;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((HomeActivity) getActivity())
                .setActionBarTitle("People");

        return inflater.inflate(R.layout.activity_my_group, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rvTeach = view.findViewById(R.id.rvTeacher);

        rvTeach.setHasFixedSize(true);
        rvTeach.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTeach.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        rvFindguru =  view.findViewById(R.id.rvFindGuru);


        setHasOptionsMenu(true);

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



        gif_find_teacher = view.findViewById(R.id.gif_load_fragment_find_teacher);


        try {
            loadJSON();
        } catch (AppException e) {
            e.printStackTrace();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadJSON() throws AppException {

        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        gif_find_teacher.setVisibility(View.VISIBLE);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            task_id = bundle.getString("task_id");
            task_type = bundle.getString("task_type");
        }

        String id = pref.getString(PrefContract.user_id, "false");

        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getAllMember(id).enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(@NonNull Call<MemberResponse> call, @NonNull Response<MemberResponse> response) {
                if (response.isSuccessful()) {

                    //Log.d("logggN", "" + response.body());
                    List<MemberItem> memberItemList = response.body().getMember();

                    teacherAdapter = new ListMemberAdapter(getActivity(), memberItemList, genProductAdapterListener(), task_id, task_type);
                    rvTeach.setAdapter(teacherAdapter);
                    teacherAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<MemberResponse> call, Throwable t) {
                gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //Toast.makeText(getActivity(), ""+sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
        //edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        //getActivity().getMenuInflater().inflate(R.menu.history, menu);
        menu.clear();

        //inflater.inflate(R.menu.history, menu);

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
//        if (id == R.id.action_history) {
//            Intent intent = new Intent(getActivity(), HistoryActivity.class);
//            startActivity(intent);
//            //getActivity().finish();
//            return true;
//        }

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

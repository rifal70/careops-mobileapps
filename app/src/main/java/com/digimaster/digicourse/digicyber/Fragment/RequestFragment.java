package com.digimaster.digicourse.digicyber.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Adapter.AdapterOnItemClickListener;
import com.digimaster.digicourse.digicyber.Adapter.RequestAdapter;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.GroupItem;
import com.digimaster.digicourse.digicyber.Model.GroupResponse;
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


public class RequestFragment extends Fragment {
    String token, userid;
    ProgressDialog loading;
    SecuredPreference pref;
    Context context;
    RequestAdapter requestAdapter;
    BaseApiService mApiService;
    RecyclerView rvRTH;
    RecyclerView.LayoutManager layoutManager;
    List<GroupItem> groupItemList = new ArrayList<>();
    Button btnBrowse;
    EditText etPhone;
    ImageView ivPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity())
                .setActionBarTitle("Join Group");
        return inflater.inflate(R.layout.activity_request, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRTH = view.findViewById(R.id.rvGroup);
        rvRTH.setHasFixedSize(true);
        rvRTH.setLayoutManager(mLayoutManager);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvRTH.setLayoutManager(layoutManager);
        //orderAdapter = new Order(getActivity(), androidItemList, genProductAdapterListener());
        requestAdapter = new RequestAdapter(getActivity(), groupItemList, genProductAdapterListener());
        //rvDosen.setItemAnimator(new SlideInUpAnimator());
        etPhone = view.findViewById(R.id.editTextPhoneGroup);
        ivPhone = view.findViewById(R.id.imageView3);

        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        mApiService = UtilsApi.getAPIService();

        context = getActivity();
        btnBrowse = view.findViewById(R.id.btn_browse_wishlist);

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

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    private void loadJSON(){
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rvRTH)
                .adapter(requestAdapter)
                .load(R.layout.activity_wishlist_adapter)
                .color(R.color.light_transparent)
                .duration(1000)
                .angle(30)
                .show();
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();

        mApiService.getGroup("onsite/check_group/"+etPhone.getText().toString()).enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupResponse> call, @NonNull Response<GroupResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    ivPhone.setVisibility(View.GONE);
                    //Log.d("logggN",""+response.body());
                    List<GroupItem> androidItems = response.body().getGroup();
                    //Toast.makeText(context, ""+androidItemList, Toast.LENGTH_SHORT).show();
                    skeletonScreen.hide();
                    requestAdapter = new RequestAdapter(getActivity(), androidItems, genProductAdapterListener());
                    rvRTH.setAdapter(requestAdapter);
                    //initDataIntent(androidItems);
                    requestAdapter.notifyDataSetChanged();
                    //rvDosen.setAdapter(orderAdapter);
                    //Log.d("logggS","response beres");
                } else {
                    loading.dismiss();
                    ivPhone.setVisibility(View.VISIBLE);
                    skeletonScreen.hide();
                    Toast.makeText(getActivity(), "Something happened, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                loading.dismiss();
                ivPhone.setVisibility(View.VISIBLE);
                skeletonScreen.hide();
                Toast.makeText(getActivity(), "There's no available group yet", Toast.LENGTH_SHORT).show();
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

package com.digimaster.digicourse.digicyber.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.ForgetPassActivity;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements OnClickListener {

    FragmentActivity mContext;
    BaseApiService mApiService;
    ProgressDialog loading;
    SecuredPreference pref;
    EditText username, pass;
    TextView forgetpass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((HomeActivity) Objects.requireNonNull(getActivity()))
                .setActionBarTitle("Please Login");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
            }
        }, 2500);

        mContext = getActivity();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mApiService = UtilsApi.getAPIService();


        username = rootView.findViewById(R.id.etPhone);
        pass = rootView.findViewById(R.id.etAddress);
        forgetpass = rootView.findViewById(R.id.tvForgetPass);

        Button button = rootView.findViewById(R.id.btnLogin);
//        Button btnLogin2 = rootView.findViewById(R.id.btnLogin2);

        TextView txtRegister = rootView.findViewById(R.id.txtRegister);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
                login();
            }
        });

//        btnLogin2.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
//                login_admin();
//            }
//        });


        txtRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new RegisterFragment();
                replaceFragment(fragment);
            }
        });


        forgetpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        item.setVisible(false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtRegister:
                new FeaturedFragment();
                break;
        }
    }


    @SuppressLint("StaticFieldLeak")
        public void login (){

            //Log.d("logggIntent", "Berhasil method login");
            mApiService.loginRequest(username.getText().toString(), pass.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                    //String pesan = jsonRESULTS.getString("success");
                                    String pesanError = jsonRESULTS.getString("message");
                                    if (pesanError.equals("Logged In Successfully")) {
                                        JSONArray userId = jsonRESULTS.getJSONArray("user");
                                        JSONObject obj = userId.getJSONObject(0);
                                        String fullname = obj.getString("fullname");
                                        String email = obj.getString("email");
                                        String id = obj.getString("id");
                                        String phone = obj.getString("phone");
                                        String bday = obj.getString("birthday");


                                        //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
                                        //Log.d("logggIntent", "Berhasil abis Toast");
                                        pref.put(PrefContract.USER_FULL_NAME, fullname);
                                        pref.put(PrefContract.email, email);
                                        pref.put(PrefContract.phone, phone);
                                        pref.put(PrefContract.bday, bday);
                                        pref.put(PrefContract.check_login, "true");
                                        pref.put(PrefContract.user_id, id);
                                        pref.put(PrefContract.role, "member");


                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        //Log.d("logggIntent", "Berhasil put");
                                        new FeaturedFragment();
                                        //Log.d("logggIntent", "Berhasil intent");
                                        //finish();
                                    } else {
                                        Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException | AppException | IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                loading.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                            loading.dismiss();
                        }
                    });

    }

    @SuppressLint("StaticFieldLeak")
    public void login_admin(){

        //Log.d("logggIntent", "Berhasil method login");
        mApiService.loginAdminRequest(username.getText().toString(), pass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Logged In Successfully")) {
                                    JSONArray userId = jsonRESULTS.getJSONArray("user");
                                    JSONObject obj = userId.getJSONObject(0);
                                    String fullname = obj.getString("fullname");
                                    String email = obj.getString("email");
                                    String id = obj.getString("id");
                                    String phone = obj.getString("phone");
                                    String address = obj.getString("address");


                                    //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
                                    //Log.d("logggIntent", "Berhasil abis Toast");
                                    pref.put(PrefContract.USER_FULL_NAME, fullname);
                                    pref.put(PrefContract.email, email);
                                    pref.put(PrefContract.phone, phone);
                                    pref.put(PrefContract.address, address);
                                    pref.put(PrefContract.check_login, "true");
                                    pref.put(PrefContract.user_id, id);
                                    pref.put(PrefContract.role, "admin");


                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    //Log.d("logggIntent", "Berhasil put");
                                    new FeaturedFragment();
                                    //Log.d("logggIntent", "Berhasil intent");
                                    //finish();
                                } else {
                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_login, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }
}




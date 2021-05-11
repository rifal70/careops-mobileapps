package com.digimaster.digicourse.digicyber.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment  {
    final Calendar myCalendar = Calendar.getInstance();
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText fullname, email, password, alias, phone, address, school;
    Button btnRegis;
    ToggleSwitch genderSwitch;
    SecuredPreference pref;
    String uid;
    CountryCodePicker ccp;
    EditText fName, lName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity())
                .setActionBarTitle("Register now. It's easy");

        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);

        fName = rootView.findViewById(R.id.etFname);
        lName = rootView.findViewById(R.id.etLname);

        fullname = rootView.findViewById(R.id.etFullName);
        phone = rootView.findViewById(R.id.etPhone);
        password = rootView.findViewById(R.id.etPassword);
        email = rootView.findViewById(R.id.etEmail);
        alias = rootView.findViewById(R.id.etAlias);

        btnRegis = rootView.findViewById(R.id.btnRegisRegis);
        ccp = rootView.findViewById(R.id.ccp);

        ccp.registerPhoneNumberTextView(phone);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    public void register () {
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);


        try {
           uid = pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

//        if(isValidEmail(email.getText().toString())) {
            //Log.d("logggIntent", "Berhasil method login");
            mApiService.registerRequest(ccp.getFullNumber(), fName.getText().toString(), lName.getText().toString()
                    , alias.getText().toString(), password.getText().toString(), email.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                    String pesanError = jsonRESULTS.getString("code");
                                    if (pesanError.equals("200")) {
//                                    String userId = jsonRESULTS.getJSONObject("data").getString("user_id");
//                                    String token = jsonRESULTS.getString("token");

                                        //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
                                        //Log.d("logggIntent", "Berhasil abis Toast");
//                                    pref.put(PrefContract.PREF_TOKEN, token);
//                                    pref.put(PrefContract.user_id, userId);
//                                    pref.put(PrefContract.check_login, "true");

                                        Toast.makeText(getActivity(), "Berhasil Register", Toast.LENGTH_SHORT).show();

                                        Fragment fragment = null;
                                        fragment = new LoginFragment();
                                        replaceFragment(fragment);

                                       // Log.d("logggIntent", "Berhasil put");
                                        //Log.d("logggIntent", "Berhasil intent");
                                        //finish();
                                    } else {
                                        Toast.makeText(getActivity(), "E-mail sudah terdaftar!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                //if (jsonRESULTS.getString("message").equals("Successfully login.")) {
                                // Jika login berhasil maka data nama yang ada di response API
                                // akan diparsing ke activity selanjutnya.

                                            /*} else {
                                                // Jika login gagal
                                                String error = jsonRESULTS.getString("message");
                                                //loading.dismiss();
                                                Toast.makeText(mContext, "USERNAME / PASSWORD SALAH", Toast.LENGTH_LONG).show();
                                            }*/
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
//        }else{
//            loading.dismiss();
//            Toast.makeText(getActivity(), "Please fill the right email", Toast.LENGTH_SHORT).show();
//        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_regis, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }



}

package com.digimaster.digicourse.digicyber.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.LoginActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digimaster.digicourse.digicyber.Contract.PrefContract.email;
import static com.digimaster.digicourse.digicyber.Contract.PrefContract.user_id;

public class DeleteAccountFragment extends DialogFragment {
    BaseApiService mApiService;
    SecuredPreference pref;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_account, null);
        Button yesbtn = view.findViewById(R.id.yesdelete);
        Button nobtn = view.findViewById(R.id.nodelete);

        builder.setView(view);
        try {
            String passing_firstname = pref.getString(user_id, "");

            yesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    /////bawah ini apus
                    mApiService.deleteAcc(passing_firstname)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Nomor Berhasil diganti", Toast.LENGTH_SHORT).show();
                                        pref.clear();
                                        try {

                                            pref.put(PrefContract.check_login, "false");
                                            Log.d("logggOut", "berhasil logout");
                                            startActivity(new Intent(getContext(), LoginActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            //sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);

                                            logout();
                                        } catch (AppException e) {
                                            e.printStackTrace();
                                        }


                                    } else {
                                        Log.e("debug", "onFailure: ERROR > " + toString());
                                        Toast.makeText(getContext(), passing_firstname, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("debug", "onFailure: ERROR > " + t.toString());

                                }
                            });


                }
            });


        } catch (AppException e) {

        }

        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }


    private void logout() {

        mApiService.requestLogout(email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            JSONObject jsonRESULTS = null;
                            try {
                                jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("successfuly logout")) {

                                    pref.clear();
                                    pref.put(PrefContract.check_login, "false");

                                    String success_message = jsonRESULTS.getString("message");

                                    Toast.makeText(getActivity(), success_message, Toast.LENGTH_LONG).show();

                                    //Log.d("logggOut", "berhasil logout");
                                    startActivity(new Intent(getActivity(), LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    getActivity().finish();


                                } else {

                                    Toast.makeText(getActivity(), "Gagal Logout", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
package com.digimaster.digicourse.digicyber.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;

import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class RegisterTeacherFragment extends Fragment {
    Button btnRegisTeach;
    final Calendar myCalendar = Calendar.getInstance();
    BaseApiService mApiService;
    ProgressDialog loading;
    EditText fullname, email, password, bday, phone, address, price, exp;
    ToggleSwitch genderSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_register_teacher, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity) getActivity())
                .setActionBarTitle("Register now. It's easy");


        mApiService = UtilsApi.getAPIService();
        btnRegisTeach = rootView.findViewById(R.id.btnRegisterTeach);
        bday = rootView.findViewById(R.id.etTtl);
        fullname = rootView.findViewById(R.id.etFullName);
        email = rootView.findViewById(R.id.etPhone);
        password = rootView.findViewById(R.id.etPass);
        phone = rootView.findViewById(R.id.etPhone);
        address = rootView.findViewById(R.id.etAddress);
        price = rootView.findViewById(R.id.etRate);
        exp = rootView.findViewById(R.id.etPengalaman);
        genderSwitch = rootView.findViewById(R.id.toogleGender_register);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Log.d("logggC","harusnya udah muncul");
            }
        });

        btnRegisTeach.setOnClickListener(new View.OnClickListener() {
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
        int kode_gender = genderSwitch.getCheckedTogglePosition();
        String nampung_gender;
        if(kode_gender == 0){
            nampung_gender = "M";
        }else{
            nampung_gender = "F";
        }

        /*if(isValidEmail(email.getText().toString())) {
            Log.d("logggIntent", "Berhasil method login");
            mApiService.registerTeachRequest(fullname.getText().toString(), bday.getText().toString(), phone.getText().toString(),
                    address.getText().toString(), price.getText().toString(), exp.getText().toString(), email.getText().toString(),
                    password.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                    String pesanError = jsonRESULTS.getString("message");
                                    if (pesanError.equals("Account Successfully Created")) {
//                                    String userId = jsonRESULTS.getJSONObject("data").getString("user_id");
//                                    String token = jsonRESULTS.getString("token");

                                        //Toast.makeText(mContext, "" + pesan + " " + userId, Toast.LENGTH_SHORT).show();
                                        Log.d("logggIntent", "Berhasil abis Toast");
//                                    pref.put(PrefContract.PREF_TOKEN, token);
//                                    pref.put(PrefContract.user_id, userId);
//                                    pref.put(PrefContract.check_login, "true");

                                        Toast.makeText(getActivity(), "BERHASIL REGISTER", Toast.LENGTH_SHORT).show();

                                        Fragment fragment = null;
                                        fragment = new LoginFragment();
                                        replaceFragment(fragment);

                                        Log.d("logggIntent", "Berhasil put");
                                        Log.d("logggIntent", "Berhasil intent");
                                        //finish();
                                    } else {
                                        Toast.makeText(getActivity(), "" + pesanError, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                //if (jsonRESULTS.getString("message").equals("Successfully login.")) {
                                // Jika login berhasil maka data nama yang ada di response API
                                // akan diparsing ke activity selanjutnya.

                                            *//*} else {
                                                // Jika login gagal
                                                String error = jsonRESULTS.getString("message");
                                                //loading.dismiss();
                                                Toast.makeText(mContext, "USERNAME / PASSWORD SALAH", Toast.LENGTH_LONG).show();
                                            }*//*
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
        }else{
            loading.dismiss();
            Toast.makeText(getActivity(), "Please fill the right email", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_regis, someFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        bday.setText(sdf.format(myCalendar.getTime()));
    }
}

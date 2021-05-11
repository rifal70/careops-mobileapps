package com.digimaster.digicourse.digicyber.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.ContactusActivity;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Activity.LoginActivity;
import com.digimaster.digicourse.digicyber.Activity.OptionSettingActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    SecuredPreference pref;
    String passing_full, passing_email, passing_gender, passing_userid, passing_phone, passing_firstname,
            passing_lastname, passing_nickname, passing_institution, passing_position, passing_photo;
    EditText full_name, email, birth, phone, first_name, last_name, nickname, institution, position;
    ToggleSwitch toggleSwitchProfile;
    CircleImageView iv_profile, ivPpSelect;
    int positionM = 0, positionF = 1;
    Uri imageuri;
    BaseApiService mApiService;
    public static final int IMAGE_CODE = 1;
    AlertDialog loading;
    View alertDialogView;
    FragmentTransaction ft;
    FragmentManager fragmentManager;
    Button savechange, edit, discard;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("My Profile");


        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
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
        mApiService = UtilsApi.getAPIService();

        alertDialogView = LayoutInflater.from(getActivity()).inflate
                (R.layout.loading_layout, null);
        loading = new AlertDialog.Builder(getActivity()).setView(alertDialogView).create();
        first_name = view.findViewById(R.id.etFirstname_profile);

        email = view.findViewById(R.id.etEmail_profile);
        toggleSwitchProfile = view.findViewById(R.id.toogleGender_profile);

        phone = view.findViewById(R.id.etPhone_profile);
        iv_profile = view.findViewById(R.id.profile_image);
        ivPpSelect = view.findViewById(R.id.profileImageSelect);

        last_name = view.findViewById(R.id.etLastname_profile);
        nickname = view.findViewById(R.id.etNickname_profile);

        institution = view.findViewById(R.id.etInstitution_profile);
        position = view.findViewById(R.id.position_profile);
        discard = view.findViewById(R.id.btnDiscard);
        savechange = view.findViewById(R.id.btnSavechange);
        edit = view.findViewById(R.id.btnEdit_profile);

        try {
            passing_firstname = pref.getString(PrefContract.first_name, "");
            passing_userid = pref.getString(PrefContract.user_id, "");
            passing_email = pref.getString(PrefContract.email, "");
            passing_gender = pref.getString(PrefContract.gender, "");
            passing_phone = pref.getString(PrefContract.phone, "");
            passing_photo = pref.getString(PrefContract.photo, "");

            passing_lastname = pref.getString(PrefContract.last_name, "");
            passing_nickname = pref.getString(PrefContract.username, "");

            passing_institution = pref.getString(PrefContract.institution, "");
            passing_position = pref.getString(PrefContract.position, "");

            if (passing_institution.equals("") || passing_position.equals("")) {
                institution.setVisibility(view.GONE);
                position.setVisibility(view.GONE);

            } else {
                institution.setText(passing_institution);
                position.setText(passing_position);
            }

        } catch (AppException e) {
            e.printStackTrace();
        }

        nickname.setText(passing_nickname);
        last_name.setText(passing_lastname);
        first_name.setText(passing_firstname);
        email.setText(passing_email);

        phone.setText(passing_phone);
        if (passing_institution.equals("") || passing_position.equals("")) {
            institution.setVisibility(view.GONE);
            position.setVisibility(view.GONE);

        } else {
            institution.setText(passing_institution);
            position.setText(passing_position);
        }

        if(!passing_photo.equals("")){
            Picasso.with(getActivity())
                    .load(getResources().getString(R.string.base_url_asset_profile)+passing_photo)
                    .into(iv_profile);
        }else{
            Picasso.with(getActivity())
                    .load(R.drawable.profile)
                    .into(iv_profile);
        }

        //PENINGPALA -> it's easy bro take it easy

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivPpSelect.setVisibility(View.VISIBLE);

                first_name.setEnabled(true);
                last_name.setEnabled(true);
                nickname.setEnabled(true);

            }
        });


        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name.setFocusable(true);
                first_name.setFocusableInTouchMode(true);
                last_name.setEnabled(false);
                first_name.setEnabled(false);
                nickname.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                institution.setEnabled(false);
                position.setEnabled(false);
                ivPpSelect.setVisibility(View.INVISIBLE);

                last_name.setText(passing_lastname);
                first_name.setText(passing_firstname);
                email.setText(passing_email);
                nickname.setText(passing_nickname);
                phone.setText(passing_phone);
            }
        });

        ivPpSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                panggilgambar();

            }
        });


        savechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bitmap = ((BitmapDrawable) iv_profile.getDrawable()).getBitmap();

                updateData(bitmap);


            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    private void panggilgambar() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);


        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();


                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));

                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    iv_profile.setImageBitmap(scaled);

                    Toast.makeText(getActivity(), "Upload image success.", Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //pictureFlag = 1;

                //ivTemp.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                Toast.makeText(getActivity(), "Image Error.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private Bitmap getDecodedImageFromUri(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rect rect = new Rect(0, 0, 0, 0);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        options.inSampleSize = getInSampleSize(options, 128, 128);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, rect, options);

        return bitmap;
    }

    private int getInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "_andro.JPEG");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @SuppressLint("StaticFieldLeak")
    public void updateData(Bitmap bitmap) {

        loading.show();
        loading.getWindow().setLayout(250, 250);

        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        RequestBody phoneRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_phone);
        RequestBody uidRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), passing_userid);
        RequestBody firstNameRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), first_name.getText().toString());
        RequestBody lastNameRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), last_name.getText().toString());
        RequestBody nicknameRB =
                RequestBody.create(MediaType.parse("multipart/form-data"), nickname.getText().toString());

//        if(isValidEmail(email.getText().toString())) {
        //Log.d("logggIntent", "Berhasil method login");
        mApiService.registerfoto(uidRB, firstNameRB, lastNameRB, nicknameRB, phoneRB, image)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");
                                String pesan = jsonRESULTS.getString("message");
                                String img = jsonRESULTS.getString("new_image");
                                if (pesanError.equals("200")) {

                                    Toast.makeText(getActivity(), "Update data success.", Toast.LENGTH_SHORT).show();

                                    ivPpSelect.setVisibility(View.INVISIBLE);
                                    last_name.setEnabled(false);
                                    first_name.setEnabled(false);
                                    nickname.setEnabled(false);

                                    try {
                                        pref.put(PrefContract.first_name, first_name.getText().toString());
                                        pref.put(PrefContract.last_name, last_name.getText().toString());
                                        pref.put(PrefContract.username, nickname.getText().toString());
                                        pref.put(PrefContract.photo, img);

                                        passing_firstname = first_name.getText().toString();
                                        passing_lastname = last_name.getText().toString();
                                        passing_nickname = nickname.getText().toString();
                                        passing_photo = img;
                                    } catch (AppException e) {
                                        e.printStackTrace();
                                    }

                                } else {

                                    Toast.makeText(getActivity(), "Update failed, please try again.", Toast.LENGTH_SHORT).show();

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
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
//        }else{
//            loading.dismiss();
//            Toast.makeText(getActivity(), "Please fill the right email", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_option) {


            Intent intent = new Intent(getActivity(), OptionSettingActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem item_more = menu.findItem(R.id.action_tiga_titik_hitam);
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem contactus = menu.findItem(R.id.contactus_option);
        MenuItem termsofservice = menu.findItem(R.id.termsofservice_option);
        MenuItem privacypolicy = menu.findItem(R.id.privacyandpolicy_option);
        MenuItem settings = menu.findItem(R.id.settings_option);
        //searchView.setMenuItem(item);
        item.setVisible(false);
        item_more.setVisible(true);
        contactus.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent = new Intent(getActivity(), ContactusActivity.class);
                startActivity(intent);
                return true;
            }
        });
        termsofservice.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        privacypolicy.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                logout();

                return false;
            }
        });

    }

    private void logout() {
        loading.show();
        loading.getWindow().setLayout(250, 250);
        mApiService.requestLogout(passing_email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
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
                                    loading.dismiss();
                                    Toast.makeText(getActivity(), "Gagal Logout", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

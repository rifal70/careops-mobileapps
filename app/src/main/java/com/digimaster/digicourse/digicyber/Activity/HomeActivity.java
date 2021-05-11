package com.digimaster.digicourse.digicyber.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.EventFragment;
import com.digimaster.digicourse.digicyber.Fragment.FaqFragment;
import com.digimaster.digicourse.digicyber.Fragment.FeaturedFragment;
import com.digimaster.digicourse.digicyber.Fragment.FindTeacherFragment;
import com.digimaster.digicourse.digicyber.Fragment.LibraryFragment;
import com.digimaster.digicourse.digicyber.Fragment.ListGroupScoreAdmin;
import com.digimaster.digicourse.digicyber.Fragment.LoginFragment;
import com.digimaster.digicourse.digicyber.Fragment.NewsFragment;
import com.digimaster.digicourse.digicyber.Fragment.ProfileFragment;
import com.digimaster.digicourse.digicyber.Fragment.RequestFragment;
import com.digimaster.digicourse.digicyber.Fragment.TaskFragment;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MaterialSearchView searchView;
    SecuredPreference pref;
    String passing, email, gender, uid, lvl, project;
    NavigationView navigationView;
    TextView user;
    BaseApiService mApiService;
    AlertDialog loading;
    Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    View alertDialogView;
    DrawerLayout drawer;
    String role;
    NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        alertDialogView = LayoutInflater.from(this).inflate
                (R.layout.loading_layout, null);

        drawer = findViewById(R.id.drawer_layout);

        loading = new AlertDialog.Builder(this).setView(alertDialogView).create();

        PageNavigationView tab = findViewById(R.id.tab);


        try {
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }


        if (role.equals("admin")) {
            navigationController = tab.material()
                    .addItem(R.drawable.ic_home_black_18dp, "Home")
                    .addItem(R.drawable.library, "Library")
                    .addItem(R.drawable.task, "Score")
                    .addItem(R.drawable.profile, "Profile")
                    .build();
        } else {
            navigationController = tab.material()
                    .addItem(R.drawable.ic_home_black_18dp, "Home")
                    .addItem(R.drawable.library, "Library")
                    .addItem(R.drawable.task, "Task")
                    .addItem(R.drawable.profile, "Profile")
                    .build();
        }


        navigationController.addSimpleTabItemSelectedListener(new SimpleTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index) {
                    case 0:
                        fragment = new FeaturedFragment();
                        //Log.d("logI","harusnya udah masuk intent extra");
                        fragmentManager = getSupportFragmentManager();
                        ft = fragmentManager.beginTransaction();

                        ft.replace(R.id.screen_area, fragment);
                        ft.commit();
                        break;

                    case 1:
                        fragment = new LibraryFragment();

                        //Log.d("logI","harusnya udah masuk intent extra");
                        fragmentManager = getSupportFragmentManager();
                        ft = fragmentManager.beginTransaction();

                        ft.replace(R.id.screen_area, fragment);
                        ft.commit();
                        break;

                    case 2:
                        if (role.equals("admin")) {
                            fragment = new ListGroupScoreAdmin();

                        } else {
                            fragment = new TaskFragment();

                        }
                        //Log.d("logI","harusnya udah masuk intent extra");
                        fragmentManager = getSupportFragmentManager();
                        ft = fragmentManager.beginTransaction();

                        ft.replace(R.id.screen_area, fragment);
                        ft.commit();

                        break;


                    case 3:
                        fragment = new ProfileFragment();

                        //Log.d("logI","harusnya udah masuk intent extra");
                        fragmentManager = getSupportFragmentManager();
                        ft = fragmentManager.beginTransaction();

                        ft.replace(R.id.screen_area, fragment);
                        ft.commit();
                        break;

                }
            }
        });

        if (savedInstanceState == null) {
            Fragment fragment = new FeaturedFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }


        if (getIntent().getStringExtra("EXTRA") != null) {
            Fragment fragment = null;
            switch (getIntent().getStringExtra("EXTRA")) {
                case "openFragmentFeat":
                    fragment = new FeaturedFragment();
                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentFind":
                    fragment = new FindTeacherFragment();
                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentVouc":
                    fragment = new LibraryFragment();

                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentWish":
                    fragment = new EventFragment();

                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentNews":
                    fragment = new NewsFragment();

                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentSign":
                    fragment = new LoginFragment();

                    //Log.d("logI","harusnya udah masuk intent extra");
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openProfile":

                    fragment = new ProfileFragment();
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentFaq":

                    fragment = new FaqFragment();
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;

                case "openFragmentGroup":

                    fragment = new RequestFragment();
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();

                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;


                default:
                    fragmentManager = getSupportFragmentManager();
                    ft = fragmentManager.beginTransaction();
                    fragment = new FeaturedFragment();
                    ft.replace(R.id.screen_area, fragment);
                    ft.commit();
                    break;
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

//        navigationView = findViewById(R.id.nav_view);
//        Menu nav_Menu = navigationView.getMenu();
//        View header = navigationView.getHeaderView(0);
//        TextView username = header.findViewById(R.id.menu_name);
//        ImageView userphoto = header.findViewById(R.id.ivUserpic);


        try {
            passing = pref.getString(PrefContract.check_login, "");
            String user_id = pref.getString(PrefContract.USER_FULL_NAME, "");
            email = pref.getString(PrefContract.email, "");
            gender = pref.getString(PrefContract.gender, "");
            uid = pref.getString(PrefContract.user_id, "");
            project = pref.getString(PrefContract.project, "");


            //Log.d("logggLVL", ""+lvl);

            //Log.d("logggEmail", ""+email);
            //Toast.makeText(this, ""+passing, Toast.LENGTH_SHORT).show();
            if (passing.equals("true")) {
//                if (gender.equals("M")) {
//                    userphoto.setImageResource(R.drawable.profile);
//                } else {
//                    userphoto.setImageResource(R.drawable.female);
//                }
//
//                username.setText(user_id);
//                nav_Menu.findItem(R.id.act_signin).setVisible(false);
//                nav_Menu.findItem(R.id.act_signout).setVisible(true);
//
//                userphoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //Toast.makeText(mContext, "Yeay berhasil", Toast.LENGTH_SHORT).show();
//                        Fragment fragment = null;
//                        fragment = new ProfileFragment();
//                        if (fragment != null) {
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                            ft.replace(R.id.screen_area, fragment);
//                            ft.commit();
//
//                        }
//
//                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                        drawer.closeDrawer(GravityCompat.START);
//                    }
//                });
            }


        } catch (AppException e) {
            e.printStackTrace();
        }


//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        searchView = findViewById(R.id.search_view);

        try {
            lvl = pref.getString(PrefContract.isteach, "");

            if (lvl.equalsIgnoreCase("Y")) {
                //Toast.makeText(mContext, "harusnya berhasil", Toast.LENGTH_LONG).show();
                //nav_Menu.findItem(R.id.nav_notification).setVisible(true);
            }

        } catch (AppException e) {
            e.printStackTrace();
        }

        //loadCert();

    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    Fragment fragment = null;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_featured:
                fragment = new FeaturedFragment();
                break;
            case R.id.nav_findteacher:
                fragment = new FindTeacherFragment();
                break;
            case R.id.nav_group:
                fragment = new RequestFragment();
                break;
            case R.id.nav_mycourse:
                Intent intent = new Intent(HomeActivity.this, SimpleTabsActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                startActivity(intent, options.toBundle());
                break;
            case R.id.nav_event:
                fragment = new EventFragment();
                break;
            case R.id.nav_announce:
                fragment = new NewsFragment();
                break;
            case R.id.nav_library:
                fragment = new LibraryFragment();
                break;
            case R.id.nav_faq:
                fragment = new FaqFragment();
                break;
            case R.id.act_signin:
                fragment = new LoginFragment();
                break;
            case R.id.act_signout:
                Logout();
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.right, R.anim.exit_left);
            ft.replace(R.id.screen_area, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout() {
        loading.show();
        loading.getWindow().setLayout(250, 250);
        mApiService.requestLogout(email)
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

                                    Toast.makeText(mContext, success_message, Toast.LENGTH_LONG).show();

                                    //Log.d("logggOut", "berhasil logout");
                                    startActivity(new Intent(HomeActivity.this, HomeActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    finish();


                                } else {
                                    loading.dismiss();
                                    Toast.makeText(mContext, "Gagal Logout", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | AppException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

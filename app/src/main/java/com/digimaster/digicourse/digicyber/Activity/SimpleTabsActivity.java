package com.digimaster.digicourse.digicyber.Activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.FeaturedFragment;
import com.digimaster.digicourse.digicyber.Fragment.FinishedTaskFragment;
import com.digimaster.digicourse.digicyber.Fragment.LearnFragment;
import com.digimaster.digicourse.digicyber.Fragment.MyCoursesFragment;
import com.digimaster.digicourse.digicyber.Fragment.MyAchievementFragment;
import com.digimaster.digicourse.digicyber.Fragment.ProfileFragment;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SimpleTabsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    NavigationView navigationView;
    MaterialSearchView searchView;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    SecuredPreference pref;
    String passing, email, gender;
    ViewPagerAdapter adapter;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tabs);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        pref = new SecuredPreference(this, PrefContract.PREF_EL);


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        PageNavigationView tab = findViewById(R.id.tab);


        NavigationController navigationController = tab.material()
                .addItem(R.drawable.ic_home_black_18dp, "Home")
                .addItem(R.drawable.checklist, "Task")
                .addItem(R.drawable.ic_book, "Learn")
                .addItem(R.drawable.user_icon, "Profile")
                .build();

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
//                        fragment = new TaskFragment();
//                        //Log.d("logI","harusnya udah masuk intent extra");
//                        fragmentManager = getSupportFragmentManager();
//                        ft = fragmentManager.beginTransaction();
//
//                        ft.replace(R.id.screen_area, fragment);
//                        ft.commit();

                        Intent intent = new Intent(SimpleTabsActivity.this, SimpleTabsActivity.class);
                        ActivityOptions options =
                                ActivityOptions.makeCustomAnimation(SimpleTabsActivity.this, R.anim.right, R.anim.exit_left);
                        startActivity(intent, options.toBundle());

                        break;

                    case 2:
                        fragment = new LearnFragment();

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

        try {
            passing = pref.getString(PrefContract.check_login, "");
            email = pref.getString(PrefContract.email, "");
            gender = pref.getString(PrefContract.gender, "");

            String fullname = pref.getString(PrefContract.USER_FULL_NAME, "");
            //Toast.makeText(this, ""+passing, Toast.LENGTH_SHORT).show();

        } catch (AppException e) {
            e.printStackTrace();
            //Log.e("logggSimpleTab", "onCreate: "+e);
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyCoursesFragment(), "UNFINISHED TASK");
        adapter.addFragment(new FinishedTaskFragment(), "FINISHED TASK");
        adapter.addFragment(new MyAchievementFragment(), "SCORES");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.nav_featured:
                Intent intent = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intent.putExtra("EXTRA", "openFragmentFeat");
                startActivity(intent, options.toBundle());
                break;
            case R.id.nav_findteacher:
                Intent intentSimpleTab = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsSimpleTab =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentSimpleTab.putExtra("EXTRA", "openFragmentFind");
                startActivity(intentSimpleTab, optionsSimpleTab.toBundle());
                break;
//            case R.id.nav_mycourse:
//                Intent intentMyCourse = new Intent (SimpleTabsActivity.this, HomeActivity.class);
//                ActivityOptions optionsMyCourse =
//                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
//                intentMyCourse.putExtra("EXTRA", "openFragmentCour");
//                startActivity(intentMyCourse, optionsMyCourse.toBundle());
//                break;
            case R.id.nav_event:
                Intent intentWish = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsWish =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentWish.putExtra("EXTRA", "openFragmentWish");
                startActivity(intentWish, optionsWish.toBundle());
                break;
            case R.id.nav_announce:
                Intent intentSett = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsSett =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentSett.putExtra("EXTRA", "openFragmentNews");
                startActivity(intentSett, optionsSett.toBundle());
                break;
            case R.id.nav_library:
                Intent intentLibb = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsLib =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentLibb.putExtra("EXTRA", "openFragmentVouc");
                startActivity(intentLibb, optionsLib.toBundle());
                break;
            case R.id.nav_faq:
                Intent intentFaq = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsFaq =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentFaq.putExtra("EXTRA", "openFragmentFaq");
                startActivity(intentFaq, optionsFaq.toBundle());
                break;
            case R.id.nav_group:
                Intent intentGroup = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsGroup =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentGroup.putExtra("EXTRA", "openFragmentGroup");
                startActivity(intentGroup, optionsGroup.toBundle());
                break;
            case R.id.act_signin:
                Intent intentSignIn = new Intent(SimpleTabsActivity.this, HomeActivity.class);
                ActivityOptions optionsSignIn =
                        ActivityOptions.makeCustomAnimation(this, R.anim.right, R.anim.exit_left);
                intentSignIn.putExtra("EXTRA", "openFragmentSign");
                startActivity(intentSignIn, optionsSignIn.toBundle());
                break;
            case R.id.act_signout:
                Logout();
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout_simple_tabs);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new FinishedTaskFragment();

                case 2:
                    return new MyAchievementFragment();

                default:
                    return new MyCoursesFragment();
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position == 0) {
                new MyCoursesFragment(); //Refresh what you need on this fragment
            } else if (position == 1) {
                new FinishedTaskFragment();
            } else if (position == 2) {
                new MyAchievementFragment();
            }
        }
    }

    private void Logout() {
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        mApiService.requestLogout(email)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("successfuly logout")) {

                                    pref.clear();
                                    pref.put(PrefContract.check_login, "false");

                                    String success_message = jsonRESULTS.getString("message");

                                    Toast.makeText(mContext, success_message, Toast.LENGTH_LONG).show();

                                    Log.d("logggOut", "berhasil logout");
                                    startActivity(new Intent(SimpleTabsActivity.this, HomeActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    //sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);

                                    finish();
                                } else {
                                    //String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, "Gagal logout", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException | AppException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                            Toast.makeText(mContext, "Gagal Logout", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

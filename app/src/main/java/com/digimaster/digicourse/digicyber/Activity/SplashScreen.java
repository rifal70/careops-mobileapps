package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.util.AppException;

public class SplashScreen extends AppCompatActivity {
    SecuredPreference pref;
    String status;

    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);
        prefs = getSharedPreferences("com.digimaster.digicourse.digicyber", MODE_PRIVATE);

        try {
            status = pref.getString(PrefContract.check_login, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            startActivity(new Intent(getApplicationContext(), LandingActivity.class));
            finish();

            prefs.edit().putBoolean("firstrun", false).apply();
        }else {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (status.equals("true")) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }, 1000L); //3000 L = 3 detik
        }
    }
}

package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.digimaster.digicourse.digicyber.Fragment.DeleteAccountFragment;
import com.digimaster.digicourse.digicyber.R;

public class OptionSettingActivity extends AppCompatActivity {
Toolbar toolbar2;
    Button notif1,notif2,pass1,pass2,phone1,phone2,delete1,delete2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    setContentView(R.layout.option_setting_profile);
        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar2.setTitle("Setting");

        notif1= findViewById(R.id.notifsetting_option1);
        notif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              {


                  Intent intent = new Intent(OptionSettingActivity.this, NotificationSettingActivity.class);
                  startActivity(intent);



              }
            }
        });
        notif2= findViewById(R.id.notifsetting_option2);
        notif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {


                    Intent intent = new Intent(OptionSettingActivity.this, NotificationSettingActivity.class);
                    startActivity(intent);


                }
            }
        });
        delete1=findViewById(R.id.deleteaccount_option1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccountFragment deleteAccountFragment = new DeleteAccountFragment();
                deleteAccountFragment.show(getSupportFragmentManager(),"  ");
            }
        });
        delete2=findViewById(R.id.deleteaccount_option2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccountFragment deleteAccountFragment = new DeleteAccountFragment();
             deleteAccountFragment.show(getSupportFragmentManager()," ");

            }
        });

       pass1=findViewById(R.id.changepass_option1);
        pass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);

            }
        });

        pass2=findViewById(R.id.changepass_option2);
        pass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


        phone1=findViewById(R.id.changephone_option1);
        phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSettingActivity.this, ChangePhoneActivity.class);
                startActivity(intent);

            }
        });

        phone2=findViewById(R.id.changephone_option2);
        phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionSettingActivity.this, ChangePhoneActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

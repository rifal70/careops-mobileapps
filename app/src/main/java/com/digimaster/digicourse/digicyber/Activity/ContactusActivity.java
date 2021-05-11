package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.digimaster.digicourse.digicyber.R;

public class ContactusActivity extends AppCompatActivity {


    Toolbar toolbar;
    EditText teks,email;
    Button kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus_activity);

        toolbar = findViewById(R.id.contactus_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setTitle("Contact Us");

        teks = findViewById(R.id.contactus_meesage);



        kirim = findViewById(R.id.contactus_btnsend);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                emailIntent.setType("plain/text");
//                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{sender});
//                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "dig");
//                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi");
//                startActivity(emailIntent);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"careops@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "CareOps");
                i.putExtra(Intent.EXTRA_TEXT   ,teks.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Choose email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    // handle edge case where no email client is installed
                }

//Kalau mau dicoba ke edcoreprogram@gmail.com tlg dikasi note:digilearning ya🙏
//String sub = "digilearning";
//    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE, Uri.fromParts(
//            "mailto","tryjuardi@gmail.com", null));
//intent.putExtra(Intent.EXTRA_SUBJECT,new String[]{sub});
//intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.toString()});
//intent.putExtra(Intent.EXTRA_TEXT,new String[]{teks.toString()});
//    startActivity(intent);

            }
        });


    }

}

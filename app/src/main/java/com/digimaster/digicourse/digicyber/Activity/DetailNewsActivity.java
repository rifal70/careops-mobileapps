package com.digimaster.digicourse.digicyber.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.R;

public class DetailNewsActivity extends AppCompatActivity {
    TextView title, isi;
    String pass_tit, pass_isi,posted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.tvTitDetailNews);
        isi = findViewById(R.id.tvIsiDetailnews);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.getString("title") != null || bundle.getString("isi") != null ||
                    bundle.getString("posted") != null) {
                pass_tit = bundle.getString("title");
                pass_isi = bundle.getString("isi");
                posted = bundle.getString("posted");

                title.setText(pass_tit);
                isi.setText(pass_isi);

            } else {
                //Log.d("logggError", "bundle error");
            }
        }

    }
}

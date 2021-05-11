package com.digimaster.digicourse.digicyber.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.digimaster.digicourse.digicyber.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebViewActivity extends AppCompatActivity {
    PDFView pdfView;
    String url_pdf, cour_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle.getString("pdf") != null) {
            url_pdf = bundle.getString("pdf");
        }


        pdfView = findViewById(R.id.pdfView);

//        Uri myUri = Uri.parse("assets.digicourse.id/digilearn/" + url_pdf);
//
//        pdfView.fromUri(myUri)
//                .load();

        new RetrivePDFStream().execute(this.getResources().getString(R.string.base_url_asset) +"sub_library/pdf/"+ url_pdf);



//        wb.getSettings().setJavaScriptEnabled(true);
//        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//        wb.loadUrl("http://docs.google.com/viewerng/viewer?url=https://digicourse.id/certificate/get_certificate_/"+id+"/"+cour_id);

    }

    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }


    }
}

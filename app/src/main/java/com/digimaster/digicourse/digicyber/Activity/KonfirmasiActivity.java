package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.util.AppException;

public class KonfirmasiActivity extends AppCompatActivity {

    Button btnHome;
    SecuredPreference pref;
    TextView txtBank, tvNamarek, tvStat, tvJml, tvNoRek;
    String passing, pnama_rek, pprice, pstat, pass_no_rek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
        txtBank = findViewById(R.id.txtNamaBank);
        tvNamarek = findViewById(R.id.tvNama_rek);
        tvStat = findViewById(R.id.tvStat);
        tvJml = findViewById(R.id.tvTotalTf);
        tvNoRek = findViewById(R.id.tvNoRek);

        pref = new SecuredPreference(this, PrefContract.PREF_EL);

        try {
            passing = pref.getString(PrefContract.payment_state,"");
            pnama_rek = pref.getString(PrefContract.nama_rekening,"");
            pprice = pref.getString(PrefContract.price,"");
            pstat = pref.getString(PrefContract.status_pembayaran,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        if(passing.equals("0")){
            passing =  "PT Bank Central Asia Tbk";
            pass_no_rek = "3100 837837";

        }
        if(passing.equals("1")){
            passing = "PT Bank Mandiri Tbk";
            pass_no_rek = "3100 837837";
        }
        if(passing.equals("2")){
            passing = "PT Bank Negara Indonesia Tbk";
            pass_no_rek = "3100 837837";
        }

        txtBank.setText(passing);
        //tvNamarek.setText(pnama_rek);
        tvJml.setText("Rp. "+pprice);
        tvStat.setText(pstat);
        tvNoRek.setText(pass_no_rek);

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent =  new Intent(KonfirmasiActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        });
    }
}

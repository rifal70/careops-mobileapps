package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.digimaster.digicourse.digicyber.R;
import com.keenfin.audioview.AudioService;
import com.keenfin.audioview.AudioView;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    AudioView audio;
    String fileAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        audio = findViewById(R.id.avAudio);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle.getString("audio") != null) {
            fileAudio = bundle.getString("audio");
        }


        Intent audioService = new Intent(this, AudioService.class);
        audioService.setAction(AudioService.ACTION_START_AUDIO);
        startService(audioService);
        try {
            audio.setDataSource(this.getResources().getString(R.string.base_url_asset)+"audio/" + fileAudio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

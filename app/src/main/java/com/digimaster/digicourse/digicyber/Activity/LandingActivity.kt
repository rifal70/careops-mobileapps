package com.digimaster.digicourse.digicyber.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digimaster.digicourse.digicyber.R
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnStartLanding.setOnClickListener{
            val intentAje = Intent(this, IntroActivity::class.java)
            startActivity(intentAje)
            finish()
        }
    }
}
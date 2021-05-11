package com.digimaster.digicourse.digicyber.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Adapter.SliderAdapter;
import com.digimaster.digicourse.digicyber.R;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mSliderViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;

    private Button next;
    private TextView skip;

    private int mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mSliderViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        next = findViewById(R.id.button5);
        skip = findViewById(R.id.button3);

        sliderAdapter = new SliderAdapter(this);

        mSliderViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSliderViewPager.addOnPageChangeListener(viewListener);

        skip.setOnClickListener(view -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        next.setOnClickListener(view -> {
            if (mCurrentPage == 2) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                mSliderViewPager.setCurrentItem(mCurrentPage + 1);
            }

        });

    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotLayout.removeAllViews();


        for (int t = 0; t < mDots.length; t++) {
            mDots[t] = new TextView(this);
            mDots[t].setText(Html.fromHtml("&#8226"));
            mDots[t].setTextSize(35);
            mDots[t].setTextColor(getResources().getColor(R.color.dark_transparent));

            mDotLayout.addView(mDots[t]);

        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            mCurrentPage = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

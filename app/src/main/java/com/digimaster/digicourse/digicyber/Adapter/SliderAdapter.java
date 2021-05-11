package com.digimaster.digicourse.digicyber.Adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.R;

public class SliderAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.mContext = context;

    }

    public int[] slide_images = {
            R.drawable.think,
            R.drawable.collaborate,
            R.drawable.learn,
    };

    public String[] slide_headings = {
            "THINK",
            "LEARN",
            "COLLABORATE",
    };

    public String[] slide_isi = {
            "A brilliant idea is never going to be the best idea\n" +
                    "without sharing it with your teammate. ",
            "Gather everyone's knowledge to achieve\n" +
                    " common goal within the team.",
            "Gather everyone's knowledge to achieve\n" +
                    " common goal within the team.",
    };


    @Override
    public int getCount(){
        return slide_images.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slideImage);
        TextView slideHeading = view.findViewById(R.id.tvHead);
        TextView slideBody = view.findViewById(R.id.tvBody);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideBody.setText(slide_isi[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}

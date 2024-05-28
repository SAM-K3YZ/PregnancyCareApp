package com.finalYearProject.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.finalYearProject.myapplication.R;
import com.finalYearProject.myapplication.adapters.ViewPagerAdapter;

public class onBoardingScreen extends AppCompatActivity {

    ViewPager slideViewPager;
    TextView skipBtn;
    ImageView rightBtn, leftBtn;
    LinearLayout dotLayout;
    ViewPagerAdapter sViewPagerAdapter;
    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.OBmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        skipBtn = findViewById(R.id.skipText);
        rightBtn = findViewById(R.id.rightArrow);
        leftBtn = findViewById(R.id.leftArrow);

        slideViewPager = findViewById(R.id.slideViewPager);
        dotLayout = findViewById(R.id.indicatorLayout);
        sViewPagerAdapter = new ViewPagerAdapter(this);

        slideViewPager.setAdapter(sViewPagerAdapter);
        setUpIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 3) {
                    slideViewPager.setCurrentItem(getItem(-1), true);
                }
                //Toast.makeText(onBoardingScreen.this, "Left button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getItem(0) < 2) {
                    slideViewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent in = new Intent(onBoardingScreen.this, SignUp.class);
                    startActivity(in);
                    finish();
                }
//                Toast.makeText(onBoardingScreen.this, "Right button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(onBoardingScreen.this, SignUp.class);
                startActivity(inte);
                finish();
                //Toast.makeText(onBoardingScreen.this, "Skip button clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setUpIndicator(int position) {

        dots = new TextView[3];
        dotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_light_screen1));
            dotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.dot_light_screen2));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpIndicator(position);

            if (position > 0) {
                leftBtn.setVisibility(View.VISIBLE);
            } else {
                leftBtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }
}
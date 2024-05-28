package com.finalYearProject.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalYearProject.myapplication.R;

public class SplashScreen extends AppCompatActivity {

    private TextView logoText;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SSmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash_screen);

        logoText = findViewById(R.id.SStextLogo);
        Animation logoTextAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.movement_animation);
        logoText.startAnimation(logoTextAnimation);
        logoTextAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoText.setVisibility(View.VISIBLE);
                final String animateText = logoText.getText().toString();
                logoText.setText("");
                count = 0;

                new CountDownTimer(animateText.length() * 100L, 100){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        logoText.setText(logoText.getText().toString()+animateText.charAt(count));
                        count++;
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(SplashScreen.this, onBoardingScreen2.class);
                        startActivity(intent);
                    }
                }.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
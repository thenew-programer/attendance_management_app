package com.uca_lefa.attendance.ui

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DURATION = 1500; // 2 seconds

    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logoImageView = findViewById(R.id.logoImageView);

        // Start blinking animation
        startBlinkingAnimation();

        // Handler to delay transition to next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity after the specified duration
                Intent intent = new Intent(SplashScreenActivity.this, AttendanceApp.class);
                startActivity(intent);
                finish(); // Finish this activity to prevent user from going back to it
            }
        }, SPLASH_SCREEN_DURATION);
    }

    private void startBlinkingAnimation() {
        // Create blinking animation
        AlphaAnimation blinkAnimation = new AlphaAnimation(1, 0.1); // Change alpha from fully visible to invisible
        blinkAnimation.setDuration(500); // Duration of each animation cycle (milliseconds)
        blinkAnimation.setRepeatMode(Animation.REVERSE); // Reverse animation to fade in
        blinkAnimation.setRepeatCount(Animation.INFINITE); // Repeat animation indefinitely

        // Set animation to logo ImageView
        logoImageView.startAnimation(blinkAnimation);
    }
}

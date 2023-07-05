package com.example.firstfirebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash);

        // Find the ImageView by its ID
        img = findViewById(R.id.logo);

        // Set the initial translation and animate it
        img.setTranslationY(-1000f);
        img.animate().translationYBy(1000f).setDuration(700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, user_panel.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it
            }
        }, 4000);
    }
}

package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int TIME_DELAY = 1000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(splash.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        }, TIME_DELAY);
    }
}

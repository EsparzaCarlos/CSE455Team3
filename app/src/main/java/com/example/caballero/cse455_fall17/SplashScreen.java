package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    Animation blue_anim;
    Animation pink_anim;
    Animation yellow_anim;
    Animation text_anim;
    ImageView text;
    ImageView blue;
    ImageView pink;
    ImageView yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        blue = (ImageView) findViewById(R.id.bluecat);
        pink = (ImageView) findViewById(R.id.pinkcat);
        yellow = (ImageView) findViewById(R.id.yellowcat);
        text = (ImageView) findViewById(R.id.spicycat);

        blue_anim = AnimationUtils.loadAnimation(this, R.anim.blue_animation);
        pink_anim = AnimationUtils.loadAnimation(this, R.anim.pink_animation);
        yellow_anim = AnimationUtils.loadAnimation(this, R.anim.yellow_animation);
        text_anim = AnimationUtils.loadAnimation(this, R.anim.text_animation);

        pink.setAnimation(pink_anim);
        blue.setAnimation(blue_anim);
        yellow.setAnimation(yellow_anim);
        text.setAnimation(text_anim);

        int TIME_DELAY = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        }, TIME_DELAY);
    }
}

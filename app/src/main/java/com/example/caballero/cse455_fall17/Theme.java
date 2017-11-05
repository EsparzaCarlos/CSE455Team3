package com.example.caballero.cse455_fall17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Theme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        TextView dark = (TextView) findViewById(R.id.themeDark);
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences theme = getSharedPreferences("THEME_SELECT" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = theme.edit();
                editor.putInt("THEME", R.layout.activity_main_dark);
                editor.putInt("THEME", R.layout.activity_professor_dark);
                editor.commit();
                startActivity(new Intent(Theme.this, MainActivity.class));
                finish();
            }
        });

        TextView light = (TextView) findViewById(R.id.themeLight);
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences theme = getSharedPreferences("THEME_SELECT" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = theme.edit();
                editor.putInt("THEME", R.layout.activity_main);
                editor.commit();
                startActivity(new Intent(Theme.this, MainActivity.class));
                finish();
            }
        });
    }
}

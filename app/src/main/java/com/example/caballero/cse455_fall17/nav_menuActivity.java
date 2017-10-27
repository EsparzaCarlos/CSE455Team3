package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;


/**
 * Created by Carlini on 10/27/2017.
 */

public class nav_menuActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.menu.nav_menu);
        }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                Intent intenttomain = new Intent(this, MainActivity.class);
                startActivity(intenttomain);
                break;
            case R.id.settings:
                Intent intenttomain2 = new Intent(this, MainActivity.class);
                startActivity(intenttomain2);
                break;
            default:
                break;
        }
    }
}


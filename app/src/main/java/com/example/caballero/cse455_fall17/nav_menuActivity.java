package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Carlini on 10/27/2017.
 */

public class nav_menuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    /*@Override
        protected void (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.menu.nav_menu);
        }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(nav_menuActivity.this, MainActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(nav_menuActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*public void onClick(View view) {
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
    }*/
}


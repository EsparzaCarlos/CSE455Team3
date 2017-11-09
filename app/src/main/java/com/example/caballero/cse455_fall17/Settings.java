package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Settings.this,MainActivity.class));
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:               //from              to
                startActivity(new Intent(Settings.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.home:               //from              to
                startActivity(new Intent(Settings.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.settings:
                return true;
            case R.id.thememenu:
                startActivity(new Intent(Settings.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                startActivity(new Intent(Settings.this, FAQ.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

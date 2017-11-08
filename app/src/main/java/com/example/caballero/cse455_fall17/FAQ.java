package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:               //from              to
                startActivity(new Intent(FAQ.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.settings:
                startActivity(new Intent(FAQ.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.thememenu:
                startActivity(new Intent(FAQ.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                startActivity(new Intent(FAQ.this, FAQ.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

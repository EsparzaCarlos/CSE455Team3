package com.example.caballero.cse455_fall17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

public class FAQ extends AppCompatActivity {

    ExpandableListView expandableListView;
    boolean thm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences theme = getSharedPreferences("THEME_SELECT" , Context.MODE_PRIVATE);
        int themeInt = theme.getInt("THEME", 0);
        if(themeInt != R.layout.activity_main_dark && themeInt != R.layout.activity_main)
            themeInt = R.layout.activity_faq;
        if(themeInt == R.layout.activity_main_dark){
            setTheme(R.style.AppThemeDark);
            themeInt = R.layout.dark_faq;
            thm = false;
        }
        if(themeInt == R.layout.activity_main){
            themeInt = R.layout.activity_faq;
            thm = true;
        }
        super.onCreate(savedInstanceState);
        setContentView(themeInt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expandableListView = (ExpandableListView) findViewById(R.id.ExpandableList);

        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(FAQ.this);
        expandableListViewAdapter.setTheme(thm);
        expandableListView.setAdapter(expandableListViewAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(FAQ.this,MainActivity.class));
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(FAQ.this,MainActivity.class));
                this.finish();
                return true;
            case R.id.home:               //from              to
                startActivity(new Intent(FAQ.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.settings:
                startActivity(new Intent(FAQ.this, Settings.class));
                this.finish();
                return true;
            case R.id.thememenu:
                startActivity(new Intent(FAQ.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                return true;
            case R.id.log:
                startActivity(new Intent(FAQ.this, com.example.caballero.cse455_fall17.Log.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

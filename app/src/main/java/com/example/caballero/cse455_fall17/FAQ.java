package com.example.caballero.cse455_fall17;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expandableListView = (ExpandableListView) findViewById(R.id.ExpandableList);

        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(FAQ.this);
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
                startActivity(new Intent(FAQ.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.thememenu:
                startActivity(new Intent(FAQ.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

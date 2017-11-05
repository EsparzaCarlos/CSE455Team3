package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ProfessorActivity extends AppCompatActivity {

    //for the drawer added by Carlos
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        //for the drawer added by Carlos
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //for the drawer to pop out added by Carlos
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            switch (item.getItemId()){
                case R.id.gohome:
                    Intent homeintent = new Intent(this, MainActivity.class);
                    startActivity(homeintent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

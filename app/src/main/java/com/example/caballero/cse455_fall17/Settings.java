package com.example.caballero.cse455_fall17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences theme = getSharedPreferences("THEME_SELECT" , Context.MODE_PRIVATE);
        int themeInt = theme.getInt("THEME", 0);
        if(themeInt != R.layout.activity_main_dark && themeInt != R.layout.activity_main)
            themeInt = R.layout.activity_settings;
        if(themeInt == R.layout.activity_main_dark){
            setTheme(R.style.AppThemeDark);
            themeInt = R.layout.dark_settings;
        }
        if(themeInt == R.layout.activity_main){
            themeInt = R.layout.activity_settings;
        }
        super.onCreate(savedInstanceState);
        setContentView(themeInt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] mainSettings = {"General","Theme"};
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mainSettings);
        ListView mainSettingsList = (ListView) findViewById(R.id.mainSettingsList);
        mainSettingsList.setAdapter(listAdapter);

        mainSettingsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String main = String.valueOf(parent.getItemAtPosition(position));
                        switch (main) {
                            case "General":
                                Toast.makeText(Settings.this,"Work in progress....", Toast.LENGTH_LONG).show();
                                break;
                            case "Theme":
                                startActivity(new Intent(Settings.this, Theme.class));
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
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
            case R.id.log:
                startActivity(new Intent(Settings.this, com.example.caballero.cse455_fall17.Log.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

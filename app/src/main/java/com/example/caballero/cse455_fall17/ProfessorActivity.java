package com.example.caballero.cse455_fall17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.util.Log;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    TextManager txt;
    String name = "";
    String phone = "";
    String email = "";
    boolean isRepeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        DBHandler db = new DBHandler(this);
        List<ProfessorInfo> professorInfos = db.getAllProf();

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.swipelistview);

        SharedPreferences prof = getSharedPreferences("PROFESSOR_SELECT", Context.MODE_PRIVATE);
        String professor = prof.getString("PROFESSOR", null);
        if(professor == null)
            professor = "empty";
        Log.v(TAG,"22"+professor);

        SharedPreferences index = getSharedPreferences("PROF_INDEX" , Context.MODE_PRIVATE);
        int indexCount = index.getInt("CURRENT", 0);
        SharedPreferences.Editor editor = index.edit();
        editor.putInt("CURRENT", professorInfos.size() + 1);
        editor.apply();
        indexCount = index.getInt("CURRENT", 0);

        final List<String> emails = new ArrayList<>();
        /*emails.add("RandomProfessor@spam4me.com");
        emails.add("Dr.Rando@spam4me.com");*/

        final List<String> phones = new ArrayList<>();
        /*phones.add("tel:0123456789");
        phones.add("tel:9876543210");*/

        ArrayList<String> list = new ArrayList<>();
        /*list.add("Dr.Professor Pat");
        list.add("Dr.Professor Guy");*/

        /*String repeat = "";
        for(int i = 0; i < list.size(); i++){
            repeat = list.get(i) + " ";
        }

        if(professor != "") {
            getContactInfo(professor);
            if(!txt.hasString(repeat, name)){
                list.add(name);
                phones.add("tel:"+phone);
                emails.add(email);
            }
        }*/
        getContactInfo(professor);
        for(ProfessorInfo professorInfo : professorInfos){
            Log.v(TAG,"qq"+professorInfo.getName());
            if(professorInfo.getName().equals("empty")){
                db.deleteProf(new ProfessorInfo(1, null,null,null));
            }
            if(professorInfo.getName().equals(name))
                isRepeat = true;

        }
        if(!isRepeat)
            db.addProf(new ProfessorInfo(indexCount, name, phone, email));
        professorInfos = db.getAllProf();
        list.clear();
        phones.clear();
        emails.clear();
        for(ProfessorInfo professorInfo : professorInfos){
            String log = "id: " + professorInfo.getId()+ " ,Name: "+ professorInfo.getName() +
                    " , phone: "+ professorInfo.getPhone() + " , Email: " +
                    professorInfo.getEmail();
            list.add(professorInfo.getName());
            phones.add("tel:"+professorInfo.getPhone());
            emails.add(professorInfo.getEmail());
            Log.v(TAG, log);
        }

        ArrayAdapter adapter = new ArrayAdapter(ProfessorActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "Call professor" item
                SwipeMenuItem callProf = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                callProf.setBackground(new ColorDrawable(Color.rgb(245, 255,
                        250)));
                // set item width
                callProf.setWidth(170);
                // set a icon
                callProf.setIcon(R.drawable.ic_action_name);
                // add to menu
                menu.addMenuItem(callProf);

                // create "delete" item
                SwipeMenuItem emailProf = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                emailProf.setBackground(new ColorDrawable(Color.rgb(175,
                        238, 238)));
                // set item width
                emailProf.setWidth(170);
                // set a icon
                emailProf.setIcon(R.drawable.ic_email);
                // add to menu
                menu.addMenuItem(emailProf);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(phones.get(position)));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emails.get(position)));
                        startActivity(Intent.createChooser(email, "Choose app to send the email"));
                        break;
                    default:
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:               //from              to
                startActivity(new Intent(ProfessorActivity.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.settings:
                startActivity(new Intent(ProfessorActivity.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.thememenu:
                startActivity(new Intent(ProfessorActivity.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                startActivity(new Intent(ProfessorActivity.this, FAQ.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getContactInfo(String s) {
        String[] array = s.split("\n");
        name = array[0];

        for (int i = 0; i < array.length; i++) {
            android.util.Log.v("TEST", array[i]);
            if (txt.hasString(array[i], "email")) {
                email = array[i].substring(array[i].indexOf(" "));
            }
            if (txt.hasString(array[i], "phone")) {
                phone = array[i].substring(array[i].indexOf(" "));
            }
            if(txt.hasString(array[i], "instructor")){
                name = array[i].substring(array[i].indexOf(" ") + 1);
            }
        }
    }
}

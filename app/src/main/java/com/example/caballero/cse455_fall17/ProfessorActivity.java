package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class ProfessorActivity extends AppCompatActivity {

    private static final String TAG = "ProfessorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.swipelistview);

        ArrayList<String> list = new ArrayList<>();
        list.add("Dr.Professor Pat");
        list.add("Dr.Professor Guy");

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
                        intent.setData(Uri.parse("tel:0123456789"));
                        startActivity(intent);
                        break;
                    case 1:
                        if(position == 0){
                            Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "RandomProfessor@spam4me.com"));

                            startActivity(Intent.createChooser(email, "Choose app to send the email"));
                            break;
                        }
                        else{
                            Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "Dr.Rando@spam4me.com"));

                            startActivity(Intent.createChooser(email, "Choose app to send the email"));
                            break;
                        }
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
}

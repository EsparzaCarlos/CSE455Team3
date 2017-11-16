package com.example.caballero.cse455_fall17;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        final Spinner spinner = (Spinner) findViewById(R.id.fromName);
        final EditText tosub = (EditText) findViewById(R.id.sendTo);
        final EditText bod = (EditText) findViewById(R.id.emailBody);

        Button sendEmail = (Button)findViewById(R.id.send);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ebod = bod.getText().toString();
                String frm = spinner.getSelectedItem().toString();
                String toE = tosub.getText().toString();

                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + toE));
                email.putExtra(Intent.EXTRA_SUBJECT,frm);
                email.putExtra(Intent.EXTRA_TEXT,ebod);

                startActivity(Intent.createChooser(email, "Choose app to send the email"));
            }
        });
        //for the easter egg on the this app sucks
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinner.getSelectedItem().toString();
                switch (item){
                    case "This app just sucks!":
                        Toast.makeText(getApplicationContext(),"Did you mean to click that please do not hurt our feelings! We have adjusted your choice for you",Toast.LENGTH_LONG).show();
                        spinner.setSelection(5);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Log.this,MainActivity.class));
        this.finish();
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
            case android.R.id.home:               //from              to
                startActivity(new Intent(Log.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.home:               //from              to
                startActivity(new Intent(Log.this, MainActivity.class));
                this.finish();
                return true;
            case R.id.settings:
                startActivity(new Intent(Log.this, Settings.class));
                this.finish();
                return true;
            case R.id.thememenu:
                startActivity(new Intent(Log.this, Theme.class));
                this.finish();
                return true;
            case R.id.faq:
                startActivity(new Intent(Log.this, FAQ.class));
                this.finish();
                return true;
            case R.id.log:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

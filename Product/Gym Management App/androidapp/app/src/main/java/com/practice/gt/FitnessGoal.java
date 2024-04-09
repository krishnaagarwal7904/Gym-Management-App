package com.practice.gt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FitnessGoal extends AppCompatActivity {

    Button btnnext, btncustom;
    CheckBox chkbm, chklw, chkgf, chksh;
    StringBuffer plantext;
    TextView tvbm, tvlw, tvgf, tvsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fitness_goal);
        setTitle("Fitness Goal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chkbm = findViewById(R.id.chkbm);
        chklw = findViewById(R.id.chklooseweight);
        chkgf = findViewById(R.id.chkgetfit);
        chksh = findViewById(R.id.chkhealthy);
        tvbm = findViewById(R.id.tvbm);
        tvlw = findViewById(R.id.tvlw);
        tvgf = findViewById(R.id.tvgf);
        tvsh = findViewById(R.id.tvsh);
        btncustom = findViewById(R.id.btncustom);


        btnnext = findViewById(R.id.btnnext);

        btncustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomEx.class));
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                plantext = new StringBuffer();

                if (chkbm.isChecked() || chklw.isChecked() || chkgf.isChecked() || chksh.isChecked()) {

                    if (chkbm.isChecked()) {
                        plantext.append(tvbm.getText().toString());
                    }
                    if (chklw.isChecked()) {
                        plantext.append(tvlw.getText().toString());
                    }
                    if (chkgf.isChecked()) {
                        plantext.append(tvgf.getText().toString());
                    }
                    if (chksh.isChecked()) {
                        plantext.append(tvsh.getText().toString());
                    }


                    startActivity(new Intent(getApplicationContext(), Activness.class).putExtra("pt", plantext.toString()));

                } else {
                    Toast.makeText(FitnessGoal.this, "Please Select atleast One Option.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        findViewById(R.id.llhis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryWorkout.class));
            }
        });
          findViewById(R.id.llhome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
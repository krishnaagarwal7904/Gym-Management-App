package com.practice.gt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Activness extends AppCompatActivity {

    Button btnnext;
    CheckBox chknal, chksa, chkac, chkvac;
    StringBuffer plantext,pt;
    TextView tvnal,tvsa,tvact,tvvact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activness);
        setTitle("Activeness");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chknal = findViewById(R.id.chknotall);
        chksa = findViewById(R.id.chksliact);
        chkac = findViewById(R.id.chkactive);
        chkvac = findViewById(R.id.chkveryactive);
        tvnal = findViewById(R.id.tvnal);
        tvsa = findViewById(R.id.tvsa);
        tvact = findViewById(R.id.tvact);
        tvvact = findViewById(R.id.tvvact);

        btnnext = findViewById(R.id.btnnext);




        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                plantext = new StringBuffer();
                pt = new StringBuffer();
                if (chknal.isChecked() || chksa.isChecked() || chkac.isChecked() || chkvac.isChecked()) {

                    if (chknal.isChecked()) {
                        plantext.append(tvnal.getText().toString());
                    }
                    if (chksa.isChecked()) {
                        plantext.append(tvsa.getText().toString());
                    }
                    if (chkac.isChecked()) {
                        plantext.append(tvact.getText().toString());
                    }
                    if (chkvac.isChecked()) {
                        plantext.append(tvvact.getText().toString());
                    }


                    Intent i = getIntent();
                    StringBuffer pp = new StringBuffer(i.getStringExtra("pt"));
                    pt = pp;

                    pt.append(plantext);
                    startActivity(new Intent(getApplicationContext(),AutomatedPlan.class).putExtra("pt",pt.toString()));

                } else {
                    Toast.makeText(Activness.this, "Please Select atleast One Option.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.practice.gt;

import adapters.BegExadapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.BegEx;
import models.Histrory;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Home extends AppCompatActivity {
    DatabaseReference reffb, refub, reflb;
    RecyclerView rvfb, rvub, rvlb;
    Button btnbasic, btncustom, btnstart;
    ArrayList<BegEx> alfb, alub, allb;
    boolean running;
    Chronometer chronometer;
    private long pauseOffset;
    DatabaseReference dbhis;
    String plan = "Automated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        rvfb = findViewById(R.id.rvfb);
        rvub = findViewById(R.id.rvub);
        rvlb = findViewById(R.id.rvlb);
        btnbasic = findViewById(R.id.btnbasic);
        btncustom = findViewById(R.id.btncustom);
        btnstart = findViewById(R.id.btnstart);
        dbhis = FirebaseDatabase.getInstance().getReference("history");

        rvfb.setHasFixedSize(true);
        rvub.setHasFixedSize(true);
        rvlb.setHasFixedSize(true);

        rvfb.setLayoutManager(new LinearLayoutManager(Home.this));
        rvub.setLayoutManager(new LinearLayoutManager(Home.this));
        rvlb.setLayoutManager(new LinearLayoutManager(Home.this));

        reffb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("fb");
        refub = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("lb");
        reflb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("ub");
        alfb = new ArrayList<>();
        alub = new ArrayList<>();
        allb = new ArrayList<>();

        reffb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("fb");
        refub = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("lb");
        reflb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("ub");


        loadlist(reffb, refub, reflb);

        btnbasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = "Automated";

                reffb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("fb");
                refub = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("ub");
                reflb = FirebaseDatabase.getInstance().getReference("ap").child(FirebaseAuth.getInstance().getUid().toString()).child("lb");


                loadlist(reffb, refub, reflb);


            }
        });

        btncustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = "Own (Custom)";

                reffb = FirebaseDatabase.getInstance().getReference("cp").child(FirebaseAuth.getInstance().getUid().toString()).child("fb");
                refub = FirebaseDatabase.getInstance().getReference("cp").child(FirebaseAuth.getInstance().getUid().toString()).child("ub");
                reflb = FirebaseDatabase.getInstance().getReference("cp").child(FirebaseAuth.getInstance().getUid().toString()).child("lb");


                loadlist(reffb, refub, reflb);

            }
        });


        findViewById(R.id.llplans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FitnessGoal.class));
            }
        });
        findViewById(R.id.llhis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HistoryWorkout.class));
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

                builder.setMessage("Do you want to Start Workout Session ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final Dialog dialogtimer = new Dialog(Home.this);
                                dialogtimer.setContentView(R.layout.dialogtimer);

                                chronometer = dialogtimer.findViewById(R.id.chomete);
                                chronometer.setFormat("Time : %s");
                                chronometer.setBase(SystemClock.elapsedRealtime());


                                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                                    @Override
                                    public void onChronometerTick(Chronometer chronometer) {
                                        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                                        int h = (int) (time / 3600000);
                                        int m = (int) (time - h * 3600000) / 60000;
                                        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                                        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                                        chronometer.setText("Time : " + t);
//                                        if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 7200000)
//                                        {
//                                            chronometer.setBase(SystemClock.elapsedRealtime());
//                                        }
                                    }
                                });
                                Button btnstart = (Button) dialogtimer.findViewById(R.id.btnstart);
                                Button btnpause = (Button) dialogtimer.findViewById(R.id.btnpause);
                                Button btnstop = (Button) dialogtimer.findViewById(R.id.btnstop);


                                btnstart.setOnClickListener(view1 -> {
                                    startChronometer(view1);
                                });

                                btnpause.setOnClickListener(view1 -> {

                                    pauseChronometer(view1);
                                });

                                btnstop.setOnClickListener(view1 -> {
                                    stopChronometer(view1);
                                    dialogtimer.dismiss();
                                });
                                dialogtimer.show();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();
            }
        });

    }

    public void startChronometer(View v) {
        Toast.makeText(this, "Sesstion Started...", Toast.LENGTH_SHORT).show();
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }

    }

    public void pauseChronometer(View v) {

        Toast.makeText(this, "Session Paused...", Toast.LENGTH_SHORT).show();
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void stopChronometer(View v) {

        if (!chronometer.getText().toString().equals("Time : 00:00"))
        {
            Toast.makeText(this, "Session Stopped...", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Home.this);
            ab.setMessage("Do you want to stop Your Session?");

            String time = chronometer.getText().toString();
            time = time.replace("Time : ", "");

            //Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
            pauseOffset = 0;

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String today = formatter.format(date);

            String id = dbhis.push().getKey().toString();
            Histrory h = new Histrory(id, today, time, plan);
            dbhis.child(FirebaseAuth.getInstance().getUid()).child(id).setValue(h);
            Toast.makeText(this, "Session Added.", Toast.LENGTH_SHORT).show();

        }


    }

    private void loadlist(DatabaseReference reffb, DatabaseReference refub, DatabaseReference reflb) {


        reffb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                alfb.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        BegEx bb = data.getValue(BegEx.class);
                        alfb.add(bb);
                    }
                    BegExadapter begExadapter = new BegExadapter(alfb, Home.this);
                    rvfb.setAdapter(begExadapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        refub.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                alub.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        BegEx bb = data.getValue(BegEx.class);
                        alub.add(bb);
                    }
                    BegExadapter begExadapter = new BegExadapter(alub, Home.this);
                    rvub.setAdapter(begExadapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reflb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                allb.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        BegEx bb = data.getValue(BegEx.class);
                        allb.add(bb);
                    }
                    BegExadapter begExadapter = new BegExadapter(allb, Home.this);
                    rvlb.setAdapter(begExadapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
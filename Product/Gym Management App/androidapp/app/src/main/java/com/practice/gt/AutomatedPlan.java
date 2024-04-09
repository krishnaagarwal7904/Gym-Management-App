package com.practice.gt;

import adapters.BegExadapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.BegEx;
import models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AutomatedPlan extends AppCompatActivity {

    DatabaseReference reffb, refub, reflb;
    RecyclerView rvfb, rvub, rvlb;
    Button btnsave;
    ArrayList<BegEx> alfb, alub, allb;

    User u;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automated_plan);
        setTitle("Automated Plan");
        rvfb = findViewById(R.id.rvfb);
        rvub = findViewById(R.id.rvub);
        rvlb = findViewById(R.id.rvlb);
        btnsave = findViewById(R.id.btnsave);

        rvfb.setHasFixedSize(true);
        rvub.setHasFixedSize(true);
        rvlb.setHasFixedSize(true);

        rvfb.setLayoutManager(new LinearLayoutManager(AutomatedPlan.this));
        rvub.setLayoutManager(new LinearLayoutManager(AutomatedPlan.this));
        rvlb.setLayoutManager(new LinearLayoutManager(AutomatedPlan.this));

        DatabaseReference refusers = FirebaseDatabase.getInstance().getReference("Users");
        Query query = refusers.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    btnsave.setVisibility(View.VISIBLE);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        u = data.getValue(User.class);

                    }

                    gender = u.getGender();
                   // Toast.makeText(AutomatedPlan.this, "gen" + gender, Toast.LENGTH_SHORT).show();
                    if (gender.equals("Male")) {

                        reffb = FirebaseDatabase.getInstance().getReference("fb");
                        refub = FirebaseDatabase.getInstance().getReference("ub");
                        reflb = FirebaseDatabase.getInstance().getReference("lb");
                    } else {
                        reffb = FirebaseDatabase.getInstance().getReference("ffb");
                        refub = FirebaseDatabase.getInstance().getReference("fub");
                        reflb = FirebaseDatabase.getInstance().getReference("flb");
                    }
                    alfb = new ArrayList<>();
                    alub = new ArrayList<>();
                    allb = new ArrayList<>();


                    Intent i = getIntent();
                    StringBuffer pt = new StringBuffer(i.getStringExtra("pt"));
                    // Toast.makeText(this, "Text:" + pt, Toast.LENGTH_SHORT).show();

                    reffb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            alfb.clear();
                            if (snapshot.exists()) {
                                for (DataSnapshot data : snapshot.getChildren()) {

                                    BegEx bb = data.getValue(BegEx.class);
                                    alfb.add(bb);
                                }
                                BegExadapter begExadapter = new BegExadapter(alfb, AutomatedPlan.this);
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
                                BegExadapter begExadapter = new BegExadapter(alub, AutomatedPlan.this);
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
                                BegExadapter begExadapter = new BegExadapter(allb, AutomatedPlan.this);
                                rvlb.setAdapter(begExadapter);


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    btnsave.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference refplan = FirebaseDatabase.getInstance().getReference("ap");

                moveFirebaseRecordfb(refplan, reffb);
                moveFirebaseRecordub(refplan, refub);
                moveFirebaseRecordlb(refplan, reflb);
            }
        });


    }

    private void moveFirebaseRecordfb(DatabaseReference refplan, DatabaseReference reffb) {

        reffb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                refplan.child(FirebaseAuth.getInstance().getUid()).child("fb").setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void moveFirebaseRecordub(DatabaseReference refplan, DatabaseReference reffb) {

        reffb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                refplan.child(FirebaseAuth.getInstance().getUid()).child("ub").setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void moveFirebaseRecordlb(DatabaseReference refplan, DatabaseReference reffb) {

        reffb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                refplan.child(FirebaseAuth.getInstance().getUid()).child("lb").setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                        if (error != null)
                        {
                            System.out.println("Copy failed");
                        }
                        else
                        {
                           startActivity(new Intent(getApplicationContext(),Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                           finishAffinity();
                            Toast.makeText(AutomatedPlan.this, "Plan Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
        switch (id){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finishAffinity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
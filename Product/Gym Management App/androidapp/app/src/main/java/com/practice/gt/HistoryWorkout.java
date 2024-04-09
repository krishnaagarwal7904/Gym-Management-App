package com.practice.gt;

import adapters.HisAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.Histrory;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryWorkout extends AppCompatActivity {

    RecyclerView rvhis;
    DatabaseReference refhis;
    ArrayList<Histrory> alhis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvhis = findViewById(R.id.rvhis);
        rvhis.setHasFixedSize(true);
        rvhis.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        refhis = FirebaseDatabase.getInstance().getReference("history").child(FirebaseAuth.getInstance().getUid());
        alhis = new ArrayList<>();

        refhis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alhis.clear();
                if (snapshot.hasChildren())
                {
                    for (DataSnapshot data: snapshot.getChildren())
                    {
                        Histrory h = data.getValue(Histrory.class);
                        alhis.add(h);
                    }
                    HisAdapter adapter = new HisAdapter(alhis,HistoryWorkout.this);
                    rvhis.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
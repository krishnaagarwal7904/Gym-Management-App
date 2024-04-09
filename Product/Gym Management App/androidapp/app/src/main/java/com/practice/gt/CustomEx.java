package com.practice.gt;

import adapters.ExcAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.Excercise;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomEx extends AppCompatActivity {

    RecyclerView rvexlist;
    DatabaseReference refex;
    ArrayList<Excercise> exlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ex);
        setTitle("Custom Plan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvexlist = findViewById(R.id.rvcustom);
        rvexlist.setHasFixedSize(true);
        rvexlist.setLayoutManager(new LinearLayoutManager(CustomEx.this));

        exlist = new ArrayList<>();

        refex = FirebaseDatabase.getInstance().getReference("exercises");
        refex.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren())
                {
                    exlist.clear();
                    for (DataSnapshot data : snapshot.getChildren())
                    {
                        Excercise e = data.getValue(Excercise.class);
                        exlist.add(e);

                    }
                    ExcAdapter adapter = new ExcAdapter(exlist,CustomEx.this);
                    rvexlist.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
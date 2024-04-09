package com.practice.gt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText uname,pw,etemail;
    Button button;
    Utility utility;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        button = (Button) findViewById(R.id.btnLogin);
        uname = (EditText) findViewById(R.id.etEmail);
        pw = (EditText) findViewById(R.id.etPassword);

        utility = new Utility(Login.this);
        firebaseAuth = FirebaseAuth.getInstance();


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = true;

                if(!Validation.email_Validation(uname))
                {
                    uname.setError("Enter Valid Email");
                    b=false;
                }

                if(!Validation.passwordValidator(pw))
                {
                    pw.setError("Enter valid Password");
                    b=false;
                }
                if (b)
                {
                    if (utility.checkInternetConnectionALL())
                    {
                        final ProgressDialog pg = new ProgressDialog(Login.this);

                        pg.setTitle("Login");
                        pg.setMessage("Please wait...");
                        pg.setCancelable(false);
                        pg.show();


                        firebaseAuth.signInWithEmailAndPassword(uname.getText().toString(),pw.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        if (task.isSuccessful())
                                        {

                                            startActivity(new Intent(Login.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            finish();
                                            pg.dismiss();

                                        }
                                        else {
                                            utility.toast(task.getException().getMessage());
                                            pg.dismiss();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        ((TextView) findViewById(R.id.tvRegister)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog pg = new ProgressDialog(Login.this);

        pg.setTitle("Login");
        pg.setMessage("Please wait...");
        pg.setCancelable(false);

        if (firebaseAuth.getCurrentUser() !=null )
        {
            pg.show();
            startActivity(new Intent(Login.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            pg.dismiss();
        }
        else {
            pg.dismiss();
        }
    }
}
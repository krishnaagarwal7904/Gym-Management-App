package com.practice.gt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText etname, etemail, etpass, etbd;
    Button btnreg;
    Utility utility;

    RadioButton rdgen;
    RadioGroup rdgendergroup;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname = findViewById(R.id.etfullname);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpassword);
        etbd = findViewById(R.id.etbdate);
        rdgendergroup = findViewById(R.id.rggender);

        utility = new Utility(Register.this);
        btnreg = findViewById(R.id.btnregister);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean b = true;

                if (!Validation.nullValidator(etname)) {
                    b = false;
                    etname.setError("Enter Full Name");
                }

                if (!Validation.nullValidator(etbd)) {
                    b = false;
                    etbd.setError("Enter Birth Date");
                }

                if (!Validation.email_Validation(etemail)) {
                    b = false;
                    etemail.setError("Enter Email");
                }


                if (!Validation.passwordValidator(etpass)) {
                    b = false;
                    etpass.setError("Enter Password");
                }

                if (b)

                {
                    if (utility.checkInternetConnectionALL())
                    {

                        firebaseAuth.createUserWithEmailAndPassword(etemail.getText().toString().trim(),
                                etpass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    int selectedId = rdgendergroup.getCheckedRadioButtonId();
                                    rdgen = (RadioButton) findViewById(selectedId);

                                    String id = firebaseAuth.getCurrentUser().getUid();
                                    User users = new
                                            User(id,etname.getText().toString().trim(),etbd.getText().toString().trim(),
                                            etemail.getText().toString().trim(),etpass.getText().toString().trim(),rdgen.getText().toString());
                                    databaseReference.child(id).setValue(users);
                                    utility.toast("Registered Successfully");
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(getApplicationContext(),Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }

                                else
                                {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Snackbar snackbar = Snackbar
                                                .make(view, "Email Already Exist", Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                    }
                                    else
                                    {
                                        Snackbar snackbar = Snackbar
                                                .make(view, task.getException().getMessage(), Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                    }
                                }

                            }
                        });

                    }
                }



            }
        });


        ((TextView) findViewById(R.id.tvLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
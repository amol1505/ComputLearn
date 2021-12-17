package com.example.computlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    //Button objects for purpose of creating account, logging in or leading to a forget password dialog
    Button createAcctButton, loginButton, forgetPassBtn;
    //Fields where  the user enters their details
    EditText username, password;
    //Firebase authentication object used to validate users input
    FirebaseAuth firebaseAuth;
    //dialog object which is used for purposes of resetting a password
    AlertDialog.Builder resetAlert;
    //layout inflater object to manipulate the view
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initiating objects so that they can be utilised
        resetAlert = new AlertDialog.Builder(this);
        firebaseAuth = FirebaseAuth.getInstance();
        createAcctButton = findViewById(R.id.createAcctButton);
        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        forgetPassBtn = findViewById(R.id.forgetPassBtn);
        inflater = this.getLayoutInflater();

        //lead user to the register activity to create an account if the create account button is clicked
        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        //when forgot password button is clicked a dialog pops up where user provides an email for a reset password link to be sent to
        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inflates view
                View view = inflater.inflate(R.layout.resetpopup, null);
                //setting properties of the alert dialog
                resetAlert.setTitle("Reset forgotten password")
                        .setMessage("Enter your email for a reset link")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //validate email address
                                EditText popupemail = view.findViewById(R.id.resetpopEmail);
                                if(popupemail.getText().toString().isEmpty()){
                                    popupemail.setError("Required");
                                    return;
                                }
                                //send email after validation
                                firebaseAuth.sendPasswordResetEmail(popupemail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this,"Email link sent", Toast.LENGTH_SHORT).show();
                                    }
                                    //display error in toast if not possible
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data is extracted and validated
                if(username.getText().toString().isEmpty()) {
                    username.setError("Email cannot be empty");
                    return;
                }
                if(password.getText().toString().isEmpty()) {
                    password.setError("Password cannot be empty");
                    return;
                }
                //user can be logged in as data has been validated
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //user has successfully logged in
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                    }
                    //display error in toast if user cannot progress
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if user has already logged on previously, redirect them straight to the dashboard for efficient use
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent( getApplicationContext(),Dashboard.class));
            finish();
        }
    }
}
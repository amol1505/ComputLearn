package com.example.computlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    //fields for user to enter
    EditText registerName, registerEmail, registerPassword, confirmPassword;
    //button which either validates data of registration process or redirect the user to login
    Button registerButton, goLogin;
    //Firebase Authentican object
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initiating components of the layout so that they can be utilised
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        goLogin = findViewById(R.id.goLogin);
        //get instance of the Firebase authentication, initating the object
        fAuth = FirebaseAuth.getInstance();
        //redirect user to login activity when user presses log in button
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        //when register button is pressed
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract users details
                String fullName = registerName.getText().toString();
                String emailAddress = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                //validates data so no field is left empty
                if(fullName.isEmpty()) {
                    registerName.setError("Full Name is required");
                    return;
                }
                if(emailAddress.isEmpty()) {
                    registerEmail.setError("Email Address is required");
                    return;
                }
                if(password.isEmpty()) {
                    confirmPassword.setError("Password is required");
                    return;
                }
                if(confirmPass.isEmpty()) {
                    registerName.setError("Please enter password in order for validation");
                    return;
                }
                if(!password.equals(confirmPass)) {
                    confirmPassword.setError("Passwords do not match, try again");
                    return;
                }
                //data already validated beyond this point
                Toast.makeText(Register.this, "Data validated", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(emailAddress, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //send user to dashboard page
                        FirebaseUser user = fAuth.getCurrentUser();
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(fullName).build();
                        user.updateProfile(profileChangeRequest);
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    //display error in toast if not possibe to register
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
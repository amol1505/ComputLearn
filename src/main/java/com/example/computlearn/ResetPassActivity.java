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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassActivity extends AppCompatActivity {
    //Fields where user enters password on two occasions
    EditText userPassword, confUserPassword;
    //Button where user tries to save the new password
    Button savePassBtn;
    FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        //initiating objects
        userPassword = findViewById(R.id.newUserPassword);
        confUserPassword = findViewById(R.id.confNewPassword);
        user = FirebaseAuth.getInstance().getCurrentUser();
        savePassBtn=findViewById(R.id.resetPassBtn);
        //when save password button is clicked, validate data so that no data is left empty or incorrect
        savePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Required");
                    return;
                }
                if(confUserPassword.getText().toString().isEmpty()){
                    confUserPassword.setError("Required");
                    return;
                }
                if(!userPassword.getText().toString().equals(confUserPassword.getText().toString())){
                    confUserPassword.setError("Passwords do not match");
                }
                //update password in Firebase if passwords match and nothing left empty then redirect to dashboard
                user.updatePassword(userPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ResetPassActivity.this, "Password updated sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    //display error in toast
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
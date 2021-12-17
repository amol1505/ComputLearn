package com.example.computlearn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    //Button objects which provide functionality to user in terms of their account
    Button verifyEmailButton2, logoutButton2, resetPasswordBtn, deleteAccount, updateEmail;
    //name and password which are displayed to the user
    TextView profileEmail, profileName;
    //Firebase authentication object to fetch the current users email and name
    FirebaseAuth firebaseAuth;
    //AlertDialog object
    AlertDialog.Builder updateAlert;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        //Objects initiated so that they can be utilised
        firebaseAuth = FirebaseAuth.getInstance();
        updateAlert = new AlertDialog.Builder(getContext());
        logoutButton2 = v.findViewById(R.id.logoutButton2);
        verifyEmailButton2 = v.findViewById(R.id.verifyEmailButton2);
        deleteAccount=v.findViewById(R.id.deleteAccount);
        resetPasswordBtn=v.findViewById(R.id.resetPasswordBtn);
        updateEmail = v.findViewById(R.id.updateEmail);
        profileEmail=v.findViewById(R.id.profileEmail);
        profileName=v.findViewById(R.id.profilename);
        //FirebaseUser object containing details of current user that is logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //setting the email and name to the values present for the current user from Firebase
        profileEmail.setText(user.getEmail());
        profileName.setText(user.getDisplayName());

        //make 'verify email' button visible only if the user hasnt verified their email
        if(!firebaseAuth.getCurrentUser().isEmailVerified()){
            verifyEmailButton2.setVisibility(View.VISIBLE);
        }
        //when verify email button is pressed a verification email is sent to the users email and then the button disappears
        verifyEmailButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Verification email has been sent", Toast.LENGTH_SHORT).show();
                        verifyEmailButton2.setVisibility(View.GONE);
                    }
                });
            }
        });
        /**a dialog pop up appears when the user presses update email where they can provide their new email which is validated
         * and updated to the Firebase system
         */
        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = inflater.inflate(R.layout.resetpopup, null);
                updateAlert.setTitle("Update your email")
                        .setMessage("Enter your new email address")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //validate email address
                                EditText popupemail = view.findViewById(R.id.resetpopEmail);
                                if(popupemail.getText().toString().isEmpty()){
                                    popupemail.setError("Required");
                                    return;
                                }
                                //update email
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.updateEmail(popupemail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(), "Email updated", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();
            }
        });
        //When delete account button is pressed a dialog pops up where user gives confirmation, the user is deleted from the Firebase and redirected to the login page.

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlert.setTitle("Delete Account Forever")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                        startActivity(new Intent(getContext(), Login.class));
                                        getActivity().finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", null)
                        .create().show();
            }
        });
        //redirect user to the reset password activity when the reset password is pressed
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ResetPassActivity.class));
            }
        });
        //sign the user out of the application and redirect them to the login class, finish is also called so it cant be accessed without login
        logoutButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });
        return v;
    }
}

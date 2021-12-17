package com.example.computlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {
    //variables representing the card looking layouts for modules and quiz
    LinearLayout programminglayout, ailayout, technologieslayout, cyberlayout, quizlayout;
    //welcome text that appears at top of screen for the user
    TextView welcomeText;
    //Firebase authentication object which is used to fetch details about the user
    FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout of home fragment so appears on street
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //variables linked to their components within the layout file so that they can be manipulated with for the view
        programminglayout = v.findViewById(R.id.programminglayout);
        ailayout = v.findViewById(R.id.ailayout);
        technologieslayout = v.findViewById(R.id.technologieslayout);
        cyberlayout = v.findViewById(R.id.cyberlayout);
        quizlayout = v.findViewById(R.id.quizlayout);
        welcomeText = v.findViewById(R.id.welcomeText);
        //Firebase objects
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //setting the welcome message to include the users name to give a personal feeling
        if (user.getDisplayName() != null) {
            welcomeText.setText("Hey " + user.getDisplayName());
        }
        /**whenever one of the card looking linear layouts are clicked, the user is navigated to their respective fragment
         * so that the user is able to see the content, however the user is led to a different activity due to
         * it having a different UI design.
         */
        programminglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgrammingFragment()).commit();
            }
        });

        ailayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtificialIntelligenceFragment()).commit();
            }
        });

        technologieslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TechnologiesFragment()).commit();
            }
        });

        cyberlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CyberSecFragment()).commit();
            }
        });

        quizlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuizActivity.class));
            }
        });
        //inflate the view onto the screen so that it appears to the user
        return v;
    }
}

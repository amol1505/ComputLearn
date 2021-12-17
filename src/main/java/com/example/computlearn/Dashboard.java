package com.example.computlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //initiate bottom navigation layout which will appear at the bottom of the screen
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //whenever bottom navigation is clicked on it will follow the scenarios set out in the itemselectedlistener 'navListener'
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //dashboard will host the home fragment in its frame layout by default unless another icon clicked
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(); //set selectedFragment to home fragment if the home icon clicked
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment(); //set selectedFragment to profile fragment if the home icon clicked
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment(); //set selectedFragment to search fragment if the search icon clicked
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment)
                    .commit(); //the frame layout within dashboard class will host whatever fragment has been clicked
            return true;
        }
    };
}
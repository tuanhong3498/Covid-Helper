package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // specify the top destination
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardFragment,
                R.id.checkInFragment,
                R.id.announcementFragment,
                R.id.profileFragment
        ).build();

        navController = Navigation.findNavController(this, R.id.fragment_container);
        // setup actionbar with custom top destination
        // on top destination, the navigation up button (an arrow pointing to the left) will not be displayed
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        findViewById(R.id.bottom_navigation_view).setVisibility(View.VISIBLE);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
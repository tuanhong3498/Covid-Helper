package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private NavController navController;
    private StateListAnimator originalAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.white)));

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

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        originalAnimator = appBarLayout.getStateListAnimator();

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
            {
                if(destination.getId() == R.id.announcementFragment)
                {
                    // remove elevation for announcement fragment
                    StateListAnimator stateListAnimator = new StateListAnimator();
                    stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(appBarLayout, "elevation", 0));
                    appBarLayout.setStateListAnimator(stateListAnimator);
                }
                else
                {
                    appBarLayout.setStateListAnimator(originalAnimator);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        findViewById(R.id.bottom_navigation_view).setVisibility(View.VISIBLE);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
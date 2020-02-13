package com.bnk.comapny.bnksys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager = getSupportFragmentManager();
    private FragmentHome home = new FragmentHome();
    private FragmentAnalysis analysis = new FragmentAnalysis();
    private FragmentProfile profile = new FragmentProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout,home).commitAllowingStateLoss();

        BottomNavigationView bnaviview = findViewById(R.id.navigationView);

        bnaviview.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = manager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.home:
                    transaction.replace(R.id.frameLayout,home).commitAllowingStateLoss();
                    break;
                case R.id.analysis:
                    transaction.replace(R.id.frameLayout,analysis).commitAllowingStateLoss();
                    break;
                case R.id.profile:
                    transaction.replace(R.id.frameLayout,profile).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}

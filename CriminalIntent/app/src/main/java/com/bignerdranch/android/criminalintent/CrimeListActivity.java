package com.bignerdranch.android.criminalintent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CrimeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);
        Log.i("CrimeListActivity","------------------>CrimeListActivityCrimeListActivity");
                FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_list_Container);

        if (fragment == null) {
            fragment = new CrimeListFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_list_Container, fragment)
                    .commit();
        }
    }
}

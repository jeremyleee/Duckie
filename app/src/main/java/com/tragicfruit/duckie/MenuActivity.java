package com.tragicfruit.duckie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Jeremy on 6/03/2015.
 * Hosts MenuFragment
 * TODO: Multiple matches
 * TODO: Redesign UI (Vincent)
 * TODO: Banner on Play Store
 * TODO: Net run rate calculator
 * TODO: Par score calculator
 * TODO: Career average calculator
 * TODO: Score keeping
 * TODO: Drawer to switch between calculators
 * TODO: Clean up messy JSON serialiser code
 */
public class MenuActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new MenuFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("a cricket calculator");
    }

}

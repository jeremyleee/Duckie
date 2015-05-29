package com.tragicfruit.duckie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by Jeremy on 15/02/2015.
 * Hosts a different fragments depending on calculator chosen
 */
public class CalculatorActivity extends AppCompatActivity {
    public static final String EXTRA_MENU_CHOICE = "com.tragicfruit.duckie.menu_choice";

    private static final String TAG = "CalculatorActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private Fragment createFragment() {
        switch (getIntent().getIntExtra(EXTRA_MENU_CHOICE, -1)) {
            case MenuFragment.DL_CALCULATOR:
                return new DLCalculatorFragment();
            case MenuFragment.OVERS_LOST_CALCULATOR:
                return new OLCalculatorFragment();
            default:
                Log.i(TAG, "Cannot find menu choice");
                finish();
                return null;
        }
    }

}

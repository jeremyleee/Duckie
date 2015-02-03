package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 * Messy code!! Just a temporary activity
 */
public class CalculatorActivity extends ActionBarActivity {
    public static final String WHICH_FRAGMENT =
            "com.tragicfruit.duckworthlewiscalculator.fragment";
    public static final String ID =
            "com.tragicfruit.duckworthlewiscalculator.id";

    private Fragment createFragment() {
        UUID id = (UUID) getIntent().getSerializableExtra(ID);
        switch (getIntent().getIntExtra(WHICH_FRAGMENT, -1)) {
            case 0:
                return InningsFragment.newInstance(id, true);
            case 1:
                return InningsFragment.newInstance(id, false);
            case 2:
                return ResultFragment.newInstance(id);
            default:
                return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();

        }
    }

}

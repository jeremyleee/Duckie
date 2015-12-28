package com.tragicfruit.duckie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Jeremy on 15/02/2015.
 * Hosts a different fragments depending on calculator chosen
 */
public class CalculatorActivity extends AppCompatActivity {
    public static final String EXTRA_MENU_CHOICE = "com.tragicfruit.duckie.menu_choice";

    private static final String TAG = "CalculatorActivity";

    public DLCalculatorFragment mDLCalculatorFragment;
    public OLCalculatorFragment mOLCalculatorFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    private Fragment createFragment() {
        switch (getIntent().getIntExtra(EXTRA_MENU_CHOICE, -1)) {
            case MenuFragment.REQUEST_DL_CALCULATOR:
                if (mDLCalculatorFragment == null)
                    mDLCalculatorFragment = DLCalculatorFragment.newInstance();
                return mDLCalculatorFragment;
            case MenuFragment.REQUEST_OVERS_LOST_CALCULATOR:
                if (mOLCalculatorFragment == null)
                    mOLCalculatorFragment = OLCalculatorFragment.newInstance();
                return mOLCalculatorFragment;
            default:
                Log.i(TAG, "Cannot find menu choice");
                finish();
                return null;
        }
    }

}

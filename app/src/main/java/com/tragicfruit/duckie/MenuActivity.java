package com.tragicfruit.duckie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jeremy on 6/03/2015.
 * Hosts MenuFragment
 */
public class MenuActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MenuFragment.newInstance();
    }
}

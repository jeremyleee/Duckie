package com.tragicfruit.duckie;

import android.support.v4.app.Fragment;

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

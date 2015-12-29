package com.tragicfruit.duckie;

import android.support.v4.app.Fragment;

/**
 * Created by Jeremy on 29/12/2015.
 * Hosts AboutFragment
 */
public class AboutActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AboutFragment.newInstance();
    }
}

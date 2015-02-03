package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Jeremy on 1/02/2015.
 * Launcher activity hosting a CalculatorFragment
 */
public class MatchActivity extends ActionBarActivity {
    private ViewPager mViewPager;
    private Match mMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_match);

        // TODO: implement user creating a match
        mMatch = new Match(true);
        MatchLab.get().addMatch(mMatch);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return InningsFragment.newInstance(mMatch.getId(), true);
                    case 1:
                        return InningsFragment.newInstance(mMatch.getId(), false);
                    case 2:
                        return ResultFragment.newInstance(mMatch.getId());
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.first_innings_label);
                    case 1:
                        return getString(R.string.second_innings_label);
                    case 2:
                        return getString(R.string.result_label);
                }

                return null;
            }
        });
    }

}

package com.tragicfruit.duckie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Jeremy on 1/02/2015.
 * Launcher activity hosting fragments for a match (two innings + result fragments)
 * List of matches: -----
 * TODO: Multiple matches
 * Menu/settings: -------
 * TODO: Add ability to change total wickets?
 * TODO: Allow changing format (50 overs, 45 overs, T20 etc)
 * TODO: Allow changing G50
 * Misc: ----------------
 * TODO: Implement checks eg. interruption inputs (interruption score higher than target score etc)
 * TODO: fix orientation change
 * TODO: Redesign UI (Vincent)
 * TODO: Overs lost calculator
 */
public class MatchActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    private Match mMatch;
    private ResultFragment mResultFragment; // required for updating result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // TODO: implement user creating a match
        try {
            mMatch = MatchLab.get(this).getMatches().get(0);
        } catch (Exception e) {
            mMatch = new Match(true, Match.ONEDAY50);
            MatchLab.get(this).addMatch(mMatch);
        }

        mResultFragment = ResultFragment.newInstance(mMatch.getId());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return InningsFragment.newInstance(mMatch.getId(), true);
                    case 1:
                        return InningsFragment.newInstance(mMatch.getId(), false);
                    case 2:
                        return mResultFragment;
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

                return "";
            }
        });

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageScrollStateChanged(int state) {}
            public void onPageSelected(int position) {
                if (position == 2)
                    mResultFragment.updateResult();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_about):
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MatchLab.get(this).saveMatches();
    }

}

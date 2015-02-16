package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jeremy on 1/02/2015.
 * Launcher activity hosting fragments for a match (two innings + result fragments)
 * List of matches: -----
 * TODO: Multiple matches
 * Menu/settings: -------
 * TODO: Add ability to change total wickets?
 * TODO: Tidy up change G50 fragment
 * Misc: ----------------
 * TODO: Implement checks eg. interruption inputs (interruption score higher than target score etc)
 * TODO: Make result updating faster
 * TODO: Redesign UI (Vincent)
 * TODO: Banner on Play Store
 * TODO: Overs lost calculator
 * TODO: Net run rate calculator
 */
public class MatchFragment extends Fragment {
    private static final String DIALOG_CHANGE_MATCH_TYPE = "change_match_type";
    private static final String DIALOG_CHANGE_G50 = "change_g50";
    private static final int REQUEST_MATCH_TYPE = 1;
    private static final int REQUEST_G50 = 2;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    private Match mMatch;
    private InningsFragment mFirstInningsFragment;
    private InningsFragment mSecondInningsFragment;
    private ResultFragment mResultFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        // TODO: implement user creating a match
        try {
            mMatch = MatchLab.get(getActivity()).getMatches().get(0);
        } catch (Exception e) {
            mMatch = new Match(true, Match.ONEDAY50);
            MatchLab.get(getActivity()).addMatch(mMatch);
        }

        mFirstInningsFragment = InningsFragment.newInstance(mMatch.getId(), true);
        mSecondInningsFragment = InningsFragment.newInstance(mMatch.getId(), false);
        mResultFragment = ResultFragment.newInstance(mMatch.getId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match, null);

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
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

            /**
             * Fixes updating fragments after rotation
             */
            private Fragment[] fragments = {mFirstInningsFragment, mSecondInningsFragment, mResultFragment};
            private FragmentManager fm = getFragmentManager();

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                fm.beginTransaction()
                        .remove(fragments[position])
                        .commit();
                fragments[position] = null;
            }

            @Override
            public Fragment instantiateItem(ViewGroup container, int position){
                Fragment fragment = getItem(position);
                fm.beginTransaction()
                        .add(container.getId(), fragment, "fragment:" + position)
                        .commit();
                return fragment;
            }
        });

        mViewPager.setOffscreenPageLimit(2);

        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.sliding_tabs);
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
                if (position == 2) {
                    mResultFragment.update();
                }
            }
        });

        return v;
    }

    public void updateFragments() {
        mFirstInningsFragment.update();
        mSecondInningsFragment.update();
        mResultFragment.update();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_match, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_about):
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
                return true;
            case (R.id.menu_change_g50):
                ChangeG50Fragment g50Fragment = ChangeG50Fragment.newInstance(mMatch.getId());
                g50Fragment.setTargetFragment(MatchFragment.this, REQUEST_G50);
                g50Fragment.show(getFragmentManager(), DIALOG_CHANGE_G50);
                return true;
            case (R.id.menu_change_match_type):
                ChangeMatchTypeFragment matchTypeFragment = ChangeMatchTypeFragment.newInstance(mMatch.getId());
                matchTypeFragment.setTargetFragment(MatchFragment.this, REQUEST_MATCH_TYPE);
                matchTypeFragment.show(getFragmentManager(), DIALOG_CHANGE_MATCH_TYPE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) return;

        if (requestCode == REQUEST_G50) {
            int g50 = data.getIntExtra(ChangeG50Fragment.EXTRA_G50, -1);
            mMatch.setG50(g50);
            updateFragments();
        } else if (requestCode == REQUEST_MATCH_TYPE) {
            int matchType = data.getIntExtra(ChangeMatchTypeFragment.EXTRA_MATCH_TYPE, -1);
            mMatch.setMatchType(matchType);
            mMatch.mFirstInnings.setMaxOvers(matchType);
            mMatch.mSecondInnings.setMaxOvers(matchType);
            updateFragments();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MatchLab.get(getActivity()).saveMatches();
    }

}

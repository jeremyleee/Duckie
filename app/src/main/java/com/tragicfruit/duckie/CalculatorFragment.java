package com.tragicfruit.duckie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Jeremy on 1/02/2015.
 * Duckworth-Lewis Calculator fragment hosting fragments for a match (two innings + result fragments)
 */
public class CalculatorFragment extends Fragment {
    private static final String DIALOG_CHANGE_MATCH_TYPE = "change_match_type";
    private static final String DIALOG_CHANGE_G50 = "change_g50";
    private static final int REQUEST_MATCH_TYPE = 0;
    private static final int REQUEST_G50 = 1;

    private ViewPager mViewPager;
    private ImageView mFirstInningsIndicator;
    private ImageView mSecondInningsIndicator;
    private ImageView mResultIndicator;

    private Calculation mMatch;
    private InningsFragment mFirstInningsFragment;
    private InningsFragment mSecondInningsFragment;
    private ResultFragment mResultFragment;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true); // Prevents crash on rotation on result fragment

        mMatch = CalculationLab.get(getActivity()).getCalculation();

        mFirstInningsFragment = InningsFragment.newInstance(true);
        mSecondInningsFragment = InningsFragment.newInstance(false);
        mResultFragment = ResultFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator, container, false);

        mFirstInningsIndicator = (ImageView) v.findViewById(R.id.first_innings_indicator);
        mSecondInningsIndicator = (ImageView) v.findViewById(R.id.second_innings_indicator);
        mResultIndicator = (ImageView) v.findViewById(R.id.result_indicator);

        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2); // All three pages retained
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
            public Fragment instantiateItem(ViewGroup container, int position) {
                Fragment fragment = getItem(position);
                fm.beginTransaction()
                        .add(container.getId(), fragment, "fragment:" + position)
                        .commit();
                return fragment;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(mViewPager.getAdapter().getPageTitle(position));
                updateIndicator(position);
                // updates result when result tab selected
                if (position == 2) {
                    mResultFragment.update();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFirstInningsIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        mSecondInningsIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });

        mResultIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        getActivity().setTitle(mViewPager.getAdapter().getPageTitle(0));

        return v;
    }

    private void updateIndicator(int position) {
        resetIndicators();
        Drawable onIndicator = getResources().getDrawable(R.drawable.indicator_on);
        switch (position) {
            case 0:
                mFirstInningsIndicator.setImageDrawable(onIndicator);
                break;
            case 1:
                mSecondInningsIndicator.setImageDrawable(onIndicator);
                break;
            case 2:
                mResultIndicator.setImageDrawable(onIndicator);
                break;
        }
    }

    private void resetIndicators() {
        Drawable offIndicator = getResources().getDrawable(R.drawable.indicator_off);
        mFirstInningsIndicator.setImageDrawable(offIndicator);
        mSecondInningsIndicator.setImageDrawable(offIndicator);
        mResultIndicator.setImageDrawable(offIndicator);
    }

    private void updateFragments() {
        mFirstInningsFragment.update();
        mSecondInningsFragment.update();
        mResultFragment.update();
    }

    // Sets mViewPager current item to next item
    public void nextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calculator, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_change_g50):
                ChangeG50Fragment g50Fragment = ChangeG50Fragment.newInstance();
                g50Fragment.setTargetFragment(CalculatorFragment.this, REQUEST_G50);
                g50Fragment.show(getFragmentManager(), DIALOG_CHANGE_G50);
                return true;
            case (R.id.menu_change_match_type):
                ChangeMatchTypeFragment matchTypeFragment = ChangeMatchTypeFragment.newInstance();
                matchTypeFragment.setTargetFragment(CalculatorFragment.this, REQUEST_MATCH_TYPE);
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
            int matchType = ChangeMatchTypeFragment.getMatchType(data);
            mMatch.setMatchType(matchType);
            mMatch.mFirstInnings.setMaxOvers(matchType);
            mMatch.mSecondInnings.setMaxOvers(matchType);
            updateFragments();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CalculationLab.get(getActivity()).saveCalculations();
    }

}

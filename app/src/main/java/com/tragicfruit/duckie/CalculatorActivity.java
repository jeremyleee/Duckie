package com.tragicfruit.duckie;

import android.support.v4.app.Fragment;

/**
 * Created by Jeremy on 15/02/2015.
 * Hosts a different fragments depending on calculator chosen
 */
public class CalculatorActivity extends SingleFragmentActivity implements InningsFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return CalculatorFragment.newInstance();
    }


    @Override
    public void nextPage() {
        CalculatorFragment calculatorFragment  = (CalculatorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        calculatorFragment.nextPage();
    }
}

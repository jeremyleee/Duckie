package com.tragicfruit.duckie;

/**
 * Created by Jeremy on 7/03/2015.
 */
public class CalculationLab {
    private static CalculationLab sCalculationLab;

    private Match mDLCalculation;
    private OversLostCalculation mOversLostCalculation;

    public static CalculationLab get() {
        if (sCalculationLab == null) {
            sCalculationLab = new CalculationLab();
        }
        return sCalculationLab;
    }

    private CalculationLab() {
        // initialise all calculations
        mDLCalculation = new Match(true, Match.ONEDAY50);
        mOversLostCalculation = new OversLostCalculation();
    }


    public Match getDLCalculation() {
        return mDLCalculation;
    }

    public OversLostCalculation getOversLostCalculation() {
        return mOversLostCalculation;
    }

}

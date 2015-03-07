package com.tragicfruit.duckie;

/**
 * Created by Jeremy on 7/03/2015.
 */
public class CalculationLab {
    private static CalculationLab sCalculationLab;

    private DLCalculation mDLCalculation;
    private OversLostCalculation mOversLostCalculation;

    public static CalculationLab get() {
        if (sCalculationLab == null) {
            sCalculationLab = new CalculationLab();
        }
        return sCalculationLab;
    }

    private CalculationLab() {
        // initialise all calculations
        mDLCalculation = new DLCalculation(true, DLCalculation.ONEDAY50);
        mOversLostCalculation = new OversLostCalculation();
    }


    public DLCalculation getDLCalculation() {
        return mDLCalculation;
    }

    public OversLostCalculation getOversLostCalculation() {
        return mOversLostCalculation;
    }

}

package com.tragicfruit.duckie;

/**
 * Created by Jeremy on 7/03/2015.
 * Stores all calculations and handles retrieving, and loading and saving to file
 */
public class CalculationLab {
    private static CalculationLab sCalculationLab;

    private DLCalculation mDLCalculation;
    private OLCalculation mOLCalculation;

    public static CalculationLab get() {
        if (sCalculationLab == null) {
            sCalculationLab = new CalculationLab();
        }
        return sCalculationLab;
    }

    private CalculationLab() {
        // initialise all calculations
        mDLCalculation = new DLCalculation(true, DLCalculation.ONEDAY50);
        mOLCalculation = new OLCalculation();
    }


    public DLCalculation getDLCalculation() {
        return mDLCalculation;
    }

    public OLCalculation getOLCalculation() {
        return mOLCalculation;
    }

    public void saveCalculations() {

    }

}

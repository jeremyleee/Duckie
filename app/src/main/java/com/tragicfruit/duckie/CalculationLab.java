package com.tragicfruit.duckie;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Jeremy on 7/03/2015.
 * Stores all calculations and handles retrieving, and loading and saving to file
 */
public class CalculationLab {
    private static final String TAG = "CalculationLab";
    private static final String FILENAME = "calculations.json";

    private static CalculationLab sCalculationLab;
    private Context mAppContext;

    private ArrayList<Calculation> mCalculations;
    private CalculationJSONSerializer mSerialiser;

    public static CalculationLab get(Context c) {
        if (sCalculationLab == null) {
            sCalculationLab = new CalculationLab(c.getApplicationContext());
        }
        return sCalculationLab;
    }

    private CalculationLab(Context context) {
        mAppContext = context;
        mSerialiser = new CalculationJSONSerializer(mAppContext, FILENAME);

        try {
            mCalculations = mSerialiser.loadCalculations();
        } catch (FileNotFoundException e) {
            initialiseCalculations();
        } catch (Exception e) {
            initialiseCalculations();
            Log.e(TAG, "Error loading matches", e);
        }
    }

    private void initialiseCalculations() {
        // initialise all calculations
        mCalculations = new ArrayList<>();
        mCalculations.add(new DLCalculation(true, DLCalculation.ONEDAY50));
        mCalculations.add(new OLCalculation());
    }

    public DLCalculation getDLCalculation() {
        return (DLCalculation) mCalculations.get(0);
    }

    public OLCalculation getOLCalculation() {
        return (OLCalculation) mCalculations.get(1);
    }

    public void saveCalculations() {
        try {
            mSerialiser.saveCalculations(mCalculations);
            Log.d(TAG, "Calculations saved to file");
        } catch (Exception e) {
            Log.d(TAG, "Error saving calculations", e);
        }
    }

}

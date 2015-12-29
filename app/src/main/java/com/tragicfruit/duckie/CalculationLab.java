package com.tragicfruit.duckie;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 7/03/2015.
 * Stores all calculations and handles retrieving, and loading and saving to file
 */
public class CalculationLab {
    private static final String TAG = "CalculationLab";
    private static final String FILENAME = "calculations.json";

    private static CalculationLab sCalculationLab;
    private Context mAppContext;

    private Calculation mCalculation;
    private CalculationJSONSerialiser mSerialiser;

    public static CalculationLab get(Context c) {
        if (sCalculationLab == null) {
            sCalculationLab = new CalculationLab(c.getApplicationContext());
        }
        return sCalculationLab;
    }

    private CalculationLab(Context context) {
        mAppContext = context;
        mSerialiser = new CalculationJSONSerialiser(mAppContext, FILENAME);

        try {
            mCalculation = mSerialiser.loadCalculation();
        } catch (FileNotFoundException e) {
            initialiseCalculation();
        } catch (Exception e) {
            initialiseCalculation();
            Log.e(TAG, "Error loading matches", e);
        }
    }

    public void initialiseCalculation() {
        // initialise all calculations
        mCalculation = new Calculation(true, Calculation.ONEDAY50);
    }

    public Calculation getCalculation() {
        return mCalculation;
    }

    public void saveCalculation() {
        try {
            mSerialiser.saveCalculation(mCalculation);
            Log.d(TAG, "Calculation saved to file");
        } catch (Exception e) {
            Log.d(TAG, "Error saving calculation", e);
        }
    }

}

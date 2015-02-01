package com.tragicfruit.duckworthlewiscalculator;

import java.util.ArrayList;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single inning within a match
 */
public class Inning {

    private int mStartingOvers;
    private int mStartingWickets = 10;
    private double mResources;
    private int mRuns;

    private ArrayList<Interruption> mInterruptions = new ArrayList<Interruption>();

    public void setRuns(int runs) {
        mRuns = runs;
    }

    public int getRuns() {
        return mRuns;
    }

    public void setOvers(int overs) {
        mStartingOvers = overs;
    }

    public int getOvers() {
        return mStartingOvers;
    }

    public ArrayList<Interruption> getInterruptions() {
        return mInterruptions;
    }

    public double getResources() {
        return mResources;
    }

    public void updateResources() {
        mResources = Resources.getPercentage(mStartingOvers, mStartingWickets);
        // Loop through interruptions to reduce resources
        for (Interruption i : getInterruptions()) {
            mResources -= i.getResourcesLost();
        }
    }

    public void addInterruption(int initialOvers, int restartOvers, int wicketsRemaining) {
        Interruption i = new Interruption(initialOvers, restartOvers, wicketsRemaining);
        getInterruptions().add(i);
    }

    class Interruption {

        private int mInitialOversRemaining;
        private int mRestartOversRemaining;
        private int mWicketsRemaining;

        public Interruption(int initialOvers, int restartOvers, int wicketsRemaining) {
            mInitialOversRemaining = initialOvers;
            mRestartOversRemaining = restartOvers;
            mWicketsRemaining = wicketsRemaining;
        }

        public double getResourcesLost() {
            double initialResources = Resources.getPercentage(mInitialOversRemaining, mWicketsRemaining);
            double restartResources = Resources.getPercentage(mRestartOversRemaining, mWicketsRemaining);
            return initialResources - restartResources;
        }

    }

}
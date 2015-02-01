package com.tragicfruit.duckworthlewiscalculator;

import java.util.ArrayList;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single inning within a match
 */
public class Inning {

    private int mStartingOvers;
    private int mStartingBalls = 0;
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

    public int getBalls() {
        return mStartingBalls;
    }

    public int getWickets() {
        return mStartingWickets;
    }

    public double getResources() {
        return mResources;
    }

    public void updateResources() {
        mResources = Resources.getPercentage(mStartingOvers, mStartingBalls, mStartingWickets);
        // Loop through interruptions to reduce resources
        for (Interruption i : mInterruptions) {
            mResources -= i.getResourcesLost();
        }
    }

    public void addInterruption(int initialOvers, int initialBalls, int restartOvers, int restartBalls, int wicketsRemaining) {
        Interruption i = new Interruption(initialOvers, initialBalls, restartOvers, restartBalls, wicketsRemaining);
        mInterruptions.add(i);
    }

    class Interruption {

        private int mInitialOversRemaining;
        private int mInitialBallsRemaining;
        private int mRestartOversRemaining;
        private int mRestartBallsRemaining;
        private int mWicketsRemaining;

        public Interruption(int initialOvers, int initialBalls, int restartOvers, int restartBalls, int wicketsRemaining) {
            mInitialOversRemaining = initialOvers;
            mInitialBallsRemaining = initialBalls;
            mRestartOversRemaining = restartOvers;
            mRestartBallsRemaining = restartBalls;
            mWicketsRemaining = wicketsRemaining;
        }

        public double getResourcesLost() {
            double initialResources = Resources.getPercentage(mInitialOversRemaining, mInitialBallsRemaining, mWicketsRemaining);
            double restartResources = Resources.getPercentage(mRestartOversRemaining, mRestartBallsRemaining, mWicketsRemaining);
            return initialResources - restartResources;
        }

    }

}
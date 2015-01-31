package com.tragicfruit.duckworthlewiscalculator;

import java.util.ArrayList;

/**
 * Created by Jeremy on 1/02/2015.
 */
public class Inning {

    private boolean mIsFirstInning;
    private int mTotalOvers;
    private int mTotalBalls;
    private int mTotalWickets;
    private double mResources;

    private int mScore;
    private ArrayList<Interruption> mInterruptions = new ArrayList<Interruption>();

    public Inning(boolean isFirstInning) {
        mIsFirstInning = isFirstInning;
        // Temporary
        mTotalOvers = 50;
        mTotalBalls = 0;
        mTotalWickets = 10;
        mResources = Resources.getPercentage(mTotalOvers, mTotalBalls, mTotalWickets);
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getScore() {
        return mScore;
    }

    public void setOvers(int overs) {
        mTotalOvers = overs;
    }

    public int getOvers() {
        return mTotalOvers;
    }

    public void setBalls(int balls) {
        mTotalBalls = balls;
    }

    public int getBalls() {
        return mTotalBalls;
    }

    public void setWickets(int wickets) {
        mTotalWickets = wickets;
    }

    public int getWickets() {
        return mTotalWickets;
    }

    public double getResources() {
        return mResources;
    }

    public void updateResources() {
        mResources = Resources.getPercentage(mTotalOvers, mTotalBalls, mTotalWickets);
    }

    public void addInterruption(int initialOvers, int initialBalls, int restartOvers, int restartBalls, int wicketsRemaining) {
        Interruption i = new Interruption(initialOvers, initialBalls, restartOvers, restartBalls, wicketsRemaining);
        mInterruptions.add(i);
        mResources -= i.getResourcesLost();
        updateResources();
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
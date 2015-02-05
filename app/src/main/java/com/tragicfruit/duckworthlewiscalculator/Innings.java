package com.tragicfruit.duckworthlewiscalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single innings within a match
 */
public class Innings {

    private int mMaxOvers;
    private int mMaxWickets = 10;
    private double mResources;
    private int mRuns;
    private int mWickets; // not really required

    public Innings() {
        mMaxOvers = -1;
        mRuns = -1;
        mWickets = -1;
    }

    private ArrayList<Interruption> mInterruptions = new ArrayList<>();

    public void setRuns(int runs) {
        mRuns = runs;
    }

    public int getRuns() {
        return mRuns;
    }

    public void setMaxOvers(int overs) {
        mMaxOvers = overs;
    }

    public int getMaxOvers() {
        return mMaxOvers;
    }

    public ArrayList<Interruption> getInterruptions() {
        return mInterruptions;
    }

    public double getResources() {
        return mResources;
    }

    public void updateResources() {
        mResources = Resources.getPercentage(mMaxOvers, getMaxWickets());
        // Loop through interruptions to reduce resources
        int totalOvers = mMaxOvers;
        for (Interruption i : mInterruptions) {
            mResources -= i.getResourcesLost(totalOvers);
            totalOvers = i.getInputNewTotalOvers();
        }
    }

    public void addInterruption(int inputRuns, int inputWickets, int inputOversCompleted, int inputNewTotalOvers) {
        Interruption i = new Interruption(this, inputRuns, inputWickets, inputOversCompleted, inputNewTotalOvers);
        mInterruptions.add(i);
        sortInterruptions();
    }

    private void sortInterruptions() {
        Collections.sort(mInterruptions, new Comparator<Interruption>() {
            @Override
            public int compare(Interruption lhs, Interruption rhs) {
                if (lhs.getInputOversCompleted() < rhs.getInputOversCompleted()) {
                    return -1;
                } else if (lhs.getInputOversCompleted() == rhs.getInputOversCompleted()) {
                    if (lhs.getInputNewTotalOvers() < rhs.getInputNewTotalOvers())
                        return 1;
                    else
                        return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    public int getWickets() {
        return mWickets;
    }

    public void setWickets(int wickets) {
        mWickets = wickets;
    }

    public int getMaxWickets() {
        return mMaxWickets;
    }

    class Interruption {
        private Innings mInnings;

        private int mInputRuns;
        private int mInputWickets;
        private int mInputOversCompleted;
        private int mInputNewTotalOvers;

        private int mBeforeOversRemaining; // number of overs remaining before interruption
        private int mAfterOversRemaining; // number of overs remaining after interruption
        private int mWicketsRemaining; // wickets in hand at interruption

        public Interruption(Innings innings, int inputRuns, int inputWickets,
                            int inputOversCompleted, int inputNewTotalOvers) {
            mInnings = innings;
            mInputRuns = inputRuns;
            mInputWickets = inputWickets;
            mInputOversCompleted = inputOversCompleted;
            mInputNewTotalOvers = inputNewTotalOvers;
        }

        public double getResourcesLost(int oldTotalOvers) {
            mWicketsRemaining = mInnings.getMaxWickets() - mInputWickets;
            mBeforeOversRemaining = oldTotalOvers - mInputOversCompleted;
            mAfterOversRemaining = mBeforeOversRemaining - (oldTotalOvers - mInputNewTotalOvers);

            double initialResources = Resources.getPercentage(mBeforeOversRemaining, mWicketsRemaining);
            double restartResources = Resources.getPercentage(mAfterOversRemaining, mWicketsRemaining);
            return initialResources - restartResources;
        }

        public int getInputRuns() {
            return mInputRuns;
        }

        public int getInputWickets() {
            return mInputWickets;
        }

        public int getInputOversCompleted() {
            return mInputOversCompleted;
        }

        public int getInputNewTotalOvers() {
            return mInputNewTotalOvers;
        }
    }

}
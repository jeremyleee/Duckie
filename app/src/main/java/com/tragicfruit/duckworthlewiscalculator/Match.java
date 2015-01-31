package com.tragicfruit.duckworthlewiscalculator;

/**
 * Created by Jeremy on 1/02/2015.
 */
public class Match {

    private boolean mIsProMatch;
    private static final int proG50 = 245;
    private static final int amateurG50 = 200;

    public Inning firstInning;
    public Inning secondInning;

    public Match(boolean isProMatch) {
        mIsProMatch = isProMatch;
        firstInning = new Inning(true);
        secondInning = new Inning(false);
    }

    private double getG50() {
        return (double) (mIsProMatch ? proG50 : amateurG50);
    }

    private boolean isValidMatch() {
        // IMPLEMENT THIS!
        return true;
    }

    public int getTargetScore() {
        if (isValidMatch()) {
            return calculateTargetScore(firstInning.getScore(), firstInning.getResources(), secondInning.getResources());
        } else {
            return -1;
        }
    }

    private int calculateTargetScore(int baseScore, double baseResources, double targetResources) {
        double targetScore;
        if (targetResources < baseResources) {
            targetScore = (double) baseScore * targetResources / baseResources;
        } else if (targetResources > baseResources) {
            targetScore = (double) baseScore + getG50() * (targetResources - baseResources) / 100.0;
        } else {
            return baseScore;
        }

        return (int) targetScore + 1;
    }

}
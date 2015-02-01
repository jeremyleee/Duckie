package com.tragicfruit.duckworthlewiscalculator;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single match
 */
public class Match {

    /** G50 is the average score expected from the team batting first in an uninterrupted
     * 50 overs-per-innings match. Current values from ICC Playing Handbook 2013-14.
     */
    private boolean mIsProMatch;
    private static final int proG50 = 245;
    private static final int amateurG50 = 200;

    public Inning firstInning;
    public Inning secondInning;

    public Match(boolean isProMatch) {
        mIsProMatch = isProMatch;
        firstInning = new Inning();
        secondInning = new Inning();
    }

    private double getG50() {
        return (double) (mIsProMatch ? proG50 : amateurG50);
    }

    private boolean isValidMatch() {
        // Implement this
        return true;
    }

    public int getTargetScore() {
        if (isValidMatch()) {
            firstInning.updateResources();
            secondInning.updateResources();
            return calculateTargetScore(firstInning.getRuns(), firstInning.getResources(), secondInning.getResources());
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
            targetScore = baseScore;
        }

        return (int) targetScore + 1;
    }

}
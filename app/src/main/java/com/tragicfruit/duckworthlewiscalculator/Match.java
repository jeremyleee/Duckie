package com.tragicfruit.duckworthlewiscalculator;

import java.util.UUID;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single match
 */
public class Match {

    private UUID mId;

    /** G50 is the average score expected from the team batting first in an uninterrupted
     * 50 overs-per-innings match. Current values from ICC Playing Handbook 2013-14.
     */
    private boolean mIsProMatch;
    private static final int proG50 = 245;
    private static final int amateurG50 = 200;

    public Innings mFirstInnings;
    public Innings mSecondInnings;

    public Match(boolean isProMatch) {
        mId = UUID.randomUUID();

        mIsProMatch = isProMatch;
        mFirstInnings = new Innings();
        mSecondInnings = new Innings();
    }

    private double getG50() {
        return (double) (mIsProMatch ? proG50 : amateurG50);
    }

    private boolean isValidMatch() {
        // TODO: Implement this
        return true;
    }

    public int getTargetScore() {
        if (isValidMatch()) {
            mFirstInnings.updateResources();
            mSecondInnings.updateResources();
            return calculateTargetScore(mFirstInnings.getRuns(), mFirstInnings.getResources(), mSecondInnings.getResources());
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

    public UUID getId() {
        return mId;
    }
}
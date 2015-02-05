package com.tragicfruit.duckworthlewiscalculator;

import java.util.UUID;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single match
 */
public class Match {

    public enum MatchType {
        TWENTY20, ONEDAY50
    }

    private MatchType mMatchType;

    /** G50 is the average score expected from the team batting first in an uninterrupted
     * 50 overs-per-innings match. Current values from ICC Playing Handbook 2013-14.
     */
    private boolean mIsProMatch;
    private static final int proG50 = 245;
    private static final int amateurG50 = 200;

    private UUID mId;

    public Innings mFirstInnings;
    public Innings mSecondInnings;

    public Match(boolean isProMatch, MatchType matchType) {
        mId = UUID.randomUUID();

        mIsProMatch = isProMatch;
        mMatchType = matchType;

        mFirstInnings = new Innings();
        mSecondInnings = new Innings();
    }

    public MatchType getMatchType() {
        return mMatchType;
    }

    private double getG50() {
        return (double) (mIsProMatch ? proG50 : amateurG50);
    }

    private boolean isValidMatch() {
        if (mMatchType == MatchType.ONEDAY50) {
            if (inningsIsValid(mFirstInnings, 20) && inningsIsValid(mSecondInnings, 20)) {
                return true;
            }
        } else if (mMatchType == MatchType.TWENTY20) {
            if (inningsIsValid(mFirstInnings, 5) && inningsIsValid(mSecondInnings, 5)) {
                return true;
            }
        }
        return false;
    }

    private boolean inningsIsValid(Innings innings, int minRequiredOvers) {
        boolean allOut = innings.getWickets() >= 10;

        boolean metRequiredOvers;
        int size;
        if ((size = innings.getInterruptions().size()) > 0) {
            Innings.Interruption lastInterruption = innings.getInterruptions().get(size - 1);
            metRequiredOvers = lastInterruption.getInputNewTotalOvers() >= minRequiredOvers;
        } else {
            metRequiredOvers = innings.getMaxOvers() >= minRequiredOvers;
        }

        return allOut || metRequiredOvers;
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
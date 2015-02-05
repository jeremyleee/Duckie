package com.tragicfruit.duckworthlewiscalculator;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 * Stores all matches and handles adding and deleting of them.
 */
public class MatchLab {
    private static MatchLab sMatchLab;

    private ArrayList<Match> mMatches;

    public static MatchLab get() {
        if (sMatchLab == null) {
            sMatchLab = new MatchLab();
        }
        return sMatchLab;
    }

    private MatchLab() {
        mMatches = new ArrayList<>();
    }

    public void addMatch(Match m) {
        mMatches.add(m);
    }

    public void deleteMatch(Match m) {
        mMatches.remove(m);
    }

    public ArrayList<Match> getMatches() {
        return mMatches;
    }

    public Match getMatch(UUID id) {
        for (Match m : mMatches) {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }
}

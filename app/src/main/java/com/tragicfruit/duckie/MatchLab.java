package com.tragicfruit.duckie;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 * Stores all matches and handles adding and deleting of them.
 */
public class MatchLab {
    private static final String TAG = "MatchLab";
    private static final String FILENAME = "matches.json";

    private static MatchLab sMatchLab;
    private Context mAppContext;

    private ArrayList<Match> mMatches;
    private DuckieJSONSerializer mSerializer;

    public static MatchLab get(Context c) {
        if (sMatchLab == null) {
            sMatchLab = new MatchLab(c.getApplicationContext());
        }
        return sMatchLab;
    }

    private MatchLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new DuckieJSONSerializer(mAppContext, FILENAME);

        try {
            mMatches = mSerializer.loadMatches();
        } catch (Exception e) {
            mMatches = new ArrayList<>();
            Log.e(TAG, "Error loading matches", e);
        }
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

    public boolean saveMatches() {
        try {
            mSerializer.saveMatches(mMatches);
            Log.d(TAG, "Matches saved to file");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error saving matchs", e);
            return false;
        }
    }

    public Match getMatch(UUID id) {
        for (Match m : mMatches) {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }
}

package com.tragicfruit.duckie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jeremy on 1/02/2015.
 * Represents a single innings within a match
 */
public class Innings {
    private static final String JSON_MAX_OVERS = "max_overs";
    private static final String JSON_MAX_WICKETS = "max_wickets";
    private static final String JSON_RESOURCES = "resources";
    private static final String JSON_RUNS = "runs";
    private static final String JSON_WICKETS = "wickets";
    private static final String JSON_INTERRUPTIONS = "interruptions";

    private int mMaxOvers;
    private int mMaxWickets = 10;
    private double mResources;
    private int mRuns;
    private int mWickets; // not really required

    private ArrayList<Interruption> mInterruptions = new ArrayList<>();

    public Innings(int initialMaxOvers) {
        mMaxOvers = initialMaxOvers;
        mRuns = -1;
        mWickets = -1;
    }

    public Innings(JSONObject json) throws JSONException {
        mMaxOvers = json.getInt(JSON_MAX_OVERS);
        mMaxWickets = json.getInt(JSON_MAX_WICKETS);
        mResources = json.getDouble(JSON_RESOURCES);
        mRuns = json.getInt(JSON_RUNS);
        mWickets = json.getInt(JSON_WICKETS);
        String jsonString = json.getString(JSON_INTERRUPTIONS);
        JSONArray array = (JSONArray) new JSONTokener(jsonString).nextValue();
        for (int i = 0; i < array.length(); i++) {
            mInterruptions.add(
                    new Interruption(this, array.getJSONObject(i))
            );
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_MAX_OVERS, mMaxOvers);
        json.put(JSON_MAX_WICKETS, mMaxWickets);
        json.put(JSON_RESOURCES, mResources);
        json.put(JSON_RUNS, mRuns);
        json.put(JSON_WICKETS, mWickets);
        JSONArray interruptionArray = new JSONArray();
        for (Interruption i : mInterruptions) {
            interruptionArray.put(i.toJSON());
        }
        json.put(JSON_INTERRUPTIONS, interruptionArray.toString());
        return json;
    }

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
        private static final String JSON_INPUT_RUNS = "input_runs";
        private static final String JSON_INPUT_WICKETS = "input_wickets";
        private static final String JSON_INPUT_OVERS_COMPLETED = "input_overs_completed";
        private static final String JSON_INPUT_NEW_TOTAL_OVERS = "input_new_total_overs";
        private static final String JSON_BEFORE_OVERS_REMAINING = "before_overs_remaining";
        private static final String JSON_AFTER_OVERS_REMAINING = "after_overs_remaining";
        private static final String JSON_WICKETS_REMAINING = "wickets_remaining";

        private Innings mInnings;

        private int mInputRuns; // not really required
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

        public Interruption(Innings innings, JSONObject json) throws JSONException {
            mInnings = innings;
            mInputRuns = json.getInt(JSON_INPUT_RUNS);
            mInputWickets = json.getInt(JSON_INPUT_WICKETS);
            mInputOversCompleted = json.getInt(JSON_INPUT_OVERS_COMPLETED);
            mInputNewTotalOvers = json.getInt(JSON_INPUT_NEW_TOTAL_OVERS);
            mBeforeOversRemaining = json.getInt(JSON_BEFORE_OVERS_REMAINING);
            mAfterOversRemaining = json.getInt(JSON_AFTER_OVERS_REMAINING);
            mWicketsRemaining = json.getInt(JSON_WICKETS_REMAINING);
        }

        public JSONObject toJSON() throws JSONException {
            JSONObject json = new JSONObject();
            json.put(JSON_INPUT_RUNS, mInputRuns);
            json.put(JSON_INPUT_WICKETS, mInputWickets);
            json.put(JSON_INPUT_OVERS_COMPLETED, mInputOversCompleted);
            json.put(JSON_INPUT_NEW_TOTAL_OVERS, mInputNewTotalOvers);
            json.put(JSON_BEFORE_OVERS_REMAINING, mBeforeOversRemaining);
            json.put(JSON_AFTER_OVERS_REMAINING, mAfterOversRemaining);
            json.put(JSON_WICKETS_REMAINING, mWicketsRemaining);
            return json;
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
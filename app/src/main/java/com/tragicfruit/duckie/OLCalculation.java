package com.tragicfruit.duckie;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jeremy on 7/03/2015.
 */
public class OLCalculation extends Calculation {
    private static final String JSON_HOURS_LOST = "hours_lost";
    private static final String JSON_MINUTES_LOST = "minutes_lost";
    private static final String JSON_OVERS_PER_HOUR = "overs_per_hour";

    private int mHoursLost;
    private int mMinutesLost;
    private double mOversPerHour;

    // Taken from ICC Handbook 2013-14
    public static final double MENS_ODI = 14.28;
    public static final double MENS_T20 = 15.0;
    public static final double WOMENS_ODI = 15.79;
    public static final double WOMENS_T20 = 16.0;

    public OLCalculation() {
        mHoursLost = -1;
        mMinutesLost = -1;
        mOversPerHour = MENS_ODI;
    }

    public OLCalculation(JSONObject json) throws JSONException {
        mHoursLost = json.getInt(JSON_HOURS_LOST);
        mMinutesLost = json.getInt(JSON_MINUTES_LOST);
        mOversPerHour = json.getDouble(JSON_OVERS_PER_HOUR);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_HOURS_LOST, mHoursLost);
        json.put(JSON_MINUTES_LOST, mMinutesLost);
        json.put(JSON_OVERS_PER_HOUR, mOversPerHour);
        return json;
    }

    public int getHoursLost() {
        return mHoursLost;
    }

    public void setHoursLost(int hoursLost) {
        mHoursLost = hoursLost;
    }

    public int getMinutesLost() {
        return mMinutesLost;
    }

    public void setMinutesLost(int minutesLost) {
        mMinutesLost = minutesLost;
    }

    public double getOversPerHour() {
        return mOversPerHour;
    }

    public void setOversPerHour(double oversPerHour) {
        mOversPerHour = oversPerHour;
    }
}

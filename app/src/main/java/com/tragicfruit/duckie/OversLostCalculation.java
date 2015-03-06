package com.tragicfruit.duckie;

/**
 * Created by Jeremy on 7/03/2015.
 */
public class OversLostCalculation {
    private int mHoursLost;
    private int mMinutesLost;
    private double mOversPerHour;

    // Taken from ICC Handbook 2013-14
    private static final double MENS_ODI = 14.28;
    private static final double MENS_T20 = 15.0;
    private static final double WOMENS_ODI = 15.79;
    private static final double WOMENS_T20 = 16.0;

    public OversLostCalculation() {
        mHoursLost = -1;
        mMinutesLost = -1;
        mOversPerHour = MENS_ODI;
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

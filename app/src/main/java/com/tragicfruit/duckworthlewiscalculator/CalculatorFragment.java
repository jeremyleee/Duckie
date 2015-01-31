package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jeremy on 1/02/2015.
 */
public class CalculatorFragment extends Fragment {

    private ArrayList<Match> mMatches = new ArrayList<Match>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Temporary
        Match myMatch = new Match(true);
        mMatches.add(myMatch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_calculator, container, false);

        return v;
    }

}

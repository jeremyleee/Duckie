package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jeremy on 1/02/2015.
 * Main fragment for user input and displaying data
 * TODO: Allow for viewing and editing interruptions
 * TODO: Add ability to change total wickets?
 * TODO: Allow changing format (50 overs, 45 overs, T20 etc)
 * TODO: Allow changing G50
 * TODO: Implement invalid matches (not enough overs played)
 * TODO: Multiple matches
 * TODO: Save to JSON
 * TODO: Swipe between innings
 * TODO: Redesign UI (Vincent)
 * TODO: fix set overs -> add interruption -> change overs (disable changing max overs after interruption added)
 */
public class MatchFragment extends Fragment {

    private Match mMatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Temporary
        mMatch = new Match(true);
        MatchLab.get().addMatch(mMatch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match, container, false);

        Button firstInningsButton = (Button) v.findViewById(R.id.first_innings_button);
        firstInningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(0);
            }
        });

        Button secondInningsButton = (Button) v.findViewById(R.id.second_innings_button);
        secondInningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(1);
            }
        });

        Button resultButton = (Button) v.findViewById(R.id.result_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(2);
            }
        });

        return v;
    }

    private void startFragment(int fragment) {
        Intent i = new Intent(getActivity(), CalculatorActivity.class);
        i.putExtra(CalculatorActivity.WHICH_FRAGMENT, fragment);
        i.putExtra(CalculatorActivity.ID, mMatch.getId());
        startActivity(i);
    }

}

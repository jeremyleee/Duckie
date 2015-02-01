package com.tragicfruit.duckworthlewiscalculator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jeremy on 1/02/2015.
 * Main fragment for user input and displaying data
 * TODO: Simplify input data from user
 * TODO: Allow for viewing and editing interruptions
 * TODO: Add ability to change total wickets?
 * TODO: Allow changing format (50 overs, 45 overs, T20 etc)
 * TODO: Allow changing G50
 * TODO: Implement invalid matches (not enough overs played)
 * TODO: Multiple matches
 * TODO: Save to JSON
 * TODO: Swipe between innings
 * TODO: Redesign UI (Vincent)
 * TODO: fix set overs, add interrupting, change overs
 */
public class CalculatorFragment extends Fragment {
    private static final String TAG = "CalculatorFragment";

    private static final String DIALOG_FIRST_INNING_INTERRUPTION = "interruption1";
    private static final String DIALOG_SECOND_INNING_INTERRUPTION = "interruption2";
    private static final int REQUEST_FIRST_INNING_INTERRUPTION = 0;
    private static final int REQUEST_SECOND_INNING_INTERRUPTION = 1;

    private Match mMatch;

    private EditText mFirstInningsOversField;
    private EditText mFirstInningsRunsField;
    private EditText mSecondInningsOversField;

    private NumberPicker mFirstInningsOvers;
    private NumberPicker mSecondInningsOvers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Temporary
        mMatch = new Match(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator, container, false);

        /**
         * Set up first innings EditText widgets
         */
        mFirstInningsRunsField = (EditText) v.findViewById(R.id.first_innings_runs_editText);
        //mFirstInningsRunsField.setText("" + mMatch.firstInning.getRuns());

        //mFirstInningsOversField = (EditText) v.findViewById(R.id.first_innings_overs_editText);
        //mFirstInningsOversField.setText("" + mMatch.firstInning.getOvers());

        mFirstInningsOvers = (NumberPicker) v.findViewById(R.id.first_innings_overs);
        mFirstInningsOvers.setMaxValue(50);
        mFirstInningsOvers.setValue(50);
        mFirstInningsOvers.setMinValue(0);

        Button firstInningInterruption = (Button) v.findViewById(R.id.first_inning_interruption_button);
        firstInningInterruption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOvers();
                FragmentManager fm = getFragmentManager();
                InterruptionFragment dialog = InterruptionFragment.newInstance(mMatch.firstInning.getOvers());
                dialog.setTargetFragment(CalculatorFragment.this, REQUEST_FIRST_INNING_INTERRUPTION);
                dialog.show(fm, DIALOG_FIRST_INNING_INTERRUPTION);
            }
        });

        /**
         * Set up second innings EditText widgets
         */

        //mSecondInningsOversField = (EditText) v.findViewById(R.id.second_innings_overs_editText);
        //mSecondInningsOversField.setText("" + mMatch.secondInning.getOvers());

        mSecondInningsOvers = (NumberPicker) v.findViewById(R.id.second_innings_overs);
        mSecondInningsOvers.setMaxValue(50);
        mSecondInningsOvers.setValue(50);

        Button secondInningInterruption = (Button) v.findViewById(R.id.second_inning_interruption_button);
        secondInningInterruption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOvers();
                FragmentManager fm = getFragmentManager();
                InterruptionFragment dialog = InterruptionFragment.newInstance(mMatch.secondInning.getOvers());
                dialog.setTargetFragment(CalculatorFragment.this, REQUEST_SECOND_INNING_INTERRUPTION);
                dialog.show(fm, DIALOG_SECOND_INNING_INTERRUPTION);
            }
        });

        /**
         * Set up display result widgets
         */
        final TextView targetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        final TextView tieTextView = (TextView) v.findViewById(R.id.tie_label);
        Button calculateButton = (Button) v.findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput()) {
                    try {
                        setOvers();
                        targetScoreTextView.setText("" + mMatch.getTargetScore());
                        tieTextView.setText("(" + (mMatch.getTargetScore() - 1) + " runs to tie)");
                    } catch (Exception e) {
                        Log.e(TAG, "Error calculating target score", e);
                        Toast.makeText(getActivity(), "Error calculating target score.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid input, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void setOvers() {
        int input = mFirstInningsOvers.getValue();
        mMatch.firstInning.setOvers(input);

        input = mSecondInningsOvers.getValue();
        mMatch.secondInning.setOvers(input);
    }

    private boolean isValidInput() {
        try {
            /*int input = Integer.parseInt(mFirstInningsOversField.getText().toString());
            if (input >= 0 && input <= 50)
                mMatch.firstInning.setOvers(input);
            else
                throw new Exception();*/

            int input = Integer.parseInt(mFirstInningsRunsField.getText().toString());
            if (input >= 0)
                mMatch.firstInning.setRuns(input);
            else
                throw new Exception();

        } catch (Exception e) {
            Log.e(TAG, "Invalid input");
            mMatch.firstInning.setRuns(-1);
        }

        return mMatch.firstInning.getRuns() >= 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_FIRST_INNING_INTERRUPTION) {
            mMatch.firstInning.addInterruption(
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_WICKETS, -1)
            );
        } else if (requestCode == REQUEST_SECOND_INNING_INTERRUPTION) {
            mMatch.secondInning.addInterruption(
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_WICKETS, -1)
            );
        }
    }

}

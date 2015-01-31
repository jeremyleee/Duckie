package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jeremy on 1/02/2015.
 */
public class CalculatorFragment extends Fragment {
    private static final String TAG = "CalculatorFragment";

    private Match mMatch;

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
        EditText firstInningsRunsField = (EditText) v.findViewById(R.id.first_innings_runs_editText);
        firstInningsRunsField.setText("" + mMatch.firstInning.getScore());
        firstInningsRunsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.firstInning.setScore(Integer.parseInt(s.toString()));
            }

        });

        EditText firstInningsWicketsField = (EditText) v.findViewById(R.id.first_innings_wickets_editText);
        firstInningsWicketsField.setText("" + mMatch.firstInning.getWickets());
        firstInningsWicketsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.firstInning.setWickets(Integer.parseInt(s.toString()));
            }

        });

        EditText firstInningsOversField = (EditText) v.findViewById(R.id.first_innings_overs_editText);
        firstInningsOversField.setText("" + mMatch.firstInning.getOvers());
        firstInningsOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.firstInning.setOvers(Integer.parseInt(s.toString()));
            }

        });

        EditText firstInningsBallsField = (EditText) v.findViewById(R.id.first_innings_balls_editText);
        firstInningsBallsField.setText("" + mMatch.firstInning.getBalls());
        firstInningsBallsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.firstInning.setBalls(Integer.parseInt(s.toString()));
            }

        });

        /**
         * Set up second innings EditText widgets
         */
        EditText secondInningsWicketsField = (EditText) v.findViewById(R.id.second_innings_wickets_editText);
        secondInningsWicketsField.setText("" + mMatch.secondInning.getScore());
        secondInningsWicketsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.secondInning.setWickets(Integer.parseInt(s.toString()));
            }

        });

        EditText secondInningsOversField = (EditText) v.findViewById(R.id.second_innings_overs_editText);
        secondInningsOversField.setText("" + mMatch.secondInning.getOvers());
        secondInningsOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.secondInning.setOvers(Integer.parseInt(s.toString()));
            }

        });

        EditText secondInningsBallsField = (EditText) v.findViewById(R.id.second_innings_balls_editText);
        secondInningsBallsField.setText("" + mMatch.secondInning.getBalls());
        secondInningsBallsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMatch.secondInning.setBalls(Integer.parseInt(s.toString()));
            }

        });

        /**
         * Set up display result widgets
         */
        final TextView targetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        Button calculateButton = (Button) v.findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatch.firstInning.updateResources();
                mMatch.secondInning.updateResources();
                targetScoreTextView.setText("" + mMatch.getTargetScore());
            }
        });

        return v;
    }

}

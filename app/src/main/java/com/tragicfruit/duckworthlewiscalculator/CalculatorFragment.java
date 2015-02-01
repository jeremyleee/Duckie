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
import android.widget.Toast;

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

        /*TextWatcher inputListener = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mInput = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mInput = -1;
                }
            }
        };*/

        /**
         * Set up first innings EditText widgets
         */
        EditText firstInningsRunsField = (EditText) v.findViewById(R.id.first_innings_runs_editText);
        firstInningsRunsField.setText("" + mMatch.firstInning.getScore());
        firstInningsRunsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0)
                        mMatch.firstInning.setScore(input);
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.firstInning.setScore(-1);
                }
            }

        });

        EditText firstInningsWicketsField = (EditText) v.findViewById(R.id.first_innings_wickets_editText);
        firstInningsWicketsField.setText("" + mMatch.firstInning.getWickets());
        firstInningsWicketsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 10)
                        mMatch.firstInning.setWickets(input);
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.firstInning.setWickets(-1);
                }
            }

        });

        EditText firstInningsOversField = (EditText) v.findViewById(R.id.first_innings_overs_editText);
        firstInningsOversField.setText("" + mMatch.firstInning.getOvers());
        firstInningsOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 50)
                        mMatch.firstInning.setOvers(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.firstInning.setOvers(-1);
                }
            }

        });

        EditText firstInningsBallsField = (EditText) v.findViewById(R.id.first_innings_balls_editText);
        firstInningsBallsField.setText("" + mMatch.firstInning.getBalls());
        firstInningsBallsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 5)
                        mMatch.firstInning.setBalls(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.firstInning.setBalls(-1);
                }
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
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 10)
                        mMatch.secondInning.setWickets(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.secondInning.setWickets(-1);
                }
            }

        });

        EditText secondInningsOversField = (EditText) v.findViewById(R.id.second_innings_overs_editText);
        secondInningsOversField.setText("" + mMatch.secondInning.getOvers());
        secondInningsOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 50)
                        mMatch.secondInning.setOvers(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.secondInning.setOvers(-1);
                }
            }

        });

        EditText secondInningsBallsField = (EditText) v.findViewById(R.id.second_innings_balls_editText);
        secondInningsBallsField.setText("" + mMatch.secondInning.getBalls());
        secondInningsBallsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 5)
                        mMatch.secondInning.setBalls(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input: ", e);
                    mMatch.secondInning.setBalls(-1);
                }
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
                if (isValidInput()) {
                    mMatch.firstInning.updateResources();
                    mMatch.secondInning.updateResources();
                    targetScoreTextView.setText("" + mMatch.getTargetScore());
                } else {
                    Toast.makeText(getActivity(), "Invalid input, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private boolean isValidInput() {
        return mMatch.firstInning.getScore() >= 0 &&
                mMatch.firstInning.getWickets() >= 0 &&
                mMatch.firstInning.getOvers() >= 0 &&
                mMatch.firstInning.getBalls() >= 0 &&
                mMatch.secondInning.getWickets() >= 0 &&
                mMatch.secondInning.getOvers() >= 0 &&
                mMatch.secondInning.getBalls() >= 0;
    }

}

package com.tragicfruit.duckworthlewiscalculator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
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
 * Main fragment for user input and displaying data
 */
public class CalculatorFragment extends Fragment {
    private static final String TAG = "CalculatorFragment";

    private static final String DIALOG_FIRST_INNING_INTERRUPTION = "interruption1";
    private static final String DIALOG_SECOND_INNING_INTERRUPTION = "interruption2";
    private static final int REQUEST_FIRST_INNING_INTERRUPTION = 0;
    private static final int REQUEST_SECOND_INNING_INTERRUPTION = 1;

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
                    Log.e(TAG, "Invalid input");
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
                    if (input >= 1 && input <= 10)
                        mMatch.firstInning.setWickets(input);
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input");
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
                    Log.e(TAG, "Invalid input");
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
                    Log.e(TAG, "Invalid input");
                    mMatch.firstInning.setBalls(-1);
                }
            }

        });

        /**
         * Set up second innings EditText widgets
         */
        EditText secondInningsWicketsField = (EditText) v.findViewById(R.id.second_innings_wickets_editText);
        secondInningsWicketsField.setText("" + mMatch.secondInning.getWickets());
        secondInningsWicketsField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 1 && input <= 10)
                        mMatch.secondInning.setWickets(Integer.parseInt(s.toString()));
                    else
                        throw new Exception();
                } catch (Exception e) {
                    Log.e(TAG, "Invalid input");
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
                    Log.e(TAG, "Invalid input");
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
                    Log.e(TAG, "Invalid input");
                    mMatch.secondInning.setBalls(-1);
                }
            }

        });

        Button firstInningInterruption = (Button) v.findViewById(R.id.first_inning_interruption_button);
        firstInningInterruption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                InterruptionFragment dialog = new InterruptionFragment();
                dialog.setTargetFragment(CalculatorFragment.this, REQUEST_FIRST_INNING_INTERRUPTION);
                dialog.show(fm, DIALOG_FIRST_INNING_INTERRUPTION);
            }
        });
        Button secondInningInterruption = (Button) v.findViewById(R.id.second_inning_interruption_button);
        secondInningInterruption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                InterruptionFragment dialog = new InterruptionFragment();
                dialog.setTargetFragment(CalculatorFragment.this, REQUEST_SECOND_INNING_INTERRUPTION);
                dialog.show(fm, DIALOG_SECOND_INNING_INTERRUPTION);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_FIRST_INNING_INTERRUPTION) {
            mMatch.firstInning.addInterruption(
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_BALLS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_BALLS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_WICKETS, -1)
            );
        } else if (requestCode == REQUEST_SECOND_INNING_INTERRUPTION) {
            mMatch.secondInning.addInterruption(
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_BEFORE_BALLS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_OVERS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_AFTER_BALLS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_WICKETS, -1)
            );
        }
    }

}

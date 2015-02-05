package com.tragicfruit.duckworthlewiscalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jeremy on 1/02/2015.
 * TODO: Simplify interruptions dialog
 */
public class InterruptionFragment extends DialogFragment {

    public static final String EXTRA_OLD_TOTAL_OVERS = "com.tragicfruit.duckworthlewiscalculator.old_total_overs";
    public static final String EXTRA_WICKETS = "com.tragicfruit.duckworthlewiscalculator.wickets";
    public static final String EXTRA_BEFORE_OVERS = "com.tragicfruit.duckworthlewiscalculator.before_overs";
    public static final String EXTRA_AFTER_OVERS = "com.tragicfruit.duckworthlewiscalculator.after_overs";

    public static final String EXTRA_INPUT_RUNS = "com.tragicfruit.duckworthlewiscalculator.input_runs";
    public static final String EXTRA_INPUT_WICKETS = "com.tragicfruit.duckworthlewiscalculator.input_wickets";
    public static final String EXTRA_INPUT_OVERS_COMPLETED = "com.tragicfruit.duckworthlewiscalculator.input_overs_completed";
    public static final String EXTRA_INPUT_NEW_TOTAL_OVERS = "com.tragicfruit.duckworthlewiscalculator.input_new_total_overs";

    private int mWicketsRemaining; // wickets in hand after interruption
    private int mBeforeOvers; // number of overs remaining before interruption
    private int mAfterOvers; // number of overs remaining after interruption

    private int mOldTotalOvers;

    private int mInputRuns;
    private int mInputWickets;
    private int mInputOversCompleted;
    private int mInputNewTotalOvers;

    private EditText mRunsField;
    private EditText mWicketsField;
    private EditText mOversField;
    private EditText mNewTotalOversField;

    // New interruption
    public static InterruptionFragment newInstance(int totalOvers) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_OLD_TOTAL_OVERS, totalOvers);

        InterruptionFragment fragment = new InterruptionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // Editing existing interruption
    public static InterruptionFragment newInstance(int totalOvers, int inputRuns, int inputWickets,
                                                   int inputOversCompleted, int inputNewTotalOvers) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_OLD_TOTAL_OVERS, totalOvers);
        args.putInt(EXTRA_INPUT_RUNS, inputRuns);
        args.putInt(EXTRA_INPUT_WICKETS, inputWickets);
        args.putInt(EXTRA_INPUT_OVERS_COMPLETED, inputOversCompleted);
        args.putInt(EXTRA_INPUT_NEW_TOTAL_OVERS, inputNewTotalOvers);

        InterruptionFragment fragment = new InterruptionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldTotalOvers = getArguments().getInt(EXTRA_OLD_TOTAL_OVERS);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_interruption, null);

        mRunsField = (EditText) v.findViewById(R.id.interruption_runs_editText);
        mWicketsField = (EditText) v.findViewById(R.id.interruption_wickets_editText);
        mOversField = (EditText) v.findViewById(R.id.interruption_overs_completed);
        mNewTotalOversField = (EditText) v.findViewById(R.id.interruption_new_total_overs);

        if (getArguments().size() > 1) {
            mRunsField.setText("" + getArguments().getInt(EXTRA_INPUT_RUNS, -1));
            mWicketsField.setText("" + getArguments().getInt(EXTRA_INPUT_WICKETS, -1));
            mOversField.setText("" + getArguments().getInt(EXTRA_INPUT_OVERS_COMPLETED, -1));
            mNewTotalOversField.setText("" + getArguments().getInt(EXTRA_INPUT_NEW_TOTAL_OVERS, -1));
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Interruption details")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isValidInput()) {
                            setResult(Activity.RESULT_OK);
                        } else {
                            setResult(Activity.RESULT_CANCELED);
                            Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create();
    }

    private boolean isValidInput() {
        try {
            // TODO: checks on valid # of overs
            mInputRuns = Integer.parseInt(mRunsField.getText().toString());
            mInputWickets = Integer.parseInt(mWicketsField.getText().toString());
            mInputOversCompleted = Integer.parseInt(mOversField.getText().toString());
            mInputNewTotalOvers = Integer.parseInt(mNewTotalOversField.getText().toString());

            mWicketsRemaining = 10 - mInputWickets;
            mBeforeOvers = mOldTotalOvers - mInputOversCompleted;
            mAfterOvers = mBeforeOvers - (mOldTotalOvers - mInputNewTotalOvers);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setResult(int result) {
        Intent data = new Intent();
        // user input
        data.putExtra(EXTRA_INPUT_RUNS, mInputRuns);
        data.putExtra(EXTRA_INPUT_WICKETS, mInputWickets);
        data.putExtra(EXTRA_INPUT_OVERS_COMPLETED, mInputOversCompleted);
        data.putExtra(EXTRA_INPUT_NEW_TOTAL_OVERS, mInputNewTotalOvers);

        // calculated figures
        data.putExtra(EXTRA_WICKETS, mWicketsRemaining);
        data.putExtra(EXTRA_BEFORE_OVERS, mBeforeOvers);
        data.putExtra(EXTRA_AFTER_OVERS, mAfterOvers);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

}

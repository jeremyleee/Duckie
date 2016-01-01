package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jeremy on 1/02/2015.
 * Displays details of an interruption
 */
public class InterruptionFragment extends DialogFragment {
    private static final String ARG_RUNS = "runs";
    private static final String ARG_WICKETS = "wickets";
    private static final String ARG_OVERS_COMPLETED = "overs_completed";
    private static final String ARG_NEW_TOTAL_OVERS = "total_overs";

    private static final String EXTRA_INPUT_RUNS = "com.tragicfruit.duckie.input_runs";
    private static final String EXTRA_INPUT_WICKETS = "com.tragicfruit.duckie.input_wickets";
    private static final String EXTRA_INPUT_OVERS_COMPLETED = "com.tragicfruit.duckie.input_overs_completed";
    private static final String EXTRA_INPUT_NEW_TOTAL_OVERS = "com.tragicfruit.duckie.input_new_total_overs";

    private int mInputRuns;
    private int mInputWickets;
    private int mInputOversCompleted;
    private int mInputNewTotalOvers;

    private EditText mRunsField;
    private EditText mWicketsField;
    private EditText mOversField;
    private EditText mNewTotalOversField;

    // Creating new interruption
    public static InterruptionFragment newInstance() {
        return new InterruptionFragment();
    }

    // Editing existing interruption
    public static InterruptionFragment newInstance(int inputRuns, int inputWickets,
                                                   int inputOversCompleted, int inputNewTotalOvers) {
        Bundle args = new Bundle();
        args.putInt(ARG_RUNS, inputRuns);
        args.putInt(ARG_WICKETS, inputWickets);
        args.putInt(ARG_OVERS_COMPLETED, inputOversCompleted);
        args.putInt(ARG_NEW_TOTAL_OVERS, inputNewTotalOvers);

        InterruptionFragment fragment = new InterruptionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_interruption, null);

        // Prevents focus on EditText when opening dialog
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        mRunsField = (EditText) v.findViewById(R.id.interruption_runs_editText);
        mWicketsField = (EditText) v.findViewById(R.id.interruption_wickets_editText);
        mOversField = (EditText) v.findViewById(R.id.interruption_overs_completed);
        mNewTotalOversField = (EditText) v.findViewById(R.id.interruption_new_total_overs);

        if (getArguments() != null) {
            mRunsField.setText(Integer.toString(getArguments().getInt(ARG_RUNS, -1)));
            mWicketsField.setText(Integer.toString(getArguments().getInt(ARG_WICKETS, -1)));
            mOversField.setText(Integer.toString(getArguments().getInt(ARG_OVERS_COMPLETED, -1)));
            mNewTotalOversField.setText(Integer.toString(getArguments().getInt(ARG_NEW_TOTAL_OVERS, -1)));
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Interruption details")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!hasEmptyFields()) {
                            setResult(Activity.RESULT_OK);
                            dismiss();
                        }
                    }
                });

                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(Activity.RESULT_CANCELED);
                        dismiss();
                    }
                });

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        setResult(Activity.RESULT_CANCELED);
                        dismiss();
                    }
                });
            }
        });

        return alertDialog;
    }

    private boolean hasEmptyFields() {
        boolean emptyFields = false;

        int warningField = R.drawable.edit_text_small_warning;
        int normalField = R.drawable.edit_text_small;

        String runs = mRunsField.getText().toString();
        if (runs.isEmpty()) {
            emptyFields = true;
            mRunsField.setBackgroundResource(warningField);
        } else {
            mRunsField.setBackgroundResource(normalField);
            mInputRuns = Integer.parseInt(runs);
        }

        String wickets = mWicketsField.getText().toString();
        if (wickets.isEmpty()) {
            emptyFields = true;
            mWicketsField.setBackgroundResource(warningField);
        } else {
            mWicketsField.setBackgroundResource(normalField);
            mInputWickets = Integer.parseInt(wickets);
        }

        String overs = mOversField.getText().toString();
        if (overs.isEmpty()) {
            emptyFields = true;
            mOversField.setBackgroundResource(warningField);
        } else {
            mOversField.setBackgroundResource(normalField);
            mInputOversCompleted = Integer.parseInt(overs);
        }

        String newOvers = mNewTotalOversField.getText().toString();
        if (newOvers.isEmpty()) {
            emptyFields = true;
            mNewTotalOversField.setBackgroundResource(warningField);
        } else {
            mNewTotalOversField.setBackgroundResource(normalField);
            mInputNewTotalOvers = Integer.parseInt(newOvers);
        }

        return emptyFields;
    }

    private void setResult(int result) {
        Intent data = new Intent();
        data.putExtra(EXTRA_INPUT_RUNS, mInputRuns);
        data.putExtra(EXTRA_INPUT_WICKETS, mInputWickets);
        data.putExtra(EXTRA_INPUT_OVERS_COMPLETED, mInputOversCompleted);
        data.putExtra(EXTRA_INPUT_NEW_TOTAL_OVERS, mInputNewTotalOvers);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

    public static void addInterruption(Innings innings, Intent data) {
        innings.addInterruption(
                data.getIntExtra(InterruptionFragment.EXTRA_INPUT_RUNS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_INPUT_WICKETS, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_INPUT_OVERS_COMPLETED, -1),
                data.getIntExtra(InterruptionFragment.EXTRA_INPUT_NEW_TOTAL_OVERS, -1)
        );
    }

}

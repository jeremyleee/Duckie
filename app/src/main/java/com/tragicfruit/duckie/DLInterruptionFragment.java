package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jeremy on 1/02/2015.
 * Displays details of an interruption
 */
public class DLInterruptionFragment extends DialogFragment {
    public static final String EXTRA_INPUT_RUNS = "com.tragicfruit.duckie.input_runs";
    public static final String EXTRA_INPUT_WICKETS = "com.tragicfruit.duckie.input_wickets";
    public static final String EXTRA_INPUT_OVERS_COMPLETED = "com.tragicfruit.duckie.input_overs_completed";
    public static final String EXTRA_INPUT_NEW_TOTAL_OVERS = "com.tragicfruit.duckie.input_new_total_overs";

    private DLCalculation mMatch;

    private int mInputRuns;
    private int mInputWickets;
    private int mInputOversCompleted;
    private int mInputNewTotalOvers;

    private EditText mRunsField;
    private EditText mWicketsField;
    private EditText mOversField;
    private EditText mNewTotalOversField;

    // Editing existing interruption
    public static DLInterruptionFragment newInstance(int inputRuns, int inputWickets,
                                                   int inputOversCompleted, int inputNewTotalOvers) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_INPUT_RUNS, inputRuns);
        args.putInt(EXTRA_INPUT_WICKETS, inputWickets);
        args.putInt(EXTRA_INPUT_OVERS_COMPLETED, inputOversCompleted);
        args.putInt(EXTRA_INPUT_NEW_TOTAL_OVERS, inputNewTotalOvers);

        DLInterruptionFragment fragment = new DLInterruptionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mMatch = ((DLInningsFragment) getTargetFragment()).getMatch();
        } catch (Exception e) {
            mMatch = null;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dl_dialog_interruption, null);

        // Prevents focus on EditText when opening dialog
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        mRunsField = (EditText) v.findViewById(R.id.interruption_runs_editText);
        mWicketsField = (EditText) v.findViewById(R.id.interruption_wickets_editText);
        mOversField = (EditText) v.findViewById(R.id.interruption_overs_completed);
        mNewTotalOversField = (EditText) v.findViewById(R.id.interruption_new_total_overs);

        if (getArguments() != null) {
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
                            Toast.makeText(getActivity(),
                                    R.string.invalid_interruption_toast,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                })
                .create();
    }

    // checks that input values are within the bounds
    private boolean isValidInput() {
        try {
            mInputRuns = Integer.parseInt(mRunsField.getText().toString());
            mInputWickets = Integer.parseInt(mWicketsField.getText().toString());
            if (mInputWickets > 10)
                throw new Exception();
            mInputOversCompleted = Integer.parseInt(mOversField.getText().toString());
            if (mInputOversCompleted > mMatch.getMatchType())
                throw new Exception();
            mInputNewTotalOvers = Integer.parseInt(mNewTotalOversField.getText().toString());
            if (mInputNewTotalOvers > mMatch.getMatchType())
                throw new Exception();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setResult(int result) {
        Intent data = new Intent();
        data.putExtra(EXTRA_INPUT_RUNS, mInputRuns);
        data.putExtra(EXTRA_INPUT_WICKETS, mInputWickets);
        data.putExtra(EXTRA_INPUT_OVERS_COMPLETED, mInputOversCompleted);
        data.putExtra(EXTRA_INPUT_NEW_TOTAL_OVERS, mInputNewTotalOvers);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

}

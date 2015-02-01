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

/**
 * Created by Jeremy on 1/02/2015.
 */
public class InterruptionFragment extends DialogFragment {

    public static final String EXTRA_WICKETS = "com.tragicfruit.duckworthlewiscalculator.wickets";
    public static final String EXTRA_BEFORE_OVERS = "com.tragicfruit.duckworthlewiscalculator.before_overs";
    public static final String EXTRA_BEFORE_BALLS = "com.tragicfruit.duckworthlewiscalculator.before_balls";
    public static final String EXTRA_AFTER_OVERS = "com.tragicfruit.duckworthlewiscalculator.after_overs";
    public static final String EXTRA_AFTER_BALLS = "com.tragicfruit.duckworthlewiscalculator.after_balls";

    private int mWickets;
    private int mBeforeOvers;
    private int mBeforeBalls;
    private int mAfterOvers;
    private int mAfterBalls;

    private EditText mWicketsField;
    private EditText mBeforeOversField;
    private EditText mBeforeBallsField;
    private EditText mAfterOversField;
    private EditText mAfterBallsField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_interruption, null);

        mWicketsField = (EditText) v.findViewById(R.id.interruption_wickets_editText);
        mBeforeOversField = (EditText) v.findViewById(R.id.before_interruption_overs_editText);
        mBeforeBallsField = (EditText) v.findViewById(R.id.before_interruption_balls_editText);
        mAfterOversField = (EditText) v.findViewById(R.id.after_interruption_overs_editText);
        mAfterBallsField = (EditText) v.findViewById(R.id.after_interruption_balls_editText);

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
                        }
                    }
                })
                .create();
    }

    private boolean isValidInput() {
        try {
            mWickets = Integer.parseInt(mWicketsField.getText().toString());
            mBeforeOvers = Integer.parseInt(mBeforeOversField.getText().toString());
            mBeforeBalls = Integer.parseInt(mBeforeBallsField.getText().toString());
            mAfterOvers = Integer.parseInt(mAfterOversField.getText().toString());
            mAfterBalls = Integer.parseInt(mAfterBallsField.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setResult(int result) {
        Intent data = new Intent();
        data.putExtra(EXTRA_WICKETS, mWickets);
        data.putExtra(EXTRA_BEFORE_OVERS, mBeforeOvers);
        data.putExtra(EXTRA_BEFORE_BALLS, mBeforeBalls);
        data.putExtra(EXTRA_AFTER_OVERS, mAfterOvers);
        data.putExtra(EXTRA_AFTER_BALLS, mAfterBalls);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

}

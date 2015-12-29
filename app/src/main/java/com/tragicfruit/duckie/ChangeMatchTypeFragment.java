package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Jeremy on 15/02/2015.
 * Allows changing match type to either 50 over match or 20 over match
 */
public class ChangeMatchTypeFragment extends DialogFragment {
    private static final String EXTRA_MATCH_TYPE = "com.tragicfruit.duckie.match_type";

    private Calculation mMatch;

    public static ChangeMatchTypeFragment newInstance() {
        return new ChangeMatchTypeFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_change_match_type, null);

        mMatch = CalculationLab.get(getActivity()).getCalculation();

        // determines preselection when user enters fragment
        int matchType = mMatch.getMatchType();
        if (matchType == Calculation.ONEDAY50) {
            ((RadioButton) v.findViewById(R.id.one_day_radioButton)).setChecked(true);
        } else if (matchType == Calculation.TWENTY20) {
            ((RadioButton) v.findViewById(R.id.twenty20_radioButton)).setChecked(true);
        }

        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.match_type_radioGroup);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.change_match_type_label)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int checkedItem = radioGroup.getCheckedRadioButtonId();
                        if (checkedItem == R.id.one_day_radioButton) {
                            setResult(Activity.RESULT_OK, Calculation.ONEDAY50);
                        } else if (checkedItem == R.id.twenty20_radioButton) {
                            setResult(Activity.RESULT_OK, Calculation.TWENTY20);
                        }
                    }
                })
                .create();
    }

    private void setResult(int result, int matchType) {
        Intent data = new Intent();
        data.putExtra(EXTRA_MATCH_TYPE, matchType);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

    public static int getMatchType(Intent data) {
        return data.getIntExtra(ChangeMatchTypeFragment.EXTRA_MATCH_TYPE, -1);
    }

}

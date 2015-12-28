package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Jeremy on 15/02/2015.
 * Allows changing G50 to a custom figure
 */
public class DLChangeG50Fragment extends DialogFragment {
    public static final String EXTRA_G50 = "com.tragicfruit.duckie.g50";

    private RadioGroup mRadioGroup;
    private EditText mCustomG50Field;

    private int mG50;
    private DLCalculation mMatch;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dl_dialog_change_g50, null);

        mMatch = CalculationLab.get(getActivity()).getDLCalculation();

        // Prevents focus on EditText when opening dialog
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        int currentG50 = mMatch.getG50();

        mRadioGroup = (RadioGroup) v.findViewById(R.id.g50_radioGroup);

        RadioButton proG50RadioButton = (RadioButton) v.findViewById(R.id.pro_g50_radioButton);
        proG50RadioButton.setText(getString(R.string.pro_g50_label, DLCalculation.proG50));

        RadioButton amateurG50RadioButton = (RadioButton) v.findViewById(R.id.amateur_g50_radioButton);
        amateurG50RadioButton.setText(getString(R.string.amateur_g50_label, DLCalculation.amateurG50));

        RadioButton customG50RadioButton = (RadioButton) v.findViewById(R.id.custom_g50_radioButton);
        mCustomG50Field = (EditText) v.findViewById(R.id.change_g50_editText);
        customG50RadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // display or remove EditText field when custom G50 radio button selected
                if (isChecked) {
                    mCustomG50Field.setVisibility(View.VISIBLE);
                } else {
                    mCustomG50Field.setVisibility(View.GONE);
                }
            }
        });

        // determines preselection when opening fragment
        if (currentG50 == DLCalculation.proG50) {
            proG50RadioButton.setChecked(true);
        } else if (currentG50 == DLCalculation.amateurG50) {
            amateurG50RadioButton.setChecked(true);
        } else {
            customG50RadioButton.setChecked(true);
            mCustomG50Field.setVisibility(View.VISIBLE);
            mCustomG50Field.setText("" + currentG50);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.change_g50_label)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int checkedItem = mRadioGroup.getCheckedRadioButtonId();
                        if (checkedItem == R.id.pro_g50_radioButton) {
                            mG50 = DLCalculation.proG50;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.amateur_g50_radioButton) {
                            mG50 = DLCalculation.amateurG50;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.custom_g50_radioButton) {
                            if (isValidInput()) {
                                setResult(Activity.RESULT_OK);
                            } else {
                                Toast.makeText(getActivity(),
                                        R.string.invalid_g50_toast,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                })
                .create();
    }

    // checks that field isn't empty
    private boolean isValidInput() {
        try {
            mG50 = Integer.parseInt(mCustomG50Field.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setResult(int result) {
        Intent data = new Intent();
        data.putExtra(EXTRA_G50, mG50);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

}

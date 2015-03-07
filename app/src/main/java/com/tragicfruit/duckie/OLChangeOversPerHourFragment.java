package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Jeremy on 7/03/2015.
 */
public class OLChangeOversPerHourFragment extends DialogFragment {
    public static final String EXTRA_G50 = "com.tragicfruit.duckie.overs_per_hour";

    private OLCalculation mCalculation;

    private RadioGroup mRadioGroup;
    private EditText mCustomOversPerHourField;

    private double mOversPerHour;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.ol_dialog_change_overs_per_hour, null);

        mCalculation = CalculationLab.get(getActivity()).getOLCalculation();

        // Prevents focus on EditText when opening dialog
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        double currentOversPerHour = mCalculation.getOversPerHour();

        mRadioGroup = (RadioGroup) v.findViewById(R.id.overs_per_hour_radioGroup);

        RadioButton mensODIRadioButton = (RadioButton) v.findViewById(R.id.mens_odi_radioButton);
        mensODIRadioButton.setText(getString(R.string.mens_odi_label, OLCalculation.MENS_ODI));

        RadioButton mensT20RadioButton = (RadioButton) v.findViewById(R.id.mens_t20_radioButton);
        mensT20RadioButton.setText(getString(R.string.mens_t20_label, OLCalculation.MENS_T20));

        RadioButton womensODIRadioButton = (RadioButton) v.findViewById(R.id.womens_odi_radioButton);
        womensODIRadioButton.setText(getString(R.string.womens_odi_label, OLCalculation.WOMENS_ODI));

        RadioButton womensT20RadioButton = (RadioButton) v.findViewById(R.id.womens_t20_radioButton);
        womensT20RadioButton.setText(getString(R.string.womens_t20_label, OLCalculation.WOMENS_T20));

        RadioButton customOversPerHourRadioButton =
                (RadioButton) v.findViewById(R.id.custom_overs_per_hour_radioButton);
        mCustomOversPerHourField = (EditText) v.findViewById(R.id.change_overs_per_hour_editText);
        customOversPerHourRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // display or remove EditText field when custom overs per hour radio button selected
                if (isChecked) {
                    mCustomOversPerHourField.setVisibility(View.VISIBLE);
                } else {
                    mCustomOversPerHourField.setVisibility(View.GONE);
                }
            }
        });

        // determines preselection when opening fragment
        if (currentOversPerHour == OLCalculation.MENS_ODI) {
            mensODIRadioButton.setChecked(true);
        } else if (currentOversPerHour == OLCalculation.MENS_T20) {
            mensT20RadioButton.setChecked(true);
        } else if (currentOversPerHour == OLCalculation.WOMENS_ODI) {
            womensODIRadioButton.setChecked(true);
        } else if (currentOversPerHour == OLCalculation.WOMENS_T20) {
            womensT20RadioButton.setChecked(true);
        } else {
            customOversPerHourRadioButton.setChecked(true);
            mCustomOversPerHourField.setVisibility(View.VISIBLE);
            mCustomOversPerHourField.setText("" + currentOversPerHour);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Change overs per hour")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int checkedItem = mRadioGroup.getCheckedRadioButtonId();
                        if (checkedItem == R.id.mens_odi_radioButton) {
                            mOversPerHour = OLCalculation.MENS_ODI;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.mens_t20_radioButton) {
                            mOversPerHour = OLCalculation.MENS_T20;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.womens_odi_radioButton) {
                            mOversPerHour = OLCalculation.WOMENS_ODI;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.womens_t20_radioButton) {
                            mOversPerHour = OLCalculation.WOMENS_T20;
                            setResult(Activity.RESULT_OK);
                        } else if (checkedItem == R.id.custom_overs_per_hour_radioButton) {
                            if (isValidInput()) {
                                setResult(Activity.RESULT_OK);
                            } else {
                                Toast.makeText(getActivity(),
                                        R.string.invalid_overs_per_hour_toast,
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
            mOversPerHour = Double.parseDouble(mCustomOversPerHourField.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setResult(int result) {
        Intent data = new Intent();
        data.putExtra(EXTRA_G50, mOversPerHour);

        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

}

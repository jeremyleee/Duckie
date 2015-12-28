package com.tragicfruit.duckie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jeremy on 6/03/2015.
 * Overs Lost Calculator fragment
 */
public class OLCalculatorFragment extends Fragment {
    private static final String DIALOG_CHANGE_OVERS_PER_HOUR = "change_overs_per_hour";
    private static final int REQUEST_OVERS_PER_HOUR = 0;

    private EditText mHoursLostField;
    private EditText mMinsLostField;
    private TextView mResultTextView;
    private View mResultSection;

    private OLCalculation mCalculation;

    public static OLCalculatorFragment newInstance() {
        return new OLCalculatorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mCalculation = CalculationLab.get(getActivity()).getOLCalculation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ol_fragment_calculator, container, false);

        getActivity().setTitle(R.string.overs_lost_calculator_label);

        // Prevents focus on EditText when opening fragment
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        mHoursLostField = (EditText) v.findViewById(R.id.hours_lost_editText);
        if (mCalculation.getHoursLost() >= 0)
            mHoursLostField.setText("" + mCalculation.getHoursLost());

        mMinsLostField = (EditText) v.findViewById(R.id.mins_lost_editText);
        if (mCalculation.getMinutesLost() >= 0)
            mMinsLostField.setText("" + mCalculation.getMinutesLost());

        mResultSection = v.findViewById(R.id.overs_lost_result_section);
        mResultTextView = (TextView) v.findViewById(R.id.overs_lost_result_textView);
        if (mCalculation.getHoursLost() >= 0 && mCalculation.getMinutesLost() >= 0) {
            updateResult();
        }

        Button calculateButton = (Button) v.findViewById(R.id.calculate_overs_lost_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResult();
            }
        });

        Button resetButton = (Button) v.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHoursLostField.setText("");
                mMinsLostField.setText("");
                mCalculation.setHoursLost(-1);
                mCalculation.setMinutesLost(-1);
                mResultSection.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private void updateResult() {
        mResultSection.setVisibility(View.VISIBLE);
        mResultTextView.setText("" + getResult());
    }

    private int getResult() {
        if (mHoursLostField.getText().toString().equals("")) {
            mHoursLostField.setText("0");
            mCalculation.setHoursLost(0);
        } else {
            mCalculation.setHoursLost(Integer.parseInt(mHoursLostField.getText().toString()));
        }

        if (mMinsLostField.getText().toString().equals("")) {
            mMinsLostField.setText("0");
            mCalculation.setMinutesLost(0);
        } else {
            mCalculation.setMinutesLost(Integer.parseInt(mMinsLostField.getText().toString()));
        }

        double totalHoursLost = mCalculation.getHoursLost() + mCalculation.getMinutesLost() / 60.0;
        double oversLost = totalHoursLost * mCalculation.getOversPerHour();

        return (int) Math.round(oversLost);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ol_menu_calculator, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_change_overs_per_hour):
                OLChangeOversPerHourFragment fragment = OLChangeOversPerHourFragment.newInstance();
                fragment.setTargetFragment(OLCalculatorFragment.this, REQUEST_OVERS_PER_HOUR);
                fragment.show(getFragmentManager(), DIALOG_CHANGE_OVERS_PER_HOUR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) return;

        if (requestCode == REQUEST_OVERS_PER_HOUR) {
            double oversPerHour = OLChangeOversPerHourFragment.getOversPerHour(data);
            mCalculation.setOversPerHour(oversPerHour);
            updateResult();
            Toast.makeText(getActivity(),
                    R.string.overs_per_hour_success_toast,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CalculationLab.get(getActivity()).saveCalculations();
    }

}

package com.tragicfruit.duckie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jeremy on 6/03/2015.
 * Overs Lost Calculator fragment
 * TODO: Change overs per hour
 */
public class OLCalculatorFragment extends Fragment {
    private EditText mHoursLostField;
    private EditText mMinsLostField;
    private TextView mResultTextView;

    private OLCalculation mCalculation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalculation = CalculationLab.get().getOLCalculation();
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

        mResultTextView = (TextView) v.findViewById(R.id.overs_lost_result_textView);
        if (mCalculation.getHoursLost() >= 0 && mCalculation.getMinutesLost() >= 0) {
            showResult();
        }

        Button calculateButton = (Button) v.findViewById(R.id.calculate_overs_lost_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
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
                mResultTextView.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private void showResult() {
        mResultTextView.setVisibility(View.VISIBLE);
        mResultTextView.setText(getResult() + " overs lost");
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
    public void onPause() {
        super.onPause();
        CalculationLab.get().saveCalculations();
    }

}

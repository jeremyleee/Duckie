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
 * TODO: Change overs per hour
 */
public class OversLostCalculatorFragment extends Fragment {
    // Taken from ICC Handbook 2013-14
    private static final double OVERS_PER_HOUR = 14.28;

    private EditText mHoursLostField;
    private EditText mMinsLostField;
    private TextView mResultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overs_lost_calculator, container, false);

        getActivity().setTitle(R.string.overs_lost_calculator_label);

        // Prevents focus on EditText when opening fragment
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        mHoursLostField = (EditText) v.findViewById(R.id.hours_lost_editText);
        mMinsLostField = (EditText) v.findViewById(R.id.mins_lost_editText);
        mResultTextView = (TextView) v.findViewById(R.id.overs_lost_result_textView);

        Button calculateButton = (Button) v.findViewById(R.id.calculate_overs_lost_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTextView.setVisibility(View.VISIBLE);
                mResultTextView.setText(getResult() + " overs lost");
            }
        });

        Button resetButton = (Button) v.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHoursLostField.setText("");
                mMinsLostField.setText("");
                mResultTextView.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private int getResult() {
        if (mHoursLostField.getText().toString().equals(""))
            mHoursLostField.setText("0");
        if (mMinsLostField.getText().toString().equals(""))
            mMinsLostField.setText("0");

        int hoursLost = Integer.parseInt(mHoursLostField.getText().toString());
        int minsLost = Integer.parseInt(mMinsLostField.getText().toString());

        double totalHoursLost = hoursLost + minsLost / 60.0;
        double oversLost = totalHoursLost * OVERS_PER_HOUR;

        return (int) Math.round(oversLost);
    }

}

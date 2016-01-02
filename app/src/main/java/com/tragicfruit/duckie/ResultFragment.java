package com.tragicfruit.duckie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jeremy on 3/02/2015.
 * Fragment for displaying the results of the Duckworth-Lewis calculation
 */
public class ResultFragment extends Fragment {
    private static final String TAG = "ResultFragment";
    private static final String DIALOG_RESET = "reset";
    private static final int REQUEST_RESET = 0;

    private Calculation mMatch;
    private TextView mTargetScoreTextView;
    private TextView mResultTextView;
    private TextView mResultDetailTextView;
    private Button mResetButton;
    private Button mMainMenuButton;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void resetCalculation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMatch = CalculationLab.get(getActivity()).getCalculation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        // Removes focus on EditText from innings fragment
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        mTargetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        mResultTextView = (TextView) v.findViewById(R.id.result_textView);
        mResultDetailTextView = (TextView) v.findViewById(R.id.result_detail_textView);

        update();

        mResetButton = (Button) v.findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetFragment dialog = ResetFragment.newInstance();
                dialog.setTargetFragment(ResultFragment.this, REQUEST_RESET);
                dialog.show(getFragmentManager(), DIALOG_RESET);
            }
        });

        mMainMenuButton = (Button) v.findViewById(R.id.back_main_menu_button);
        mMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    private boolean isValidInput() {
        return mMatch.mFirstInnings.getMaxOvers() >= 0
            && mMatch.mFirstInnings.getRuns() >= 0
            && mMatch.mSecondInnings.getMaxOvers() >= 0;
    }

    public void update() {
        if (isValidInput()) {
            try {
                int targetScore = mMatch.getTargetScore();
                if (targetScore >= 0) {
                    mTargetScoreTextView.setVisibility(View.VISIBLE);

                    mResultTextView.setText(Integer.toString(targetScore));
                    mResultDetailTextView.setText(getString(R.string.result, Integer.toString(targetScore - 1)));
                } else {
                    // No result (not reached minimum required overs)
                    mTargetScoreTextView.setVisibility(View.GONE);

                    mResultTextView.setText(R.string.no_result_label);
                    int minOvers = mMatch.getMatchType() == Calculation.ONEDAY50 ? 20 : 5;
                    mResultDetailTextView.setText(getString(R.string.no_result_detail, minOvers));
                }
            } catch (Exception e) {
                // Valid inputs, but error with finding resources
                mTargetScoreTextView.setVisibility(View.GONE);

                mResultTextView.setText(R.string.calculation_error_label);
                mResultDetailTextView.setText(R.string.calculation_error_detail);

                Log.i(TAG, "Error calculating target score");
            }
        } else {
            // Missing one of the input values
            mTargetScoreTextView.setVisibility(View.GONE);

            mResultTextView.setText(R.string.calculation_error_label);
            mResultDetailTextView.setText(R.string.calculation_error_detail);

            Log.i(TAG, "Invalid input");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_RESET) {
            mCallbacks.resetCalculation();
        }
    }
}

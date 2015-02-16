package com.tragicfruit.duckie;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 * Fragment for displaying the results of the Duckworth-Lewis calculation
 */
public class ResultFragment extends Fragment {
    private static final String EXTRA_MATCH_ID =
            "com.tragicfruit.duckie.match_id";

    private static final String TAG = "ResultFragment";

    private Match mMatch;
    private TextView mTargetScoreTextView;
    private TextView mResultTextView;
    private TextView mResultDetailTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_MATCH_ID);
        mMatch = MatchLab.get(getActivity()).getMatch(matchId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        mTargetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        mResultTextView = (TextView) v.findViewById(R.id.result_textView);
        mResultDetailTextView = (TextView) v.findViewById(R.id.result_detail_textView);

        update();

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

                    mResultTextView.setText("" + targetScore);
                    mResultDetailTextView.setText("(" + (targetScore - 1) + " runs to tie)");
                } else {
                    // No result (not reached minimum required overs)
                    mTargetScoreTextView.setVisibility(View.GONE);

                    mResultTextView.setText(R.string.no_result_label);
                    int minOvers = mMatch.getMatchType() == Match.ONEDAY50 ? 20 : 5;
                    mResultDetailTextView.setText(getString(R.string.no_result_detail, minOvers));
                }
            } catch (Exception e) {
                // Valid inputs, but error with finding resources
                mTargetScoreTextView.setVisibility(View.GONE);

                mResultTextView.setText(R.string.calculation_error_label);
                mResultDetailTextView.setText(R.string.calculation_error_detail);

                Log.e(TAG, "Error calculating target score", e);
            }
        } else {
            // Missing one of the input values
            mTargetScoreTextView.setVisibility(View.GONE);

            mResultTextView.setText(R.string.calculation_error_label);
            mResultDetailTextView.setText(R.string.calculation_error_detail);

            Log.i(TAG, "Invalid input");
        }
    }

    public static ResultFragment newInstance(UUID matchId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MATCH_ID, matchId);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

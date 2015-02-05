package com.tragicfruit.duckworthlewiscalculator;

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
            "com.tragicfruit.duckworthlewiscalculator.match_id";

    private static final String TAG = "ResultFragment";

    private Match mMatch;
    private View mGoodResult;
    private TextView mBadResult;
    private TextView mTargetScoreTextView;
    private TextView mTieTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_MATCH_ID);
        mMatch = MatchLab.get().getMatch(matchId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        mGoodResult = v.findViewById(R.id.good_result);
        mBadResult = (TextView) v.findViewById(R.id.bad_result_textView);

        mTargetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        mTieTextView = (TextView) v.findViewById(R.id.tie_label);

        updateResult();

        return v;
    }

    private boolean isValidInput() {
        return mMatch.mFirstInnings.getMaxOvers() >= 0
            && mMatch.mFirstInnings.getRuns() >= 0
            && mMatch.mSecondInnings.getMaxOvers() >= 0;
    }

    public void updateResult() {
        if (isValidInput()) {
            try {
                if (mMatch.getTargetScore() >= 0) {
                    mGoodResult.setVisibility(View.VISIBLE);
                    mBadResult.setVisibility(View.INVISIBLE);

                    mTargetScoreTextView.setText("" + mMatch.getTargetScore());
                    mTieTextView.setText("(" + (mMatch.getTargetScore() - 1) + " runs to tie)");
                } else {
                    // No result (not reached minimum required overs)
                    mGoodResult.setVisibility(View.INVISIBLE);
                    mBadResult.setVisibility(View.VISIBLE);

                    int minOvers = mMatch.getMatchType() == Match.MatchType.ONEDAY50 ? 20 : 5;
                    mBadResult.setText(getString(R.string.no_result_message, minOvers));
                }
            } catch (Exception e) {
                // Valid inputs, but error with finding resources
                mBadResult.setText(R.string.calculation_error_message);

                mGoodResult.setVisibility(View.INVISIBLE);
                mBadResult.setVisibility(View.VISIBLE);

                Log.e(TAG, "Error calculating target score", e);
            }
        } else {
            // Missing one of the input values
            Log.i(TAG, "Invalid input");
            mGoodResult.setVisibility(View.INVISIBLE);
            mBadResult.setVisibility(View.VISIBLE);
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

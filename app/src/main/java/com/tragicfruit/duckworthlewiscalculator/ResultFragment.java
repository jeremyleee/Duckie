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
 */
public class ResultFragment extends Fragment {
    private static final String EXTRA_RESULT_MATCH_ID =
            "com.tragicfruit.duckworthlewiscalculator.result_match_id";

    private static final String TAG = "ResultFragment";

    private Match mMatch;
    private View mGoodResult;
    private TextView mBadResult;
    private TextView mTargetScoreTextView;
    private TextView mTieTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_RESULT_MATCH_ID);
        mMatch = MatchLab.get().getMatch(matchId);
    }

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
        return mMatch.mFirstInnings.getOvers() >= 0
            && mMatch.mFirstInnings.getRuns() >= 0
            && mMatch.mSecondInnings.getOvers() >= 0;
    }

    public void updateResult() {
        if (isValidInput()) {
            try {
                mGoodResult.setVisibility(View.VISIBLE);
                mBadResult.setVisibility(View.INVISIBLE);

                mTargetScoreTextView.setText("" + mMatch.getTargetScore());
                mTieTextView.setText("(" + (mMatch.getTargetScore() - 1) + " runs to tie)");
            } catch (Exception e) {
                mGoodResult.setVisibility(View.INVISIBLE);
                mBadResult.setVisibility(View.VISIBLE);

                Log.e(TAG, "Error calculating target score", e);
            }
        } else {
            mGoodResult.setVisibility(View.INVISIBLE);
            mBadResult.setVisibility(View.VISIBLE);
        }
    }

    public static ResultFragment newInstance(UUID matchId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESULT_MATCH_ID, matchId);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

package com.tragicfruit.duckworthlewiscalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 */
public class ResultFragment extends Fragment {
    private static final String EXTRA_RESULT_MATCH_ID =
            "com.tragicfruit.duckworthlewiscalculator.result_match_id";

    private static final String TAG = "ResultFragment";

    private Match mMatch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_RESULT_MATCH_ID);
        mMatch = MatchLab.get().getMatch(matchId);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        final TextView targetScoreTextView = (TextView) v.findViewById(R.id.target_score_textView);
        final TextView tieTextView = (TextView) v.findViewById(R.id.tie_label);

        if (isValidInput()) {
            try {
                targetScoreTextView.setText("" + mMatch.getTargetScore());
                tieTextView.setText("(" + (mMatch.getTargetScore() - 1) + " runs to tie)");
            } catch (Exception e) {
                Log.e(TAG, "Error calculating target score", e);
                Toast.makeText(getActivity(), "Error calculating target score.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Invalid input, try again.", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private boolean isValidInput() {
        return mMatch.mFirstInnings.getOvers() >= 0
                && mMatch.mFirstInnings.getRuns() >= 0
                && mMatch.mSecondInnings.getOvers() >= 0;
    }

    public static Fragment newInstance(UUID matchId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESULT_MATCH_ID, matchId);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

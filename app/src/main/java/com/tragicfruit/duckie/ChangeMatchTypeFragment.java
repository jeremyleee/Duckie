package com.tragicfruit.duckie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.UUID;

/**
 * Created by Jeremy on 15/02/2015.
 * Allows changing match type to either 50 over match or 20 over match
 */
public class ChangeMatchTypeFragment extends DialogFragment {
    private static final String EXTRA_MATCH_ID = "com.tragicfruit.duckie.match_id";

    private Match mMatch;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_change_match_type, null);


        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_MATCH_ID);
        mMatch = MatchLab.get(getActivity()).getMatch(matchId);

        int matchType = mMatch.getMatchType();
        if (matchType == Match.ONEDAY50) {
            ((RadioButton) v.findViewById(R.id.one_day_radioButton)).setChecked(true);
        } else if (matchType == Match.TWENTY20) {
            ((RadioButton) v.findViewById(R.id.twenty20_radioButton)).setChecked(true);
        }

        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.match_type_radioGroup);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.change_match_type_label)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int checkedItem = radioGroup.getCheckedRadioButtonId();
                        if (checkedItem == R.id.one_day_radioButton) {
                            mMatch.setMatchType(Match.ONEDAY50);
                            mMatch.mFirstInnings.setMaxOvers(Match.ONEDAY50);
                            mMatch.mSecondInnings.setMaxOvers(Match.ONEDAY50);
                        } else if (checkedItem == R.id.twenty20_radioButton) {
                            mMatch.setMatchType(Match.TWENTY20);
                            mMatch.mFirstInnings.setMaxOvers(Match.TWENTY20);
                            mMatch.mSecondInnings.setMaxOvers(Match.TWENTY20);
                        }
                        ((MatchActivity) getActivity()).updateFragments();
                    }
                })
                .create();
    }

    public static ChangeMatchTypeFragment newInstance(UUID matchId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MATCH_ID, matchId);

        ChangeMatchTypeFragment fragment = new ChangeMatchTypeFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

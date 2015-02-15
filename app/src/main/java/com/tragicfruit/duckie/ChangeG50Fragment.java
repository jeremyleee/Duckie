package com.tragicfruit.duckie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Jeremy on 15/02/2015.
 * Allows changing G50 to a custom figure
 */
public class ChangeG50Fragment extends DialogFragment {
    private static final String EXTRA_MATCH_ID = "com.tragicfruit.duckie.match_id";

    private EditText mChangeG50Field;
    private int mG50;
    private Match mMatch;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_change_g50, null);


        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_MATCH_ID);
        mMatch = MatchLab.get(getActivity()).getMatch(matchId);

        int currentG50 = mMatch.getG50();
        mChangeG50Field = (EditText) v.findViewById(R.id.change_g50_editText);
        mChangeG50Field.setText("" + currentG50);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.change_g50_label)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isValidInput()) {
                            Toast.makeText(getActivity(),
                                    getString(R.string.g50_changed_toast, mG50),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            // TODO: change this to maintain fragment independence
                            ((MatchActivity) getActivity()).updateFragments();
                        } else {
                            Toast.makeText(getActivity(),
                                    R.string.invalid_g50_toast,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                })
                .create();
    }

    private boolean isValidInput() {
        try {
            mG50 = Integer.parseInt(mChangeG50Field.getText().toString());
            mMatch.setG50(mG50);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ChangeG50Fragment newInstance(UUID matchId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MATCH_ID, matchId);

        ChangeG50Fragment fragment = new ChangeG50Fragment();
        fragment.setArguments(args);

        return fragment;
    }

}

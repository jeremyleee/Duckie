package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Jeremy on 30/12/2015.
 */
public class ResetFragment extends DialogFragment {

    public static ResetFragment newInstance() {
        return new ResetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.reset_warning)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.reset_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                            }
                        })
                .create();
    }

}

package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Jeremy on 17/02/2015.
 * Requests confirmation from user to delete fragment
 */
public class DeleteInterruptionFragment extends DialogFragment {

    public static DeleteInterruptionFragment newInstance() {
        return new DeleteInterruptionFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.delete_interruption_message)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.delete_interruption_positive_button,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                })
                .create();
    }

}

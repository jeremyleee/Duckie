package com.tragicfruit.duckie;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeremy on 3/02/2015.
 * Fragment for user input and displaying data for a single innings
 */
public class DLInningsFragment extends Fragment {
    private static final String EXTRA_IS_FIRST_INNINGS =
            "com.tragicfruit.duckie.is_first_innings";

    private static final String DIALOG_INTERRUPTION = "interruption";
    private static final String DIALOG_DELETE_INTERRUPTION = "delete_interruption";
    private static final int REQUEST_INTERRUPTION = 0;
    private static final int REQUEST_DELETE_INTERRUPTION = 1;

    private DLCalculation mMatch;
    private DLInnings mInnings;
    private boolean mIsFirstInnings;
    private ArrayList<DLInnings.Interruption> mInterruptions;

    private LinearLayout mInterruptionList;
    private TextView mInterruptionsLabel;
    private EditText mWicketsField;
    private EditText mOversField;
    private EditText mRunsField;
    private Button mAddInterruptionButton;
    private Button mContinueButton;

    private DLInnings.Interruption mCurrentInterruptionEdited; // points to interruption being edited
    private DLInnings.Interruption mCurrentInterruptionDeleted; // points to interruption being deleted

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMatch = CalculationLab.get(getActivity()).getDLCalculation();

        mIsFirstInnings = getArguments().getBoolean(EXTRA_IS_FIRST_INNINGS);
        if (mIsFirstInnings) {
            mInnings = mMatch.mFirstInnings;
        } else {
            mInnings = mMatch.mSecondInnings;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dl_fragment_innings, container, false);

        // Initially hides virtual keyboard
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // Prevents focus on EditText when opening fragment
        View focusHere = v.findViewById(R.id.focus_here);
        focusHere.requestFocus();

        /**
         * Set up EditText fields
         */
        TextView inningsLabel = (TextView) v.findViewById(R.id.innings_label);
        View firstInningsScoreSection = v.findViewById(R.id.first_innings_score_section);
        if (mIsFirstInnings) {
            inningsLabel.setText(R.string.first_innings_label);
            firstInningsScoreSection.setVisibility(View.VISIBLE);

            mRunsField  = (EditText) v.findViewById(R.id.first_innings_runs_editText);
            mRunsField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void afterTextChanged(Editable s) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int input = Integer.parseInt(s.toString());
                        mInnings.setRuns(input);
                    } catch (Exception e) {
                        mInnings.setRuns(-1);
                    }
                }
            });

            mWicketsField = (EditText) v.findViewById(R.id.first_innings_wickets_editText);
            mWicketsField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void afterTextChanged(Editable s) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int input = Integer.parseInt(s.toString());
                        if (input <= 10) {
                            mInnings.setWickets(input);
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        mInnings.setWickets(-1);
                    }
                }
            });
        } else {
            inningsLabel.setText(R.string.second_innings_label);
            firstInningsScoreSection.setVisibility(View.GONE);
        }

        mOversField = (EditText) v.findViewById(R.id.max_overs_editText);
        mOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input <= mMatch.getMatchType()) {
                        mInnings.setMaxOvers(input);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    mInnings.setMaxOvers(-1);
                }
            }
        });

        mAddInterruptionButton  = (Button) v.findViewById(R.id.add_interruption_button);
        mAddInterruptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DLInterruptionFragment dialog = new DLInterruptionFragment();
                dialog.setTargetFragment(DLInningsFragment.this, REQUEST_INTERRUPTION);
                dialog.show(fm, DIALOG_INTERRUPTION);
            }
        });

        mInterruptionsLabel = (TextView) v.findViewById(R.id.interruptions_label);
        mInterruptionList = (LinearLayout) v.findViewById(R.id.interruption_list_section);

        mContinueButton = (Button) v.findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CalculatorActivity) getActivity()).mDLCalculatorFragment.nextPage();
            }
        });

        update();

        return v;
    }

    // displays data from model
    public void update() {
        if (mInnings.getRuns() >= 0)
            mRunsField.setText("" + mInnings.getRuns());

        if (mInnings.getWickets() >= 0)
            mWicketsField.setText("" + mInnings.getWickets());

        if (mInnings.getMaxOvers() >= 0)
            mOversField.setText("" + mInnings.getMaxOvers());

        updateInterruptionList();
    }

    private void updateInterruptionList() {
        mInterruptions = mInnings.getInterruptions();
        if (mInterruptions.isEmpty()) {
            mInterruptionsLabel.setVisibility(View.GONE);
        } else {
            mInterruptionsLabel.setVisibility(View.VISIBLE);
        }

        mInterruptionList.removeAllViews(); // reset list of interruptions
        for (final DLInnings.Interruption i : mInterruptions) {
            // adds a single interruption to be displayed
            RelativeLayout interruptionListItem = (RelativeLayout) getActivity().getLayoutInflater()
                    .inflate(R.layout.dl_list_item_interruption, mInterruptionList, false);
            TextView interruptionTitle = ((TextView) interruptionListItem.findViewById(R.id.interruption_desc_textView));
            String title = getString(R.string.interruption_title,
                    i.getInputRuns(), i.getInputWickets(), i.getInputOversCompleted());
            interruptionTitle.setText(title);
            mInterruptionList.addView(interruptionListItem);

            // set listeners on interruption buttons
            ImageView interruptionEditButton = (ImageView) interruptionListItem.findViewById(R.id.interruption_edit_button);
            if (interruptionEditButton != null) {
                interruptionEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentInterruptionEdited = i;
                        FragmentManager fm = getFragmentManager();
                        DLInterruptionFragment dialog = DLInterruptionFragment.newInstance(i.getInputRuns(),
                                i.getInputWickets(), i.getInputOversCompleted(), i.getInputNewTotalOvers());
                        dialog.setTargetFragment(DLInningsFragment.this, REQUEST_INTERRUPTION);
                        dialog.show(fm, DIALOG_INTERRUPTION);
                    }
                });
            }

            ImageView interruptionDeleteButton = (ImageView) interruptionListItem.findViewById(R.id.interruption_delete_button);
            if (interruptionDeleteButton != null) {
                interruptionDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentInterruptionDeleted = i;
                        FragmentManager fm = getFragmentManager();
                        DLDeleteInterruptionFragment dialog = new DLDeleteInterruptionFragment();
                        dialog.setTargetFragment(DLInningsFragment.this, REQUEST_DELETE_INTERRUPTION);
                        dialog.show(fm, DIALOG_DELETE_INTERRUPTION);
                    }
                });
            }
        }
    }

    public DLCalculation getMatch() {
        return mMatch;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            mCurrentInterruptionEdited = null;
            mCurrentInterruptionDeleted = null;
            return;
        }

        if (requestCode == REQUEST_INTERRUPTION) {
            if (mCurrentInterruptionEdited != null) {
                // remove old interruption after edit & replace with new one
                mInnings.getInterruptions().remove(mCurrentInterruptionEdited);
                mCurrentInterruptionEdited = null;
            }

            mInnings.addInterruption(
                    data.getIntExtra(DLInterruptionFragment.EXTRA_INPUT_RUNS, -1),
                    data.getIntExtra(DLInterruptionFragment.EXTRA_INPUT_WICKETS, -1),
                    data.getIntExtra(DLInterruptionFragment.EXTRA_INPUT_OVERS_COMPLETED, -1),
                    data.getIntExtra(DLInterruptionFragment.EXTRA_INPUT_NEW_TOTAL_OVERS, -1)
            );
            updateInterruptionList();
        } else if (requestCode == REQUEST_DELETE_INTERRUPTION) {
            mInterruptions.remove(mCurrentInterruptionDeleted);
            mCurrentInterruptionDeleted = null;
            updateInterruptionList();
        }
    }

    public static DLInningsFragment newInstance(boolean isFirstInnings) {
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_IS_FIRST_INNINGS, isFirstInnings);

        DLInningsFragment fragment = new DLInningsFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

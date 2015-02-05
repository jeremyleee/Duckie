package com.tragicfruit.duckworthlewiscalculator;

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
import java.util.UUID;

/**
 * Created by Jeremy on 3/02/2015.
 * Fragment for user input and displaying data for a single innings
 */
public class InningsFragment extends Fragment {
    private static final String EXTRA_MATCH_ID =
            "com.tragicfruit.duckworthlewiscalculator.match_id";
    private static final String EXTRA_IS_FIRST_INNINGS =
            "com.tragicfruit.duckworthlewiscalculator.is_first_innings";

    private static final String DIALOG_INTERRUPTION = "interruption";
    private static final int REQUEST_INTERRUPTION = 0;

    private Innings mInnings;
    private boolean mIsFirstInnings;

    private LinearLayout mInterruptionList;
    private TextView mInterruptionsLabel;
    private EditText mWicketsField;
    private EditText mOversField;
    private EditText mRunsField;
    private Button mAddInterruptionButton;

    private Innings.Interruption mCurrentInterruptionEdited;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID matchId = (UUID) getArguments().getSerializable(EXTRA_MATCH_ID);
        Match match = MatchLab.get().getMatch(matchId);

        mIsFirstInnings = getArguments().getBoolean(EXTRA_IS_FIRST_INNINGS);
        if (mIsFirstInnings) {
            mInnings = match.mFirstInnings;
        } else {
            mInnings = match.mSecondInnings;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_innings, container, false);

        // Initially hides virtual keyboard
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        /**
         * Set up innings specific widgets
         */
        TextView inningsLabel = (TextView) v.findViewById(R.id.innings_label);
        View firstInningsScoreSection = v.findViewById(R.id.first_innings_score_section);
        if (mIsFirstInnings) {
            inningsLabel.setText(R.string.first_innings_label);
            firstInningsScoreSection.setVisibility(View.VISIBLE);

            mRunsField  = (EditText) v.findViewById(R.id.first_innings_runs_editText);
            if (mInnings.getRuns() >= 0)
                mRunsField.setText("" + mInnings.getRuns());
            mRunsField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void afterTextChanged(Editable s) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int input = Integer.parseInt(s.toString());
                        if (input >= 0) {
                            mInnings.setRuns(input);
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        mInnings.setRuns(-1);
                    }
                }
            });

            mWicketsField = (EditText) v.findViewById(R.id.first_innings_wickets_editText);
            if (mInnings.getWickets() >= 0)
                mWicketsField.setText("" + mInnings.getWickets());
            mWicketsField.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void afterTextChanged(Editable s) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int input = Integer.parseInt(s.toString());
                        if (input >= 0 && input <= 10) {
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
        if (mInnings.getMaxOvers() >= 0)
            mOversField.setText("" + mInnings.getMaxOvers());
        mOversField.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int input = Integer.parseInt(s.toString());
                    if (input >= 0 && input <= 50) {
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
                InterruptionFragment dialog = new InterruptionFragment();
                dialog.setTargetFragment(InningsFragment.this, REQUEST_INTERRUPTION);
                dialog.show(fm, DIALOG_INTERRUPTION);
            }
        });

        mInterruptionsLabel = (TextView) v.findViewById(R.id.interruptions_label);
        mInterruptionList = (LinearLayout) v.findViewById(R.id.interruption_list_section);
        updateInterruptionList();

        return v;
    }

    private void updateInterruptionList() {
        final ArrayList<Innings.Interruption> interruptions = mInnings.getInterruptions();
        if (interruptions.isEmpty()) {
            mInterruptionsLabel.setVisibility(View.GONE);
        } else {
            mInterruptionsLabel.setVisibility(View.VISIBLE);
        }

        mInterruptionList.removeAllViews();
        for (final Innings.Interruption i : interruptions) {

            RelativeLayout interruptionListItem = (RelativeLayout) getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_interruption, mInterruptionList, false);
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
                        InterruptionFragment dialog = InterruptionFragment.newInstance(i.getInputRuns(),
                                i.getInputWickets(), i.getInputOversCompleted(), i.getInputNewTotalOvers());
                        dialog.setTargetFragment(InningsFragment.this, REQUEST_INTERRUPTION);
                        dialog.show(fm, DIALOG_INTERRUPTION);
                    }
                });
            }

            ImageView interruptionDeleteButton = (ImageView) interruptionListItem.findViewById(R.id.interruption_delete_button);
            if (interruptionDeleteButton != null) {
                interruptionDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interruptions.remove(i);
                        updateInterruptionList();
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            mCurrentInterruptionEdited = null;
            return;
        }

        if (requestCode == REQUEST_INTERRUPTION) {
            if (mCurrentInterruptionEdited != null) {
                mInnings.getInterruptions().remove(mCurrentInterruptionEdited);
                mCurrentInterruptionEdited = null;
            }

            mInnings.addInterruption(
                    data.getIntExtra(InterruptionFragment.EXTRA_INPUT_RUNS, -1),
                    data.getIntExtra(InterruptionFragment.EXTRA_INPUT_WICKETS, -1),
                    data.getIntExtra(InterruptionFragment.EXTRA_INPUT_OVERS_COMPLETED, -1),
                    data.getIntExtra(InterruptionFragment.EXTRA_INPUT_NEW_TOTAL_OVERS, -1)
            );
            updateInterruptionList();
        }
    }

    public static InningsFragment newInstance(UUID matchId, boolean isFirstInnings) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MATCH_ID, matchId);
        args.putBoolean(EXTRA_IS_FIRST_INNINGS, isFirstInnings);

        InningsFragment fragment = new InningsFragment();
        fragment.setArguments(args);

        return fragment;
    }

}

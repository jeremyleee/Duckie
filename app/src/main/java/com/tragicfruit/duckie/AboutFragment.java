package com.tragicfruit.duckie;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Jeremy on 6/02/2015.
 * Explains the Duckworth-Lewis method and the app's capabilities
 */
public class AboutFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_about_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new SelectionAdapter());

//        // rate app button sends user to Play Store
//        Button rateAppButton = (Button) v.findViewById(R.id.rate_app_button);
//        rateAppButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String appPackageName = getActivity().getPackageName();
//                try {
//                    Uri playStoreLink = Uri.parse("market://details?id=" + appPackageName);
//                    Intent i = new Intent(Intent.ACTION_VIEW, playStoreLink);
//                    startActivity(i);
//                } catch (android.content.ActivityNotFoundException e) {
//                    // Devices without Play Store
//                    Uri playStoreLink = Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
//                    Intent i = new Intent(Intent.ACTION_VIEW, playStoreLink);
//                    startActivity(i);
//                }
//
//
//            }
//        });
//
//        // send feedback button opens email app ready to end email
//        Button sendFeedbackButton = (Button) v.findViewById(R.id.send_feedback_button);
//        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String version;
//                try {
//                    version = getActivity().getPackageManager()
//                            .getPackageInfo(getActivity().getPackageName(), 0).versionName;
//                } catch (Exception e) {
//                    version = "no version";
//                }
//                String uriString = "mailto:"
//                        + getString(R.string.feedback_email_address)
//                        + "?subject=" + getString(R.string.feedback_email_subject, version);
//                Intent i = new Intent(Intent.ACTION_SENDTO);
//                i.setData(Uri.parse(uriString));
//                startActivity(i);
//            }
//        });

        return v;
    }

    private class SelectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mLabel;
        public ImageView mIcon;
        public int mSelectionIndex;

        public SelectionHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mLabel = (TextView) itemView.findViewById(R.id.list_item_about_text);
            mIcon = (ImageView) itemView.findViewById(R.id.list_item_about_icon);
        }

        public void setSelectionIndex(int selectionIndex) {
            mSelectionIndex = selectionIndex;
        }

        @Override
        public void onClick(View v) {
            switch (mSelectionIndex) {
                case 0:
//                    holder.mLabel.setText(R.string.faq_button);
                    break;
                case 1:
                    String appPackageName = getActivity().getPackageName();
                    try {
                        Uri playStoreLink = Uri.parse("market://details?id=" + appPackageName);
                        Intent i = new Intent(Intent.ACTION_VIEW, playStoreLink);
                        startActivity(i);
                    } catch (android.content.ActivityNotFoundException e) {
                        // Devices without Play Store
                        Uri playStoreLink = Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
                        Intent i = new Intent(Intent.ACTION_VIEW, playStoreLink);
                        startActivity(i);
                    }
                    break;
                case 2:
                    String version;
                    try {
                        version = getActivity().getPackageManager()
                                .getPackageInfo(getActivity().getPackageName(), 0).versionName;
                    } catch (Exception e) {
                        version = "no version";
                    }
                    String uriString = "mailto:"
                            + getString(R.string.feedback_email_address)
                            + "?subject=" + getString(R.string.feedback_email_subject, version);
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse(uriString));
                    startActivity(i);
                    break;
            }
        }
    }

    private class SelectionAdapter extends RecyclerView.Adapter<SelectionHolder> {

        @Override
        public SelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_about, parent, false);
            return new SelectionHolder(view);
        }

        @Override
        public void onBindViewHolder(SelectionHolder holder, int position) {
            holder.setSelectionIndex(position);
            switch (position) {
                case 0:
                    holder.mLabel.setText(R.string.faq_button);
                    holder.mIcon.setImageDrawable(
                            getResources().getDrawable(R.drawable.ic_faq)
                    );
                    break;
                case 1:
                    holder.mLabel.setText(R.string.rate_app_button);
                    holder.mIcon.setImageDrawable(
                            getResources().getDrawable(R.drawable.ic_rate)
                    );
                    break;
                case 2:
                    holder.mLabel.setText(R.string.send_feedback_button);
                    holder.mIcon.setImageDrawable(
                            getResources().getDrawable(R.drawable.ic_feedback)
                    );
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}

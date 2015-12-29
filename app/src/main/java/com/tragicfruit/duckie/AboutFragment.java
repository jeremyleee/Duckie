package com.tragicfruit.duckie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jeremy on 6/02/2015.
 * Explains the Duckworth-Lewis method and the app's capabilities
 */
public class AboutFragment extends Fragment {

    private static final String TAG = "AboutFragment";

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        try {
            TextView versionLabel = (TextView) v.findViewById(R.id.version_label);
            String version = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
            versionLabel.setText(getString(R.string.app_version_label, version));
        } catch (Exception e) {
            Log.e(TAG, "Error reporting version number", e);
        }

        // rate app button sends user to Play Store
        Button rateAppButton = (Button) v.findViewById(R.id.rate_app_button);
        rateAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


            }
        });

        // send feedback button opens email app ready to end email
        Button sendFeedbackButton = (Button) v.findViewById(R.id.send_feedback_button);
        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        return v;
    }
}

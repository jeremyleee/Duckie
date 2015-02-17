package com.tragicfruit.duckie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jeremy on 6/02/2015.
 * Explains the Duckworth-Lewis method and the app's capabilties
 */
public class AboutActivity extends ActionBarActivity {
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            TextView versionLabel = (TextView) findViewById(R.id.version_label);
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionLabel.setText(getString(R.string.app_version_label, version));
        } catch (Exception e) {
            Log.e("AboutActivity", "Error reporting version number", e);
        }

        // rate app button sends user to Play Store
        Button rateAppButton = (Button) findViewById(R.id.rate_app_button);
        rateAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getPackageName();
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
        Button sendFeedbackButton = (Button) findViewById(R.id.send_feedback_button);
        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String version;
                try {
                    version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null)
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

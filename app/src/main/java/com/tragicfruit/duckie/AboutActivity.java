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

        Button leaveFeedbackLabel = (Button) findViewById(R.id.leave_feedback_label);
        leaveFeedbackLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "mailto:"
                        + getString(R.string.feedback_email_address)
                        + "?subject=" + getString(R.string.feedback_email_subject);
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

package com.tragicfruit.duckie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jeremy on 6/03/2015.
 * Menu screen for user to choose which calculator to use
 */
public class MenuFragment extends Fragment {
    public static final int DL_CALCULATOR = 0;
    public static final int OVERS_LOST_CALCULATOR = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle("a cricket calculator");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        Button dlCalculatorButton = (Button) v.findViewById(R.id.dl_calculator_button);
        dlCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CalculatorActivity.class);
                i.putExtra(CalculatorActivity.EXTRA_MENU_CHOICE, DL_CALCULATOR);
                startActivity(i);
            }
        });

        Button oversLostCalculatorButton = (Button) v.findViewById(R.id.overs_lost_calculator_button);
        oversLostCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CalculatorActivity.class);
                i.putExtra(CalculatorActivity.EXTRA_MENU_CHOICE, OVERS_LOST_CALCULATOR);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_about):
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

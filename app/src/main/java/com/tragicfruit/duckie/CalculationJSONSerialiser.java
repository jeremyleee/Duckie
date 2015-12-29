package com.tragicfruit.duckie;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 6/02/2015.
 * Saves and loads calculations to JSON file
 */
public class CalculationJSONSerialiser {

    private Context mContext;
    private String mFileName;

    public CalculationJSONSerialiser(Context c, String f) {
        mContext = c;
        mFileName = f;
    }

    public ArrayList<Calculation> loadCalculations() throws IOException, JSONException {
        ArrayList<Calculation> calculations = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            calculations.add(new Calculation(array.getJSONObject(0)));
        } catch (FileNotFoundException e) {
            // App starting fresh
            throw new FileNotFoundException();
        } finally {
            if (reader != null)
                reader.close();
        }
        return calculations;
    }

    public void saveCalculations(List<Calculation> calculations) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for (Calculation c : calculations) {
            array.put(c.toJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}

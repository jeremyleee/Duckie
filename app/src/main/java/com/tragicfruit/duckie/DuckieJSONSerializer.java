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

/**
 * Created by Jeremy on 6/02/2015.
 * Saves and loads matches to JSON file
 */
public class DuckieJSONSerializer {

    private Context mContext;
    private String mFileName;

    public DuckieJSONSerializer(Context c, String f) {
        mContext = c;
        mFileName = f;
    }

    public ArrayList<DLCalculation> loadMatches() throws IOException, JSONException {
        ArrayList<DLCalculation> matches = new ArrayList<>();
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
            for (int i = 0; i < array.length(); i++) {
                matches.add(new DLCalculation(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // App starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return matches;
    }

    public void saveMatches(ArrayList<DLCalculation> matches) throws IOException, JSONException {
        JSONArray array = new JSONArray();
        for (DLCalculation m : matches) {
            array.put(m.toJSON());
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

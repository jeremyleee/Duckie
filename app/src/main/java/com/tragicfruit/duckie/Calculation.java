package com.tragicfruit.duckie;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jeremy on 7/03/2015.
 */
public abstract class Calculation {

    public abstract JSONObject toJSON() throws JSONException;

}

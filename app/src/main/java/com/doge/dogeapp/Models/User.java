package com.doge.dogeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int _id;
    private String name;

    public String getName() {
        return name;
    }

    public User(JSONObject user) throws JSONException {

        this._id = Integer.parseInt(user.getJSONObject("user").get("_id").toString());
        this.name = user.getJSONObject("user").get("name").toString();

    }

    public int getId() {
        return _id;
    }
}

package com.doge.dogeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String id;

    public User(JSONObject user) throws JSONException {

        this.id = (String) user.getJSONObject("user").get("_id").toString();
    }

    public String getId() {
        return id;
    }
}

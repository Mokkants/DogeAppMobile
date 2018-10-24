package com.doge.dogeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {

    private int _id;
    private String text;


    public int getId() {
        return _id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "_id=" + _id +
                ", text='" + text + '\'' +
                '}';
    }

    public Post(JSONObject o) throws JSONException {
        this(o.getInt("_id"), o.getString("text"));
    }

    public Post(int _id, String text) {
        this._id = _id;
        this.text = text;
    }
}

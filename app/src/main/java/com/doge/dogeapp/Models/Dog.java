package com.doge.dogeapp.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Dog {
    private int _id;
    private String name;
    private String breed;
    private boolean isSocial;
    private String shortInfo;

    public Dog(JSONObject current) throws JSONException {
        this(current.getInt("_id"), current.getString("name"), current.getString("breed"), current.getBoolean("isSocial"), current.getString("shortInfo"));
    }


    public Dog(int _id, String name, String breed, boolean isSocial, String shortInfo) {
        this._id = _id;
        this.name = name;
        this.breed = breed;
        this.isSocial = isSocial;
        this.shortInfo = shortInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String isSocial() {
        if (isSocial)
            return "Yes";
        else
            return "No";
    }

    public void setSocial(boolean social) {
        isSocial = social;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", isSocial='" + isSocial + '\'' +
                ", shortInfo='" + shortInfo + '\'' +
                '}';
    }
}

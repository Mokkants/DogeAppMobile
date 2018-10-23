package com.doge.dogeapp.Models;

public class Dog {
    private String name;
    private String breed;
    private boolean isSocial;
    private String shortInfo;

    public Dog(){

    }

    public Dog(String name, String breed, boolean isSocial, String shortInfo) {
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

    public boolean isSocial() {
        return isSocial;
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
}

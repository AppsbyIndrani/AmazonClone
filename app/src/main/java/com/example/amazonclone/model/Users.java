package com.example.amazonclone.model;

public class Users
{

    String uid,name,email,imageuri;

    public Users(String uid, String name, String email, String imageuri) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageuri = imageuri;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}

package com.example.movies.models;

public class Gravatar {
    public String hash;

    public Gravatar(){}

    public Gravatar(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

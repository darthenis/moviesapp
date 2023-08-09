package com.example.movies.models;

public class Tmdb {
    private String avatar_path;

    public Tmdb(){}
    public Tmdb(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }
}

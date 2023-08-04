package com.example.movies.models;

public class SearchMovie {

    private boolean adult;
    private String backdrop_path;
    private int id;
    private String name;
    private String original_language;
    private String original_name;
    private String overview;
    private String poster_path;

    public SearchMovie(){}

    public SearchMovie(boolean adult, String backdrop_path, int id, String name, String original_language, String original_name, String overview, String poster_path) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.name = name;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}

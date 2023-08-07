package com.example.movies.models;

import java.util.ArrayList;

public class CreditsMovie {
    public int id;
    public ArrayList<Cast> cast;

    public ArrayList<Crew> crew;

    public CreditsMovie(int id, ArrayList<Cast> cast, ArrayList<Crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public CreditsMovie(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }
}

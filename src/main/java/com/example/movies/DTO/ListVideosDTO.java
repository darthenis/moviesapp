package com.example.movies.DTO;

import java.util.ArrayList;

public class ListVideosDTO {
    public int id;
    public ArrayList<VideosDTO> results;

    public ListVideosDTO(){}
    public ListVideosDTO(int id, ArrayList<VideosDTO> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideosDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideosDTO> results) {
        this.results = results;
    }
}

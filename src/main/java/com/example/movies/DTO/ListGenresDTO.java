package com.example.movies.DTO;

import com.example.movies.models.Genre;

import java.util.List;

public class ListGenresDTO {

    List<Genre> genres;

    public ListGenresDTO(List<Genre> genres) {
        this.genres = genres;
    }

    public ListGenresDTO() {
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}

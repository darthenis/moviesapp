package com.example.movies.DTO;

import com.example.movies.models.Dates;
import com.example.movies.models.Movie;

import java.util.List;

public class ListMoviesDTO {
    private Dates dates;
    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;



    public ListMoviesDTO(int page, List<Movie> results, int total_pages, int total_results, Dates dates) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.dates = dates;
    }
    public ListMoviesDTO(){}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }
}

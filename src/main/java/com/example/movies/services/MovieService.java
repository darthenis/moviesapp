package com.example.movies.services;

import com.example.movies.DTO.*;
import com.example.movies.models.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MovieService {

    OkHttpClient client = new OkHttpClient();

    private String API_KEY = "9b8b5a71ed3f7d0620bf8ec5f58c0fca";

    private ListEnum selectedList;

    private int page = 1;

    private int totalPages = 1;

    public MovieService(){

    }

    public List<Movie> getMovies(ListEnum listEnum) throws IOException {
        String list;
        switch (listEnum){
            case NOW_PLAYING:
                list= "now_playing";
                this.selectedList = ListEnum.NOW_PLAYING;
                break;
            case POPULAR:
                list = "popular";
                this.selectedList = ListEnum.POPULAR;
                break;
            case TOP_RATED:
                list = "top_rated";
                this.selectedList = ListEnum.TOP_RATED;
                break;
            default:
                list = "upcoming";
                this.selectedList = ListEnum.UPCOMING;
                break;
        }
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+list+"?language=es-ES&page="+page+"&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        ListMoviesDTO listMoviesDTO = objectMapper.readValue(response.body().string(), ListMoviesDTO.class);
        this.totalPages = listMoviesDTO.getTotal_pages();
        return listMoviesDTO.getResults();

    }

    public void setPage(int page){
        this.page = page;
    }

    public int getPage(){
        return this.page;
    }

    public int getTotalPages(){
        return this.totalPages;
    }
    public List<Genre> getGenres() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/genre/movie/list?language=es&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListGenresDTO.class).getGenres();
    }

    public List<Movie> filterByGenre(int id) throws IOException {
        String type = "";
        String sort = "sort_by=popularity.desc&";
        switch (selectedList) {
            case POPULAR:
                break;
            case TOP_RATED:
                type = "without_genres=99,10755&vote_count.gte=200";
                sort = "sort_by=vote_average.desc&";
                break;
            default:
                type = "with_release_type=2|3";
                break;
        }

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-Es&page=1&" + sort + type + "&with_genres=" + id + "&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        ListMoviesDTO listMoviesDTO = objectMapper.readValue(response.body().string(), ListMoviesDTO.class);
        this.totalPages = listMoviesDTO.getTotal_pages();
        System.out.println("total: "+this.totalPages);
        return listMoviesDTO.getResults();

    }

    public List<SearchMovie> searchByQuery(String query) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/collection?query="+query+"&include_adult=false&language=en-US&page=1&api_key="+ API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        ListSearchMoviesDTO list = objectMapper.readValue(response.body().string(), ListSearchMoviesDTO.class);
        this.totalPages = list.getTotal_pages();
        return list.getResults();

    }

    public MovieDetail getDetailMovie(Integer id) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+id+"?language=es-ES&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), MovieDetail.class);
    }

    public CreditsMovie getCredits(int id) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+id+"/credits?language=es-ES&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), CreditsMovie.class);
    }

    public ListVideosDTO getVideos(int id) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+id+"/videos?language=es-MX&api_key="+API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListVideosDTO.class);
    }

    public List<Movie> getRecommend(int id) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+id+"/recommendations?language=es-ES&page=1&api_key="+API_KEY)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListMoviesDTO.class).getResults();
    }
}

package com.example.movies.services;

import com.example.movies.models.ListEnum;
import com.example.movies.models.ListMovies;
import com.example.movies.models.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MovieService {

    OkHttpClient client = new OkHttpClient();

    public List<Movie> getMovies(ListEnum listEnum) throws IOException {
        String list;
        switch (listEnum){
            case NOW_PLAYING:
                list= "now_playing";
                break;
            case POPULAR:
                list = "popular";
                break;
            case TOP_RATED:
                list = "top_rated";
                break;
            default:
                list = "upcoming";
                break;
        }
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+list+"?language=es-ES&page=1")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjhiNWE3MWVkM2Y3ZDA2MjBiZjhlYzVmNThjMGZjYSIsInN1YiI6IjYyOTkyNmViY2RkYmJjNmUwOTVjZjE4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ml8V2_zplNSePypNBheHkWszv-7eZZMJGPxzmcfBv5k")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListMovies.class).getResults();

    }
}

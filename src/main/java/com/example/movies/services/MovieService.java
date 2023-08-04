package com.example.movies.services;

import com.example.movies.DTO.ListGenresDTO;
import com.example.movies.DTO.ListSearchMoviesDTO;
import com.example.movies.models.SearchMovie;
import com.example.movies.models.Genre;
import com.example.movies.models.ListEnum;
import com.example.movies.DTO.ListMoviesDTO;
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

    private ListEnum selectedList;

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
                .url("https://api.themoviedb.org/3/movie/"+list+"?language=es-ES&page=1")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjhiNWE3MWVkM2Y3ZDA2MjBiZjhlYzVmNThjMGZjYSIsInN1YiI6IjYyOTkyNmViY2RkYmJjNmUwOTVjZjE4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ml8V2_zplNSePypNBheHkWszv-7eZZMJGPxzmcfBv5k")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListMoviesDTO.class).getResults();

    }

    public List<Genre> getGenres() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/genre/movie/list?language=es")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjhiNWE3MWVkM2Y3ZDA2MjBiZjhlYzVmNThjMGZjYSIsInN1YiI6IjYyOTkyNmViY2RkYmJjNmUwOTVjZjE4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ml8V2_zplNSePypNBheHkWszv-7eZZMJGPxzmcfBv5k")
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
            case NOW_PLAYING:
                type = "with_release_type=2|3";
                break;
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
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-Es&page=1&" + sort + type + "&with_genres=" + id)
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjhiNWE3MWVkM2Y3ZDA2MjBiZjhlYzVmNThjMGZjYSIsInN1YiI6IjYyOTkyNmViY2RkYmJjNmUwOTVjZjE4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ml8V2_zplNSePypNBheHkWszv-7eZZMJGPxzmcfBv5k")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListMoviesDTO.class).getResults();

    }

    public List<SearchMovie> searchByQuery(String query) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/collection?query="+query+"&include_adult=false&language=en-US&page=1")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjhiNWE3MWVkM2Y3ZDA2MjBiZjhlYzVmNThjMGZjYSIsInN1YiI6IjYyOTkyNmViY2RkYmJjNmUwOTVjZjE4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ml8V2_zplNSePypNBheHkWszv-7eZZMJGPxzmcfBv5k")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), ListSearchMoviesDTO.class).getResults();

    }
}

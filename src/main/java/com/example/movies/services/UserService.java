package com.example.movies.services;

import com.example.movies.DTO.GetTokenRequest;
import com.example.movies.models.MovieDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class UserService {

    private String API_KEY = "9b8b5a71ed3f7d0620bf8ec5f58c0fca";

    private String sessionId;

    public void login(String username, String password) throws IOException {
        String request_token = getRequestToken();
        System.out.println("token: "+request_token);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"username\":\"darthenis\",\"password\":\"lorencia676\",\"request_token\":\""+request_token+"\"}");
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key="+API_KEY)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        System.out.println("validating token"+response.body().string());
        getSessionId(request_token);
    }

    private String getRequestToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication/token/new?api_key="+API_KEY)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body().string(), GetTokenRequest.class).getRequest_token();
    }

    private void getSessionId(String request_token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication/session/new?api_key="+API_KEY+"&request_token="+request_token)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        this.sessionId = objectMapper.readValue(response.body().string(), GetTokenRequest.class).getSession_id();
    }
}

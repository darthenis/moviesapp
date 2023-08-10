package com.example.movies.services;

import com.example.movies.DTO.GetTokenRequest;
import com.example.movies.security.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class AuthService {

    private String API_KEY = "9b8b5a71ed3f7d0620bf8ec5f58c0fca";

    private String session_id;

    public void login(String username, String password, boolean keepSession) throws IOException {
        String request_token = getRequestToken();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"request_token\":\""+request_token+"\"}");
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key="+API_KEY)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        response.close();
        if(response.code() == 200){
            SessionManager.saveSession_id(getSessionId(request_token), keepSession);
        } else {
            throw new IOException("invalid");
        }
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
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), GetTokenRequest.class).getRequest_token();
    }

    private String getSessionId(String request_token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/authentication/session/new?api_key="+API_KEY+"&request_token="+request_token)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), GetTokenRequest.class).getSession_id();
    }

    public String getSession_id(){
        return this.session_id;
    }
}

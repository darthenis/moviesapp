package com.example.movies.services;

import com.example.movies.models.User;
import com.example.movies.security.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserService {
    private String API_KEY = "9b8b5a71ed3f7d0620bf8ec5f58c0fca";
    OkHttpClient client = new OkHttpClient();
    private String session_id;

    private User user;

    public UserService() {
        if(SessionManager.loadToken() != null){
            this.session_id = SessionManager.loadToken();
        } else {
            this.session_id = SessionManager.getSession_id();
        }
    }

    private User getAccount() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/account?api_key="+API_KEY+"&session_id="+session_id)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        assert response.body() != null;
        return objectMapper.readValue(response.body().string(), User.class);
    }

    public User getUser(){
        if(this.user == null){
            try{
                this.user = getAccount();
            }catch(IOException ioException){
                ioException.printStackTrace();
                System.out.println(ioException.getMessage());
            }
        }
        return this.user;
    }
}

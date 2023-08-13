package com.example.movies.view;

import com.example.movies.App;
import com.example.movies.controllers.DetailsMovieController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class MovieDetails {

    private static FXMLLoader fxmlLoader;

    private static ScrollPane scrollPane;

    public static ScrollPane getFxmlLoader() throws IOException {
        if(fxmlLoader == null){
            fxmlLoader = new FXMLLoader(App.class.getResource("details-movie.fxml"));
            scrollPane = fxmlLoader.load();
            return scrollPane;
        }else{
            return scrollPane;
        }
    }

    public static void close(){
        DetailsMovieController detailsMovieController = fxmlLoader.getController();
        detailsMovieController.close();
    }

    public static DetailsMovieController getController(){
        return fxmlLoader.getController();
    }
}

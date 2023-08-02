package com.example.movies.controllers;

import com.example.movies.models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MovieCardController{
    @FXML
    private ImageView movieImage;

    @FXML
    private Label titleLabel;

    String baseUrl = "https://image.tmdb.org/t/p/w500";

    public void setData(Movie movie){
        Image image = new Image(baseUrl+movie.getPoster_path(), true);
        titleLabel.setText(movie.getTitle());
        movieImage.setImage(image);
    }
}

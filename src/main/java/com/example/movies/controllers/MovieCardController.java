package com.example.movies.controllers;

import com.example.movies.models.Movie;
import com.example.movies.models.SearchMovie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieCardController {

    @FXML
    private VBox containerCard;

    @FXML
    private ImageView movieImage;

    @FXML
    private Label titleLabel;

    @FXML
    private TextFlow textFlow;

    @FXML
    private Label titleTextFlow;

    @FXML
    private Label averageLabel;

    @FXML
    private Label yearLabel;


    String baseUrl = "https://image.tmdb.org/t/p/w500";

    public void setData(Movie movie){
        Image image = new Image(baseUrl+movie.getPoster_path(), true);
        Text text = new Text(cutText(movie.getOverview()));
        text.getStyleClass().add("textCard");
        textFlow.getChildren().add(text);
        titleLabel.setText(movie.getTitle());
        titleTextFlow.setText(movie.getTitle());
        yearLabel.setText(String.valueOf(movie.getRelease_date().getYear()));
        averageLabel.setText(String.valueOf(movie.getVote_average()));
        movieImage.setImage(image);
        containerCard.setOnMouseEntered(event -> {
            textFlow.setOpacity(1); // Mostrar el texto afectado al hacer hover
        });

        containerCard.setOnMouseExited(event -> {
            textFlow.setOpacity(0); // Ocultar el texto afectado al salir del hover
        });
    }

    public void setDataSearch(SearchMovie searchMovie){
        Image image = new Image(baseUrl+searchMovie.getPoster_path(), true);
        Text text = new Text(cutText(searchMovie.getOverview()));
        text.getStyleClass().add("textCard");
        textFlow.getChildren().add(text);
        titleLabel.setText(searchMovie.getName());
        titleTextFlow.setText(searchMovie.getName());


        containerCard.setOnMouseEntered(event -> {
            System.out.println("entro");
            textFlow.setOpacity(1); // Mostrar el texto afectado al hacer hover
        });

        containerCard.setOnMouseExited(event -> {
            textFlow.setOpacity(0); // Ocultar el texto afectado al salir del hover
        });

        movieImage.setImage(image);
    }

    private String cutText(String text) {
        if(text.length() > 19){
            return text.substring(0, 75) + "...";
        } else {
            return text;
        }

    }

    public VBox getContainerCard(){
        return containerCard;
    }
}

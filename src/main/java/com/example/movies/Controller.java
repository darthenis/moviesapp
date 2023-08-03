package com.example.movies;

import com.example.movies.controllers.MovieCardController;
import com.example.movies.models.ListEnum;
import com.example.movies.models.Movie;
import com.example.movies.services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private GridPane gridMovies;
    @FXML
    private ScrollPane mainScroll;
    private final MovieService movieService;
    List<Movie> listMovies;
    @FXML
    Label upcomingLabel;
    @FXML
    private Label nowPlayingLabel;
    @FXML
    private Label popularLabel;
    @FXML
    private Label topLabel;

    public Controller(){
        movieService = new MovieService();
    }

    @FXML
    void getList(ListEnum listEnum) throws IOException {
            listMovies = movieService.getMovies(listEnum);
            gridMovies.getChildren().clear();
            int column = 1;
            int row = 0;
            try{
                for(Movie m : listMovies){
                    URL fxmlLocation = getClass().getResource("movieCard.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                    VBox movieBox = fxmlLoader.load();
                    MovieCardController movieCardController = fxmlLoader.getController();
                    movieCardController.setData(m);

                    gridMovies.add(movieBox, column++, row);
                    GridPane.setMargin(movieBox, new Insets(5));

                    if(column == 5){
                        column = 1;
                        ++row;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    void getLists(MouseEvent mouseEvent){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        try {
            this.getList(ListEnum.NOW_PLAYING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        nowPlayingLabel.setBorder(getBottomBorder());

        this.popularLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.POPULAR);
                this.clearBorder();
                this.popularLabel.setBorder(getBottomBorder());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.topLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.TOP_RATED);
                this.clearBorder();
                this.topLabel.setBorder(getBottomBorder());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.nowPlayingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.NOW_PLAYING);
                this.clearBorder();
                this.nowPlayingLabel.setBorder(getBottomBorder());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.upcomingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.UPCOMING);
                this.clearBorder();
                this.upcomingLabel.setBorder(getBottomBorder());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void clearBorder(){
        BorderStroke borderStroke = new BorderStroke(null, null, null, null);
        Border border = new Border(borderStroke);
        nowPlayingLabel.setBorder(border);
        popularLabel.setBorder(border);
        topLabel.setBorder(border);
        upcomingLabel.setBorder(border);
    }

    Border getBottomBorder(){
        BorderStroke borderStroke = new BorderStroke(
                Color.WHITE,               // Color del borde
                BorderStrokeStyle.SOLID, // Estilo del borde (puede ser punteado, discontinuo, etc.)
                null,                   // Radios de esquina (null para usar los valores por defecto)
                new BorderWidths(0, 0, 1, 0) // Grosor del borde (arriba, derecha, abajo, izquierda)
        );

        // Crea un borde con el borde inferior personalizado
        return new Border(borderStroke);
    }

}
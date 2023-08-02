package com.example.movies;

import com.example.movies.controllers.MovieCardController;
import com.example.movies.models.Movie;
import com.example.movies.services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    public Controller(){
        movieService = new MovieService();
    }

    @FXML
    void getList() throws IOException {
            listMovies = movieService.getMovies();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        try {
            this.getList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //upcomingLabel.setUnderline(true);
        //upcomingLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        upcomingLabel.setBorder(getBottomBorder());

    }

    Border getBottomBorder(){
        BorderStroke borderStroke = new BorderStroke(
                Color.GRAY,               // Color del borde
                BorderStrokeStyle.SOLID, // Estilo del borde (puede ser punteado, discontinuo, etc.)
                null,                   // Radios de esquina (null para usar los valores por defecto)
                new BorderWidths(1, 0, 1, 0) // Grosor del borde (arriba, derecha, abajo, izquierda)
        );

        // Crea un borde con el borde inferior personalizado
        return new Border(borderStroke);
    }

}
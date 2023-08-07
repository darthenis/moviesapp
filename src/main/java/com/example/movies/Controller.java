package com.example.movies;

import com.example.movies.controllers.DetailsMovieController;
import com.example.movies.controllers.MovieCardController;
import com.example.movies.models.Genre;
import com.example.movies.models.ListEnum;
import com.example.movies.models.Movie;
import com.example.movies.models.SearchMovie;
import com.example.movies.services.MovieService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Label labelSelected;

    private Label genreSelected;
    @FXML
    private HBox containerMain;
    @FXML
    private GridPane gridMovies;
    @FXML
    private ScrollPane mainScroll;
    private final MovieService movieService;

    @FXML
    private Label upcomingLabel;
    @FXML
    private Label nowPlayingLabel;
    @FXML
    private Label popularLabel;
    @FXML
    private Label topLabel;

    @FXML
    private TextField searchField;

    @FXML
    private VBox menu;

    @FXML
    private ScrollPane menuScroll;

    public Controller(){
        movieService = new MovieService();
    }

    @FXML
    void getList(ListEnum listEnum) throws IOException {
            List<Movie> listMovies = movieService.getMovies(listEnum);
            this.generateCardsMovie(listMovies, null);
    }

    private void reGenerateGrid(){
        containerMain.getChildren().clear();
        containerMain.getChildren().add(mainScroll);
    }

    private void addEventClickedMovie(VBox container, Movie movie){
        container.setOnMouseClicked(mouseEvent -> {
            containerMain.getChildren().clear();
            URL fxmlLocation = getClass().getResource("details-movie.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            try {
                BorderPane detailPane = fxmlLoader.load();
                DetailsMovieController detailsCardController = fxmlLoader.getController();
                detailsCardController.setData(movie);
                containerMain.getChildren().add(detailPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void generateCardsMovie(List<Movie> movies, List<SearchMovie> searchMovies){
        reGenerateGrid();
        gridMovies.getChildren().clear();
        int column = 1;
        int row = 0;

        try{
            if(searchMovies == null){
                for(Movie m : movies){
                    URL fxmlLocation = getClass().getResource("movieCard.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                    VBox movieBox = fxmlLoader.load();
                    MovieCardController movieCardController = fxmlLoader.getController();
                    movieCardController.setData(m);
                    addEventClickedMovie(movieCardController.getContainerCard(), m);

                    gridMovies.add(movieBox, column++, row);
                    GridPane.setMargin(movieBox, new Insets(5));

                    if(column == 6){
                        column = 1;
                        ++row;
                    }
                }
            } else {
                for(SearchMovie m : searchMovies){
                    URL fxmlLocation = getClass().getResource("movieCard.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                    VBox movieBox = fxmlLoader.load();
                    MovieCardController movieCardController = fxmlLoader.getController();
                    movieCardController.setDataSearch(m);

                    gridMovies.add(movieBox, column++, row);
                    GridPane.setMargin(movieBox, new Insets(5));

                    if(column == 6){
                        column = 1;
                        ++row;
                    }
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
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        try {
            this.generateGenres();
            this.getList(ListEnum.NOW_PLAYING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.nowPlayingLabel.getStyleClass().add("buttonSelected");
        this.labelSelected = nowPlayingLabel;

        this.popularLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.POPULAR);
                this.clearBackground();
                this.popularLabel.getStyleClass().remove("buttonUnselected");
                this.popularLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = popularLabel;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.topLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.TOP_RATED);
                this.clearBackground();
                this.topLabel.getStyleClass().remove("buttonUnselected");
                this.topLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = topLabel;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.nowPlayingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.NOW_PLAYING);
                this.clearBackground();
                this.nowPlayingLabel.getStyleClass().remove("buttonUnselected");
                this.nowPlayingLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = nowPlayingLabel;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.upcomingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                this.getList(ListEnum.UPCOMING);
                this.clearBackground();
                this.upcomingLabel.getStyleClass().remove("buttonUnselected");
                this.upcomingLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = upcomingLabel;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        List<SearchMovie> searchMovies= movieService.searchByQuery(searchField.getText());
                        generateCardsMovie(null, searchMovies);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    void clearBackground(){
        labelSelected.getStyleClass().remove("buttonSelected");
        labelSelected.getStyleClass().add("buttonUnselected");
    }


    private void generateGenres() throws IOException {
        reGenerateGrid();
        List<Genre> genres = movieService.getGenres();
        for(Genre genre : genres){
            Label label = new Label();
            label.setText(genre.getName());
            label.getStyleClass().add("genreLabel");
            label.setId(genre.getName()+"Label");
            label.setOnMouseClicked(mouseEvent -> {
                try {
                    this.genreLabelEvent(genre.getId());
                    setUnderlineLabel(genreSelected, label);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            menu.getChildren().add(label);
        }
    }

    void setUnderlineLabel(Label oldlabel, Label newLabel){
        if(oldlabel != null){
            oldlabel.getStyleClass().remove("selectedGenre");
        }
        newLabel.getStyleClass().add("selectedGenre");
        genreSelected = newLabel;
    }

    void genreLabelEvent(int id) throws IOException {
        List<Movie> movies = this.movieService.filterByGenre(id);
        this.generateCardsMovie(movies, null);
    }

}
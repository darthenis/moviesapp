package com.example.movies.controllers;

import com.example.movies.App;
import com.example.movies.models.*;
import com.example.movies.security.SessionManager;
import com.example.movies.services.MovieService;
import com.example.movies.services.UserService;
import com.example.movies.view.MovieDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class Controller implements Initializable {

    @FXML
    private MenuItem logout;
    //agregar logout funcionalidad
    @FXML
    private Label labelSelected;
    @FXML
    private MenuButton userName;
    @FXML
    private Label genreSelected;
    @FXML
    private HBox containerMain;
    @FXML
    private GridPane gridMovies;
    @FXML
    private ScrollPane mainScroll;
    private final MovieService movieService;

    private final UserService userService;

    private ListEnum listEnum;

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

    @FXML
    private Label pagesLabel;

    private Stage stage;

    public Controller(){
        userService = new UserService();
        movieService = new MovieService();
    }

    @FXML
    void setList(ListEnum listEnum) throws IOException {
            List<Movie> listMovies = movieService.getMovies(listEnum);
            this.generateCardsMovie(listMovies, null);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


    private void addEventClickedMovie(VBox container, Movie movie){
        container.setOnMouseClicked(mouseEvent -> {
            gridMovies.getChildren().clear();
            try {

                ScrollPane detailPane = MovieDetails.getFxmlLoader();
                gridMovies.getChildren().clear();
                gridMovies.getChildren().add(detailPane);
                DetailsMovieController detailsCardController = MovieDetails.getController();
                detailsCardController.setData(movie);

            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void generateCardsMovie(List<Movie> movies, List<SearchMovie> searchMovies){
        gridMovies.getChildren().clear();
        int column = 1;
        int row = 0;

        try{
            if(searchMovies == null){
                for(Movie m : movies){
                    URL fxmlLocation = App.class.getResource("movieCard.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                    VBox movieBox = fxmlLoader.load();
                    MovieCardController movieCardController = fxmlLoader.getController();
                    movieCardController.setData(m);
                    addEventClickedMovie(movieCardController.getContainerCard(), m);

                    gridMovies.add(movieBox, column++, row);

                    if(column != 2){
                        GridPane.setMargin(movieBox, new Insets(5));
                    }


                    if(column == 6){
                        column = 1;
                        ++row;
                    }
                }
            } else {
                for(SearchMovie m : searchMovies){
                    URL fxmlLocation = App.class.getResource("movieCard.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                    VBox movieBox = fxmlLoader.load();
                    MovieCardController movieCardController = fxmlLoader.getController();
                    movieCardController.setDataSearch(m);

                    gridMovies.add(movieBox, column++, row);
                    if(column != 2){
                        GridPane.setMargin(movieBox, new Insets(5));
                    }


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

        String username = userService.getUser().getUsername();

        this.userName.setText(username);

        this.setEventMenu();

        this.setSearchEvent();

        this.setEventLogout();

        try {
            this.generateGenres();
            this.setList(ListEnum.NOW_PLAYING);
            this.setPagesLabel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

    private void setEventLogout(){
        this.logout.setOnAction(actionEvent -> {
            try {
                SessionManager.clearSession();
                reloadLoginScreen();
            } catch (BackingStoreException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void reloadLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1010, 650);
        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
    }

    private void setSearchEvent(){
        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        List<SearchMovie> searchMovies= movieService.searchByQuery(searchField.getText());
                        generateCardsMovie(null, searchMovies);
                        clearBackground();
                        labelSelected = null;
                        setPagesLabel();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void setEventMenu(){
        this.nowPlayingLabel.getStyleClass().add("buttonSelected");
        this.labelSelected = nowPlayingLabel;
        this.listEnum = ListEnum.NOW_PLAYING;


        this.popularLabel.setOnMouseClicked(mouseEvent -> {
            try {
                MovieDetails.close();
                this.movieService.setPage(1);
                this.listEnum = ListEnum.POPULAR;
                this.setList(ListEnum.POPULAR);
                this.clearBackground();
                this.mainScroll.setVvalue(0);
                this.popularLabel.getStyleClass().remove("buttonUnselected");
                this.popularLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = popularLabel;
                this.setPagesLabel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.topLabel.setOnMouseClicked(mouseEvent -> {
            try {
                MovieDetails.close();
                this.movieService.setPage(1);
                this.listEnum = ListEnum.TOP_RATED;
                this.setList(ListEnum.TOP_RATED);
                this.clearBackground();
                this.mainScroll.setVvalue(0);
                this.topLabel.getStyleClass().remove("buttonUnselected");
                this.topLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = topLabel;
                this.setPagesLabel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.nowPlayingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                MovieDetails.close();
                this.movieService.setPage(1);
                this.listEnum = ListEnum.NOW_PLAYING;
                this.setList(ListEnum.NOW_PLAYING);
                this.clearBackground();
                this.mainScroll.setVvalue(0);
                this.nowPlayingLabel.getStyleClass().remove("buttonUnselected");
                this.nowPlayingLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = nowPlayingLabel;
                this.setPagesLabel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.upcomingLabel.setOnMouseClicked(mouseEvent -> {
            try {
                MovieDetails.close();
                this.movieService.setPage(1);
                this.listEnum = ListEnum.UPCOMING;
                this.setList(ListEnum.UPCOMING);
                this.clearBackground();
                this.mainScroll.setVvalue(0);
                this.upcomingLabel.getStyleClass().remove("buttonUnselected");
                this.upcomingLabel.getStyleClass().add("buttonSelected");
                this.labelSelected = upcomingLabel;
                this.setPagesLabel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void clearBackground(){
        if(labelSelected != null) {
            labelSelected.getStyleClass().remove("buttonSelected");
            labelSelected.getStyleClass().add("buttonUnselected");
        }
    }


    private void generateGenres() throws IOException {
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
        this.clearBackground();
        this.setPagesLabel();
        this.generateCardsMovie(movies, null);
    }

    @FXML
    void passPage(ActionEvent event) throws IOException {
        String button = ((Button)event.getSource()).getText();
        int page = this.movieService.getPage();
        if(button.equals("Ant") && page > 1){
            this.mainScroll.setVvalue(0);
            this.movieService.setPage(this.movieService.getPage() - 1);
            this.setList(this.listEnum);
            this.setPagesLabel();
        }else if(page < this.movieService.getTotalPages()){
            this.mainScroll.setVvalue(0);
            this.movieService.setPage(this.movieService.getPage() + 1);
            this.setList(this.listEnum);
            this.setPagesLabel();
        }
    }

    private void setPagesLabel(){
        this.pagesLabel.setText("Pag "+this.movieService.getPage()+" de " + this.movieService.getTotalPages());
    }
}
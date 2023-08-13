package com.example.movies.controllers;

import com.example.movies.DTO.ListVideosDTO;
import com.example.movies.DTO.VideosDTO;
import com.example.movies.models.*;
import com.example.movies.services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DetailsMovieController implements Initializable {

    @FXML
    private ScrollPane scrollDetail;

    @FXML
    private WebView trailerView;

    @FXML
    private Label avaregeLabel;

    @FXML
    private Label castingLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView imageDetails;

    @FXML
    private TextFlow sinopsisContainer;

    @FXML
    private Label tagLineLabel;

    @FXML
    private Label titleLabel;

    private MovieService movieService;

    private String linkYotube;

    String baseUrl = "https://image.tmdb.org/t/p/w500";

    public DetailsMovieController(){
        this.movieService = new MovieService();
    }

    public void setData(Movie movie) throws IOException, URISyntaxException {
        MovieDetail movieDetail = movieService.getDetailMovie(movie.getId());
        ListVideosDTO listVideosDTO = movieService.getVideos(movieDetail.getId());
        for (VideosDTO video : listVideosDTO.getResults()) {
            if (video.getSite().equals("YouTube") && video.isOfficial() && video.getType().equals("Trailer")) {
                trailerView.getEngine().load("https://www.youtube.com/embed/"+video.getKey());
                break; // Encontramos el primer objeto que coincide, salimos del bucle
            }
        }
        avaregeLabel.setText(String.valueOf(movieDetail.getVote_average())+ " ("+String.valueOf(movieDetail.getVote_count())+")");
        if(movieDetail.getTagline().isEmpty()){
            tagLineLabel.setPrefHeight(30);
            tagLineLabel.setFont(new Font(32));
        }else{
            tagLineLabel.setText(movieDetail.getTagline());
        }

        Image image = new Image(baseUrl+movieDetail.getPoster_path(), true);
        imageDetails.setImage(image);
        List<Genre> genres = movieDetail.getGenres();
        genreLabel.setText(genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", ")));
        Text textSinopsis = new Text(movieDetail.getOverview());
        textSinopsis.setFont(new Font(14));
        textSinopsis.setStyle("-fx-fill: white;");
        sinopsisContainer.getChildren().clear();
        sinopsisContainer.getChildren().add(textSinopsis);
        dateLabel.setText(movieDetail.getRelease_date());
        titleLabel.setText(movieDetail.getTitle());

        CreditsMovie creditsMovie = movieService.getCredits(movieDetail.getId());
        List<Crew> directors = creditsMovie.getCrew().stream().filter(crew -> crew.getJob().equals("Director")).toList();
        List<Cast> casts = creditsMovie.getCast().subList(0, Math.min(creditsMovie.getCast().size(), 5));
        directorLabel.setText(directors.stream()
                .map(Crew::getName)
                .collect(Collectors.joining(", ")));
        castingLabel.setText(casts.stream()
                .map(Cast::getName)
                .collect(Collectors.joining(", ")));

    }

    /*private void setRecommendMovies(int id) throws IOException {
        List<Movie> movies = movieService.getRecommend(id);
        int count = 1;
        int column = 1;
        int row = 0;
        System.out.println(movies.size());
        for(Movie movie : movies){
            if(count <= 5){
                count++;
                URL fxmlLocation = App.class.getResource("movieCard.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                VBox movieBox = fxmlLoader.load();
                MovieCardController movieCardController = fxmlLoader.getController();
                movieCardController.setData(movie);
                addEventClickedMovie(movieCardController.getContainerCard(), movie);

                gridRecommend.add(movieBox, column++, row);
                GridPane.setMargin(movieBox, new Insets(5));
            }else {
                break;
            }

        }
    }*/

    /*private void addEventClickedMovie(VBox container, Movie movie){
        container.setOnMouseClicked(mouseEvent -> {
            gridRecommend.getChildren().clear();
            gridRecommend.getChildren().add(new Label("Loading..."));
            URL fxmlLocation = App.class.getResource("details-movie.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            try {

                ScrollPane detailPane = fxmlLoader.load();
                gridRecommend.getChildren().clear();
                gridRecommend.getChildren().add(detailPane);
                DetailsMovieController detailsCardController = fxmlLoader.getController();
                detailsCardController.setData(movie);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }*/


    @FXML
    void openLink(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(linkYotube));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollDetail.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollDetail.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void close(){
        trailerView.getEngine().load(null); // Detener cualquier carga de contenido
    }
}

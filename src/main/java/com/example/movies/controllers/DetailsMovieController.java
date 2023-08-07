package com.example.movies.controllers;

import com.example.movies.models.*;
import com.example.movies.services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DetailsMovieController {


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

    String baseUrl = "https://image.tmdb.org/t/p/w500";

    public DetailsMovieController(){
        this.movieService = new MovieService();
    }

    public void setData(Movie movie) throws IOException {
        MovieDetail movieDetail = movieService.getDetailMovie(movie.getId());
        avaregeLabel.setText(String.valueOf(movieDetail.getVote_average())+ " ("+String.valueOf(movieDetail.getVote_count())+")");
        tagLineLabel.setText(movieDetail.getTagline());
        Image image = new Image(baseUrl+movieDetail.getPoster_path(), true);
        imageDetails.setImage(image);
        List<Genre> genres = movieDetail.getGenres();
        genreLabel.setText(genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", ")));
        Text textSinopsis = new Text(movieDetail.getOverview());
        textSinopsis.setStyle("-fx-fill: white;");
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
}

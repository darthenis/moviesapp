module com.example.movies {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.desktop;
    requires java.prefs;
    requires javafx.web;


    opens com.example.movies to javafx.fxml;
    exports com.example.movies;
    exports com.example.movies.controllers;
    exports com.example.movies.models;
    opens com.example.movies.controllers to javafx.fxml;
    exports com.example.movies.DTO;
}
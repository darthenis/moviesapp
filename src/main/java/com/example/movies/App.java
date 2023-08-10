package com.example.movies;

import com.example.movies.controllers.Controller;
import com.example.movies.controllers.LoginController;
import com.example.movies.security.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene;
        if(SessionManager.getPersistent() == null){
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            scene = new Scene(fxmlLoader.load(), 1010, 650);
            LoginController controller = fxmlLoader.getController();
            controller.setStage(stage);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            scene = new Scene(fxmlLoader.load(), 1010, 650);
            Controller controller = fxmlLoader.getController();
            controller.setStage(stage);
        }

        stage.setTitle("TodoPelis");
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
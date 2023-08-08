package com.example.movies.controllers;

import com.example.movies.App;
import com.example.movies.services.UserService;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button buttonLogin;

    @FXML
    private ImageView imageLoading;

    private UserService userService;

    private String sessionId;

    private Stage stage;

    public LoginController(){
        this.userService = new UserService();
    }

    @FXML
    void login(ActionEvent event) {
        this.activeLoading();

        Task<Void> loginTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                userService.login(usernameInput.getText(), passwordInput.getText());
                return null;
            }
        };

        loginTask.setOnSucceeded(workerStateEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1010, 650);
                stage.setScene(scene);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        new Thread(loginTask).start();

    }

    private void activeLoading(){
        imageLoading.setOpacity(1);
        usernameInput.setOpacity(0);
        passwordInput.setOpacity(0);
        buttonLogin.setOpacity(0);
    }

    public String getSessionId(){
        return sessionId;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    private void animationLoading(){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), imageLoading);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animationLoading();
    }
}

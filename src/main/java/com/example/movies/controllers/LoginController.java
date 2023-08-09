package com.example.movies.controllers;

import com.example.movies.App;
import com.example.movies.security.SessionManager;
import com.example.movies.services.AuthService;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private String session_id;
    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button buttonLogin;

    @FXML
    private ImageView imageLoading;

    @FXML
    private Label errorLabel;

    @FXML
    private Pane options;

    private AuthService authService;

    private Stage stage;

    public LoginController(){
        this.authService = new AuthService();
    }

    @FXML
    void login(ActionEvent event) {
        this.activeLoading();
        Task<Void> loginTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                authService.login(usernameInput.getText(), passwordInput.getText());
                return null;
            }
        };

        loginTask.setOnSucceeded(workerStateEvent -> {
            try {
                SessionManager.setSession_id(authService.getSession_id());
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1010, 650);
                stage.setScene(scene);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        loginTask.setOnFailed(workerStateEvent -> this.loginError());

        new Thread(loginTask).start();

    }

    private void loginError(){
        this.desactiveLoading();
        this.usernameInput.setText("");
        this.passwordInput.setText("");
        this.errorLabel.setOpacity(1);

    }

    private void activeLoading(){
        imageLoading.setOpacity(1);
        usernameInput.setOpacity(0);
        passwordInput.setOpacity(0);
        buttonLogin.setOpacity(0);
        options.setOpacity(0);
        this.errorLabel.setOpacity(0);
    }

    private void desactiveLoading(){
        imageLoading.setOpacity(0);
        usernameInput.setOpacity(1);
        passwordInput.setOpacity(1);
        options.setOpacity(1);
        buttonLogin.setOpacity(1);
    }

    public String getSessionId(){
        return this.session_id;
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

    @FXML
    void onRegister(MouseEvent event) throws IOException {
        if(event.getButton().toString().equals("PRIMARY")){
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.themoviedb.org/signup"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

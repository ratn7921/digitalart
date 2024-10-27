package com.example.digitalartgallery;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main extends Application {

    private User currentUser;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Digital Art Gallery");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Registration and Login UI
        showLoginOrRegister(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginOrRegister(BorderPane root) {
        VBox loginPane = new VBox(10);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e -> {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(usernameField.getText(), passwordField.getText());
            if (user != null) {
                currentUser = user;
                showGallery(root);
            } else {
                // Handle login failure
                Alert alert = new Alert(Alert.AlertType.ERROR, "Login failed!");
                alert.show();
            }
        });

        registerButton.setOnAction(e -> {
            UserDAO userDAO = new UserDAO();
            boolean registered = userDAO.registerUser(usernameField.getText(), passwordField.getText());
            if (registered) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful!");
                alert.show();
            } else {
                // Handle registration failure
                Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed!");
                alert.show();
            }
        });

        loginPane.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton, registerButton);
        root.setCenter(loginPane);
    }

    private void showGallery(BorderPane root) {
        GalleryController galleryController = new GalleryController(root, currentUser);
        root.setLeft(galleryController.getUserArtworksPane());
        root.setRight(galleryController.getAllArtworksPane());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

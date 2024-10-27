package com.example.digitalartgallery;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryController {
    private VBox userArtworksPane;
    private VBox allArtworksPane;
    private List<ImageModel> userImages;
    private List<ImageModel> allImages;
    private ImageView userImageView;
    private ImageView allImageView;
    private Label userStatusLabel;
    private Label allStatusLabel;
    private User currentUser;

    public GalleryController(BorderPane root, User user) {
        this.currentUser = user;
        userImages = new ArrayList<>();
        allImages = new ArrayList<>();
        userArtworksPane = new VBox(10);
        allArtworksPane = new VBox(10);
        userImageView = new ImageView();
        allImageView = new ImageView();
        userStatusLabel = new Label("User Artworks");
        allStatusLabel = new Label("All Artworks");

        setupUI();
        loadUserArtworks();
        loadAllArtworks();
    }

    public VBox getUserArtworksPane() {
        return userArtworksPane;
    }

    public VBox getAllArtworksPane() {
        return allArtworksPane;
    }

    private void setupUI() {
        Button uploadButton = new Button("Upload Images");
        uploadButton.setOnAction(e -> uploadImages());

        userArtworksPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
        userArtworksPane.getChildren().addAll(userStatusLabel, uploadButton, userImageView);

        allArtworksPane.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
        allArtworksPane.getChildren().addAll(allStatusLabel, allImageView);
    }

    private void uploadImages() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            ArtworkDAO artworkDAO = new ArtworkDAO();
            for (File file : selectedFiles) {
                String imagePath = file.toURI().toString();
                Artwork artwork = new Artwork(0, currentUser.getId(), imagePath, file.getName());
                artworkDAO.uploadArtwork(artwork);
                userImages.add(new ImageModel(imagePath));
                userStatusLabel.setText("Uploaded: " + file.getName());
            }
            displayUserImages();
        }
    }

    private void loadUserArtworks() {
        ArtworkDAO artworkDAO = new ArtworkDAO();
        List<Artwork> userArtworks = artworkDAO.getUserArtworks(currentUser.getId());
        for (Artwork artwork : userArtworks) {
            userImages.add(new ImageModel(artwork.getImagePath()));
        }
        displayUserImages();
    }

    private void loadAllArtworks() {
        ArtworkDAO artworkDAO = new ArtworkDAO();
        List<Artwork> allArtworks = artworkDAO.getAllArtworks();
        for (Artwork artwork : allArtworks) {
            allImages.add(new ImageModel(artwork.getImagePath()));
        }
        displayAllImages();
    }

    private void displayUserImages() {
        if (!userImages.isEmpty()) {
            ImageModel image = userImages.get(userImages.size() - 1);
            userImageView.setImage(new Image(image.getImagePath()));
            userImageView.setFitWidth(250);
            userImageView.setFitHeight(250);
            userImageView.setPreserveRatio(true);
        }
    }

    private void displayAllImages() {
        if (!allImages.isEmpty()) {
            ImageModel image = allImages.get(allImages.size() - 1);
            allImageView.setImage(new Image(image.getImagePath()));
            allImageView.setFitWidth(250);
            allImageView.setFitHeight(250);
            allImageView.setPreserveRatio(true);
        }
    }
}

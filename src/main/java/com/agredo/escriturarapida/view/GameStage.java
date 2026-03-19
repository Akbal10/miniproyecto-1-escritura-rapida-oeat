package com.agredo.escriturarapida.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GameStage extends Stage implements IGameStage {

    private Parent root;

    public GameStage() {
        loadStage();
    }

    @Override
    public void loadStage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/agredo/escriturarapida/game-view.fxml")
            );

            root = loader.load();
            Scene scene = new Scene(root, 750, 423);

            setTitle("Escritura Rápida");
            setScene(scene);
            setResizable(false);

            applyStyles();

        } catch (IOException e) {
            System.err.println("Error loading game view: " + e.getMessage());
        }
    }

    @Override
    public void applyStyles() {
        if (!(root instanceof Region region)) {
            System.err.println("Root is not a Region.");
            return;
        }

        URL imageUrl = getClass().getResource("/com/agredo/escriturarapida/bg-abbys1.jpg");

        if (imageUrl == null) {
            System.err.println("Background image not found.");
            return;
        }

        Image image = new Image(imageUrl.toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        100, 100,
                        true, true,
                        true, false
                )
        );

        BackgroundFill overlay = new BackgroundFill(
                Color.rgb(0, 0, 0, 0.55),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );

        region.setBackground(new Background(
                new BackgroundFill[]{overlay},
                new BackgroundImage[]{backgroundImage}
        ));
    }
}

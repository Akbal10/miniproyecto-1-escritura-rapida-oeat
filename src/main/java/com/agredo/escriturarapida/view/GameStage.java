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

/**
 * Represents the main stage of the game.
 *
 * <p>This class is responsible for loading the main FXML view,
 * creating the scene, configuring the application window,
 * and applying the visual background style.</p>
 *
 * <p>The stage uses a dark fantasy themed background with a semi-transparent
 * dark overlay to improve readability of the interface elements.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 */
public class GameStage extends Stage implements IGameStage {

    /** Root node loaded from the FXML file. */
    private Parent root;

    /**
     * Creates the main game stage and loads its content.
     */
    public GameStage() {
        loadStage();
    }

    /**
     * Loads the FXML layout, creates the scene,
     * and configures the main window properties.
     *
     * <p>This method sets the title, scene size,
     * and prevents the window from being resizable.</p>
     */
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

    /**
     * Applies the visual background style to the root container.
     *
     * <p>The background consists of:</p>
     * <ul>
     *     <li>A centered fantasy-themed background image</li>
     *     <li>A dark semi-transparent overlay to improve contrast</li>
     * </ul>
     *
     * <p>If the root node is not a {@link Region}, or if the image
     * cannot be found, the method exits safely and prints an error message.</p>
     */
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
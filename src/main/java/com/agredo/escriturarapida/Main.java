package com.agredo.escriturarapida;

import com.agredo.escriturarapida.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point of the "Escritura Rápida" application.
 *
 * <p>This class launches the JavaFX application and initializes
 * the main game window using {@link GameStage}.</p>
 *
 * <p>It acts as the bridge between the JavaFX runtime and
 * the custom game stage implementation.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     *
     * <p>This method is automatically called by the JavaFX runtime.
     * It creates and displays the main game stage.</p>
     *
     * @param stage the primary stage provided by JavaFX (not used directly)
     */
    @Override
    public void start(Stage stage) {
        GameStage gameStage = new GameStage();
        gameStage.show();
    }

    /**
     * Main method of the application.
     *
     * <p>Launches the JavaFX runtime environment.</p>
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
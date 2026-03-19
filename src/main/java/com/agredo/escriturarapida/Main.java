package com.agredo.escriturarapida;

import com.agredo.escriturarapida.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the application.
 *
 * @author Omar Agredo
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        GameStage gameStage = new GameStage();
        gameStage.show();
    }

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
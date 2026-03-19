package com.agredo.escriturarapida.view;

/**
 * Interface that defines the main behavior of the game stage.
 *
 * @author Omar Agredo
 * @version 1.0
 */
public interface IGameStage {

    /**
     * Loads and configures the main stage.
     */
    void loadStage();

    /**
     * Applies the visual style of the game.
     */
    void applyStyles();
}
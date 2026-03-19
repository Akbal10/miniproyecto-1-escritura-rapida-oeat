package com.agredo.escriturarapida.view;

/**
 * Defines the basic behavior of the game stage.
 *
 * <p>This interface establishes the essential responsibilities
 * of any stage implementation in the application:</p>
 *
 * <ul>
 *     <li>Loading and initializing the UI</li>
 *     <li>Applying visual styles and theming</li>
 * </ul>
 *
 * <p>It allows different stage implementations to follow
 * a consistent structure.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 */
public interface IGameStage {

    /**
     * Loads the stage content and initializes the scene.
     *
     * <p>This typically includes loading the FXML file,
     * setting up the scene, and configuring the window.</p>
     */
    void loadStage();

    /**
     * Applies visual styles to the stage.
     *
     * <p>This includes background, colors, and general UI theming.</p>
     */
    void applyStyles();
}
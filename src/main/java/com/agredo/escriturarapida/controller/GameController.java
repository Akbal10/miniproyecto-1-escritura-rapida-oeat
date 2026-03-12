package com.agredo.escriturarapida.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import com.agredo.escriturarapida.model.IWords;
import com.agredo.escriturarapida.model.Words;

/**
 * Controller class for the "Escritura Rápida" game.
 * This class handles the game logic, manages the countdown timer,
 * validates user input, and updates the UI components based on level progression.
 *
 * @author Tu Nombre (com.agredo)
 * @version 1.0
 * @see com.agredo.escriturarapida.model.Words
 * @see com.agredo.escriturarapida.model.IWords
 */

public class GameController {

    @FXML private Label levelLabel;
    @FXML private Label timeLabel;
    @FXML private Label wordLabel;
    @FXML private TextField inputField;
    @FXML private Label messageLabel;
    @FXML private Button startButton;

    /** * Instance of the word model to fetch random challenges.
     */
    private final IWords words = new Words();
    /** * The current word the player must type.
     */
    private String currentWord = "";
    /** * Current level progress. Starts at level 1.
     */
    private int currentLevel = 1;
    /** * Remaining seconds for the current level.
     */
    private int timeLeft = 20;
    /** * The starting time for each level, which decreases as difficulty increases.
     */
    private int baseTime = 20;
    /** * Animation timeline used to manage the 1-second interval countdown.
     */
    private Timeline timer;

    /**
     * Initializes the game when the Start button is pressed.
     * Sets the first word and focuses the text field for immediate typing.
     */

    @FXML
    private void handleStart() {
        currentWord = words.getRandomWord();
        wordLabel.setText(currentWord);

        messageLabel.setText("Type it and press Enter");
        inputField.clear();
        inputField.requestFocus();

        setupTimer();
    }
    /**
     * Prepares the UI and logic for the next level.
     * Fetches a new word and restarts the countdown timer.
     * * @see #setupTimer()
     */

    private void nextLevel() {
        currentWord = words.getRandomWord();
        wordLabel.setText(currentWord);
        inputField.clear();
        inputField.requestFocus();
        messageLabel.setText("Type it and press Enter");

        setupTimer();
    }

    /**
     * Configures and starts the countdown timer for the current level.
     * The timer decreases every second and triggers automatic validation if it reaches zero.
     * * @see #handleSubmit()
     */

    private void setupTimer() {
        if (timer != null) timer.stop();

        timeLeft = baseTime;
        timeLabel.setText("Time: " + timeLeft + "s");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timeLabel.setText("Time: " + timeLeft + "s");

            if (timeLeft <= 0) {
                timer.stop();
                handleSubmit();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /**
     * Validates the player's typed input against the target word.
     * If correct, the player wins the level. If incorrect or time is up, the game resets.
     * * @see #processWin()
     * @see #resetGame()
     */

    @FXML
    private void handleSubmit() {
        String typed = inputField.getText();


        if (timer != null) timer.stop();

        if (typed != null && typed.equals(currentWord)) {
            messageLabel.setText("✅ ¡Correcto!");
            processWin();
        } else {
            messageLabel.setText("❌ " + (timeLeft <= 0 ? "Tiempo agotado" : "Incorrecto"));
            resetGame();
        }
        inputField.requestFocus();
    }

    /**
     * Updates the level count and adjusts the game difficulty.
     * Difficulty increases by reducing base time by 2 seconds every 5 levels,
     * with a minimum limit of 2 seconds.
     * * @see #nextLevel()
     */

    private void processWin() {
        currentLevel++;
        levelLabel.setText("Level: " + currentLevel);


        if (currentLevel % 5 == 1 && currentLevel > 1) {
            if (baseTime > 2) {
                baseTime -= 2;
            }
        }
        nextLevel();
    }

    /**
     * Resets the game state after a failure.
     * Disables the game loop and enables the start button for a new session.
     */

    private void resetGame() {
        startButton.setDisable(false);
        inputField.clear();
    }
}

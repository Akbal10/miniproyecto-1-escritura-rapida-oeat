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
 * @author Omar Agredo
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
        startButton.setDisable(true);
        inputField.setDisable(false);

        currentLevel = 1;
        baseTime = 20;

        levelLabel.setText("Level: 1");

        currentWord = words.getWordByLevel(currentLevel);
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
        currentWord = words.getWordByLevel(currentLevel);
        wordLabel.setText(currentWord);

        inputField.clear();
        inputField.requestFocus();

        messageLabel.setText("A new word emerges from the abyss...");
        setInfoMessageStyle();

        setDefaultWordStyle();
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
        updateTimeStyle();

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
            messageLabel.setText("You survive this trial.");
            setSuccessMessageStyle();
            wordLabel.setStyle("""
    -fx-text-fill: #22c55e;
    -fx-font-size: 32px;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, rgba(34,197,94,0.45), 12, 0.4, 0, 0);
""");
            processWin();
        } else {
            messageLabel.setText(timeLeft <= 0
                    ? "The Abyss Has Claimed You."
                    : "You Failed the Trial.");
            setErrorMessageStyle();
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

        if (currentLevel % 5 == 0 && baseTime > 2) {
            baseTime -= 2;
        }

        nextLevel();
    }


    /**
     * Resets the full game state after a failure.
     */
    private void resetGame() {

        if (timer != null) timer.stop();

        currentLevel = 1;
        baseTime = 20;
        timeLeft = baseTime;
        currentWord = "";

        levelLabel.setText("Level: 1");
        timeLabel.setText("Time: 20s");
        wordLabel.setText("Begin the Trial");

        messageLabel.setText("The Abyss waits... Press Start.");
        setErrorMessageStyle();

        inputField.clear();
        inputField.setDisable(true);

        startButton.setDisable(false);
        updateTimeStyle();
        setDefaultWordStyle();
    }

    @FXML
    public void initialize() {
        inputField.setDisable(true);

        levelLabel.setText("Trial: 1");
        timeLabel.setText("Time: 20s");
        wordLabel.setText("Begin the Trial");
        messageLabel.setText("Enter the abyss when ready...");

        applyInitialStyles();

        startButton.setOnMouseEntered(e -> startButton.setStyle("""
    -fx-background-color: rgba(212,175,55,0.15);
    -fx-border-color: #d4af37;
    -fx-border-radius: 8;
    -fx-background-radius: 8;
    -fx-text-fill: #f5deb3;
    -fx-padding: 6 20 6 20;
    -fx-font-size: 14px;
    -fx-font-weight: bold;
"""));

        startButton.setOnMouseExited(e -> startButton.setStyle("""
    -fx-background-color: transparent;
    -fx-border-color: #d4af37;
    -fx-border-radius: 8;
    -fx-background-radius: 8;
    -fx-text-fill: #d4af37;
    -fx-padding: 6 20 6 20;
    -fx-font-size: 14px;
    -fx-font-weight: bold;
"""));
    }

    /**
     * Applies the initial visual styles of the game components.
     */
    private void applyInitialStyles() {
        setDefaultWordStyle();
        setInfoMessageStyle();
        updateTimeStyle();
    }

    /**
     * Updates the timer label color depending on the remaining time.
     */
    private void updateTimeStyle() {
        String color;

        if (timeLeft <= 5) {
            color = "#ef4444";
        } else if (timeLeft <= 10) {
            color = "#f59e0b";
        } else {
            color = "#facc15";
        }

        timeLabel.setStyle("""
        -fx-text-fill: %s;
        -fx-font-size: 16px;
        -fx-font-weight: bold;
    """.formatted(color));
    }

    /**
     * Applies the info style to the message label.
     */
    private void setInfoMessageStyle() {
        messageLabel.setStyle("""
        -fx-text-fill: #9ca3af;
        -fx-font-size: 14px;
        -fx-padding: 10;
    """);
    }

    /**
     * Applies the success style to the message label.
     */
    private void setSuccessMessageStyle() {
        messageLabel.setStyle("""
        -fx-text-fill: #22c55e;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-padding: 10;
    """);
    }

    /**
     * Applies the error style to the message label.
     */
    private void setErrorMessageStyle() {
        messageLabel.setStyle("""
        -fx-text-fill: #ef4444;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-padding: 10;
    """);
    }

    /**
     * Applies the default style to the central word label.
     */
    private void setDefaultWordStyle() {
        wordLabel.setStyle("""
        -fx-text-fill: #f4c542;
        -fx-font-size: 28px;
        -fx-font-weight: bold;
        -fx-effect: dropshadow(gaussian, rgba(244,197,66,0.35), 14, 0.35, 0, 0);
    """);
    }
}

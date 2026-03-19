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
 * Main controller for the "Escritura Rápida" game.
 *
 * <p>This class manages the entire game flow:
 * <ul>
 *     <li>Game start and reset</li>
 *     <li>Level progression</li>
 *     <li>Countdown timer logic</li>
 *     <li>User input validation</li>
 *     <li>UI updates and styling</li>
 * </ul>
 *
 * <p>The difficulty increases dynamically by reducing the available time
 * every 5 levels, forcing the player to react faster.</p>
 *
 * <p>The game follows a trial-based system inspired by dark fantasy themes,
 * where the player must survive typing challenges.</p>
 *
 * @author Omar Agredo
 * @version 1.0
 * @see com.agredo.escriturarapida.model.Words
 * @see com.agredo.escriturarapida.model.IWords
 */
public class GameController {

    /** Displays the current level/trial. */
    @FXML private Label levelLabel;

    /** Displays remaining time. */
    @FXML private Label timeLabel;

    /** Displays the word the player must type. */
    @FXML private Label wordLabel;

    /** Input field where the player types the word. */
    @FXML private TextField inputField;

    /** Displays feedback messages (info, success, error). */
    @FXML private Label messageLabel;

    /** Button used to start the game. */
    @FXML private Button startButton;

    /** Word provider (model). Handles vocabulary selection by difficulty. */
    private final IWords words = new Words();

    /** Current target word that the player must type. */
    private String currentWord = "";

    /** Current level (starts at 1). */
    private int currentLevel = 1;

    /** Remaining time for the current level (seconds). */
    private int timeLeft = 20;

    /**
     * Base time for each level.
     * This value decreases as difficulty increases.
     */
    private int baseTime = 20;

    /**
     * Timeline used as a game loop for the countdown timer.
     * Executes every second.
     */
    private Timeline timer;

    /**
     * Starts a new game session.
     *
     * <p>Initializes level, resets time, loads the first word,
     * enables input, and starts the countdown.</p>
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
     * Prepares the next level.
     *
     * <p>Loads a new word, resets input field, updates UI messages,
     * and restarts the timer.</p>
     *
     * @see #setupTimer()
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
     * Initializes and starts the countdown timer.
     *
     * <p>The timer decreases every second. When it reaches zero,
     * the system automatically validates the input.</p>
     *
     * @see #handleSubmit()
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
     * Validates the user's input.
     *
     * <p>If the typed word matches the target word:
     * <ul>
     *     <li>Player wins the level</li>
     *     <li>Progresses to next level</li>
     * </ul>
     *
     * <p>If incorrect or time runs out:
     * <ul>
     *     <li>Game resets</li>
     * </ul>
     *
     * @see #processWin()
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
     * Handles level progression after a successful attempt.
     *
     * <p>Every 5 levels, the available time is reduced by 2 seconds
     * to increase difficulty. Minimum time is capped at 2 seconds.</p>
     *
     * @see #nextLevel()
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
     * Resets the entire game state after failure.
     *
     * <p>Stops timer, resets variables, disables input,
     * and restores initial UI state.</p>
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

    /**
     * Initializes the UI when the scene is loaded.
     *
     * <p>Sets default texts, disables input,
     * and applies initial styles.</p>
     */
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
     * Applies default styles when the game loads.
     */
    private void applyInitialStyles() {
        setDefaultWordStyle();
        setInfoMessageStyle();
        updateTimeStyle();
    }

    /**
     * Updates timer color depending on urgency.
     *
     * <ul>
     *     <li>Red → critical (≤5s)</li>
     *     <li>Orange → warning (≤10s)</li>
     *     <li>Yellow → normal</li>
     * </ul>
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

    /** Applies neutral/info style to messages. */
    private void setInfoMessageStyle() {
        messageLabel.setStyle("""
            -fx-text-fill: #9ca3af;
            -fx-font-size: 14px;
            -fx-padding: 10;
        """);
    }

    /** Applies success style to messages. */
    private void setSuccessMessageStyle() {
        messageLabel.setStyle("""
            -fx-text-fill: #22c55e;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 10;
        """);
    }

    /** Applies error style to messages. */
    private void setErrorMessageStyle() {
        messageLabel.setStyle("""
            -fx-text-fill: #ef4444;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 10;
        """);
    }

    /** Applies default visual style to the main word. */
    private void setDefaultWordStyle() {
        wordLabel.setStyle("""
            -fx-text-fill: #f4c542;
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-effect: dropshadow(gaussian, rgba(244,197,66,0.35), 14, 0.35, 0, 0);
        """);
    }
}

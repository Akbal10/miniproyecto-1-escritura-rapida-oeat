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


public class GameController {

    @FXML private Label levelLabel;
    @FXML private Label timeLabel;
    @FXML private Label wordLabel;
    @FXML private TextField inputField;
    @FXML private Label messageLabel;
    @FXML private Button startButton;

    private final IWords words = new Words();
    private String currentWord = "";
    private int currentLevel = 1;
    private int timeLeft = 20;
    private int baseTime = 20;
    private Timeline timer;

    @FXML
    private void handleStart() {
        currentWord = words.getRandomWord();
        wordLabel.setText(currentWord);

        messageLabel.setText("Type it and press Enter");
        inputField.clear();
        inputField.requestFocus();
    }

    private void nextLevel() {
        currentWord = words.getRandomWord();
        wordLabel.setText(currentWord);
        inputField.clear();
        inputField.requestFocus();
        messageLabel.setText("Type it and press Enter");

        setupTimer();
    }

    private void setupTimer() {
        if (timer != null) timer.stop(); // Limpiar el anterior

        timeLeft = baseTime; // Reiniciar al tiempo actual de la dificultad
        timeLabel.setText("Time: " + timeLeft + "s");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timeLabel.setText("Time: " + timeLeft + "s");

            if (timeLeft <= 0) {
                timer.stop();
                handleSubmit(); // HU-2: Validación automática al llegar a cero
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play(); // ¡Sin esto el reloj no se mueve!
    }
    @FXML
    private void handleSubmit() {
        String typed = inputField.getText();

        // Detenemos el timer para que no siga bajando mientras procesamos
        if (timer != null) timer.stop();

        if (typed != null && typed.equals(currentWord)) {
            messageLabel.setText("✅ ¡Correcto!");
            processWin(); // Esto maneja el nivel y el nuevo tiempo
        } else {
            // HU-4: Mensaje de error claro [cite: 80]
            messageLabel.setText("❌ " + (timeLeft <= 0 ? "Tiempo agotado" : "Incorrecto"));
            resetGame(); // Según HU-3, si falla vuelve a empezar
        }
        inputField.requestFocus();
    }

    private void processWin() {
        currentLevel++; // HU-3: El nivel incrementa tras cada acierto
        levelLabel.setText("Level: " + currentLevel);


        if (currentLevel % 5 == 1 && currentLevel > 1) {
            if (baseTime > 2) {
                baseTime -= 2; // Reducción de 2 segundos
            }
        }
        nextLevel();
    }

    private void resetGame() {
        startButton.setDisable(false);
        inputField.clear();
    }
}

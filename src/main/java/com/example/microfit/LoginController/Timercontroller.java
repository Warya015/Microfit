package com.example.microfit.LoginController;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Timercontroller {

    @FXML
    private Text timerMinutes; // Het minuten-display in de cirkel

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Circle timerCircle;

    private Timeline timeline;
    private int timeInSeconds = 1800; // 30 minuten standaard

    @FXML
    public void initialize() {
        // Zorg dat de tekst correct weergegeven wordt bij het laden
        updateTimerDisplay();
    }

    @FXML
    private void onStartButtonClicked() {
        if (timeline == null || timeline.getStatus() != Animation.Status.RUNNING) {
            startTimer();
        }
    }

    @FXML
    private void onResetButtonClicked() {
        stopTimer();
        timeInSeconds = 1800; // Reset naar 30 minuten
        updateTimerDisplay();
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeInSeconds--;
            updateTimerDisplay();

            if (timeInSeconds <= 0) {
                stopTimer();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void updateTimerDisplay() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;

        // Werk de minuten bij en toon een passend formaat
        timerMinutes.setText(String.format("%02d:%02d", minutes, seconds));
    }
}

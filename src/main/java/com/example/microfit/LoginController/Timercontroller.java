package com.example.microfit.LoginController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Timercontroller {

    @FXML private Text timerMinutes;  // Voor de timer in minuten
    @FXML private Circle timerCircle;  // De cirkel om de voortgang te tonen
    @FXML private Button startButton;  // Start knop
    @FXML private Button resetButton;  // Reset knop

    private Timeline timeline;
    private int minutes = 30;  // Starttijd in minuten
    private boolean isRunning = false;  // Voor het starten en stoppen van de timer

    @FXML
    public void initialize() {
        resetButton.setDisable(true);  // Start met de reset knop uitgeschakeld

        startButton.setOnAction(e -> {
            if (isRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        resetButton.setOnAction(e -> resetTimer());
    }

    // Timer starten
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            minutes--;
            if (minutes <= 0) {
                stopTimer();
            }
            updateTimerDisplay();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        isRunning = true;
        startButton.setText("Stop");
        resetButton.setDisable(false);
    }

    // Timer stoppen
    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        isRunning = false;
        startButton.setText("Start");
    }

    // Timer resetten
    private void resetTimer() {
        stopTimer();
        minutes = 30;
        updateTimerDisplay();
        resetButton.setDisable(true);
    }

    // Timer update op scherm
    private void updateTimerDisplay() {
        timerMinutes.setText(String.format("%02d", minutes));
        // Optionaal: Cirkel op basis van tijd vullen (bijv. met een draaiende voortgangsbalk)
        double progress = 1.0 - (minutes / 30.0);
        timerCircle.setStrokeWidth(progress * 15);
    }

    // Methodes die gekoppeld zijn aan de knoppen in de FXML:
    @FXML
    public void onStartButtonClicked() {
        if (isRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    @FXML
    public void onResetButtonClicked() {
        resetTimer();
    }
}

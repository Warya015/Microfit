package com.example.microfit.LoginController;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Timercontroller {

    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Text timerMinutes;
    @FXML
    private Circle timerCircle;

    private int totalSeconds = 1800; // 30 minuten in seconden
    private int currentSeconds = totalSeconds;

    private AnimationTimer timer;

    @FXML
    public void initialize() {
        updateTimerDisplay();
        updateCircle();
    }

    @FXML
    private void onStartButtonClicked() {
        if (timer != null) {
            timer.stop(); // Stop een bestaande timer
        }

        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0 || now - lastUpdate >= 1_000_000_000) { // 1 seconde
                    if (currentSeconds > 0) {
                        currentSeconds--;
                        updateTimerDisplay();
                        updateCircle();
                    } else {
                        timer.stop();
                        System.out.println("Timer voltooid!");
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    @FXML
    private void onResetButtonClicked() {
        if (timer != null) {
            timer.stop();
        }
        currentSeconds = totalSeconds;
        updateTimerDisplay();
        updateCircle();
    }

    private void updateTimerDisplay() {
        int minutes = currentSeconds / 60;
        int seconds = currentSeconds % 60;
        timerMinutes.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void updateCircle() {
        double progress = (double) currentSeconds / totalSeconds;
        timerCircle.setStrokeDashOffset(-progress * 628.0); // 2 * Math.PI * 100 (radius van cirkel)
    }
}

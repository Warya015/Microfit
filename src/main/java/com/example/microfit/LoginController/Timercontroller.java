package com.example.microfit.LoginController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Timercontroller {

    @FXML
    private Circle timerCircle;

    @FXML
    private Text timerMinutes; // Om de resterende tijd weer te geven

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button settingsButton; // De knop voor instellingen

    private boolean isRunning = false; // Houdt bij of de timer aan of uit is
    private int timeRemaining = 1800; // Beginwaarde in seconden (30 minuten)

    private Timeline timer; // Timeline om de timer aan te sturen

    @FXML
    private void initialize() {
        resetTimer(); // Zorg ervoor dat de timer correct is ingesteld bij de start
    }

    @FXML
    private void onStartButtonClicked() {
        if (isRunning) {
            stopTimer(); // Stop de timer als hij al draait
        } else {
            startTimer(); // Start de timer
        }
    }

    @FXML
    private void onResetButtonClicked() {
        resetTimer(); // Reset de timer naar de beginwaarde
    }

    private void startTimer() {
        isRunning = true;
        startButton.setText("Stop"); // Verander de tekst naar 'Stop' als de timer draait

        // De timeline zorgt ervoor dat elke seconde de timer wordt bijgewerkt
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--; // Verminder de tijd met 1 seconde
            updateTimerDisplay(); // Werk de timerweergave bij

            if (timeRemaining <= 0) {
                stopTimer(); // Stop de timer als de tijd op is
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Laat de timeline onbeperkt doorgaan
        timer.play(); // Start de timer
    }

    private void stopTimer() {
        isRunning = false;
        startButton.setText("Start"); // Zet de knoptekst terug naar 'Start'
        if (timer != null) {
            timer.stop(); // Stop de timeline
        }
    }

    private void resetTimer() {
        stopTimer(); // Stop de timer als deze loopt
        timeRemaining = 1800; // Reset de tijd naar 30 minuten (1800 seconden)
        updateTimerDisplay(); // Werk de weergave bij
    }

    private void updateTimerDisplay() {
        // Werk de weergave bij met het resterende aantal minuten en seconden
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerMinutes.setText(timeString); // Update de timerweergave
    }

    @FXML
    private void goToSettings() {
        try {
            // Laad de Instellingen-pagina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/microfit/Instellingen_Pagina6.fxml"));
            Parent root = loader.load();

            // Verkrijg de huidige stage (venster)
            Stage stage = (Stage) settingsButton.getScene().getWindow();

            // Toon de Instellingen-pagina in het huidige venster
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

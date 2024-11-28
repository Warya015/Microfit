package com.example.microfit.LoginController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class Timercontroller {

    @FXML
    private Circle timerCircle;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button settingsButton; // De knop voor instellingen

    private boolean isRunning = false;
    private int timeRemaining = 30; // In minuten

    @FXML
    private void initialize() {
        resetTimer();
    }

    @FXML
    private void onStartButtonClicked() {
        if (isRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    @FXML
    private void onResetButtonClicked() {
        resetTimer();
    }

    private void startTimer() {
        isRunning = true;
        startButton.setText("Stop");
        // Hier kun je je timer-logica implementeren
    }

    private void stopTimer() {
        isRunning = false;
        startButton.setText("Start");
        // Timer-stoplogica
    }

    private void resetTimer() {
        stopTimer();
        timeRemaining = 30; // Reset naar standaardwaarde
        // Update weergave als je een timer-indicatie hebt
    }

    @FXML
    private void goToSettings() {
        try {
            // Laad de Instellingen-pagina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/microfit/Instellingen_Pagina6.fxml"));            Parent root = loader.load();

            // Verkrijg de huidige stage (venster)
            Stage stage = (Stage) settingsButton.getScene().getWindow();

            // Toon de Instellingen-pagina in het huidige venster
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

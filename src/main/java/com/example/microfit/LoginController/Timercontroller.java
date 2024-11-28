package com.example.microfit.LoginController;

import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class Timercontroller {

    @FXML
    private Circle timerCircle;

    @FXML
    private Text timerMinutes;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button settingsButton;

    private boolean isRunning = false;
    private int timeRemaining = 1800;
    private Timeline timer;
    private int warningCount = 0;
    private SerialPort serialPort;

    // Initialiseer de seriële poort
    private void initializeSerialPort() {
        if (serialPort == null) {
            serialPort = SerialPort.getCommPort("COM5"); // Vervang door de juiste poortnaam
            serialPort.setBaudRate(9600);
        }
    }

    // Open de seriële poort
    private void openSerialPort() {
        if (serialPort != null && !serialPort.isOpen()) {
            if (serialPort.openPort()) {
                System.out.println("Seriële poort geopend: " + serialPort.getSystemPortName());
            } else {
                System.err.println("Kan seriële poort niet openen: " + serialPort.getSystemPortName());
            }
        }
    }

    // Sluit de seriële poort
    private void closeSerialPort() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
            System.out.println("Seriële poort gesloten: " + serialPort.getSystemPortName());
        }
    }

    @FXML
    private void initialize() {
        initializeSerialPort(); // Initialiseer de seriële poort
        resetTimer(); // Reset de timer bij start
    }

    @FXML
    private void onStartButtonClicked() {
        if (isRunning) {
            stopTimer();
        } else {
            openSerialPort(); // Open de seriële poort bij het starten van de timer
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

        // De timeline zorgt ervoor dat elke seconde de timer wordt bijgewerkt
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            updateTimerDisplay();

            checkDistance(); // Controleer de afstand en toon waarschuwing indien nodig

            if (timeRemaining <= 0) {
                stopTimer();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        isRunning = false;
        startButton.setText("Start");
        if (timer != null) {
            timer.stop();
        }
        closeSerialPort(); // Sluit de seriële poort bij het stoppen van de timer
    }

    private void resetTimer() {
        stopTimer();
        timeRemaining = 1800; // Reset de tijd naar 30 minuten (1800 seconden)
        updateTimerDisplay();
        warningCount = 0;
    }

    private void updateTimerDisplay() {
        // Update de timer weergave
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerMinutes.setText(timeString);
    }

    private void checkDistance() {
        // Lees de afstand van de Arduino via de seriële poort
        double distance = getDistanceFromSerial();

        System.out.println("Gemeten afstand: " + distance);

        if (distance < 10) { // Als de afstand onder de 10 cm is
            warningCount++;

            if (warningCount > 3) { // Als er meer dan 3 waarschuwingen zijn
                resetTimer(); // Reset de timer
            }

            showWarningPopup(); // Toon de popup notificatie
        }
    }

    private double getDistanceFromSerial() {
        if (serialPort != null && serialPort.isOpen()) {
            byte[] readBuffer = new byte[1024];
            int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
            if (numRead > 0) {
                try {
                    String receivedData = new String(readBuffer, 0, numRead).trim();
                    String[] lines = receivedData.split("\n");
                    String lastLine = lines[lines.length - 1].trim();

                    if (lastLine.startsWith("Distance:")) {
                        String distanceStr = lastLine.replace("Distance:", "").trim();
                        return Double.parseDouble(distanceStr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Voeg deze foutafhandelingscode toe
                }
            }
        }
        return 100; // Default waarde als de seriële poort niet goed werkt
    }

    private void showWarningPopup() {
        Alert warningPopup = new Alert(AlertType.WARNING);
        warningPopup.setTitle("Waarschuwing");
        warningPopup.setHeaderText(null); // Geen koptekst
        warningPopup.setContentText("Je bent te dichtbij! Pas je postuur aan.");

        warningPopup.getDialogPane().setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-family: 'Roboto', sans-serif; " +
                        "-fx-font-size: 14px;"
        );
        warningPopup.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        warningPopup.show();

        Timeline closePopupTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> warningPopup.close()));
        closePopupTimeline.play();
    }

    @FXML
    private void goToSettings() {
        try {
            closeSerialPort(); // Sluit de seriële poort bij het navigeren naar een andere pagina
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/microfit/Instellingen_Pagina6.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) settingsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.microfit.LoginController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.io.IOException;  // Dit is de ontbrekende import voor IOException
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class createAccount {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button backToLoginButton;

    // Wachtwoordsterkte-indicatoren
    @FXML
    private ProgressBar passwordStrengthBar;

    @FXML
    private Label passwordStrengthLabel;

    // Lokaal opslaan van gebruikers
    private static final HashMap<String, String> localUserDatabase = new HashMap<>();

    @FXML
    public void initialize() {
        // Voeg een listener toe voor het wachtwoordveld
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> updatePasswordStrength(newValue));
    }

    // Methode voor het registreren van een gebruiker
    @FXML
    public void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Controleer of de velden niet leeg zijn
        if (username.isEmpty() || password.isEmpty()) {
            showError("Vul alle velden in.");
        } else if (localUserDatabase.containsKey(username)) {
            // Controleer of de gebruikersnaam al bestaat
            showError("Gebruikersnaam bestaat al. Kies een andere.");
        } else {
            // Voeg de gebruiker toe aan de lokale database
            localUserDatabase.put(username, password);
            showSuccess("Account succesvol aangemaakt! Je wordt nu teruggestuurd naar de loginpagina.");

            // Navigeren naar de loginpagina na registratie
            loadPage("/com/example/microfit/Login_Pagina1.fxml"); // Gebruik de loadPage methode
        }
    }

    // Methode om de wachtwoordsterkte bij te werken
    private void updatePasswordStrength(String password) {
        int strength = getPasswordStrength(password);

        // Update de progress bar en de tekst
        if (strength == 0) {
            passwordStrengthBar.setProgress(0.0);
            passwordStrengthLabel.setText("Zwak");
            passwordStrengthLabel.setStyle("-fx-text-fill: red;");
            passwordStrengthBar.setStyle("-fx-accent: #f44336;"); // Rood voor zwak
        } else if (strength == 1) {
            passwordStrengthBar.setProgress(0.5);
            passwordStrengthLabel.setText("Middelmatig");
            passwordStrengthLabel.setStyle("-fx-text-fill: orange;");
            passwordStrengthBar.setStyle("-fx-accent: #ff9800;"); // Oranje voor middelmatig
        } else if (strength == 2) {
            passwordStrengthBar.setProgress(1.0);
            passwordStrengthLabel.setText("Sterk");
            passwordStrengthLabel.setStyle("-fx-text-fill: green;");
            passwordStrengthBar.setStyle("-fx-accent: #4caf50;"); // Groen voor sterk
        }
    }

    // Methode om de sterkte van het wachtwoord te berekenen
    private int getPasswordStrength(String password) {
        if (password.length() < 8) {
            return 0; // Zwak
        } else if (password.length() >= 8 && !containsLetterAndDigit(password)) {
            return 1; // Middelmatig
        } else if (password.length() >= 8 && containsLetterAndDigit(password)) {
            return 2; // Sterk
        }
        return 0; // Als het wachtwoord geen van de voorwaarden voldoet, als zwak beschouwen
    }

    // Methode om te controleren of het wachtwoord zowel een letter als een cijfer bevat
    private boolean containsLetterAndDigit(String password) {
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }

    // Methode voor het laden van de volgende pagina (loginpagina in dit geval).
    private void loadPage(String pagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            AnchorPane root = loader.load();

            // Verkrijg de huidige Stage (window) via de Button
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(root); // Maak een nieuwe Scene
            stage.setScene(scene); // Zet de nieuwe scene
            stage.show(); // Toon de nieuwe scene

        } catch (IOException e) {
            e.printStackTrace(); // Log de fout indien de FXML niet kan worden geladen
            showError("Er is een fout opgetreden bij het laden van de loginpagina.");
        }
    }

    // Methode voor het teruggaan naar de loginpagina.
    @FXML
    public void handleBackToLogin() {
        loadPage("/com/example/microfit/Login_Pagina1.fxml"); // Laad de loginpagina
    }

    // Hulpmethode om een foutmelding weer te geven.
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-opacity: 1; -fx-text-fill: RED;");
    }

    // Hulpmethode om een succesmelding weer te geven.
    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-opacity: 1; -fx-text-fill: GREEN;");
    }

    // Methode om toegang te geven aan LoginController om lokaal opgeslagen gebruikers te gebruiken.
    public static void addUser(String username, String password) {
        localUserDatabase.put(username, password);
    }

    // Statistische methode voor authenticatie.
    public static boolean authenticate(String username, String password) {
        return localUserDatabase.containsKey(username) && localUserDatabase.get(username).equals(password);
    }
}

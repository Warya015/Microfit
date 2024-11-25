package com.example.microfit.LoginController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    // Deze methode wordt uitgevoerd wanneer de gebruiker op de "Log in" knop klikt
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Controleer de inloggegevens voor de tijdelijke gebruikers
        if (username.equals("Abdi") && password.equals("Abdi123")) {
            // Als login succesvol is voor Abdi, laad Timer_Pagina3.fxml
            loadPage("/com/example/microfit/Timer_Pagina3.fxml");

        } else if (username.equals("Gabe") && password.equals("Gabe123")) {
            // Als login succesvol is voor Gabe, controleer of hij beheerder is
            if (isAdmin(username)) {
                // Gabe is een beheerder, laad Beheer_Pagina5.fxml
                loadPage("/com/example/microfit/Beheer_Pagina5.fxml");
            } else {
                showErrorAlert("Toegang geweigerd", "Je hebt geen beheerdersrechten.");
            }

        } else {
            // Als login niet succesvol is, toon een foutmelding
            showErrorAlert("Inloggen mislukt", "De gebruikersnaam of het wachtwoord is onjuist.");
        }
    }

    // Methode om te controleren of de gebruiker een beheerder is
    private boolean isAdmin(String username) {
        // In deze mock-up is alleen Gabe een beheerder
        return username.equals("Gabe");
    }

    // Methode voor het laden van de volgende pagina
    private void loadPage(String pagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            AnchorPane root = loader.load();

            // Maak een nieuwe Scene met de geladen FXML
            Scene scene = new Scene(root);

            // Verkrijg de huidige Stage (window)
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene); // Zet de nieuwe scene
            stage.show(); // Toon de nieuwe scene

        } catch (IOException e) {
            e.printStackTrace(); // Log de fout indien de FXML niet kan worden geladen
            showErrorAlert("Fout bij laden van pagina", "Er is een probleem bij het laden van de volgende pagina.");
        }
    }

    // Methode voor het tonen van een foutmelding (popup)
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait(); // Toon de alert en wacht totdat de gebruiker het afsluit
    }
}

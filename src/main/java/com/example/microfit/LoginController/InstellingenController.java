package com.example.microfit.LoginController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class InstellingenController {

    // Declare buttons from FXML
    @FXML
    private Button nieuweMicrofitButton;   // "Nieuwe Microfit Verbinden"
    @FXML
    private Button wachtwoordVeranderenButton;  // "Wachtwoord veranderen"
    @FXML
    private Button accountVerwijderenButton;  // "Account Verwijderen"
    @FXML
    private Button terugButton;   // "Terug"

    // Method to handle "Nieuwe Microfit Verbinden" button click
    @FXML
    private void handleNieuweMicrofitButton() {
        try {
            // Load the next FXML page (Microfitverbinden_Pagina2.fxml)
            AnchorPane newPage = FXMLLoader.load(getClass().getResource("/com/example/microfit/Microfitverbinden_Pagina2.fxml"));

            // Get current stage and set the new scene
            Stage stage = (Stage) nieuweMicrofitButton.getScene().getWindow();
            stage.setScene(new Scene(newPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle "Wachtwoord veranderen" button click
    @FXML
    private void handleWachtwoordVeranderenButton() {
        // Do nothing for now, as per your request
    }

    // Method to handle "Account Verwijderen" button click
    @FXML
    private void handleAccountVerwijderenButton() {
        try {
            // Load the next FXML page (Verwijderacc_Pagina7.fxml)
            AnchorPane newPage = FXMLLoader.load(getClass().getResource("/com/example/microfit/Verwijderacc_Pagina7.fxml"));

            // Get current stage and set the new scene
            Stage stage = (Stage) accountVerwijderenButton.getScene().getWindow();
            stage.setScene(new Scene(newPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle "Terug" button click
    @FXML
    private void handleTerugButton() {
        try {
            // Load the next FXML page (Timer_Pagina3.fxml)
            AnchorPane newPage = FXMLLoader.load(getClass().getResource("/com/example/microfit/Timer_Pagina3.fxml"));

            // Get current stage and set the new scene
            Stage stage = (Stage) terugButton.getScene().getWindow();
            stage.setScene(new Scene(newPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

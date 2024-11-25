package com.example.microfit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_Pagina1.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("MicroFit Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Print de fout naar de console voor debugging
            System.err.println("Fout bij het laden van de FXML.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

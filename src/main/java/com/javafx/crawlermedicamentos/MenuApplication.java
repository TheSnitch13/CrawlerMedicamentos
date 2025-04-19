package com.javafx.crawlermedicamentos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.javafx.crawlermedicamentos.Utilidades.mostrarError;

public class MenuApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/javafx/crawlermedicamentos/menu.fxml"));

            Scene scene = new Scene(root);

            primaryStage.setTitle("Crawler de Medicamentos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            mostrarError("Ha ocurrido un error", e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
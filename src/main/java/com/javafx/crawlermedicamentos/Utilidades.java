package com.javafx.crawlermedicamentos;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilidades {

    public static void mostrarError(String titulo, String mensaje) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(titulo);
            alerta.setContentText(mensaje);
            alerta.showAndWait();
    }
}

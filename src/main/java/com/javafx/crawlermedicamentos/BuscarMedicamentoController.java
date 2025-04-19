package com.javafx.crawlermedicamentos;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

import static com.javafx.crawlermedicamentos.Utilidades.mostrarError;

public class BuscarMedicamentoController {

    @FXML private TextField TextFielBuscarMedicamento;
    @FXML private Button ButtonBuscarMedicamento;
    @FXML private Button ButtonVolverBuscarMedicamento;
    @FXML private CheckBox CheckBoxFarmaciasGuadalajara, CheckBoxFarmaciasDelAhorro,
            CheckBoxFarmaciasBenavides, CheckBoxFarmaciasSanPablo, CheckBoxFarmaciasSimilares;

    @FXML
    private void buscarMedicamento() {
        String medicamento = TextFielBuscarMedicamento.getText().trim();

        if (medicamento.isEmpty()) {
            TextFielBuscarMedicamento.setText("Por favor, ingresa un nombre de medicamento.");
            return;
        }

        int cantidadCrawlers = contarCrawlersSeleccionados();
        if (cantidadCrawlers == 0) {
            System.out.println("Por favor, selecciona al menos una farmacia.");
            return;
        }

        try {
            Stage stage = (Stage) ButtonBuscarMedicamento.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BarraDeEspera.fxml"));
            Parent root = loader.load();
            BarraDeEsperaController barraDeEsperaController = loader.getController();

            barraDeEsperaController.configurarProgreso(cantidadCrawlers);
            barraDeEsperaController.ejecutarCrawlers(
                    medicamento,
                    CheckBoxFarmaciasGuadalajara.isSelected(),
                    CheckBoxFarmaciasDelAhorro.isSelected(),
                    CheckBoxFarmaciasBenavides.isSelected(),
                    CheckBoxFarmaciasSimilares.isSelected(),
                    CheckBoxFarmaciasSanPablo.isSelected(),
                    resultados -> mostrarResultados(resultados, stage)
            );

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            mostrarError("Ha ocurrido un error", e.getMessage());
        }
    }

    private int contarCrawlersSeleccionados() {
        int count = 0;
        if (CheckBoxFarmaciasGuadalajara.isSelected()) count++;
        if (CheckBoxFarmaciasDelAhorro.isSelected()) count++;
        if (CheckBoxFarmaciasBenavides.isSelected()) count++;
        if (CheckBoxFarmaciasSimilares.isSelected()) count++;
        if (CheckBoxFarmaciasSanPablo.isSelected()) count++;
        return count;
    }

    private void mostrarResultados(List<Producto> productos, Stage stage) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Resultados.fxml"));
                Parent root = loader.load();
                ResultadosController resultadosController = loader.getController();
                productos.sort(Comparator.comparingDouble(Producto::getPrecio));

                resultadosController.setResultadosTabla(FXCollections.observableArrayList(productos));

                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

            } catch (Exception e) {
                mostrarError("Ha ocurrido un error", e.getMessage());
            }
        });
    }

    @FXML
    private void volverAlMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ButtonVolverBuscarMedicamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            mostrarError("Ha ocurrido un error", e.getMessage());
        }
    }
}




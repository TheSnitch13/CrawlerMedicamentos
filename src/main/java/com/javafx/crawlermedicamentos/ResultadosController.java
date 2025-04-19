package com.javafx.crawlermedicamentos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

import static com.javafx.crawlermedicamentos.Utilidades.mostrarError;

public class ResultadosController {

    @FXML private Label LabelResultados;
    @FXML private TableView<Producto> tableViewResultados;
    @FXML private TableColumn<Producto, String> ColumnMedicamento;
    @FXML private TableColumn<Producto, String> ColumnPrecio;
    @FXML private TableColumn<Producto, String> ColumnFarmacia;
    @FXML private TableColumn<Producto, String> ColumnEnlace;
    @FXML private Button ButtonVolverResultados;

    @FXML
    public void initialize() {
        ColumnMedicamento.setCellValueFactory(new PropertyValueFactory<>("medicamento"));
        ColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioFormateado"));
        ColumnFarmacia.setCellValueFactory(new PropertyValueFactory<>("farmacia"));
        ColumnEnlace.setCellValueFactory(new PropertyValueFactory<>("url"));

        // Personaliza la columna de enlace para que abra el navegador al hacer clic
        ColumnEnlace.setCellFactory(tc -> new TableCell<Producto, String>() {
            @Override
            protected void updateItem(String enlace, boolean empty) {
                super.updateItem(enlace, empty);
                if (empty || enlace == null) {
                    setText(null);
                    setOnMouseClicked(null);
                    setStyle(null);
                } else {
                    setText(enlace);
                    setStyle("-fx-text-fill: blue; -fx-underline: true;"); // Estilo visual de hipervínculo
                    setOnMouseClicked(event -> {
                        try {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(new URI(enlace));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

        ButtonVolverResultados.setOnAction(event -> volverAlInicio());
    }

    private void volverAlInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuscarMedicamento.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ButtonVolverResultados.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            mostrarError("Ha ocurrido un error", e.getMessage());
        }
    }

    public void setResultadosTabla(ObservableList<Producto> productos) {
        tableViewResultados.setItems(productos);
    }
}



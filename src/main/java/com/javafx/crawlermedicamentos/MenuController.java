package com.javafx.crawlermedicamentos;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;

import static com.javafx.crawlermedicamentos.Utilidades.mostrarError;


public class MenuController {

    @FXML
    private AnchorPane rootPaneMenu;

    @FXML
    private Button btnSelectBuscarMedicamento;

    @FXML
    private Label labelBuscarMedicamento;

    @FXML
    private ImageView ImageViewMenu;

    @FXML
    private ImageView ImageViewBuscarMedicamento;


    @FXML
    private void handleBuscarMedicamento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("buscarMedicamento.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Crawler de Medicamentos");
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setResizable(false);

            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            mostrarError("Ha ocurrido un error", e.getMessage());
        }
    }
}
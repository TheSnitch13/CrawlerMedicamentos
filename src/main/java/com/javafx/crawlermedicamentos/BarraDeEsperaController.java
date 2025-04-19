package com.javafx.crawlermedicamentos;

import com.javafx.crawlermedicamentos.CrawlerFarmaciasBenavides.FarmaciasBenavidesCrawler;
import com.javafx.crawlermedicamentos.CrawlerFarmaciasDelAhorro.FarmaciasDelAhorroCrawler;
import com.javafx.crawlermedicamentos.CrawlerFarmaciasGuadalajara.FarmaciasGuadalajaraCrawler;
import com.javafx.crawlermedicamentos.CrawlerFarmaciasSanPablo.FarmaciaSanPabloCrawler;
import com.javafx.crawlermedicamentos.CrawlerFarmaciasSimilares.FarmaciasSimilaresCrawler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BarraDeEsperaController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ProgressIndicator progressIndicator;

    private double progresoActual = 0.0;
    private double incrementoPorPaso = 0.0;

    public void configurarProgreso(int cantidadCrawlers) {
        if (cantidadCrawlers <= 0) throw new IllegalArgumentException("Debe haber al menos un crawler.");
        incrementoPorPaso = (1.0 / cantidadCrawlers) / 2.0;
        progresoActual = 0.0;
        Platform.runLater(() -> {
            progressBar.setProgress(0.0);
            progressIndicator.setProgress(0.0);
        });
    }

    public synchronized void incrementarProgreso() {
        progresoActual += incrementoPorPaso;
        if (progresoActual > 1.0) progresoActual = 1.0;
        final double progresoFinal = progresoActual;

        Platform.runLater(() -> {
            progressBar.setProgress(progresoFinal);
            progressIndicator.setProgress(progresoFinal);
        });
    }

    public void ejecutarCrawlers(String medicamento,
                                 boolean usarGuadalajara,
                                 boolean usarAhorro,
                                 boolean usarBenavides,
                                 boolean usarSimilares,
                                 boolean usarSanPablo,
                                 Consumer<List<Producto>> onFinish) {

        new Thread(() -> {
            List<Producto> productos = new ArrayList<>();

            if (usarGuadalajara) {
                ejecutarCrawler(new FarmaciasGuadalajaraCrawler(), medicamento, productos);
            }
            if (usarAhorro) {
                ejecutarCrawler(new FarmaciasDelAhorroCrawler(), medicamento, productos);
            }
            if (usarBenavides) {
                ejecutarCrawler(new FarmaciasBenavidesCrawler(), medicamento, productos);
            }
            if (usarSimilares) {
                ejecutarCrawler(new FarmaciasSimilaresCrawler(), medicamento, productos);
            }
            if (usarSanPablo) {
                ejecutarCrawler(new FarmaciaSanPabloCrawler(), medicamento, productos);
            }

            Platform.runLater(() -> onFinish.accept(productos));

        }).start();
    }

    private void ejecutarCrawler(FarmaciaCrawler crawler, String medicamento, List<Producto> productos) {

        incrementarProgreso();

        List<Producto> resultados = crawler.buscarProductos(medicamento);

        incrementarProgreso();

        if (resultados != null && !resultados.isEmpty()) {
            synchronized (productos) {
                productos.addAll(resultados);
            }
        }
    }
}

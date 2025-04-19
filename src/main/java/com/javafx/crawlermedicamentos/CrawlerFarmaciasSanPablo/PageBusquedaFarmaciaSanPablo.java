package com.javafx.crawlermedicamentos.CrawlerFarmaciasSanPablo;

import com.javafx.crawlermedicamentos.Producto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageBusquedaFarmaciaSanPablo {

    private WebDriver driver;

    public PageBusquedaFarmaciaSanPablo(WebDriver driver) {
        this.driver = driver;
    }

    public List<Producto> obtenerDatos() {
        List<Producto> productos = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.cssSelector("app-product-list-item"));

        System.out.println("Total de productos encontrados en la página: " + elementos.size());

        for (WebElement el : elementos) {
            try {
                WebElement nombreEl = el.findElement(By.cssSelector("a.nameProduct"));
                String nombre = nombreEl.getText().trim();

                WebElement detalleEl = el.findElement(By.cssSelector("div.custom-postop"));
                String detalle = detalleEl.getText().trim();

                String nombreCompleto = nombre + " " + detalle;

                WebElement precioEl = el.findElement(By.cssSelector("p"));
                String precioStr = precioEl.getText().replace("$", "").replace("MXN", "").replace(",", "").trim();
                double precio = Double.parseDouble(precioStr);

                // Nota: En algunos casos puede no haber URL visible
                String url = "";

                productos.add(new Producto(nombreCompleto, precio, "Farmacias San Pablo", url));

            } catch (Exception e) {
                System.err.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        System.out.println("Extracción de productos completada.\nTotal productos extraídos: " + productos.size());
        return productos;
    }
}

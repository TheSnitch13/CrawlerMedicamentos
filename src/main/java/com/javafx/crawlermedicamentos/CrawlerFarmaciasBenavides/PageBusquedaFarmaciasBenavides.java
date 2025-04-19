package com.javafx.crawlermedicamentos.CrawlerFarmaciasBenavides;

import com.javafx.crawlermedicamentos.Producto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageBusquedaFarmaciasBenavides {
    private WebDriver driver;

    public PageBusquedaFarmaciasBenavides(WebDriver driver) {
        this.driver = driver;
    }

    public List<Producto> obtenerDatos() {
        List<Producto> productos = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.cssSelector("li.item.product.product-item.product-knockout"));

        System.out.println("Total de productos encontrados en la página: " + elementos.size());

        for (WebElement el : elementos) {
            try {
                String marca = "";
                try {
                    marca = el.findElement(By.cssSelector(".product-item-brand")).getText().trim();
                } catch (Exception e) {
                    marca = "Marca no disponible";
                }

                String descripcion = "";
                String url = "";
                try {
                    WebElement enlace = el.findElement(By.cssSelector("a.product-item-link"));
                    descripcion = enlace.getText().trim();
                    url = enlace.getAttribute("href");
                } catch (Exception e) {
                    descripcion = "Descripción no disponible";
                }

                String presentacion = "";
                try {
                    presentacion = el.findElement(By.cssSelector("div.product-item-presentation")).getText().trim();
                } catch (Exception e) {
                    presentacion = "Presentación no disponible";
                }

                double precio = 0.0;
                try {
                    WebElement precioElement = el.findElement(By.cssSelector(".price"));
                    String precioStr = precioElement.getText().replace("$", "").replace(",", "").trim();
                    precio = Double.parseDouble(precioStr);
                } catch (Exception e) {
                    System.out.println("Precio no disponible para producto: " + descripcion);
                }

                String nombreFinal = marca + " " + descripcion + " " + presentacion;

                productos.add(new Producto(nombreFinal.trim(), precio, "Farmacias Benavides", url));

            } catch (Exception e) {
                System.err.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        System.out.println("Extracción de productos completada.\nTotal productos extraídos: " + productos.size());

        return productos;
    }
}

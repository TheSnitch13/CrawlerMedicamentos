package com.javafx.crawlermedicamentos.CrawlerFarmaciasSimilares;

import com.javafx.crawlermedicamentos.Producto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageBusquedaFarmaciaSimilar {
    private WebDriver driver;

    public PageBusquedaFarmaciaSimilar(WebDriver driver) {
        this.driver = driver;
    }

    public List<Producto> obtenerDatos() {
        List<Producto> productos = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.cssSelector(".vtex-product-summary-2-x-container--app-shelf-summary"));
        System.out.println("Total de productos encontrados: " + elementos.size());

        for (WebElement el : elementos) {
            try {
                WebElement nombreElement = el.findElement(By.cssSelector("span.vtex-product-summary-2-x-productBrand"));
                String nombre = nombreElement.getText().trim();

                WebElement enlaceElement = el.findElement(By.cssSelector("a.vtex-product-summary-2-x-clearLink"));
                String url = enlaceElement.getAttribute("href");

                String entero = el.findElement(By.cssSelector("span.vtex-product-summary-2-x-currencyInteger")).getText().trim();
                String fraccion = el.findElement(By.cssSelector("span.vtex-product-summary-2-x-currencyFraction")).getText().trim();
                String precioTexto = entero + "." + fraccion;

                double precio;
                try {
                    precio = Double.parseDouble(precioTexto);
                } catch (NumberFormatException e) {
                    System.err.println("No se pudo convertir el precio: " + precioTexto);
                    continue;
                }

                productos.add(new Producto(nombre, precio, "Farmacias Similares", url));

            } catch (Exception e) {
                System.err.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        System.out.println("Extracción completada. Total productos extraídos: " + productos.size());
        return productos;
    }
}

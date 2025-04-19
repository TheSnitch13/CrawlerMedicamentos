package com.javafx.crawlermedicamentos.CrawlerFarmaciasGuadalajara;

import com.javafx.crawlermedicamentos.Producto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageBusquedaFarmacia {
    private WebDriver driver;

    public PageBusquedaFarmacia(WebDriver driver) {
        this.driver = driver;
    }

    public List<Producto> obtenerDatos() {
        List<Producto> productos = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.cssSelector("div.product_info"));

        System.out.println("Total de productos encontrados en la página: " + elementos.size());

        for (WebElement el : elementos) {

            try {
                WebElement nombreDiv = el.findElement(By.cssSelector("div.product_name"));
                WebElement enlace = nombreDiv.findElement(By.tagName("a"));

                String nombre = enlace.getText().replaceAll("\\s+", " ").trim();

                String url = enlace.getAttribute("href");

                WebElement precioSpan = el.findElement(By.cssSelector("span[id^='offerPrice_']"));
                String precioStr = precioSpan.getText().replace("$", "").replace(",", "").trim();
                double precio = Double.parseDouble(precioStr);

                productos.add(new Producto(nombre, precio, "Farmacias Guadalajara", url));

            } catch (Exception e) {
                System.err.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        System.out.println("Extracción de productos completada.\n Total productos extraídos: " + productos.size());

        return productos;
    }

}

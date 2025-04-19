package com.javafx.crawlermedicamentos.CrawlerFarmaciasDelAhorro;

import com.javafx.crawlermedicamentos.Producto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageBusquedaFarmaciaDelAhorro {

    private WebDriver driver;

    public PageBusquedaFarmaciaDelAhorro(WebDriver driver) {
        this.driver = driver;
    }

    public List<Producto> obtenerDatos() {
        List<Producto> productos = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.cssSelector("li.item.product.product-item"));

        System.out.println("Total de productos encontrados en la página: " + elementos.size());

        //int maxProductos = 3;
        //int count = 0;

        for (WebElement el : elementos) {
            //if (count >= maxProductos) break;

            try {
                // Obtener el contenedor con el nombre y enlace del producto
                WebElement nombreElemento = el.findElement(By.cssSelector("strong.product.name.product-item-name a"));

                String nombre = nombreElemento.getText();

                // Obtener la URL del producto
                String url = nombreElemento.getAttribute("href");

                // Obtener el precio desde el span#offerPrecio


                WebElement precioSpan = el.findElement(By.cssSelector("div.price-box.price-final_price span.price"));
                String precioStr = precioSpan.getText().replace("$", "").replace(",", "").trim();
                double precio = Double.parseDouble(precioStr);

                // Crear objeto Producto
                productos.add(new Producto(nombre, precio, "Farmacias Del Ahorro", url));

            } catch (Exception e) {
                System.err.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        System.out.println("Extracción de productos completada.\n Total productos extraídos: " + productos.size());

        return productos;
    }
}

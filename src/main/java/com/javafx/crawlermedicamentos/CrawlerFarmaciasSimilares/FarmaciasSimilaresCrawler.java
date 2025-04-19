package com.javafx.crawlermedicamentos.CrawlerFarmaciasSimilares;

import com.javafx.crawlermedicamentos.FarmaciaCrawler;
import com.javafx.crawlermedicamentos.Producto;
import com.javafx.crawlermedicamentos.HomeFarmacia;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FarmaciasSimilaresCrawler implements FarmaciaCrawler {
    @Override
    public List<Producto> buscarProductos(String nombre) {
        System.setProperty("webdriver.chrome.driver", "D:\\Workspace\\CrawlerMedicamentos\\drivers\\chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\alanh\\AppData\\Local\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        List<Producto> resultados = new ArrayList<>();

        try {
            HomeFarmacia home = new HomeFarmacia(driver);
            PageBusquedaFarmaciaSimilar pagina = new PageBusquedaFarmaciaSimilar(driver);

            String terminoCodificado = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
            String urlBusqueda = "https://www.farmaciasdesimilares.com/" + terminoCodificado + "?map=ft";

            System.out.println("Navegando a la página de búsqueda directa con URL: " + urlBusqueda);
            home.goTo(urlBusqueda);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Esperando que los resultados se carguen...");
            Thread.sleep(5000);

            System.out.println("Obteniendo datos de los productos...");
            resultados = pagina.obtenerDatos();

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos.");
            } else {
                System.out.println("Productos extraídos exitosamente.");
            }

        } catch (Exception e) {
            System.err.println("Error durante la ejecución del crawler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return resultados;
    }
}

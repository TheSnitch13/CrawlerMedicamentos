package com.javafx.crawlermedicamentos;

import org.openqa.selenium.WebDriver;

public class HomeFarmacia {
    private WebDriver driver;

    public HomeFarmacia(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo(String url) {
        System.out.println("Navegando a la URL: " + url);
        driver.get(url);
    }
}
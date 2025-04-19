module com.javafx.crawlermedicamentos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;
    requires java.desktop;

    opens com.javafx.crawlermedicamentos to javafx.fxml;
    exports com.javafx.crawlermedicamentos;
}
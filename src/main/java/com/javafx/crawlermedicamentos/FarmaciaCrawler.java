package com.javafx.crawlermedicamentos;

import java.util.List;

public interface FarmaciaCrawler {
    List<Producto> buscarProductos(String nombre);
}
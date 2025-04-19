package com.javafx.crawlermedicamentos;

public class Producto {
    private String medicamento;
    private double precio;
    private String farmacia;
    private String url;

    public Producto(String medicamento, double precio, String farmacia, String url) {
        this.medicamento = medicamento;
        this.precio = precio;
        this.farmacia = farmacia;
        this.url = url;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrecioFormateado() {
        return String.format("$%.2f", precio);
    }


    @Override
    public String toString() {
        return "Producto{" +
                "medicamento='" + medicamento + '\'' +
                ", precio=" + precio +
                ", farmacia='" + farmacia + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
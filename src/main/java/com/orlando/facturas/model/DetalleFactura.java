/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.model;

/**
 *
 * @author Orlando
 */
public class DetalleFactura {
    private int id;
    private String facturaFolio;
    private String articuloCodigo;
    private int cantidad;
    private double precioUnitario;

    public DetalleFactura() {
    }

    public DetalleFactura(int id, String facturaFolio, String articuloCodigo, int cantidad, double precioUnitario) {
        this.id = id;
        this.facturaFolio = facturaFolio;
        this.articuloCodigo = articuloCodigo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacturaFolio() {
        return facturaFolio;
    }

    public void setFacturaFolio(String facturaFolio) {
        this.facturaFolio = facturaFolio;
    }

    public String getArticuloCodigo() {
        return articuloCodigo;
    }

    public void setArticuloCodigo(String articuloCodigo) {
        this.articuloCodigo = articuloCodigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "DetalleFactura #" + id;
    }
}

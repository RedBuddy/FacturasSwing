/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.model;

import java.time.LocalDate;

/**
 *
 * @author Orlando
 */
public class Factura {
    private String folio;
    private LocalDate fecha;
    private String clienteCodigo;
    private String vendedorCodigo;
    private String ivaCodigo;

    public Factura() {
    }

    public Factura(String folio, LocalDate fecha, String clienteCodigo, String vendedorCodigo, String ivaCodigo) {
        this.folio = folio;
        this.fecha = fecha;
        this.clienteCodigo = clienteCodigo;
        this.vendedorCodigo = vendedorCodigo;
        this.ivaCodigo = ivaCodigo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public String getVendedorCodigo() {
        return vendedorCodigo;
    }

    public void setVendedorCodigo(String vendedorCodigo) {
        this.vendedorCodigo = vendedorCodigo;
    }

    public String getIvaCodigo() {
        return ivaCodigo;
    }

    public void setIvaCodigo(String ivaCodigo) {
        this.ivaCodigo = ivaCodigo;
    }

    @Override
    public String toString() {
        return "Factura #" + folio;
    }
}

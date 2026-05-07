/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.model;

/**
 *
 * @author Orlando
 */
public class Iva {
    private String codigo;
    private String descripcion;
    private double porcentaje;

    public Iva() {
    }

    public Iva(String codigo, String descripcion, double porcentaje) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return descripcion + " (" + porcentaje + "%)";
    }
}

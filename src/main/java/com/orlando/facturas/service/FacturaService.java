/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.service;

import com.orlando.facturas.model.Factura;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Orlando
 */
public class FacturaService {
    private static final List<Factura> facturas = new ArrayList<>();

    public List<Factura> obtenerTodos() {
        return new ArrayList<>(facturas);
    }

    public Optional<Factura> obtenerPorFolio(String folio) {
        return facturas.stream().filter(f -> f.getFolio().equals(folio)).findFirst();
    }

    public Factura guardar(Factura factura) {
        obtenerPorFolio(factura.getFolio()).ifPresent(f -> {
            f.setFecha(factura.getFecha());
            f.setClienteCodigo(factura.getClienteCodigo());
            f.setVendedorCodigo(factura.getVendedorCodigo());
            f.setIvaCodigo(factura.getIvaCodigo());
        });
        if (!facturas.contains(factura)) {
            facturas.add(factura);
        }
        return factura;
    }

    public boolean eliminar(String folio) {
        return facturas.removeIf(f -> f.getFolio().equals(folio));
    }
}

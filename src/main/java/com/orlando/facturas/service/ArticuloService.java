/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.service;

import com.orlando.facturas.model.Articulo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Orlando
 */
public class ArticuloService {
    private static final List<Articulo> articulos = new ArrayList<>();

    public List<Articulo> obtenerTodos() {
        return new ArrayList<>(articulos);
    }

    public Optional<Articulo> obtenerPorCodigo(String codigo) {
        return articulos.stream().filter(a -> a.getCodigo().equals(codigo)).findFirst();
    }

    public List<Articulo> buscar(String termino) {
        return articulos.stream()
                .filter(a -> a.getDescripcion().toLowerCase().contains(termino.toLowerCase()) ||
                        a.getCodigo().toLowerCase().contains(termino.toLowerCase()))
                .toList();
    }

    public Articulo guardar(Articulo articulo) {
        obtenerPorCodigo(articulo.getCodigo()).ifPresent(a -> {
            a.setDescripcion(articulo.getDescripcion());
            a.setPrecioUnitario(articulo.getPrecioUnitario());
        });
        if (!articulos.contains(articulo)) {
            articulos.add(articulo);
        }
        return articulo;
    }

    public boolean eliminar(String codigo) {
        return articulos.removeIf(a -> a.getCodigo().equals(codigo));
    }
}

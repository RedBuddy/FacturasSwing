/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.service;

import com.orlando.facturas.model.Vendedor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Orlando
 */
public class VendedorService {
    private static final List<Vendedor> vendedores = new ArrayList<>();

    public List<Vendedor> obtenerTodos() {
        return new ArrayList<>(vendedores);
    }

    public Optional<Vendedor> obtenerPorCodigo(String codigo) {
        return vendedores.stream().filter(v -> v.getCodigo().equals(codigo)).findFirst();
    }

    public List<Vendedor> buscar(String termino) {
        return vendedores.stream()
                .filter(v -> v.getNombre().toLowerCase().contains(termino.toLowerCase()))
                .toList();
    }

    public Vendedor guardar(Vendedor vendedor) {
        obtenerPorCodigo(vendedor.getCodigo()).ifPresent(v -> {
            v.setNombre(vendedor.getNombre());
            v.setApellidos(vendedor.getApellidos());
        });
        if (!vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
        }
        return vendedor;
    }

    public boolean eliminar(String codigo) {
        return vendedores.removeIf(v -> v.getCodigo().equals(codigo));
    }
}

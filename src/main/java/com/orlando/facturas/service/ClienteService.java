/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.service;

import com.orlando.facturas.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Orlando
 */
public class ClienteService {
    private static final List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    public Optional<Cliente> obtenerPorCodigo(String codigo) {
        return clientes.stream().filter(c -> c.getCodigo().equals(codigo)).findFirst();
    }

    public List<Cliente> buscar(String termino) {
        return clientes.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(termino.toLowerCase()) ||
                        c.getCodigo().toLowerCase().contains(termino.toLowerCase()))
                .toList();
    }

    public Cliente guardar(Cliente cliente) {
        obtenerPorCodigo(cliente.getCodigo()).ifPresent(c -> {
            c.setNombre(cliente.getNombre());
            c.setApellidos(cliente.getApellidos());
        });
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
        return cliente;
    }

    public boolean eliminar(String codigo) {
        return clientes.removeIf(c -> c.getCodigo().equals(codigo));
    }
}

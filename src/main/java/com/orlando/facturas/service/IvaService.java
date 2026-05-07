/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.service;

import com.orlando.facturas.model.Iva;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Orlando
 */
public class IvaService {
    private static final List<Iva> ivas = new ArrayList<>();

    static {
        ivas.add(new Iva("IVA0", "IVA 0%", 0));
        ivas.add(new Iva("IVA5", "IVA 5%", 5));
        ivas.add(new Iva("IVA10", "IVA 10%", 10));
        ivas.add(new Iva("IVA19", "IVA 19%", 19));
    }

    public List<Iva> obtenerTodos() {
        return new ArrayList<>(ivas);
    }

    public Optional<Iva> obtenerPorCodigo(String codigo) {
        return ivas.stream().filter(i -> i.getCodigo().equals(codigo)).findFirst();
    }

    public Iva guardar(Iva iva) {
        obtenerPorCodigo(iva.getCodigo()).ifPresent(i -> {
            i.setDescripcion(iva.getDescripcion());
            i.setPorcentaje(iva.getPorcentaje());
        });
        if (!ivas.contains(iva)) {
            ivas.add(iva);
        }
        return iva;
    }

    public boolean eliminar(String codigo) {
        return ivas.removeIf(i -> i.getCodigo().equals(codigo));
    }
}

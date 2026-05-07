/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.ui.components;

import com.orlando.facturas.model.DetalleFactura;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Orlando
 */
public class DetalleFacturaTable extends JTable {
    private DetalleTableModel model;

    public DetalleFacturaTable() {
        this.model = new DetalleTableModel();
        this.setModel(model);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void cargarDetalles(List<DetalleFactura> detalles) {
        model.setDetalles(new ArrayList<>(detalles));
    }

    public List<DetalleFactura> obtenerDetalles() {
        return model.detalles;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        model.agregarFila(detalle);
    }

    public void eliminarDetalle(int fila) {
        if (fila >= 0 && fila < model.getRowCount()) {
            model.eliminarFila(fila);
        }
    }

    public void limpiar() {
        model.limpiar();
    }

    private class DetalleTableModel extends AbstractTableModel {
        private List<DetalleFactura> detalles = new ArrayList<>();
        private String[] columnas = {"Articulo Codigo", "Cantidad", "Precio Unitario", "Subtotal"};

        @Override
        public int getRowCount() {
            return detalles.size();
        }

        @Override
        public int getColumnCount() {
            return columnas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            DetalleFactura d = detalles.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return d.getArticuloCodigo();
                case 1:
                    return d.getCantidad();
                case 2:
                    return String.format("%.2f", d.getPrecioUnitario());
                case 3:
                    double subtotal = d.getCantidad() * d.getPrecioUnitario();
                    return String.format("%.2f", subtotal);
                default:
                    return "";
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }

        public void setDetalles(List<DetalleFactura> detalles) {
            this.detalles = detalles;
            fireTableDataChanged();
        }

        public void agregarFila(DetalleFactura detalle) {
            detalles.add(detalle);
            fireTableRowsInserted(detalles.size() - 1, detalles.size() - 1);
        }

        public void eliminarFila(int fila) {
            detalles.remove(fila);
            fireTableRowsDeleted(fila, fila);
        }

        public void limpiar() {
            detalles.clear();
            fireTableDataChanged();
        }
    }
}

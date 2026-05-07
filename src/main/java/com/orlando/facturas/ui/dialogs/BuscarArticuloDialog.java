package com.orlando.facturas.ui.dialogs;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.orlando.facturas.model.Articulo;
import com.orlando.facturas.service.ArticuloService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Orlando
 */
public class BuscarArticuloDialog extends JDialog {
    private JTextField textoBuscar;
    private JTable tablaArticulos;
    private ArticuloTableModel modeloTabla;
    private Articulo articuloSeleccionado;
    private ArticuloService articuloService;

    public BuscarArticuloDialog(Frame propietario, ArticuloService articuloService) {
        super(propietario, "Buscar Articulo", true);
        this.articuloService = articuloService;
        inicializar();
    }

    private void inicializar() {
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        this.add(panelPrincipal);
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Buscar:"));
        textoBuscar = new JTextField(20);
        panelBusqueda.add(textoBuscar);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(btnBuscar);
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        modeloTabla = new ArticuloTableModel();
        tablaArticulos = new JTable(modeloTabla);
        tablaArticulos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaArticulos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.addActionListener(e -> seleccionar());
        panelBotones.add(btnSeleccionar);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cancelar());
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        cargarTodos();
    }

    private void buscar() {
        String termino = textoBuscar.getText();
        List<Articulo> resultados = articuloService.buscar(termino);
        modeloTabla.setArticulos(resultados);
    }

    private void cargarTodos() {
        modeloTabla.setArticulos(articuloService.obtenerTodos());
    }

    private void seleccionar() {
        int fila = tablaArticulos.getSelectedRow();
        if (fila >= 0) {
            articuloSeleccionado = modeloTabla.getArticulo(fila);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un articulo");
        }
    }

    private void cancelar() {
        articuloSeleccionado = null;
        this.dispose();
    }

    public Articulo obtenerSeleccionado() {
        return articuloSeleccionado;
    }

    private class ArticuloTableModel extends AbstractTableModel {
        private List<Articulo> articulos = new ArrayList<>();
        private String[] columnas = {"Codigo", "Descripcion", "Precio"};
        @Override
        public int getRowCount() {
            return articulos.size();
        }
        @Override
        public int getColumnCount() {
            return columnas.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Articulo a = articulos.get(rowIndex);
            switch (columnIndex) {
                case 0: return a.getCodigo();
                case 1: return a.getDescripcion();
                case 2: return String.format("$%.2f", a.getPrecioUnitario());
                default: return "";
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }
        public void setArticulos(List<Articulo> articulos) {
            this.articulos = articulos;
            fireTableDataChanged();
        }
        public Articulo getArticulo(int fila) {
            return articulos.get(fila);
        }
    }
}

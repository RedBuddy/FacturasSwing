package com.orlando.facturas.ui.frames;

import com.orlando.facturas.model.Articulo;
import com.orlando.facturas.service.ArticuloService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloFrame extends JFrame {
    private ArticuloService articuloService;
    private JTable tabla;
    private ArticuloTableModel modeloTabla;
    private JTextField txtCodigo, txtDescripcion, txtPrecio;

    public ArticuloFrame(ArticuloService articuloService) {
        this.articuloService = articuloService;
        inicializar();
    }

    private void inicializar() {
        this.setTitle("Gestionar Articulos");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        JPanel panelFormulario = crearPanelFormulario();
        modeloTabla = new ArticuloTableModel();
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarEnFormulario());
        JScrollPane scrollPane = new JScrollPane(tabla);
        JPanel panelBotones = crearPanelBotones();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFormulario, scrollPane);
        splitPane.setDividerLocation(150);
        this.add(splitPane);
        this.add(panelBotones, BorderLayout.SOUTH);
        cargarArticulos();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Codigo:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);
        panel.add(new JLabel("Descripcion:"));
        txtDescripcion = new JTextField();
        panel.add(txtDescripcion);
        panel.add(new JLabel("Precio Unitario:"));
        txtPrecio = new JTextField();
        panel.add(txtPrecio);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        panel.add(btnGuardar);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminar());
        panel.add(btnEliminar);
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        panel.add(btnLimpiar);
        return panel;
    }

    private void cargarArticulos() {
        modeloTabla.setArticulos(articuloService.obtenerTodos());
    }

    private void cargarEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Articulo articulo = modeloTabla.getArticulo(fila);
            txtCodigo.setText(articulo.getCodigo());
            txtDescripcion.setText(articulo.getDescripcion());
            txtPrecio.setText(String.valueOf(articulo.getPrecioUnitario()));
        }
    }

    private void guardar() {
        if (txtCodigo.getText().trim().isEmpty() || txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Codigo y descripcion son requeridos");
            return;
        }
        try {
            Articulo articulo = new Articulo();
            articulo.setCodigo(txtCodigo.getText());
            articulo.setDescripcion(txtDescripcion.getText());
            articulo.setPrecioUnitario(Double.parseDouble(txtPrecio.getText()));
            articuloService.guardar(articulo);
            cargarArticulos();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Articulo guardado exitosamente");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser numerico");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un articulo");
            return;
        }
        Articulo articulo = modeloTabla.getArticulo(fila);
        articuloService.eliminar(articulo.getCodigo());
        cargarArticulos();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Articulo eliminado exitosamente");
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        tabla.clearSelection();
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

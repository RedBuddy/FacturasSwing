package com.orlando.facturas.ui.frames;

import com.orlando.facturas.model.Vendedor;
import com.orlando.facturas.service.VendedorService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorFrame extends JFrame {
    private VendedorService vendedorService;
    private JTable tabla;
    private VendedorTableModel modeloTabla;
    private JTextField txtCodigo, txtNombre, txtApellidos;

    public VendedorFrame(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
        inicializar();
    }

    private void inicializar() {
        this.setTitle("Gestionar Vendedores");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        JPanel panelFormulario = crearPanelFormulario();
        modeloTabla = new VendedorTableModel();
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarEnFormulario());
        JScrollPane scrollPane = new JScrollPane(tabla);
        JPanel panelBotones = crearPanelBotones();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFormulario, scrollPane);
        splitPane.setDividerLocation(150);
        this.add(splitPane);
        this.add(panelBotones, BorderLayout.SOUTH);
        cargarVendedores();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Codigo:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);
        panel.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panel.add(txtApellidos);
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

    private void cargarVendedores() {
        modeloTabla.setVendedores(vendedorService.obtenerTodos());
    }

    private void cargarEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Vendedor vendedor = modeloTabla.getVendedor(fila);
            txtCodigo.setText(vendedor.getCodigo());
            txtNombre.setText(vendedor.getNombre());
            txtApellidos.setText(vendedor.getApellidos());
        }
    }

    private void guardar() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son requeridos");
            return;
        }
        try {
            Vendedor vendedor = new Vendedor();
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                vendedor.setCodigo(modeloTabla.getVendedor(fila).getCodigo());
            }
            vendedor.setCodigo(txtCodigo.getText());
            vendedor.setNombre(txtNombre.getText());
            vendedor.setApellidos(txtApellidos.getText());
            vendedorService.guardar(vendedor);
            cargarVendedores();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Vendedor guardado exitosamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un vendedor");
            return;
        }
        Vendedor vendedor = modeloTabla.getVendedor(fila);
        vendedorService.eliminar(vendedor.getCodigo());
        cargarVendedores();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Vendedor eliminado exitosamente");
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        tabla.clearSelection();
    }

    private class VendedorTableModel extends AbstractTableModel {
        private List<Vendedor> vendedores = new ArrayList<>();
        private String[] columnas = {"Codigo", "Nombre", "Apellidos"};
        @Override
        public int getRowCount() {
            return vendedores.size();
        }
        @Override
        public int getColumnCount() {
            return columnas.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Vendedor v = vendedores.get(rowIndex);
            switch (columnIndex) {
                case 0: return v.getCodigo();
                case 1: return v.getNombre();
                case 2: return v.getApellidos();
                default: return "";
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }
        public void setVendedores(List<Vendedor> vendedores) {
            this.vendedores = vendedores;
            fireTableDataChanged();
        }
        public Vendedor getVendedor(int fila) {
            return vendedores.get(fila);
        }
    }
}

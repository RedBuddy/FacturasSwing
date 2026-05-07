package com.orlando.facturas.ui.frames;

import com.orlando.facturas.model.Iva;
import com.orlando.facturas.service.IvaService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IvaFrame extends JFrame {
    private IvaService ivaService;
    private JTable tabla;
    private IvaTableModel modeloTabla;
    private JTextField txtCodigo, txtDescripcion, txtPorcentaje;

    public IvaFrame(IvaService ivaService) {
        this.ivaService = ivaService;
        inicializar();
    }

    private void inicializar() {
        this.setTitle("Gestionar IVA");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        JPanel panelFormulario = crearPanelFormulario();
        modeloTabla = new IvaTableModel();
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarEnFormulario());
        JScrollPane scrollPane = new JScrollPane(tabla);
        JPanel panelBotones = crearPanelBotones();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFormulario, scrollPane);
        splitPane.setDividerLocation(120);
        this.add(splitPane);
        this.add(panelBotones, BorderLayout.SOUTH);
        cargarIvas();
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
        panel.add(new JLabel("Porcentaje:"));
        txtPorcentaje = new JTextField();
        panel.add(txtPorcentaje);
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

    private void cargarIvas() {
        modeloTabla.setIvas(ivaService.obtenerTodos());
    }

    private void cargarEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Iva iva = modeloTabla.getIva(fila);
            txtCodigo.setText(iva.getCodigo());
            txtDescripcion.setText(iva.getDescripcion());
            txtPorcentaje.setText(String.valueOf(iva.getPorcentaje()));
        }
    }

    private void guardar() {
        if (txtCodigo.getText().trim().isEmpty() || txtDescripcion.getText().trim().isEmpty() || txtPorcentaje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son requeridos");
            return;
        }
        try {
            Iva iva = new Iva();
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                iva.setCodigo(modeloTabla.getIva(fila).getCodigo());
            }
            iva.setCodigo(txtCodigo.getText());
            iva.setDescripcion(txtDescripcion.getText());
            iva.setPorcentaje(Double.parseDouble(txtPorcentaje.getText()));
            ivaService.guardar(iva);
            cargarIvas();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "IVA guardado exitosamente");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El porcentaje debe ser numerico");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un IVA");
            return;
        }
        Iva iva = modeloTabla.getIva(fila);
        ivaService.eliminar(iva.getCodigo());
        cargarIvas();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "IVA eliminado exitosamente");
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPorcentaje.setText("");
        tabla.clearSelection();
    }

    private class IvaTableModel extends AbstractTableModel {
        private List<Iva> ivas = new ArrayList<>();
        private String[] columnas = {"Codigo", "Descripcion", "Porcentaje"};
        @Override
        public int getRowCount() {
            return ivas.size();
        }
        @Override
        public int getColumnCount() {
            return columnas.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Iva i = ivas.get(rowIndex);
            switch (columnIndex) {
                case 0: return i.getCodigo();
                case 1: return i.getDescripcion();
                case 2: return String.format("%.2f%%", i.getPorcentaje());
                default: return "";
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }
        public void setIvas(List<Iva> ivas) {
            this.ivas = ivas;
            fireTableDataChanged();
        }
        public Iva getIva(int fila) {
            return ivas.get(fila);
        }
    }
}

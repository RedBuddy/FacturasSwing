package com.orlando.facturas.ui.frames;

import com.orlando.facturas.model.Cliente;
import com.orlando.facturas.service.ClienteService;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteFrame extends JFrame {
    private ClienteService clienteService;
    private JTable tabla;
    private ClienteTableModel modeloTabla;
    private JTextField txtCodigo, txtNombre, txtApellidos;

    public ClienteFrame(ClienteService clienteService) {
        this.clienteService = clienteService;
        inicializar();
    }

    private void inicializar() {
        this.setTitle("Gestionar Clientes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        JPanel panelFormulario = crearPanelFormulario();
        modeloTabla = new ClienteTableModel();
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarEnFormulario());
        JScrollPane scrollPane = new JScrollPane(tabla);
        JPanel panelBotones = crearPanelBotones();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFormulario, scrollPane);
        splitPane.setDividerLocation(150);
        this.add(splitPane);
        this.add(panelBotones, BorderLayout.SOUTH);
        cargarClientes();
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Código:"));
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

    private void cargarClientes() {
        modeloTabla.setClientes(clienteService.obtenerTodos());
    }

    private void cargarEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Cliente cliente = modeloTabla.getCliente(fila);
            txtCodigo.setText(cliente.getCodigo());
            txtNombre.setText(cliente.getNombre());
            txtApellidos.setText(cliente.getApellidos());
        }
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido");
            return;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son requeridos");
            return;
        }
        Cliente cliente = new Cliente();
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            cliente.setCodigo(modeloTabla.getCliente(fila).getCodigo());
        } else if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código es requerido para nuevos clientes");
            return;
        } else {
            cliente.setCodigo(txtCodigo.getText());
        }
        cliente.setNombre(txtNombre.getText());
        cliente.setApellidos(txtApellidos.getText());
        clienteService.guardar(cliente);
        cargarClientes();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente");
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        Cliente cliente = modeloTabla.getCliente(fila);
        clienteService.eliminar(cliente.getCodigo());
        cargarClientes();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        tabla.clearSelection();
    }

    private class ClienteTableModel extends AbstractTableModel {
        private List<Cliente> clientes = new ArrayList<>();
        private String[] columnas = {"Código", "Nombre", "Apellidos"};
        @Override
        public int getRowCount() {
            return clientes.size();
        }
        @Override
        public int getColumnCount() {
            return columnas.length;
        }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Cliente c = clientes.get(rowIndex);
            switch (columnIndex) {
                case 0: return c.getCodigo();
                case 1: return c.getNombre();
                case 2: return c.getApellidos();
                default: return "";
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }
        public void setClientes(List<Cliente> clientes) {
            this.clientes = clientes;
            fireTableDataChanged();
        }
        public Cliente getCliente(int fila) {
            return clientes.get(fila);
        }
    }
}

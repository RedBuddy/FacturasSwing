package com.orlando.facturas.ui.frames;

import com.orlando.facturas.model.*;
import com.orlando.facturas.service.*;
import com.orlando.facturas.ui.components.DetalleFacturaTable;
import com.orlando.facturas.ui.dialogs.BuscarArticuloDialog;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FacturaFrame extends JFrame {
    private ClienteService clienteService;
    private ArticuloService articuloService;
    private VendedorService vendedorService;
    private IvaService ivaService;
    private FacturaService facturaService;
    private JLabel labelClienteInfo;
    private DetalleFacturaTable tablaDetalles;
    private Cliente clienteSeleccionado;
    private Factura facturaActual;
    private JTextField txtFolio, txtCantidad;
    private JComboBox<String> comboVendedor;
    private JComboBox<String> comboIva;

    public FacturaFrame(ClienteService clienteService, ArticuloService articuloService,
                        VendedorService vendedorService, IvaService ivaService, FacturaService facturaService) {
        this.clienteService = clienteService;
        this.articuloService = articuloService;
        this.vendedorService = vendedorService;
        this.ivaService = ivaService;
        this.facturaService = facturaService;
        inicializar();
    }

    private void inicializar() {
        this.setTitle("Crear Factura");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(crearPanelPrincipal(), BorderLayout.CENTER);
        this.add(crearPanelBotones(), BorderLayout.SOUTH);
        nuevoFactura();
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelNorte = crearPanelNorte();
        
        // Panel simplificado para mostrar cliente seleccionado
        JPanel panelCliente = new JPanel(new BorderLayout());
        panelCliente.setBorder(BorderFactory.createTitledBorder("Cliente Seleccionado"));
        labelClienteInfo = new JLabel("Ningún cliente seleccionado");
        panelCliente.add(labelClienteInfo, BorderLayout.CENTER);
        
        tablaDetalles = new DetalleFacturaTable();
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelCliente, panelCentro);
        splitPane.setDividerLocation(100);
        
        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelNorte() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Folio:"));
        txtFolio = new JTextField();
        txtFolio.setEditable(false);
        panel.add(txtFolio);
        
        panel.add(new JLabel("Fecha:"));
        JLabel labelFecha = new JLabel(LocalDate.now().toString());
        panel.add(labelFecha);
        
        panel.add(new JLabel("Vendedor:"));
        comboVendedor = new JComboBox<>();
        cargarVendedores();
        panel.add(comboVendedor);
        
        panel.add(new JLabel("Cliente:"));
        JButton btnBuscarCliente = new JButton("Buscar Cliente");
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        panel.add(btnBuscarCliente);
        
        panel.add(new JLabel("IVA:"));
        comboIva = new JComboBox<>();
        cargarIvas();
        panel.add(comboIva);
        
        panel.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panel.add(txtCantidad);
        
        JButton btnAgregarDetalle = new JButton("Agregar Articulo");
        btnAgregarDetalle.addActionListener(e -> agregarDetalle());
        panel.add(btnAgregarDetalle);
        
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar Factura");
        btnGuardar.addActionListener(e -> guardarFactura());
        panel.add(btnGuardar);
        JButton btnNueva = new JButton("Nueva Factura");
        btnNueva.addActionListener(e -> nuevoFactura());
        panel.add(btnNueva);
        JButton btnEliminarDetalle = new JButton("Eliminar Detalle");
        btnEliminarDetalle.addActionListener(e -> eliminarDetalle());
        panel.add(btnEliminarDetalle);
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> this.dispose());
        panel.add(btnCerrar);
        return panel;
    }

    private void cargarVendedores() {
        comboVendedor.removeAllItems();
        for (Vendedor v : vendedorService.obtenerTodos()) {
            comboVendedor.addItem(v.getCodigo() + " - " + v.getNombre() + " " + v.getApellidos());
        }
    }

    private void cargarIvas() {
        comboIva.removeAllItems();
        for (Iva i : ivaService.obtenerTodos()) {
            comboIva.addItem(i.getCodigo() + " - " + i.getDescripcion() + " (" + i.getPorcentaje() + "%)");
        }
    }

    private void buscarCliente() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del cliente:");
        if (codigo != null && !codigo.isEmpty()) {
            List<Cliente> resultados = clienteService.buscar(codigo);
            if (!resultados.isEmpty()) {
                clienteSeleccionado = resultados.get(0);
                labelClienteInfo.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + 
                                         " (Código: " + clienteSeleccionado.getCodigo() + ")");
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        }
    }

    private void agregarDetalle() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero");
            return;
        }
        BuscarArticuloDialog dialogo = new BuscarArticuloDialog(this, articuloService);
        dialogo.setVisible(true);
        Articulo articulo = dialogo.obtenerSeleccionado();
        if (articulo != null) {
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                    return;
                }
                DetalleFactura detalle = new DetalleFactura();
                detalle.setArticuloCodigo(articulo.getCodigo());
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(articulo.getPrecioUnitario());
                tablaDetalles.agregarDetalle(detalle);
                txtCantidad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser numerica");
            }
        }
    }

    private void eliminarDetalle() {
        int fila = tablaDetalles.getSelectedRow();
        if (fila >= 0) {
            tablaDetalles.eliminarDetalle(fila);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un detalle para eliminar");
        }
    }

    private void guardarFactura() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        if (tablaDetalles.obtenerDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue detalles a la factura");
            return;
        }
        facturaActual.setClienteCodigo(clienteSeleccionado.getCodigo());
        
        // Extraer codigo vendedor de la selección del combo
        String vendedorSeleccionado = (String) comboVendedor.getSelectedItem();
        String vendedorCodigo = vendedorSeleccionado.split(" - ")[0];
        facturaActual.setVendedorCodigo(vendedorCodigo);
        
        // Extraer codigo IVA de la selección del combo
        String ivaSeleccionado = (String) comboIva.getSelectedItem();
        String ivaCodigo = ivaSeleccionado.split(" - ")[0];
        facturaActual.setIvaCodigo(ivaCodigo);
        
        // Set detalles con folio de factura
        List<DetalleFactura> detalles = tablaDetalles.obtenerDetalles();
        for (DetalleFactura d : detalles) {
            d.setFacturaFolio(facturaActual.getFolio());
        }
        
        facturaService.guardar(facturaActual);
        JOptionPane.showMessageDialog(this, "Factura guardada exitosamente");
        nuevoFactura();
    }

    private void nuevoFactura() {
        facturaActual = new Factura();
        facturaActual.setFolio("F" + System.currentTimeMillis());
        facturaActual.setFecha(LocalDate.now());
        txtFolio.setText(facturaActual.getFolio());
        clienteSeleccionado = null;
        labelClienteInfo.setText("Ningún cliente seleccionado");
        tablaDetalles.limpiar();
        txtCantidad.setText("");
    }
}

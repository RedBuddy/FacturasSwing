package com.orlando.facturas.ui.frames;

import com.orlando.facturas.service.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private ClienteService clienteService;
    private ArticuloService articuloService;
    private VendedorService vendedorService;
    private IvaService ivaService;
    private FacturaService facturaService;

    public MainFrame() {
        inicializarServicios();
        inicializarUI();
    }

    private void inicializarServicios() {
        clienteService = new ClienteService();
        articuloService = new ArticuloService();
        vendedorService = new VendedorService();
        ivaService = new IvaService();
        facturaService = new FacturaService();
    }

    private void inicializarUI() {
        this.setTitle("Sistema de Facturas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        JMenuBar menuBar = crearMenuBar();
        this.setJMenuBar(menuBar);

        JPanel panelPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JButton btnClientes = crearBoton("Gestionar Clientes", e -> abrirFrameClientes());
        JButton btnArticulos = crearBoton("Gestionar Articulos", e -> abrirFrameArticulos());
        JButton btnVendedores = crearBoton("Gestionar Vendedores", e -> abrirFrameVendedores());
        JButton btnIva = crearBoton("Gestionar IVA", e -> abrirFrameIva());
        JButton btnFacturas = crearBoton("Gestionar Facturas", e -> abrirFrameFacturas());

        panelPrincipal.add(btnClientes);
        panelPrincipal.add(btnArticulos);
        panelPrincipal.add(btnVendedores);
        panelPrincipal.add(btnIva);
        panelPrincipal.add(btnFacturas);

        this.add(panelPrincipal);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        return menuBar;
    }

    private JButton crearBoton(String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.addActionListener(listener);
        return boton;
    }

    private void abrirFrameClientes() {
        new ClienteFrame(clienteService).setVisible(true);
    }

    private void abrirFrameArticulos() {
        new ArticuloFrame(articuloService).setVisible(true);
    }

    private void abrirFrameVendedores() {
        new VendedorFrame(vendedorService).setVisible(true);
    }

    private void abrirFrameIva() {
        new IvaFrame(ivaService).setVisible(true);
    }

    private void abrirFrameFacturas() {
        new FacturaFrame(clienteService, articuloService, vendedorService, ivaService, facturaService).setVisible(true);
    }
}

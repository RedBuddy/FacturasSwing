/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.ui.components;

import com.orlando.facturas.model.Cliente;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Orlando
 */
public class ClientePanel extends JPanel {
    private JLabel labelCodigo;
    private JLabel labelNombre;
    private JLabel labelApellidos;

    public ClientePanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder("Informacion del Cliente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Codigo
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Codigo:"), gbc);
        gbc.gridx = 1;
        labelCodigo = new JLabel("");
        this.add(labelCodigo, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        labelNombre = new JLabel("");
        this.add(labelNombre, gbc);

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        labelApellidos = new JLabel("");
        this.add(labelApellidos, gbc);
    }

    public void mostrarCliente(Cliente cliente) {
        if (cliente != null) {
            labelCodigo.setText(cliente.getCodigo());
            labelNombre.setText(cliente.getNombre());
            labelApellidos.setText(cliente.getApellidos());
        } else {
            limpiar();
        }
    }

    public void limpiar() {
        labelCodigo.setText("");
        labelNombre.setText("");
        labelApellidos.setText("");
    }
}

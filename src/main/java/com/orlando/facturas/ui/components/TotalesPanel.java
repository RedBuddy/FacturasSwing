/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.orlando.facturas.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Orlando
 */
public class TotalesPanel extends JPanel {
    private JLabel labelSubtotal;
    private JLabel labelImpuesto;
    private JLabel labelTotal;

    public TotalesPanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder("Totales"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Subtotal
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label1 = new JLabel("Subtotal:");
        label1.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(label1, gbc);
        gbc.gridx = 1;
        labelSubtotal = new JLabel("$0.00");
        labelSubtotal.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(labelSubtotal, gbc);

        // Impuesto
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label2 = new JLabel("Impuesto:");
        label2.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(label2, gbc);
        gbc.gridx = 1;
        labelImpuesto = new JLabel("$0.00");
        labelImpuesto.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(labelImpuesto, gbc);

        // Total
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel label3 = new JLabel("Total:");
        label3.setFont(new Font("Arial", Font.BOLD, 14));
        label3.setForeground(Color.BLUE);
        this.add(label3, gbc);
        gbc.gridx = 1;
        labelTotal = new JLabel("$0.00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotal.setForeground(Color.BLUE);
        this.add(labelTotal, gbc);
    }

    public void actualizarTotales(double subtotal, double impuesto, double total) {
        labelSubtotal.setText(String.format("$%.2f", subtotal));
        labelImpuesto.setText(String.format("$%.2f", impuesto));
        labelTotal.setText(String.format("$%.2f", total));
    }

    public void limpiar() {
        actualizarTotales(0, 0, 0);
    }
}

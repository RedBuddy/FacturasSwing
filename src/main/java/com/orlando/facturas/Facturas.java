/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.orlando.facturas;

import com.orlando.facturas.ui.frames.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Orlando
 */
public class Facturas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

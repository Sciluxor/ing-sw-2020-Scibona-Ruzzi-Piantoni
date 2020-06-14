package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe che estende MouseAdapter che colora il bordo del bottone, in cui si passa sopra col mouse, di giallo
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class ColorBorderGodCards extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton c = (JButton)e.getSource();
        c.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
        c.setBorderPainted(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton c = (JButton)e.getSource();
        c.setBorderPainted(false);
    }
}

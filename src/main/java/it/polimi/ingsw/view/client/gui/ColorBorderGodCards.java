package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

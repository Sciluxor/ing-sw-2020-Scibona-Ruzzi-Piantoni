package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that extends MouseAdapter which colors the edge of the button, in which you pass over it with the mouse, in yellow
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

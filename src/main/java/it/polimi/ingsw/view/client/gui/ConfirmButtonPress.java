package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static it.polimi.ingsw.view.client.gui.Gui.lconfirm;
import static it.polimi.ingsw.view.client.gui.Gui.lconfirmPress;

public class ConfirmButtonPress extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        JButton c = (JButton)e.getSource();
        c.setIcon(lconfirmPress.getIcon());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton c = (JButton)e.getSource();
        c.setIcon(lconfirm.getIcon());
    }
}

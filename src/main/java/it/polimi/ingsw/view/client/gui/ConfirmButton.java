package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import static it.polimi.ingsw.view.client.gui.Gui.getD;
import static it.polimi.ingsw.view.client.gui.Gui.lconfirm;

public class ConfirmButton extends JButton{

    public ConfirmButton() {
        setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setIcon(lconfirm.getIcon());
        addMouseListener(new ConfirmButtonPress());
    }
}

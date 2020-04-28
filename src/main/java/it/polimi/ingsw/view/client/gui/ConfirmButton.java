package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.getD;
import static it.polimi.ingsw.view.client.gui.Gui.lconfirm;

public class ConfirmButton{

    public static JButton confirmButton() {
        JButton confirm = new JButton();
        confirm.setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        confirm.setOpaque(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setIcon(lconfirm.getIcon());
        //confirm.setVisible(true);
        confirm.addMouseListener(new ConfirmButtonPress());
        return confirm;
    }
}

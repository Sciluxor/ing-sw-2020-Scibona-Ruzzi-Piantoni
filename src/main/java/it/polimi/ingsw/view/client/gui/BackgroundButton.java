package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import static it.polimi.ingsw.view.client.gui.Gui.backgroundPanel;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class BackgroundButton {

    public static JButton backgroundButton(){
        JButton back = new JButton();
        back.setIcon(backgroundPanel.getIcon());
        back.setBounds(0, 0, getD().width, getD().height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }
}

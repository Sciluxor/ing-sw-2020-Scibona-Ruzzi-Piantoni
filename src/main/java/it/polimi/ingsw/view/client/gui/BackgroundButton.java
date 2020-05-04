package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class BackgroundButton {

    public static JButton backgroundButton(int n){
        JButton back = new JButton();
        switch (n){
            case 0:
                back.setIcon(backgroundPanel.getIcon());
                break;
            case 1:
                back.setIcon(internalBackgroundPanel.getIcon());
                break;
            default:
        }

        back.setBounds(0, 0, getD().width, getD().height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }
}

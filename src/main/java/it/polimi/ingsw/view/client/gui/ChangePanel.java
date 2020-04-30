package it.polimi.ingsw.view.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.polimi.ingsw.view.client.gui.Gui.changePanel;

public class ChangePanel implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        changePanel();
    }
}

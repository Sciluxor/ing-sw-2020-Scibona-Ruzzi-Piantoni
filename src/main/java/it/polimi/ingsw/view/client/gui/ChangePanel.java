package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChangePanel implements ActionListener {
    Gui gui;
    public ChangePanel(Gui istance){
        gui = istance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> gui.changePanel());
    }
}

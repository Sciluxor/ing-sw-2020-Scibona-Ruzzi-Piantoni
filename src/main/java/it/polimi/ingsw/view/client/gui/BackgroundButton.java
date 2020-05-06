package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class BackgroundButton {

    static JLabel cover;
    static JLabel cover2;
    static {
        try {
            cover = ImageHandler.setImage("resources/Graphics/background_panels.png", 100, 100, d.width, d.height);
            cover2 = ImageHandler.setImage("resources/Graphics/background.png", 100, 100, d.width, d.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
    static JLabel backgroundPanel = new JLabel(cover.getIcon());
    static JLabel internalBackgroundPanel = new JLabel(cover2.getIcon());

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


        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }
}

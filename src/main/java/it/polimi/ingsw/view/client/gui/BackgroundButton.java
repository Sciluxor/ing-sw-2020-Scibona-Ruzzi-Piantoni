package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.network.client.ClientGameController;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class BackgroundButton {

    static JLabel cover;
    static JLabel cover2;
    static JLabel cover3;
    static {
        try {
            cover = ImageHandler.setImage("resources/Graphics/background_panels.png", 100, 100, d.width, d.height);
            cover2 = ImageHandler.setImage("resources/Graphics/background.png", 100, 100, d.width, d.height);
            cover3 = ImageHandler.setImage("resources/Graphics/background2.png", 100, 100, d.width, d.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
    static JLabel backgroundPanel = new JLabel(cover.getIcon());
    static JLabel internalBackgroundPanel = new JLabel(cover2.getIcon());
    static JLabel internalBackgroundPanel2 = new JLabel(cover3.getIcon());

    public static JButton backgroundButton(int n){
        JButton back = new JButton();
        switch (n){
            case 0:
                back.setIcon(backgroundPanel.getIcon());
                break;
            case 1:
                back.setIcon(internalBackgroundPanel.getIcon());
                break;
            case 2:
                back.setIcon(internalBackgroundPanel2.getIcon());
                break;
            default:
        }

        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }

    public static JButton backgroundButtonPersonalized(int n, Dimension frame){
        JButton back = new JButton();
        JLabel cover11 = new JLabel();
        JLabel backgroundPanel;

        switch (n){
            case 0:
                try {
                   cover11 = ImageHandler.setImage("resources/Graphics/background_panels.png", 100, 100, frame.width, frame.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                break;
            case 1:
                try {
                    cover11 = ImageHandler.setImage("resources/Graphics/background.png", 100, 100, frame.width, frame.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                break;
            case 2:
                try {
                    cover11 = ImageHandler.setImage("resources/Graphics/background2.png", 100, 100, frame.width, frame.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                break;

            case 3:
                try {
                    cover11 = ImageHandler.setImage("resources/Graphics/panel_buildings.png", 100, 100, frame.width, frame.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                break;
            default:
        }
        backgroundPanel = new JLabel(cover11.getIcon());
        back.setIcon(backgroundPanel.getIcon());
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }
}

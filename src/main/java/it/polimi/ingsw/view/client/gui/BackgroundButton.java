package it.polimi.ingsw.view.client.gui;


import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class for the creation of the background for the JDesktopPanes
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class BackgroundButton {

    private static final Dimension d = getD();
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

    /**
     * Private class builder
     */
    private BackgroundButton() {
        throw new IllegalStateException("BackgroundButton class cannot be instantiated");
    }

    /**
     * Method for the construction of the chosen background
     * @param n parameter for the choice of the background
     * @return background as JButton
     */

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

    /**
     * Method for the construction of the chosen background by specifying the size
     * @param n parameter for the choice of the background
     * @param frame window size to size the background
     * @return background as JButton
     */

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

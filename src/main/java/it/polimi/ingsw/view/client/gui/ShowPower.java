package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

/**
 * Class that extends MouseAdapter to show the description of the card as the mouse cursor moves over it
 */

public class ShowPower extends MouseAdapter {
    JInternalFrame intFrame;
    Dimension frameSize;
    Dimension intFrameSize;
    JButton buttonBackground;
    JLabel cover;
    JLabel label;
    double y;

    public ShowPower(JInternalFrame frame, Dimension size, Dimension intSize, JButton button, JLabel cov, JLabel lab, boolean center){
        intFrame = frame;
        frameSize = size;
        intFrameSize = intSize;
        buttonBackground = button;
        cover = cov;
        label = lab;
        if (center){
            y = 19.5;
        }
        else
            y = 8.5;
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        JButton c = (JButton)e.getSource();
        if (c.getX() < frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100) {
            intFrame.setBounds(((frameSize.width * 9 / 100) + c.getX()), (int) (frameSize.height * y / 100), intFrameSize.width, intFrameSize.height);
        }
        else if (c.getX() >= frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100){
            intFrame.setBounds((c.getX() - (frameSize.width * 48 / 100)), (int) (frameSize.height * y / 100), intFrameSize.width, intFrameSize.height);
        }
        else if (c.getX() < frameSize.width * 50/100 && c.getY() >= frameSize.height * 40/100){
            intFrame.setBounds(((frameSize.width * 9 / 100) + c.getX()), (frameSize.height * 33 / 100), intFrameSize.width, intFrameSize.height);
        }
        else
            intFrame.setBounds((c.getX() - (frameSize.width * 48 / 100)), (frameSize.height * 33 / 100), intFrameSize.width, intFrameSize.height);

        buttonBackground.setIcon(null);
        try {
            cover = ImageHandler.setImage("resources/Graphics/gods/" + c.getName() + "_description.png", 100, 100, intFrame.getWidth() , intFrame.getHeight() );
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        label.setIcon(cover.getIcon());
        buttonBackground.setIcon(label.getIcon());
        intFrame.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
            intFrame.setVisible(false);
        }

}

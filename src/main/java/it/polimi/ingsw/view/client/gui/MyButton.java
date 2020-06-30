package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class that extends JButton for the build of the buttons
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class MyButton extends JButton{

    int type;
    Dimension labelSize = new Dimension(d.width * 13/100, d.height * 5/100);
    JLabel lConfirm;
    JLabel lConfirmPress;
    JLabel lBack;
    JLabel lBackPress;
    JLabel lNewGame;
    JLabel lNewGamePress;
    JLabel lClose;
    JLabel lClosePress;
    JLabel lKeep;
    JLabel lKeepPress;
    JLabel lContinue;
    JLabel lContinuePress;
    transient MP3 click = new MP3(MUSIC + "Click.mp3");

    /**
     * Public constructor
     * @param n Parameter to decide which button you want
     */

    public MyButton(int n) {
        type = n;

        try {
            lConfirm = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_confirm.png", 100, 100, labelSize.width, labelSize.height);
            lConfirmPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_confirm_press.png", 100, 100, labelSize.width, labelSize.height);
            lBack = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_back.png", 100, 100, labelSize.width, labelSize.height);
            lBackPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_back_press.png", 100, 100, labelSize.width, labelSize.height);
            lNewGame = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_newgame.png", 100, 100, labelSize.width, labelSize.height);
            lNewGamePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_newgame_press.png", 100, 100, labelSize.width, labelSize.height);
            lClose = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_close.png", 100, 100, labelSize.width, labelSize.height);
            lClosePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_close_press.png", 100, 100, labelSize.width, labelSize.height);
            lKeep = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_keep.png", 100, 100, labelSize.width, labelSize.height);
            lKeepPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_keep_press.png", 100, 100, labelSize.width, labelSize.height);
            lContinue = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_continue.png", 100, 100, labelSize.width, labelSize.height);
            lContinuePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_continue_press.png", 100, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 79.5 / 100), labelSize.width, labelSize.height);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        addMouseListener(new ButtonPress());
        switch (type){
            case 0:
                setIcon(lConfirm.getIcon());
                break;

            case 1:
                setIcon(lBack.getIcon());
                break;

            case 2:
                setIcon(lNewGame.getIcon());
                break;

            case 3:
                setIcon(lClose.getIcon());
                break;

            case 4:
                setIcon(lKeep.getIcon());
                break;

            case 5:
                setIcon(lContinue.getIcon());
                break;

            default:
        }
    }

    /**
     * Class that extends MouseAdapter to animate the button click
     */

    private class ButtonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            click.play();
            JButton c = (JButton)e.getSource();
            switch (type){
                case 0:
                    c.setIcon(lConfirmPress.getIcon());
                    break;

                case 1:
                    c.setIcon(lBackPress.getIcon());
                    break;

                case 2:
                    c.setIcon(lNewGamePress.getIcon());
                    break;

                case 3:
                    c.setIcon(lClosePress.getIcon());
                    break;

                case 4:
                    setIcon(lKeepPress.getIcon());
                    break;

                case 5:
                    setIcon(lContinuePress.getIcon());
                    break;

                default:
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            switch (type){
                case 0:
                    c.setIcon(lConfirm.getIcon());
                    break;

                case 1:
                    c.setIcon(lBack.getIcon());
                    break;

                case 2:
                    c.setIcon(lNewGame.getIcon());
                    break;

                case 3:
                    c.setIcon(lClose.getIcon());
                    break;

                case 4:
                    setIcon(lKeep.getIcon());
                    break;

                case 5:
                    setIcon(lContinue.getIcon());
                    break;

                default:
            }
        }
    }
}

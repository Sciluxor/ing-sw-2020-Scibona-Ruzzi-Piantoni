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
    JLabel lconfirm;
    JLabel lconfirmPress;
    JLabel lback;
    JLabel lbackPress;
    JLabel lnewGame;
    JLabel lnewGamePress;
    JLabel lclose;
    JLabel lclosePress;
    JLabel lkeep;
    JLabel lkeepPress;
    JLabel lcontinue;
    JLabel lcontinuePress;
    transient MP3 click = new MP3(MUSIC + "Click.mp3");

    /**
     * Public constructor
     * @param n Parameter to decide which button you want
     */

    public MyButton(int n) {
        type = n;

        try {
            lconfirm = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_confirm.png", 100, 100, labelSize.width, labelSize.height);
            lconfirmPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_confirm_press.png", 100, 100, labelSize.width, labelSize.height);
            lback = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_back.png", 100, 100, labelSize.width, labelSize.height);
            lbackPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_back_press.png", 100, 100, labelSize.width, labelSize.height);
            lnewGame = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_newgame.png", 100, 100, labelSize.width, labelSize.height);
            lnewGamePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_newgame_press.png", 100, 100, labelSize.width, labelSize.height);
            lclose = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_close.png", 100, 100, labelSize.width, labelSize.height);
            lclosePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_close_press.png", 100, 100, labelSize.width, labelSize.height);
            lkeep = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_keep.png", 100, 100, labelSize.width, labelSize.height);
            lkeepPress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_keep_press.png", 100, 100, labelSize.width, labelSize.height);
            lcontinue = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_continue.png", 100, 100, labelSize.width, labelSize.height);
            lcontinuePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_continue_press.png", 100, 100, labelSize.width, labelSize.height);
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
                setIcon(lconfirm.getIcon());
                break;

            case 1:
                setIcon(lback.getIcon());
                break;

            case 2:
                setIcon(lnewGame.getIcon());
                break;

            case 3:
                setIcon(lclose.getIcon());
                break;

            case 4:
                setIcon(lkeep.getIcon());
                break;

            case 5:
                setIcon(lcontinue.getIcon());
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
                    c.setIcon(lconfirmPress.getIcon());
                    break;

                case 1:
                    c.setIcon(lbackPress.getIcon());
                    break;

                case 2:
                    c.setIcon(lnewGamePress.getIcon());
                    break;

                case 3:
                    c.setIcon(lclosePress.getIcon());
                    break;

                case 4:
                    setIcon(lkeepPress.getIcon());
                    break;

                case 5:
                    setIcon(lcontinuePress.getIcon());
                    break;

                default:
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            switch (type){
                case 0:
                    c.setIcon(lconfirm.getIcon());
                    break;

                case 1:
                    c.setIcon(lback.getIcon());
                    break;

                case 2:
                    c.setIcon(lnewGame.getIcon());
                    break;

                case 3:
                    c.setIcon(lclose.getIcon());
                    break;

                case 4:
                    setIcon(lkeep.getIcon());
                    break;

                case 5:
                    setIcon(lcontinue.getIcon());
                    break;

                default:
            }
        }
    }
}

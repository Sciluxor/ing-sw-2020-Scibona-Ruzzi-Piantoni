package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class MyButton extends JButton{

    int type = 0;
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
    {
        try {
            lconfirm = ImageHandler.setImage("resources/Graphics/button_confirm.png", 100, 100, labelSize.width, labelSize.height);
            lconfirmPress = ImageHandler.setImage("resources/Graphics/button_confirm_press.png", 100, 100, labelSize.width, labelSize.height);
            lback = ImageHandler.setImage("resources/Graphics/button_back.png", 100, 100, labelSize.width, labelSize.height);
            lbackPress = ImageHandler.setImage("resources/Graphics/button_back_press.png", 100, 100, labelSize.width, labelSize.height);
            lnewGame = ImageHandler.setImage("resources/Graphics/button_newgame.png", 100, 100, labelSize.width, labelSize.height);
            lnewGamePress = ImageHandler.setImage("resources/Graphics/button_newgame_press.png", 100, 100, labelSize.width, labelSize.height);
            lclose = ImageHandler.setImage("resources/Graphics/button_close.png", 100, 100, labelSize.width, labelSize.height);
            lclosePress = ImageHandler.setImage("resources/Graphics/button_close_press.png", 100, 100, labelSize.width, labelSize.height);
            lkeep = ImageHandler.setImage("resources/Graphics/button_keep.png", 100, 100, labelSize.width, labelSize.height);
            lkeepPress = ImageHandler.setImage("resources/Graphics/button_keep_press.png", 100, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
    MP3 click = new MP3("resources/Music/Click.mp3");

    public MyButton(int n) {
        type = n;
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

            default:
        }
    }

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

                default:
            }
        }
    }
}

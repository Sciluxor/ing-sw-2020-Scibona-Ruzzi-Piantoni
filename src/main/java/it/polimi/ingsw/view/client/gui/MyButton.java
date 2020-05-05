package it.polimi.ingsw.view.client.gui;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class MyButton extends JButton{

    int type = 0;
    JLabel lconfirm;
    JLabel lconfirmPress;
    JLabel lback;
    JLabel lbackPress;
    JLabel lnewGame;
    JLabel lnewGamePress;
    JLabel lclose;
    JLabel lclosePress;
    {
        try {
            lconfirm = ImageHandler.setImage("resources/Graphics/button_confirm.png", 100, 100, d.width * 13/100, d.height * 5/100);
            lconfirmPress = ImageHandler.setImage("resources/Graphics/button_confirm_press.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
            lback = ImageHandler.setImage("resources/Graphics/button_back.png", 100, 100, d.width * 13/100, d.height * 5/100);
            lbackPress = ImageHandler.setImage("resources/Graphics/button_back_press.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
            lnewGame = ImageHandler.setImage("resources/Graphics/button_newgame.png", 100, 100, d.width * 13/100, d.height * 5/100);
            lnewGamePress = ImageHandler.setImage("resources/Graphics/button_newgame_press.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
            lclose = ImageHandler.setImage("resources/Graphics/button_close.png", 100, 100, d.width * 13/100, d.height * 5/100);
            lclosePress = ImageHandler.setImage("resources/Graphics/button_close_press.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public MyButton(int n) {
        type = n;
        setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        addMouseListener(new buttonPress());
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
        }
    }

    private class buttonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            switch (type){
                case 0:
                    c.setIcon(lconfirmPress.getIcon());
                    break;

                case 1:
                    c.setIcon(lbackPress.getIcon());
                    break;

                case 2:
                    setIcon(lnewGamePress.getIcon());
                    break;

                case 3:
                    setIcon(lclosePress.getIcon());
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

                default:
            }
        }
    }
}

package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class WaitChallenger extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;


    public WaitChallenger(JInternalFrame frame, int wi, int he, String name) throws IOException {

        frameSize.setSize(wi, he);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/waiting_for_challenger_to_choose_the_god.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 7.5/100), frameSize.height * 39/100, frameSize.width * 85/100, frameSize.height * 22/100);
        add(label);

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }
}

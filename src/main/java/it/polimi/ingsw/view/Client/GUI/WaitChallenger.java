package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WaitChallenger extends JPanel{
    Dimension frameSize = new Dimension();


    public WaitChallenger(Dimension screen, Dimension frame, JLabel background) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton back = new JButton();
        back.setIcon(background.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);

        JLabel label = new JLabel("Waiting for Challenger to choose the Gods");

        label.setBounds(frameSize.width * 50/100 - 125, frameSize.height * 50/100 - 50, 250, 100);
        add(label);
        add(back);
    }
}

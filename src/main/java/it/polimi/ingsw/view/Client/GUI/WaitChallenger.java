package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;

public class WaitChallenger extends JPanel{
    Dimension frameSize = new Dimension();

    public WaitChallenger(Dimension screen, Dimension frame) {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = new JLabel("Waiting for Challenger to choose the Gods");

        label.setBounds(frameSize.width * 50/100 - 125, frameSize.height * 50/100 - 50, 250, 100);
        add(label);
    }
}

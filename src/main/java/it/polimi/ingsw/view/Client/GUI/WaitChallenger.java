package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WaitChallenger extends JPanel{
    Dimension frameSize = new Dimension();


    public WaitChallenger(Dimension screen, Dimension frame) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel cover = ImageHandler.setImage("src/main/resources/Graphics/background_panels.png", 100, 100, frameSize.width, frameSize.height);
        JLabel sfondo = new JLabel(cover.getIcon());
        JButton back = new JButton();
        back.setIcon(sfondo.getIcon());
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

package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;

public class WaitChallenger extends JDesktopPane{

    Dimension frameSize = new Dimension();


    public WaitChallenger(int wi, int he) throws IOException {

        frameSize.setSize(wi, he);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/waiting_for_challenger_to_choose_the_god.png", 100, 100, frameSize.width * 40/100, frameSize.height * 20/100);
        label.setBounds(frameSize.width * 30/100, frameSize.height * 5/100, frameSize.width * 40/100, frameSize.height * 20/100);
        add(label);

        JButton back = backgroundButton(1 );
        add(back);
    }
}

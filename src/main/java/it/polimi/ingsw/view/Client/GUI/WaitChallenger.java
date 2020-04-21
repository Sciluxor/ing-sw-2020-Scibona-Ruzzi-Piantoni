package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static it.polimi.ingsw.view.Client.GUI.Gui.BackgroundButton;

public class WaitChallenger extends JPanel{

    Dimension frameSize = new Dimension();


    public WaitChallenger(Dimension frame) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_for_challenger_to_choose_the_god.png", 100, 100, frameSize.width * 40/100, frameSize.height * 20/100);
        label.setBounds(frameSize.width * 30/100, frameSize.height * 5/100, frameSize.width * 40/100, frameSize.height * 20/100);
        add(label);

        JButton back = BackgroundButton();
        add(back);
    }
}

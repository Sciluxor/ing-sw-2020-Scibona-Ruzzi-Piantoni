package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChallengerChoiseFirstPlayer extends JPanel{
    Dimension frameSize = new Dimension();

    public ChallengerChoiseFirstPlayer(Dimension screen, Dimension frame, Integer numberOfPlayer, ArrayList<String> players) {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = new JLabel("Choose the first player");
        JButton player1 = new JButton(players.get(1));
        JButton player2;

        label.setBounds(frameSize.width * 50/100 - 70, frameSize.height * 35/100 - 50, 140, 100);
        add(label);

        player1.setBounds(frameSize.width * 50/100 - (screen.width * 11/100)/2, frameSize.height * 50/100 - 50, screen.width * 10/100, screen.height * 3/100);
        add(player1);
        player1.addActionListener(new Gui.ChangePanel());

        if (numberOfPlayer == 3){
            player2 = new JButton(players.get(2));
            player2.setBounds(frameSize.width * 50/100 - (screen.width * 11/100)/2, frameSize.height * 55/100 - 50, screen.width * 10/100, screen.height * 3/100);
            add(player2);
            player2.addActionListener(new Gui.ChangePanel());
        }

    }
}

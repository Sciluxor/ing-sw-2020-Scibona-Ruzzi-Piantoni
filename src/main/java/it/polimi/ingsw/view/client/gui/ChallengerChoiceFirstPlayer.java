package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
public class ChallengerChoiceFirstPlayer extends JPanel{

    Gui gui;
    Dimension frameSize = new Dimension();

    public ChallengerChoiceFirstPlayer(Gui istance, Dimension frame, Integer numberOfPlayer, List<Player> players) throws IOException {

        gui = istance;
        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton player1 = new JButton(players.get(0).getNickname());
        JButton player2 = new JButton(players.get(1).getNickname());
        JButton player3;

        JLabel choose = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_the_first_player.png", 100, 100, frameSize.width * 40/100, frameSize.height * 10/100);
        choose.setBounds(frameSize.width * 30/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(choose);

        player1.setBounds(frameSize.width * 35/100, frameSize.height * 38/100, frameSize.width * 30/100, frameSize.height * 5/100);
        add(player1);
        player1.addActionListener(new ChangePanel(gui));

        player2.setBounds(frameSize.width * 35/100, frameSize.height * 45/100, frameSize.width * 30/100, frameSize.height * 5/100);
        add(player2);
        player2.addActionListener(new ChangePanel(gui));

        if (numberOfPlayer == 3){
            player3 = new JButton(players.get(2).getNickname());
            player3.setBounds(frameSize.width * 35/100, frameSize.height * 52/100, frameSize.width * 30/100, frameSize.height * 5/100);
            add(player3);
            player3.addActionListener(new ChangePanel(gui));
        }
        JButton back = backgroundButton(0);
        add(back);

    }
}

package it.polimi.ingsw.view.Client.GUI;

import it.polimi.ingsw.model.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LobbyGui extends JPanel{
    Dimension frameSize = new Dimension();

    public LobbyGui(Dimension screen, Dimension frame, Integer actualPlayer, Integer numberOfPlayer, ArrayList<Player> players, JLabel background) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton back = new JButton();
        back.setIcon(background.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);

        JLabel wait = new JLabel("Waiting others players");
        JLabel number = new JLabel(actualPlayer + " of " + numberOfPlayer);
        JTextField numberPlayers = new JTextField(6);
        JTextArea textArea = new JTextArea();

        wait.setBounds(frameSize.width * 50/100 - 75, frameSize.height * 30/100 - 50, 150, 100);
        add(wait);

        number.setBounds(frameSize.width * 50/100 - 30, frameSize.height * 38/100 - 10, 60, 20);
        add(number);

        textArea.setBounds(frameSize.width*50/100 - 75, frameSize.height*50/100 - 25, 150,50);
        add(textArea);

        for (Player player : players){
            textArea.append(player.getNickname() + "\n");
        }
        add(back);
    }
}

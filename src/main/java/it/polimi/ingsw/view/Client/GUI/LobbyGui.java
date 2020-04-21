package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class LobbyGui extends JPanel{
    Dimension frameSize = new Dimension();

    public LobbyGui(Dimension screen, Dimension frame, Integer actualPlayer, Integer numberOfPlayer, ArrayList<String> players) throws IOException {

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

        for (String player : players){
            textArea.append(player + "\n");
        }
        add(back);
    }
}

package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class ChallengerChoiceFirstPlayer extends JDesktopPane{

    Gui gui;
    Board board;
    Dimension frameSize = new Dimension();
    MyButton backButton = new MyButton(1);
    JInternalFrame intFrame;

    public ChallengerChoiceFirstPlayer(Gui istance, Board istance2, JInternalFrame frame, Dimension dimensionFrame, Integer numberOfPlayer, List<Player> players) throws IOException {

        gui = istance;
        board = istance2;
        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);

        JButton player1 = new JButton(players.get(0).getNickname());
        JButton player2 = new JButton(players.get(1).getNickname());
        JButton player3;

        JLabel choose = ImageHandler.setImage("resources/Graphics/Texts/choose_the_first_player.png", 100, 100, frameSize.width * 40/100, frameSize.height * 10/100);
        choose.setBounds(frameSize.width * 30/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(choose);

        player1.setBounds(frameSize.width * 35/100, frameSize.height * 38/100, frameSize.width * 30/100, frameSize.height * 5/100);
        add(player1);
        player1.addActionListener(new Choose());

        player2.setBounds(frameSize.width * 35/100, frameSize.height * 45/100, frameSize.width * 30/100, frameSize.height * 5/100);
        add(player2);
        player2.addActionListener(new Choose());

        if (numberOfPlayer == 3){
            player3 = new JButton(players.get(2).getNickname());
            player3.setBounds(frameSize.width * 35/100, frameSize.height * 52/100, frameSize.width * 30/100, frameSize.height * 5/100);
            add(player3);
            player3.addActionListener(new Choose());
        }
        backButton.setBounds((int) (frameSize.width * 43.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(backButton);
        backButton.addActionListener(new Close());

        JButton back = backgroundButton(0);
        add(back);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }

    private class Choose implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            board.setFirstPlayer(c.getText());
            intFrame.setVisible(false);
            board.challResponse();
        }
    }
}

package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChallengerChoiceFirstPlayer extends JDesktopPane{

    Gui gui;
    Board board;
    Dimension frameSize = new Dimension();
    Dimension labelSize = new Dimension();
    MyButton backButton = new MyButton(1);
    JInternalFrame intFrame;
    JLabel lblue;
    JLabel lbluePress;
    JLabel lpurple;
    JLabel lpurplePress;
    JLabel lwhite;
    JLabel lwhitePress;

    public ChallengerChoiceFirstPlayer(Gui istance, Board istance2, JInternalFrame frame, Dimension dimensionFrame, Integer numberOfPlayer, List<Player> players) throws IOException {

        gui = istance;
        board = istance2;
        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        labelSize.setSize(frameSize.width * 30/100, frameSize.height * 10/100);
        setPreferredSize(frameSize);
        setLayout(null);

        lblue = ImageHandler.setImage("resources/Graphics/button_blue.png", 100, 100, labelSize.width, labelSize.height);
        lbluePress = ImageHandler.setImage("resources/Graphics/button_blue_press.png", 100, 100, labelSize.width, labelSize.height);
        lpurple = ImageHandler.setImage("resources/Graphics/button_purple.png", 100, 100, labelSize.width, labelSize.height);
        lpurplePress = ImageHandler.setImage("resources/Graphics/button_purple_press.png", 100, 100, labelSize.width, labelSize.height);
        lwhite = ImageHandler.setImage("resources/Graphics/button_white.png", 100, 100, labelSize.width, labelSize.height);
        lwhitePress = ImageHandler.setImage("resources/Graphics/button_white_press.png", 100, 100, labelSize.width, labelSize.height);
        JLabel namePlayer1 = new JLabel(players.get(0).getNickName());
        JLabel namePlayer2 = new JLabel(players.get(1).getNickName());
        JLabel namePlayer3 = null;


        JButton player1 = new JButton();
        player1.setName(players.get(0).getNickName());
        JButton player2 = new JButton();
        player2.setName(players.get(1).getNickName());
        JButton player3 = new JButton();

        addColorButton(player1);
        buttonStyle(player1);
        addColorButton(player2);
        buttonStyle(player2);

        JLabel choose = ImageHandler.setImage("resources/Graphics/Texts/choose_the_first_player.png", 100, 100, frameSize.width * 40/100, frameSize.height * 10/100);
        choose.setBounds(frameSize.width * 30/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(choose);

        namePlayer1.setBounds((int) ((frameSize.width * 40/100)), frameSize.height * 37/100, labelSize.width, labelSize.height);
        namePlayer1.setFont(felixBold);
        add(namePlayer1);
        player1.setBounds(((frameSize.width * 50/100) - (labelSize.width / 2)), frameSize.height * 38/100, labelSize.width, labelSize.height);
        add(player1);
        player1.addActionListener(new Choose());

        namePlayer2.setBounds((int) ((frameSize.width * 40/100)), frameSize.height * 49/100, labelSize.width, labelSize.height);
        namePlayer2.setFont(felixBold);
        add(namePlayer2);
        player2.setBounds(((frameSize.width * 50/100) - (labelSize.width / 2)), frameSize.height * 50/100, labelSize.width, labelSize.height);
        add(player2);
        player2.addActionListener(new Choose());


        if (numberOfPlayer == 3){
            namePlayer3  = new JLabel(players.get(2).getNickName());
            player3.setName(players.get(2).getNickName());
            namePlayer3.setBounds((int) ((frameSize.width * 40/100)), frameSize.height * 61/100, labelSize.width, labelSize.height);
            namePlayer3.setFont(felixBold);
            add(namePlayer3);
            addColorButton(player3);
            buttonStyle(player3);
            player3.setBounds(((frameSize.width * 50/100) - (labelSize.width / 2)), frameSize.height * 62/100, labelSize.width, labelSize.height);
            add(player3);
            player3.addActionListener(new Choose());
        }
        backButton.setBounds((int) (frameSize.width * 43.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(backButton);
        backButton.addActionListener(new Close());

        JButton back = backgroundButton(0);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
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
            board.setFirstPlayer(c.getName());
            intFrame.setVisible(false);
            board.callChallengerResponse();
            board.buttonChooseFirst.setEnabled(false);
        }
    }

    private void addColorButton(JButton buttonPlayer){
        for (Player player : board.allPlayer){
            if (player.getNickName().equalsIgnoreCase(buttonPlayer.getName())){

                if(player.getColor().toString().equalsIgnoreCase("BLUE")){
                    buttonPlayer.setIcon(lblue.getIcon());
                }
                else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
                    buttonPlayer.setIcon(lwhite.getIcon());
                }
                else {
                    buttonPlayer.setIcon(lpurple.getIcon());
                }
            }
        }
    }

    private void buttonStyle(JButton buttonPlayer){
        buttonPlayer.setOpaque(false);
        buttonPlayer.setContentAreaFilled(false);
        buttonPlayer.setFocusPainted(false);
        buttonPlayer.setBorderPainted(false);
        buttonPlayer.addMouseListener(new ButtonPress());
    }

    private class ButtonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton)e.getSource();

            for (Player player : board.allPlayer){
                if (player.getNickName().equalsIgnoreCase(c.getName())){
                    if(player.getColor().toString().equalsIgnoreCase("BLUE")){
                        c.setIcon(lbluePress.getIcon());
                    }
                    else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
                        c.setIcon(lwhitePress.getIcon());
                    }
                    else {
                        c.setIcon(lpurplePress.getIcon());
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();

            for (Player player : board.allPlayer){
                if (player.getNickName().equalsIgnoreCase(c.getName())){
                    if(player.getColor().toString().equalsIgnoreCase("BLUE")){
                        c.setIcon(lblue.getIcon());
                    }
                    else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
                        c.setIcon(lwhite.getIcon());
                    }
                    else {
                        c.setIcon(lpurple.getIcon());
                    }
                }
            }
        }
    }
}

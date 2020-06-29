package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.getBolddimension;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class that extends JDesktopPane for the build of the pane that make the Challenger choose the First Player
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class ChallengerChoiceFirstPlayer extends JDesktopPane{

    transient Gui gui;
    transient Board board;
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
    transient MP3 click;
    double bold = getBolddimension();

    /**
     * Public constructor
     * @param istance Reference to the client GUI class
     * @param istance2 Reference to the Board class instanced by the GUI
     * @param frame Reference to the JInternalFrame where the current JDesktopPane ChallengerChoiceFirstPlayer will be inserted
     * @param dimensionFrame Size of the JInternalFrame
     * @param numberOfPlayer Number of Players in play
     * @param players List of Players in play
     * @throws IOException if the loading of the inscription or the button pictures was not successful
     */

    public ChallengerChoiceFirstPlayer(Gui istance, Board istance2, JInternalFrame frame, Dimension dimensionFrame, Integer numberOfPlayer, List<Player> players) throws IOException {

        gui = istance;
        board = istance2;
        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        labelSize.setSize(frameSize.width * 30/100, frameSize.height * 10/100);
        setPreferredSize(frameSize);
        setLayout(null);
        click = new MP3(MUSIC + "Click.mp3");

        lblue = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_blue.png", 100, 100, labelSize.width, labelSize.height);
        lbluePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_blue_press.png", 100, 100, labelSize.width, labelSize.height);
        lpurple = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_purple.png", 100, 100, labelSize.width, labelSize.height);
        lpurplePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_purple_press.png", 100, 100, labelSize.width, labelSize.height);
        lwhite = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_white.png", 100, 100, labelSize.width, labelSize.height);
        lwhitePress = ImageHandler.setImage(RESOURCES_GRAPHICS + "button_white_press.png", 100, 100, labelSize.width, labelSize.height);
        JLabel namePlayer1 = new JLabel(players.get(0).getNickName());
        JLabel namePlayer2 = new JLabel(players.get(1).getNickName());
        JLabel namePlayer3;


        JButton player1 = new JButton();
        player1.setName(players.get(0).getNickName());
        JButton player2 = new JButton();
        player2.setName(players.get(1).getNickName());
        JButton player3 = new JButton();

        addColorButton(player1);
        buttonStyle(player1);
        addColorButton(player2);
        buttonStyle(player2);

        JLabel choose = ImageHandler.setImage(TEXT + "choose_the_first_player.png", 100, 100, frameSize.width * 40/100, frameSize.height * 10/100);
        choose.setBounds(frameSize.width * 30/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(choose);

        namePlayer1.setBounds((int) (((double)frameSize.width * 49/100) - ((namePlayer1.getText().length() * bold) / 2)), frameSize.height * 37/100, labelSize.width, labelSize.height);
        namePlayer1.setFont(getFelixBold());
        add(namePlayer1);
        player1.setBounds((frameSize.width * 35/100), frameSize.height * 38/100, labelSize.width, labelSize.height);
        add(player1);
        player1.addActionListener(new Choose());

        namePlayer2.setBounds((int) (((double)frameSize.width * 49/100) - ((namePlayer2.getText().length() * bold) / 2)), frameSize.height * 49/100, labelSize.width, labelSize.height);
        namePlayer2.setFont(getFelixBold());
        add(namePlayer2);
        player2.setBounds((frameSize.width * 35/100), frameSize.height * 50/100, labelSize.width, labelSize.height);
        add(player2);
        player2.addActionListener(new Choose());


        if (numberOfPlayer == 3){
            namePlayer3  = new JLabel(players.get(2).getNickName());
            player3.setName(players.get(2).getNickName());
            namePlayer3.setBounds((int) (((double)frameSize.width * 49/100) - ((namePlayer3.getText().length() * bold) / 2)), frameSize.height * 61/100, labelSize.width, labelSize.height);
            namePlayer3.setFont(getFelixBold());
            add(namePlayer3);
            addColorButton(player3);
            buttonStyle(player3);
            player3.setBounds((frameSize.width * 35/100), frameSize.height * 62/100, labelSize.width, labelSize.height);
            add(player3);
            player3.addActionListener(new Choose());
        }
        backButton.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(backButton);
        backButton.addActionListener(new Close());

        JButton back = backgroundButton(0);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    /**
     * Class that implements ActionListener for the JButton Close which closes the current JInternalFrame
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }

    /**
     * Class that implements ActionListener for Players JButtons for the choice of the first Player
     */

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

    /**
     * Method for setting the JButton icon color according to the color of the corresponding Player
     * @param buttonPlayer JButton of the Player
     */

    private void addColorButton(JButton buttonPlayer){
        for (Player player : board.allPlayer){
            if (player.getNickName().equalsIgnoreCase(buttonPlayer.getName())){

                if(player.getColor().equals(Color.BLUE)){
                    buttonPlayer.setIcon(lblue.getIcon());
                }
                else if(player.getColor().equals(Color.WHITE)){
                    buttonPlayer.setIcon(lwhite.getIcon());
                }
                else {
                    buttonPlayer.setIcon(lpurple.getIcon());
                }
            }
        }
    }

    /**
     * Method for setting the JButton of the Player
     * @param buttonPlayer JButton of the Player
     */

    private void buttonStyle(JButton buttonPlayer){
        buttonPlayer.setOpaque(false);
        buttonPlayer.setContentAreaFilled(false);
        buttonPlayer.setFocusPainted(false);
        buttonPlayer.setBorderPainted(false);
        buttonPlayer.addMouseListener(new ButtonPress());
    }

    /**
     * Class that extends MouseAdapter to animate the button click
     */

    private class ButtonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            click.play();
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

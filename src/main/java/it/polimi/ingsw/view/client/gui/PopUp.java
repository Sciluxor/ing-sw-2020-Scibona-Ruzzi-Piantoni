package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.getBolddimension;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class for the build of the JPanel of the various PopUp
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class PopUp {
    Gui gui;
    JPanel window;
    Dimension intFrameSize = new Dimension();
    Dimension labelSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton button1;
    MyButton button2;
    JButton back = backgroundButton(1);
    JTextField nickname;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JPanel panel;
    String name;
    double bold = getBolddimension();

    /**
     * Class builder
     * @param istance2 Reference to the client GUI class
     * @param d Main window size
     * @param stopper Parameter indicating the possible player causing an exception
     */

    public PopUp(Gui istance2, Dimension d, String stopper){

        gui = istance2;
        window = new JPanel();
        intFrameSize.setSize(d.getWidth() * 55/100, d.getHeight() * 60/100);
        labelSize.setSize(intFrameSize.width * 55/100,intFrameSize.height * 20/100);
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        nickname = new JTextField(20);
        panel = new JPanel();
        name = stopper;
    }

    /**
     * Method that build the JPanel based on parameter n
     * @param n Parameter indicating the JPanel to be built according to the situation
     * @return The JPanel Built
     */

    public JPanel lobbyPopUp(int n){
        window.removeAll();
        window.setLayout(null);
        switch (n){
            case 0:
                button1 = new MyButton(0);
                button1.setBounds(((intFrameSize.width * 50/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
                button1.addActionListener(new Send());
                window.add(button1);

                nickname.setBounds((intFrameSize.width * 35/100), (intFrameSize.height * 60/100), intFrameSize.width * 30/100,intFrameSize.height * 4/100);
                nickname.setText("AnotherNic");
                window.add(nickname);

                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/nickname.png", 97, 100, intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((intFrameSize.width * 20/100), (int) (intFrameSize.height * 59.5/100), intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                addLabel1();

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("resources/Graphics/Texts/your_nickname_is_already_used.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel2(10);
                addLabel2();

                label3 = new JLabel();
                try {
                    label3 = ImageHandler.setImage("resources/Graphics/Texts/change_your_nickname.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label3.setBounds(((intFrameSize.width * 50/100) - (labelSize.width / 2)), (intFrameSize.height * 25/100), labelSize.width, labelSize.height);
                addLabel3();

                setBack();
                break;

            case 1:
                setDisconnection();
                button1.addActionListener(new NewGame());

                setBack();
                break;

            case 2:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1(50, 35);
                addLabel1();

                button1 = new MyButton(3);
                button1.setBounds((int) (((double)intFrameSize.width * 50 / 100) -  ((getD().getWidth() * 13 / 100) / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button1.addActionListener(new Close());
                window.add(button1);

                setBack();
                break;

            case 3:
                setDisconnection();
                button1.addActionListener(new NewGameEndGame());

                setBack();
                break;

            case 4:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/has_disconnected.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1NoCenter();
                addLabel1();

                setDisconnectionTime();

                setBack();
                break;

            case 5:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/did_not_play.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1NoCenter();
                addLabel1();

                setDisconnectionTime();

                setBack();
                break;

            case 6:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/time_is_up.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1(50, 35);
                addLabel1();

                setDisconnectionTime();
                window.remove(label2);

                setBack();
                break;

            case 7:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/there_was_an_error.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1(50, 10);
                addLabel1();

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("resources/Graphics/Texts/please_use_an_original.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel2(20);
                addLabel2();

                label3 = new JLabel();
                try {
                    label3 = ImageHandler.setImage("resources/Graphics/Texts/version_of_the_game.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label3.setBounds(((intFrameSize.width * 50/100) - (labelSize.width / 2)), (intFrameSize.height * 30/100), labelSize.width, labelSize.height);
                addLabel3();

                button1 = new MyButton(3);
                button1.setBounds(((intFrameSize.width * 50/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
                button1.addActionListener(new Close());
                window.add(button1);

                setBack();
                break;

            case 8:

                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/there_was_an_error_with.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel1(30, 20);
                addLabel1();

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("resources/Graphics/Texts/start_new_game_or_close.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel2(40);
                addLabel2();

                label3 = new JLabel(name);
                label3.setBounds((intFrameSize.width * 60/100), (intFrameSize.height * 27/100), intFrameSize.width * 60/100, intFrameSize.width * 5/100);
                label3.setFont(getFelixBold());
                addLabel3();

                setButtons();

                button1.addActionListener(new NewGameError());
                button2.addActionListener(new Close());

                setBack();
                break;

            default:
        }
        return window;
    }

    /**
     * Method that set the position of the label1
     * @param x Horizontal position provided
     * @param y Vertical position provided
     */

    private void setLabel1(int x, int y){
        label1.setBounds(((intFrameSize.width * x/100) - (labelSize.width / 2)), (intFrameSize.height * y/100), labelSize.width, labelSize.height);
    }

    /**
     * Method that set the position of the label1 not centered respect of the label length
     */

    private void setLabel1NoCenter(){
        label1.setBounds((intFrameSize.width * 40/100), (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
    }

    /**
     * Method that set the position of the label2
     * @param y Horizontal position provided
     */

    private void setLabel2(int y){
        label2.setBounds(((intFrameSize.width * 50/100) - (labelSize.width / 2)), (intFrameSize.height * y/100), labelSize.width, labelSize.height);
    }

    /**
     * Method that set the background
     */

    private void setBack(){
        back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
        window.add(back);
    }

    /**
     * Method that add label1 to the window
     */

    private void addLabel1(){
        window.add(label1);
    }

    /**
     * Method that add label2 to the window
     */

    private void addLabel2(){
        window.add(label2);
    }

    /**
     * Method that add label3 to the window
     */

    private void addLabel3(){
        window.add(label3);
    }

    /**
     * Method that builds the popup messages for connection loss
     */

    private void setDisconnection(){
        label1 = new JLabel();
        try {
            label1 = ImageHandler.setImage("resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel1(50, 20);
        window.add(label1);

        label2 = new JLabel();
        try {
            label2 = ImageHandler.setImage("resources/Graphics/Texts/start_new_game_or_close.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel2(40);

        setButtonsForDisconnection();

        button2.addActionListener(new Close());
    }

    /**
     * Method that sets the popup messages for unplayed turn
     */

    private void setDisconnectionTime(){
        label2 = new JLabel(name);
        label2.setBounds((int) (((double)intFrameSize.width * 30/100) - (name.length() * bold)), (int) (intFrameSize.height * 41.5/100), intFrameSize.width * 60/100, intFrameSize.width * 5/100);
        label2.setFont(getFelixBold());

        setButtonsForDisconnection();

        button1.addActionListener(new NewGameEndGame());
        button2.addActionListener(new Close());
    }

    /**
     * Method that set buttons for the setDisconnections methods
     */

    private void setButtonsForDisconnection() {
        window.add(label2);
        setButtons();
    }

    /**
     * Method that set the buttons
     */

    private void setButtons(){
        button1 = new MyButton(2);
        button1.setBounds(((intFrameSize.width * 35/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
        button2 = new MyButton(3);
        button2.setBounds(((intFrameSize.width * 65/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
        window.add(button1);
        window.add(button2);
    }

    /**
     * Class that extends ActionListener to send the new nickname
     */

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nickname.getText().equals("") && nickname.getText().length() > ConstantsContainer.MIN_LENGHT_NICK &&  nickname.getText().length() < ConstantsContainer.MAX_LENGHT_NICK){
                gui.setNamePlayer(nickname.getText());
                gui.updateNickName(nickname.getText());
                gui.popUp.setVisible(false);
                gui.lobby.backButton.setEnabled(true);
            }
        }
    }

    /**
     * Class that extends ActionListener to return to login for a new game
     */

    private class NewGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.backToLogin(true);
            gui.popUp.dispose();
            gui.popUp.getContentPane().removeAll();
            gui.newPopUp();
            gui.lobby.backButton.setEnabled(true);
        }
    }

    /**
     * Class that extends ActionListener to return to login for a new game after the end of a game
     */

    private class NewGameEndGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.board.f.dispose();
            gui.backToLogin(false);
            gui.frame.setVisible(true);
            gui.popUp.dispose();
            gui.board.stopMusic();

        }
    }

    /**
     * Class that extends ActionListener to return to login for a new game after game error
     */

    private class NewGameError implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gui.board != null) {
                gui.board.f.dispose();
                gui.board.stopMusic();
            }
            gui.popUp.dispose();
            gui.popUp.getContentPane().removeAll();
            gui.newPopUp();
            gui.backToLogin(false);
            gui.frame.setVisible(true);
            gui.lobby.backButton.setEnabled(true);
        }
    }

    /**
     * Class that implements ActionListener for the JButton Close which closes the current PopUp
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.popUp.dispose();
            gui.frame.dispose();
            System.exit(0);
        }
    }
}

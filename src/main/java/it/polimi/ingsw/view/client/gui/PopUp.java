package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.getBoldDimension;
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
    double bold = getBoldDimension();

    /**
     * Public constructor
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

                newLabel(1);
                try {
                    label1 = ImageHandler.setImage(TEXT + "nickname.png", 97, 100, intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((intFrameSize.width * 20/100), (int) (intFrameSize.height * 59.5/100), intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                addLabel1();

                newLabel(2);
                try {
                    label2 = ImageHandler.setImage(TEXT + "your_nickname_is_already_used.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                setLabel2(10);
                addLabel2();

                newLabel(3);
                try {
                    label3 = ImageHandler.setImage(TEXT + "change_your_nickname.png", 97, 100, labelSize.width, labelSize.height);
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
                setPopUp(TEXT + "you_have_been_disconnected.png");

                setButtonClose();

                setBack();
                break;

            case 3:
                setDisconnection();
                button1.addActionListener(new NewGameEndGame());

                setBack();
                break;

            case 4:
                setPopUp4or5(TEXT + "has_disconnected.png");

                break;

            case 5:
                setPopUp4or5(TEXT + "did_not_play.png");

                break;

            case 6:
                setPopUp(TEXT + "time_is_up.png");

                setDisconnectionTime();
                window.remove(label2);

                setBack();
                break;

            case 7:
                setPopUp(TEXT + "there_was_an_error.png", 50, 10, TEXT + "please_use_an_original.png", 20);

                newLabel(3);
                try {
                    label3 = ImageHandler.setImage(TEXT + "version_of_the_game.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label3.setBounds(((intFrameSize.width * 50/100) - (labelSize.width / 2)), (intFrameSize.height * 30/100), labelSize.width, labelSize.height);
                addLabel3();

                setButtonClose();

                setBack();
                break;

            case 8:
                setPopUp(TEXT + "there_was_an_error_with.png", 30, 20, TEXT + "start_new_game_or_close.png", 40);

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
     * Method that set the position of the label2
     * @param y Vertical position provided
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
     * Method that add label1 to window
     */

    private void addLabel1(){
        window.add(label1);
    }

    /**
     * Method that add label2 to window
     */

    private void addLabel2(){
        window.add(label2);
    }

    /**
     * Method that add label3 to window
     */

    private void addLabel3(){
        window.add(label3);
    }

    /**
     * Method that initialize the label selected
     * @param x Number of the label selected
     */

    private void newLabel(int x){
        if (x == 1){
            label1 = new JLabel();
        }
        else if (x == 2){
            label2 = new JLabel();
        }
        else
            label3 = new JLabel();
    }

    /**
     * Method that set the 4th or 5th popup
     * @param path Path to the message of the popup
     */

    private void setPopUp4or5(String path){
        newLabel(1);
        try {
            label1 = ImageHandler.setImage(path, 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        label1.setBounds((intFrameSize.width * 40/100), (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
        addLabel1();

        setDisconnectionTime();

        setBack();
    }

    /**
     * Method that set the label1 in the popup
     * @param path Path to the message of the popup
     */

    private void setPopUp(String path){
        newLabel(1);
        try {
            label1 = ImageHandler.setImage(path, 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel1(50, 35);
        addLabel1();
    }

    /**
     * Method that set the label1 and label2 in the popup
     * @param path1 Path to the message of the label1
     * @param x1 Horizontal position provided for label1
     * @param y1 Vertical position provided for label1
     * @param path2 Path to the message of the label2
     * @param y2 Horizontal position provided for label2
     */

    private void setPopUp(String path1, int x1, int y1, String path2, int y2){
        newLabel(1);
        try {
            label1 = ImageHandler.setImage(path1, 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel1(x1, y1);
        addLabel1();

        newLabel(2);
        try {
            label2 = ImageHandler.setImage(path2, 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel2(y2);
        addLabel2();
    }

    /**
     * Method that builds the popup messages for connection loss
     */

    private void setDisconnection(){
        newLabel(1);
        try {
            label1 = ImageHandler.setImage(TEXT + "you_have_been_disconnected.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel1(50, 20);
        window.add(label1);

        newLabel(2);
        try {
            label2 = ImageHandler.setImage(TEXT + "start_new_game_or_close.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        setLabel2(40);

        setButtonsForDisconnection();

        button2.addActionListener(new Close());
    }

    /**
     * Method that sets the popup messages for not played turn
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
     * Method that set the button close
     */

    private void setButtonClose(){
        button1 = new MyButton(3);
        button1.setBounds(((intFrameSize.width * 50/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
        button1.addActionListener(new Close());
        window.add(button1);
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

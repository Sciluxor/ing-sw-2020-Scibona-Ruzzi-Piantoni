package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.getBoldDimension;
import static it.polimi.ingsw.view.client.gui.Gui.*;

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
    String name = null;
    double bold = getBoldDimension();

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

    public JPanel lobbyPopUp(int n){
        window.removeAll();
        window.setLayout(null);
        //window.setSize(intFrameSize);
        switch (n){
            case 0:
                button1 = new MyButton(0);
                button1.setBounds((int) ((intFrameSize.width * 50/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), buttonSize.width, buttonSize.height);
                button1.addActionListener(new Send());
                window.add(button1);

                nickname.setBounds((int) (intFrameSize.width * 35/100), (int) (intFrameSize.height * 60/100), intFrameSize.width * 30/100,intFrameSize.height * 4/100);
                nickname.setText("AnotherNic");
                window.add(nickname);

                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/nickname.png", 97, 100, intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 20/100), (int) (intFrameSize.height * 59.5/100), intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                window.add(label1);

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("resources/Graphics/Texts/your_nickname_is_already_used.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label2.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 10/100), labelSize.width, labelSize.height);
                window.add(label2);

                label3 = new JLabel();
                try {
                    label3 = ImageHandler.setImage("resources/Graphics/Texts/change_your_nickname.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label3.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 25/100), labelSize.width, labelSize.height);
                window.add(label3);

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 1:
                setDisconnection();
                button1.addActionListener(new NewGame());

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 2:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
                window.add(label1);
                button1 = new MyButton(3);
                button1.setBounds((int) (((double)intFrameSize.width * 50 / 100) -  ((getD().getWidth() * 13 / 100) / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button1.addActionListener(new Close());
                window.add(button1);

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 3:
                setDisconnection();
                button1.addActionListener(new NewGameEndGame());

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 4:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/has_disconnected.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 40/100), (int) (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
                window.add(label1);

                setDisconnectionTime();

                //back = backgroundButton(2);
                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 5:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/did_not_play.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 40/100), (int) (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
                window.add(label1);

                setDisconnectionTime();

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            case 6:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("resources/Graphics/Texts/time_is_up.png", 97, 100, labelSize.width, labelSize.height);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 35/100), labelSize.width, labelSize.height);
                window.add(label1);

                setDisconnectionTime();
                window.remove(label2);

                back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
                window.add(back);
                break;

            default:
        }
        return window;
    }

    private void setDisconnection(){
        label1 = new JLabel();
        try {
            label1 = ImageHandler.setImage("resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        label1.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 20/100), labelSize.width, labelSize.height);
        window.add(label1);

        label2 = new JLabel();
        try {
            label2 = ImageHandler.setImage("resources/Graphics/Texts/start_new_game_or_close.png", 97, 100, labelSize.width, labelSize.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        label2.setBounds((int) ((intFrameSize.width * 50/100) - (labelSize.width / 2)), (int) (intFrameSize.height * 40/100), labelSize.width, labelSize.height);
        window.add(label2);

        button1 = new MyButton(2);
        button1.setBounds((int) ((intFrameSize.width * 35/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) buttonSize.width, buttonSize.height);
        button2 = new MyButton(3);
        button2.setBounds((int) ((intFrameSize.width * 65/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) buttonSize.width, buttonSize.height);
        window.add(button1);
        window.add(button2);
        button2.addActionListener(new Close());
    }

    private void setDisconnectionTime(){
        label2 = new JLabel(name);
        label2.setBounds((int) (((double)intFrameSize.width * 30/100) - (name.length() * bold)), (int) (intFrameSize.height * 41.5/100), intFrameSize.width * 60/100, intFrameSize.width * 5/100);
        label2.setFont(felixBold);
        window.add(label2);

        button1 = new MyButton(2);
        button1.setBounds((int) ((intFrameSize.width * 35/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) buttonSize.width, buttonSize.height);
        button2 = new MyButton(3);
        button2.setBounds((int) ((intFrameSize.width * 65/100) - (buttonSize.width / 2)), (int) (intFrameSize.height * 79.5 / 100), (int) buttonSize.width, buttonSize.height);
        window.add(button1);
        button1.addActionListener(new NewGameEndGame());
        window.add(button2);
        button2.addActionListener(new Close());
    }

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

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.popUp.dispose();
            gui.frame.dispose();
            System.exit(0);
        }
    }
}

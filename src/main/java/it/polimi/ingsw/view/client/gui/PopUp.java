package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class PopUp {
    Gui gui;
    JPanel window;
    Dimension intFrameSize = new Dimension();
    Dimension labelSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton button1;
    MyButton button2;
    JTextField nickname;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JPanel panel;

    public PopUp(Gui istance2, Dimension d){

        gui = istance2;
        window = new JPanel();
        intFrameSize.setSize(d.getWidth() * 55/100, d.getHeight() * 60/100);
        labelSize.setSize(intFrameSize.width * 55/100,intFrameSize.height * 20/100);
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        nickname = new JTextField(20);
        panel = new JPanel();
    }

    public JPanel lobbyPopUp(int n){
        window.removeAll();
        window.setLayout(null);
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

                break;

            case 1:
                setDisconnection();
                button1.addActionListener(new NewGame());
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
                button1.setBounds((int) (intFrameSize.width * 41.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button1.addActionListener(new Close());
                window.add(button1);
                break;

            case 3:
                setDisconnection();
                button1.addActionListener(new NewGameEndGame());
                break;

            default:
        }
        JButton back = backgroundButton(1);
        back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
        window.add(back);
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
            gui.backToLogin(true);
            gui.frame.setVisible(true);
            gui.popUp.dispose();

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

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
    MyButton button1;
    MyButton button2;
    JTextField nickname;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JPanel panel;

    public PopUp(Gui istance, Dimension d){
        gui = istance;
        window = new JPanel();
        intFrameSize.setSize(d.getWidth() * 70/100, d.getHeight() * 75/100);
        nickname = new JTextField(20);
        panel = new JPanel();
    }

    public JPanel lobbyPopUp(int n){
        window.removeAll();
        window.setLayout(null);
        switch (n){
            case 0:
                button1 = new MyButton(0);
                button1.setBounds((int) (intFrameSize.width * 41.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button1.addActionListener(new Send());
                window.add(button1);

                nickname.setBounds((int) (intFrameSize.width * 35/100), (int) (intFrameSize.height * 60/100), intFrameSize.width * 30/100,intFrameSize.height * 4/100);
                nickname.setText("Other Nickname");
                window.add(nickname);

                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("src/main/resources/Graphics/Texts/nickname.png", 97, 100, intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 20/100), (int) (intFrameSize.height * 59.5/100), intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                window.add(label1);

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("src/main/resources/Graphics/Texts/your_nickname_is_already_used.png", 97, 100, intFrameSize.width * 50/100,intFrameSize.height * 15/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label2.setBounds((int) (intFrameSize.width * 25/100), (int) (intFrameSize.height * 10/100), intFrameSize.width * 50/100,intFrameSize.height * 15/100);
                window.add(label2);

                label3 = new JLabel();
                try {
                    label3 = ImageHandler.setImage("src/main/resources/Graphics/Texts/change_your_nickname.png", 97, 100, intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label3.setBounds((int) (intFrameSize.width * 30/100), (int) (intFrameSize.height * 25/100), intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                window.add(label3);

                break;

            case 1:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("src/main/resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 30/100), (int) (intFrameSize.height * 20/100), intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                window.add(label1);

                label2 = new JLabel();
                try {
                    label2 = ImageHandler.setImage("src/main/resources/Graphics/Texts/start_new_game_or_close.png", 97, 100, intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label2.setBounds((int) (intFrameSize.width * 30/100), (int) (intFrameSize.height * 40/100), intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                window.add(label2);

                button1 = new MyButton(2);
                button1.setBounds((int) (intFrameSize.width * 29.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button2 = new MyButton(3);
                button2.setBounds((int) (intFrameSize.width * 56.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                window.add(button1);
                button1.addActionListener(new NewGame());
                window.add(button2);
                button2.addActionListener(new Close());
                break;

            case 2:
                label1 = new JLabel();
                try {
                    label1 = ImageHandler.setImage("src/main/resources/Graphics/Texts/you_have_been_disconnected.png", 97, 100, intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                label1.setBounds((int) (intFrameSize.width * 30/100), (int) (intFrameSize.height * 35/100), intFrameSize.width * 40/100,intFrameSize.height * 15/100);
                window.add(label1);
                button1 = new MyButton(3);
                button1.setBounds((int) (intFrameSize.width * 41.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                button1.addActionListener(new Close());
                window.add(button1);
                break;
            default:
        }
        JButton back = backgroundButton(1);
        back.setBounds(0, 0, intFrameSize.width, intFrameSize.height);
        window.add(back);
        return window;
    }

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nickname.getText().equals("") && nickname.getText().length() > ConstantsContainer.MIN_LENGHT_NICK &&  nickname.getText().length() < ConstantsContainer.MAX_LENGHT_NICK){
                gui.setNamePlayer(nickname.getText());
                gui.updateNickName(nickname.getText());
                gui.popUp.setVisible(false);
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

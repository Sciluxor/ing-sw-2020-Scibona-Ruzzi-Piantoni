package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class PopUp {
    Gui gui;
    JPanel window;
    Dimension intFrameSize = new Dimension();
    ConfirmButton confirm;
    JTextField nickname;
    JLabel nicknameLabel;
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
                confirm = new ConfirmButton();
                confirm.setBounds((int) (intFrameSize.width * 41.12 / 100), (int) (intFrameSize.height * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                confirm.addActionListener(new Send());
                window.add(confirm);
                nicknameLabel = new JLabel("nickname");

                nickname.setBounds((int) (intFrameSize.width * 35/100), (int) (intFrameSize.height * 50/100), intFrameSize.width * 30/100,intFrameSize.height * 4/100);
                nickname.setText("Other Nickname");
                window.add(nickname);
                try {
                    nicknameLabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/nickname.png", 97, 100, intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                nicknameLabel.setBounds((int) (intFrameSize.width * 20/100), (int) (intFrameSize.height * 49.5/100), intFrameSize.width * 15/100,intFrameSize.height * 5/100);
                window.add(nicknameLabel);

                break;

            case 1:
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
}

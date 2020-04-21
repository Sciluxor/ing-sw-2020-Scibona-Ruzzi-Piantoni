package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.Client.GUI.Gui.setNamePlayer;
import static it.polimi.ingsw.view.Client.GUI.Gui.setNumberOfPlayers;

public class Login extends JPanel{

    Dimension frameSize = new Dimension();
    static JTextField nickname;
    static JTextField numberPlayers;

    public Login(Dimension frame) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton confirm = new JButton();
        nickname = new JTextField(20);
        numberPlayers = new JTextField(20);

        JLabel cover = ImageHandler.setImage("src/main/resources/Graphics/background_login.png", 100, 100, frameSize.width, frameSize.height);
        JLabel sfondo = new JLabel(cover.getIcon());
        JButton back = new JButton();
        back.setIcon(sfondo.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);

        JLabel santoriniLabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/Santorini_HD.png", 100, 100, frameSize.width * 30/100,frameSize.height*20/100);
        JLabel nicknameLabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/nickname.png", 97, 100, frameSize.width * 15/100,frameSize.height * 5/100);
        JLabel numberPlayersLabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/number_of_players.png", 97, 100, frameSize.width * 20/100,frameSize.height * 5/100);


        santoriniLabel.setBounds(frameSize.width * 35/100 , frameSize.height * 7/100, frameSize.width * 30/100,frameSize.height*20/100);
        add(santoriniLabel);

        confirm.setBounds((int) (frameSize.width * 43.5/100), (int) (frameSize.height * 79.5/100), (int) (frameSize.width * 13/100), (int) (frameSize.height * 5/100));
        confirm.setOpaque(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setIcon(Gui.lconfirm.getIcon());
        confirm.addMouseListener(new Gui.ConfirmButtonPress());
        add(confirm);

        nickname.setBounds((int) (frameSize.width * 40/100), (int) (frameSize.height * 46.5/100), frameSize.width * 20/100,frameSize.height * 3/100);
        add(nickname);
        nicknameLabel.setBounds((int) (frameSize.width * 24.5/100), (int) (frameSize.height * 45.5/100), frameSize.width * 15/100,frameSize.height * 5/100);
        add(nicknameLabel);

        numberPlayers.setBounds( (frameSize.width * 40/100), (int) (frameSize.height * 56.5/100), frameSize.width * 20/100,frameSize.height * 3/100);
        add(numberPlayers);
        numberPlayersLabel.setBounds((int) (frameSize.width * 19.5/100), (int) (frameSize.height * 55.5/100), frameSize.width * 20/100,frameSize.height * 5/100);
        add(numberPlayersLabel);

        add(back);
        confirm.addActionListener(new Gui.ChangePanel());
        confirm.addActionListener(new Send());
    }

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nickname.getText().equals("")){
                setNamePlayer(nickname.getText());
            }

            if (!numberPlayers.getText().equals("")) {
                setNumberOfPlayers((Integer.parseInt(numberPlayers.getText())));
            }
        }
    }
}
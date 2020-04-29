package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import static it.polimi.ingsw.view.client.gui.Gui.*;

public class Login extends JPanel{

    private Gui gui;
    Dimension frameSize = new Dimension();
    JTextField nickname;
    JTextField numberPlayers;
    JTextField port;
    JTextField address;

    public Login(Gui istance, Dimension frame) throws IOException {

        gui = istance;
        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        nickname = new JTextField(20);
        numberPlayers = new JTextField(20);
        port = new JTextField(20);
        address = new JTextField(20);

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
        //JLabel portlabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/port.png", 97, 100, frameSize.width * 15/100,frameSize.height * 5/100);
        //JLabel addresslabel = ImageHandler.setImage("src/main/resources/Graphics/Texts/address.png", 97, 100, frameSize.width * 20/100,frameSize.height * 5/100);


        santoriniLabel.setBounds(frameSize.width * 35/100 , frameSize.height * 7/100, frameSize.width * 30/100,frameSize.height*20/100);
        add(santoriniLabel);


        ConfirmButton confirm = new ConfirmButton();
        add(confirm);

        nickname.setBounds((int) (frameSize.width * 40/100), (int) (frameSize.height * 35/100), frameSize.width * 20/100,frameSize.height * 3/100);
        nickname.setText("Nickname");
        add(nickname);
        nicknameLabel.setBounds((int) (frameSize.width * 24.5/100), (int) (frameSize.height * 34/100), frameSize.width * 15/100,frameSize.height * 5/100);
        add(nicknameLabel);

        numberPlayers.setBounds( (frameSize.width * 40/100), (int) (frameSize.height * 45/100), frameSize.width * 20/100,frameSize.height * 3/100);
        numberPlayers.setText("2");
        add(numberPlayers);
        numberPlayersLabel.setBounds((int) (frameSize.width * 19.5/100), (int) (frameSize.height * 44/100), frameSize.width * 20/100,frameSize.height * 5/100);
        add(numberPlayersLabel);

        port.setBounds((int) (frameSize.width * 40/100), (int) (frameSize.height * 55/100), frameSize.width * 20/100,frameSize.height * 3/100);
        port.setText("4700");
        add(port);
        /*portlabel.setBounds((int) (frameSize.width * 24.5/100), (int) (frameSize.height * 54/100), frameSize.width * 15/100,frameSize.height * 5/100);
        add(portlabel);*/

        address.setBounds((int) (frameSize.width * 40/100), (int) (frameSize.height * 65/100), frameSize.width * 20/100,frameSize.height * 3/100);
        address.setText("127.0.0.1");
        add(address);
        /*addresslabel.setBounds((int) (frameSize.width * 24.5/100), (int) (frameSize.height * 64/100), frameSize.width * 15/100,frameSize.height * 5/100);
        add(addresslabel);*/

        add(back);
        confirm.addActionListener(new Send());
    }

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nickname.getText().equals("") && !numberPlayers.getText().equals("") && (numberPlayers.getText().equals("2") || numberPlayers.getText().equals("3"))){
                setNamePlayer(nickname.getText());
                setNumberOfPlayers((Integer.parseInt(numberPlayers.getText())));
                gui.guiOpenConnection(nickname.getText(), (Integer.parseInt(numberPlayers.getText())), address.getText(), (Integer.parseInt(port.getText())));
                panelManager(0);
            }
        }
    }

}

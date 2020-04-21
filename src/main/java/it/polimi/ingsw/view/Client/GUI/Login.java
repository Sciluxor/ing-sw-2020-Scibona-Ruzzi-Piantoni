package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login extends JPanel{
    private JTextField nickname;
    Dimension frameSize = new Dimension();




    public Login(Dimension screen, Dimension frame) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton confirm = new JButton("Confirm");
        nickname = new JTextField(20);
        JTextField numberPlayers = new JTextField(20);
        double ratio = (screen.getWidth()/screen.getHeight());

        JLabel cover = ImageHandler.setImage("src/main/resources/Graphics/background_login.png", 100, 100, frameSize.width, frameSize.height);
        JLabel sfondo = new JLabel(cover.getIcon());
        JLabel santoriniLabel = ImageHandler.setImage("src/main/resources/Graphics/Text/Santorini_HD.png", 100, 100, frameSize.width, frameSize.height);
        JLabel nicknameLabel = ImageHandler.setImage("src/main/resources/Graphics/Text/nickname.png", 100, 100, frameSize.width, frameSize.height);
        JLabel numberPlayersLabel = ImageHandler.setImage("src/main/resources/Graphics/Text/number_of_players.png", 100, 100, frameSize.width, frameSize.height);

        JButton back = new JButton();
        back.setIcon(sfondo.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);



        santoriniLabel.setBounds(frameSize.width * 50/100 - (screen.width * 4/100)/2, frameSize.height * 20/100 - (screen.height * 4/100)/2, screen.width * 10/100,screen.height*10/100);
        add(santoriniLabel);

        confirm.setBounds(frameSize.width * 50/100 - (screen.width * 10/100)/2, frameSize.height * 82/100 - 50, screen.width * 10/100, screen.height * 3/100);
        confirm.setOpaque(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        add(confirm);

        nickname.setBounds(frameSize.width * 47/100 - (screen.width * 6/100)/2, frameSize.height * 45/100 - (screen.height * 6/100)/2, screen.width * 12/100,screen.height * 3/100);
        add(nickname);
        nicknameLabel.setBounds(frameSize.width * 37/100 - (screen.width * 6/100)/2, frameSize.height * 45/100 - (screen.height * 6/100)/2, screen.width * 12/100,screen.height * 3/100);
        add(nicknameLabel);

        numberPlayers.setBounds(frameSize.width * 47/100 - (screen.width * 6/100)/2, frameSize.height * 55/100 - (screen.height * 6/100)/2, screen.width * 12/100,screen.height * 3/100);
        add(numberPlayers);
        numberPlayersLabel.setBounds(frameSize.width * 37/100 - (screen.width * 6/100)/2, frameSize.height * 55/100 - (screen.height * 6/100)/2, screen.width * 12/100,screen.height * 3/100);
        add(numberPlayersLabel);

        add(back);
        confirm.addActionListener(new Gui.ChangePanel());

    }
}

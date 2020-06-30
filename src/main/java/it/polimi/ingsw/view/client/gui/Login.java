package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.ConstantsContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

/**
 * Class that extends JPanel that builds the JPanel for the login
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class Login extends JPanel{

    private final transient Gui gui;
    Dimension frameSize = new Dimension();
    private final JTextField nickname;
    JTextField numberPlayers;
    JTextField port;
    JTextField address;
    MyButton confirm;
    boolean firstConnection;

    /**
     * Public constructor
     * @param instance Reference to the client GUI class
     * @param frame Size of the frame where the current JPanel Login will be inserted
     * @param connection Boolean saying if it's a first connection
     * @throws IOException if the loading of the inscription or the background was not successful
     */

    public Login(Gui instance, Dimension frame, boolean connection) throws IOException {

        this.firstConnection = connection;
        gui = instance;
        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        nickname = new JTextField(20);
        numberPlayers = new JTextField(20);
        port = new JTextField(20);
        address = new JTextField(20);

        JLabel cover = ImageHandler.setImage(RESOURCES_GRAPHICS + "background_login.png", 100, 100, frameSize.width, frameSize.height);
        JLabel background = new JLabel(cover.getIcon());
        JButton back = new JButton();
        back.setIcon(background.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);

        JLabel santoriniLabel = ImageHandler.setImage(TEXT + "Santorini_HD.png", 100, 100, frameSize.width * 30/100,frameSize.height*20/100);
        JLabel nicknameLabel = ImageHandler.setImage(TEXT + "nickname.png", 97, 100, frameSize.width * 15/100,frameSize.height * 5/100);
        JLabel numberPlayersLabel = ImageHandler.setImage(TEXT + "number_of_players.png", 97, 100, frameSize.width * 20/100,frameSize.height * 5/100);
        JLabel portLabel = ImageHandler.setImage(TEXT + "port.png", 97, 100, frameSize.width * 5/100,frameSize.height * 5/100);
        JLabel addressLabel = ImageHandler.setImage(TEXT + "address.png", 97, 100, frameSize.width * 10/100,frameSize.height * 5/100);


        santoriniLabel.setBounds(frameSize.width * 35/100 , frameSize.height * 7/100, frameSize.width * 30/100,frameSize.height*20/100);
        add(santoriniLabel);


        confirm = new MyButton(0);
        add(confirm);

        nickname.setBounds((frameSize.width * 40/100), (frameSize.height * 35/100), frameSize.width * 20/100,frameSize.height * 3/100);
        nickname.setText("Nickname");
        add(nickname);
        nicknameLabel.setBounds((int) (frameSize.width * 24.5/100), (frameSize.height * 34/100), frameSize.width * 15/100,frameSize.height * 5/100);
        add(nicknameLabel);

        numberPlayers.setBounds((frameSize.width * 40/100), (frameSize.height * 45/100), frameSize.width * 20/100,frameSize.height * 3/100);
        numberPlayers.setText("2");
        add(numberPlayers);
        numberPlayersLabel.setBounds((int) (frameSize.width * 19.5/100), (frameSize.height * 44/100), frameSize.width * 20/100,frameSize.height * 5/100);
        add(numberPlayersLabel);

        if (firstConnection){
        port.setBounds((frameSize.width * 40/100), (frameSize.height * 55/100), frameSize.width * 20/100,frameSize.height * 3/100);
        port.setText("4700");
        add(port);
        add(port);
        portLabel.setBounds((int) (frameSize.width * 34.5/100), (frameSize.height * 54/100), frameSize.width * 5/100,frameSize.height * 5/100);
        add(portLabel);

        address.setBounds((frameSize.width * 40/100), (frameSize.height * 65/100), frameSize.width * 20/100,frameSize.height * 3/100);
        address.setText("54.237.47.88");
        add(address);
        addressLabel.setBounds((int) (frameSize.width * 29.5/100), (frameSize.height * 64/100), frameSize.width * 10/100,frameSize.height * 5/100);
        add(addressLabel);
        }

        add(back);
        confirm.addActionListener(new Send());
    }

    /**
     * Class that implements ActionListener for opening the first connection or for starting new games from an already open connection
     */

    private class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nickname.getText().equals("") && nickname.getText().length() >= ConstantsContainer.MIN_LENGHT_NICK &&  nickname.getText().length() <= ConstantsContainer.MAX_LENGHT_NICK &&
                    !numberPlayers.getText().equals("") && (numberPlayers.getText().equals("2") || numberPlayers.getText().equals("3")) && firstConnection){
                gui.setNamePlayer(nickname.getText());
                gui.setNumberOfPlayers((Integer.parseInt(numberPlayers.getText())));
                try {
                    gui.openConnection(nickname.getText(), (Integer.parseInt(numberPlayers.getText())), address.getText(), (Integer.parseInt(port.getText())));
                    gui.loginToLobby();
                } catch (ConnectException connectException) {
                    LOGGER.severe(connectException.getMessage());
                    gui.backToLogin(true);
                }
            }
            else{
                if (!nickname.getText().equals("") && nickname.getText().length() >= ConstantsContainer.MIN_LENGHT_NICK &&  nickname.getText().length() <= ConstantsContainer.MAX_LENGHT_NICK &&
                        !numberPlayers.getText().equals("") && (numberPlayers.getText().equals("2") || numberPlayers.getText().equals("3"))){
                    gui.setNamePlayer(nickname.getText());
                    gui.setNumberOfPlayers((Integer.parseInt(numberPlayers.getText())));
                    gui.newGame(nickname.getText(), (Integer.parseInt(numberPlayers.getText())));
                    gui.loginToLobby();
                }
            }
        }
    }

}

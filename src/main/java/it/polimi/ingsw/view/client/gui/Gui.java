package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.controller.ClientGameController;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Gui extends ClientGameController {

    public static final Logger LOGGER = Logger.getLogger("Gui");
    static final String FELIX = "Felix Titling";

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int)(screenSize.getWidth());
    static int height = (int)(screenSize.getHeight());
    static Dimension d = new Dimension(width * 95/100, height * 95/100);
    static JFrame frame = new JFrame("Santorini");
    static JPanel login = null;
    static JPanel lobby = null;
    static JPanel challengerChoiseCards = null;
    static JPanel waitChallenger = null;
    static JPanel challengerChoiseFirst = null;
    static JPanel chooseCard3 = null;
    static JPanel chooseCard2 = null;
    static JPanel chooseCard1 = null;
    static JPanel chooseCard0 = null;
    static JLabel lconfirm;
    static JLabel lconfirmPress;
    static JLabel cover;
    static {
        try {
            lconfirm = ImageHandler.setImage("src/main/resources/Graphics/button_confirm.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
            lconfirmPress = ImageHandler.setImage("src/main/resources/Graphics/button_confirm_press.png", 100, 100, (int) (d.width * 13/100), (int) (d.height * 5/100));
            cover = ImageHandler.setImage("src/main/resources/Graphics/background_panels.png", 100, 100, d.width, d.height);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
    static JLabel backgroundPanel = new JLabel(cover.getIcon());
    static int panelInUse = 0;
    private static int numberOfPlayers = 2;
    private static int actualPlayers = 1;

    static Font felixSmall = new Font(FELIX, Font.PLAIN, (int) (13 * screenSize.getHeight() / 1080));
    static Font felixNormal = new Font(FELIX, Font.PLAIN, (int) (20 * screenSize.getHeight() / 1080));
    static Font felixBold = new Font(FELIX, Font.BOLD, (int) (25 * screenSize.getHeight() / 1080));
    static List<Player> players = new ArrayList<>();


    private void show() throws IOException {

        Player ale = new Player("Alessandro");
        Player edo = new Player("Edoardo");
        Player lui = new Player("Luigi");
        ale.setColor(Color.BLUE);
        edo.setColor(Color.WHITE);
        lui.setColor(Color.PURPLE);
        //players.add(ale);
        players.add(edo);
        players.add(lui);







        login = new Login(d);                                                                                                   //schermata 0 sistemata
        lobby = new LobbyGui(d, actualPlayers, numberOfPlayers, players);                                                       //schermata 1  sistemata
        //challengerChoiseCards2 = new ChallengerChoiseCards(d, numberOfPlayers, background_panel);                              //schermata 2 sistemata
        //challengerChoiseCards3 = new ChallengerChoiseCards(d, numberOfPlayers, background_panel);                              //schermata 3 sistemata
        waitChallenger = new WaitChallenger(d);                                                                                 //schermata 4 sistemata
        //challengerChoiseFirst2 = new ChallengerChoiseFirstPlayer(d, numberOfPlayers, players, background_panel);              //schermata 5 sistemata
        //challengerChoiseFirst3 = new ChallengerChoiseFirstPlayer(d, numberOfPlayers, players, background_panel);              //schermata 6 sistemata
        //chooseCard3 = new ChooseCard(screenSize, d, 3);                                                             //schermata 7 sistemata
        //chooseCard2 = new ChooseCard(screenSize, d, 2);                                                             //schermata 8 sistemata
        //chooseCard1 = new ChooseCard(screenSize, d, 1);                                                             //schermata 9 sistemata
        //chooseCard0 = new ChooseCard(screenSize, d, 0);                                                             //schermata 10 sistemata
        //board2 = new Board(screenSize, d, 2, players, "GID01");                                                                   //schermata 11
        //board3 = new Board(screenSize, d, 3, players, "GID01");                                                                   //schermata 12




        frame.setPreferredSize(d);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.add(login);

        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }


    public static void panelManager(int panel) throws IOException {
        switch (panel){
            case 0:
                frame.remove(login);
                challengerChoiseCards = new ChallengerChoiseCards(d, numberOfPlayers);
                frame.add(challengerChoiseCards);
                panelInUse = 1;
                break;
            case 1:
                frame.remove(challengerChoiseCards);
                challengerChoiseFirst = new ChallengerChoiseFirstPlayer(d, numberOfPlayers, players);
                frame.add(challengerChoiseFirst);
                panelInUse = 2;
                break;

            case 2:
                frame.remove(challengerChoiseFirst);
                chooseCard2 = new ChooseCard(screenSize, d, 2);
                frame.add(chooseCard2);
                panelInUse = 3;
                break;

            case 3:
                frame.dispose();
                Board board = new Board();
                board.show(screenSize, numberOfPlayers, players, "GID01");
                panelInUse = 4;
                break;

            case 4:

                //frame.add(new Board(boardSize, 2), BorderLayout.CENTER);
                //frame.remove(board3);
                //frame.add(board2);
                panelInUse = 5;
                break;
            default:
        }
        if (panelInUse != 4){
            frame.repaint();
            frame.validate();
        }
    }



    public void avvio() { //avvio
        SwingUtilities.invokeLater(() -> {
            try {
                Gui gui = new Gui();
                gui.show();
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        });
    }



    public static class ChangePanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (panelInUse){
                case 0:
                    try {
                        panelManager(0);
                    } catch (IOException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                    break;

                case 1:
                    try {
                        panelManager(1);
                    } catch (IOException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                    break;

                case 2:
                    try {
                        panelManager(2);
                    } catch (IOException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                    break;

                case 3:
                    try {
                        panelManager(3);
                    } catch (IOException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                    break;

                case 4:
                    try {
                        panelManager(4);
                    } catch (IOException ex) {
                        LOGGER.severe(ex.getMessage());
                    }
                    break;
                default:
            }

        }

    }

    public static JButton confirmButtonCreate() throws IOException {
        JButton confirm = new JButton();
        lconfirm = ImageHandler.setImage("src/main/resources/Graphics/button_confirm.png", 100, 100, (int) (d.getWidth() * 13/100), (int) (d.getHeight() * 5/100));
        lconfirmPress = ImageHandler.setImage("src/main/resources/Graphics/button_confirm_press.png", 100, 100, (int) (d.getWidth() * 13/100), (int) (d.getHeight() * 5/100));

        confirm.setBounds((int) (d.getWidth() * 43.5/100), (int) (d.getHeight() * 79.5/100), (int) (d.getWidth() * 13/100), (int) (d.getHeight() * 5/100));
        confirm.setOpaque(false);
        confirm.setContentAreaFilled(false);
        confirm.setFocusPainted(false);
        confirm.setBorderPainted(false);
        confirm.setIcon(Gui.lconfirm.getIcon());
        //confirm.setVisible(true);
        confirm.addMouseListener(new ConfirmButtonPress());
        confirm.addActionListener(new Gui.ChangePanel());
        return confirm;
    }

    public static class ConfirmButtonPress implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setIcon(lconfirmPress.getIcon());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setIcon(lconfirm.getIcon());
        }

        @Override
        public void mouseEntered(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mouseExited(MouseEvent e) {/*override unnecessary*/}
    }

    public static JButton backgroundButton(){
        JButton back = new JButton();
        back.setIcon(backgroundPanel.getIcon());
        back.setBounds(0, 0, d.width, d.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        return back;
    }


    public static void setNumberOfPlayers(int numberOfPlayers) {
        Gui.numberOfPlayers = numberOfPlayers;
    }

    public static void setNamePlayer(String name) {
        players.add(0, new Player(name));
        players.get(0).setColor(Color.BLUE);
    }

    public static void eliminateActionClass(JButton button, Class clas){
        for (int x = 0; x < button.getActionListeners().length; x++){
            if (button.getActionListeners()[x].getClass().equals(clas))
                button.removeActionListener(button.getActionListeners()[x]);
        }
    }

    public static void eliminateMouseClass(JButton button, Class clas){
        for (int x = 0; x < button.getMouseListeners().length; x++){
            if (button.getMouseListeners()[x].getClass().equals(clas))
                button.removeMouseListener(button.getMouseListeners()[x]);
        }
    }


}

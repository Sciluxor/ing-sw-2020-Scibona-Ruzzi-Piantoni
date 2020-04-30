package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.network.client.ClientGameController;
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
    static LobbyGui lobby = null;
    static JDesktopPane challengerChoiseCards = null;
    static JPanel waitChallenger = null;
    static JPanel challengerChoiseFirst = null;
    static JDesktopPane chooseCard3 = null;
    static JDesktopPane chooseCard2 = null;
    static JPanel chooseCard1 = null;
    static JPanel chooseCard0 = null;
    static JLabel lconfirm;
    static JLabel lconfirmPress;
    static JLabel cover;
    static {
        try {
            lconfirm = ImageHandler.setImage("src/main/resources/Graphics/button_confirm.png", 100, 100, d.width * 13/100, d.height * 5/100);
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
    static List<Player> players2 = new ArrayList<>();


    private void show() throws IOException {

        Player ale = new Player("Alessandro");
        Player edo = new Player("Edoardo");
        Player lui = new Player("Luigi");
        ale.setColor(Color.BLUE);
        edo.setColor(Color.WHITE);
        lui.setColor(Color.PURPLE);
        //players.add(ale);
        //players.add(edo);
        //players.add(lui);

        players2.add(ale);
        players2.add(edo);
        players2.add(lui);






        login = new Login(this, d);                                                                                      //schermata 0 sistemata
        //lobby = new LobbyGui(this, d, numberOfPlayers);                                                       //schermata 1  sistemata
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


    private void panelManager(int panel){
        switch (panel){
            case 0:
                frame.remove(login);
                try {
                    lobby = new LobbyGui(this, d, numberOfPlayers, Gui.players);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                frame.add(lobby.getPane());
                panelInUse = 1;
                break;
            case 1:
                frame.remove(challengerChoiseCards);
                try {
                    challengerChoiseFirst = new ChallengerChoiseFirstPlayer(this, d, numberOfPlayers, players);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
                frame.setContentPane(challengerChoiseFirst);
                panelInUse = 2;
                break;

            case 2:
                frame.remove(challengerChoiseFirst);
                if (numberOfPlayers == 2){
                    try {
                        chooseCard2 = new ChooseCard(this, screenSize, d, numberOfPlayers);
                    } catch (IOException e) {
                        LOGGER.severe(e.getMessage());
                    }
                    frame.setContentPane(chooseCard2);
                }
                else{
                    try {
                        chooseCard3 = new ChooseCard(this, screenSize, d, numberOfPlayers);
                    } catch (IOException e) {
                        LOGGER.severe(e.getMessage());
                    }
                    frame.setContentPane(chooseCard3);
                }
                panelInUse = 3;
                break;

            case 3:
                frame.dispose();
                Board board = new Board();
                try {
                    board.show(screenSize, numberOfPlayers, players, "GID01");
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
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




    public void changePanel (){

            switch (panelInUse){
                case 0:
                    panelManager(0);
                    break;

                case 1:
                    panelManager(1);
                    break;

                case 2:
                    panelManager(2);
                    break;

                case 3:
                    panelManager(3);
                    break;

                case 4:
                    panelManager(4);
                    break;
                default:
            }
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        Gui.numberOfPlayers = numberOfPlayers;
    }

    public void setNamePlayer(String name) {
        players.add(new Player(name));
        players.get(0).setColor(Color.BLUE);
    }

    public static Dimension getD() {
        return d;
    }

    @Override
    public void updateLobbyPlayer() {
        SwingUtilities.invokeLater(() -> {
            LobbyGui.stamp(getPlayers());
        });
    }

    @Override
    public void nickUsed() {

    }

    @Override
    public void startGame() {
        frame.dispose();
        Board board = new Board();
        try {
            board.show(screenSize, numberOfPlayers, getPlayers(), "GID01");
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void challengerChoice() {

    }

    @Override
    public void cardChoice() {

    }

    @Override
    public void placeWorker() {

    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void notifyWin() {

    }

    @Override
    public void addConstraint() {

    }

    @Override
    public void onDisconnection() {

    }

    @Override
    public void errorMessage() {

    }

    @Override
    public void startTurn() {

    }

}

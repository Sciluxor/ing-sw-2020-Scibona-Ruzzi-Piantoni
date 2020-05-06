package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import java.awt.*;
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
    Dimension intFrameSize = new Dimension(d.width * 70/100, d.height * 75/100);
    JFrame frame = new JFrame("Santorini");
    JPanel login = null;
    LobbyGui lobby = null;
    JDesktopPane challengerChoiseCards = null;
    JPanel waitChallenger = null;
    JPanel challengerChoiseFirst = null;
    JDesktopPane chooseCard3 = null;
    JDesktopPane chooseCard2 = null;
    JDesktopPane challengerChoiseCards2 = null;
    JDesktopPane challengerChoiseCards3 = null;
    JPanel lobbyPanel = null;
    JPanel chooseCard0 = null;
    Board board;
    PopUp constructorPopUp = null;
    JFrame popUp = new JFrame();
    static int panelInUse = 0;
    private int numberOfPlayers = 2;
    private static int actualPlayers = 1;

    static Font felixSmall = new Font(FELIX, Font.PLAIN, (int) (13 * screenSize.getHeight() / 1080));
    static Font felixNormal = new Font(FELIX, Font.PLAIN, (int) (20 * screenSize.getHeight() / 1080));
    static Font felixBold = new Font(FELIX, Font.BOLD, (int) (25 * screenSize.getHeight() / 1080));
    static List<Player> players = new ArrayList<>();
    String nickname;
    JButton buttonBackground = new JButton();


    private void show() throws IOException {


        Player ale = new Player("Alessandro");
        Player edo = new Player("Edoardo");
        Player lui = new Player("Luigi");
        ale.setColor(Color.BLUE);
        edo.setColor(Color.WHITE);
        lui.setColor(Color.PURPLE);


        constructorPopUp = new PopUp(this, d);
        newPopUp();


        board = new Board();

        login = new Login(this, d, true);                                                                                      //schermata 0 sistemata
        //lobby = new LobbyGui(this, d, numberOfPlayers);                                                       //schermata 1  sistemata
        //challengerChoiseCards2 = new ChallengerChoiceCards(d, numberOfPlayers, background_panel);                              //schermata 2 sistemata
        //challengerChoiseCards3 = new ChallengerChoiceCards(d, numberOfPlayers, background_panel);                              //schermata 3 sistemata
       // waitChallenger = new WaitChallenger(d);                                                                                 //schermata 4 sistemata
        //challengerChoiseFirst2 = new ChallengerChoiceFirstPlayer(d, numberOfPlayers, players, background_panel);              //schermata 5 sistemata
        //challengerChoiseFirst3 = new ChallengerChoiceFirstPlayer(d, numberOfPlayers, players, background_panel);              //schermata 6 sistemata
        //chooseCard3 = new ChooseCard(screenSize, d, 3);                                                             //schermata 7 sistemata
        //chooseCard2 = new ChooseCard(screenSize, d, 2);                                                             //schermata 8 sistemata
        //chooseCard1 = new ChooseCard(screenSize, d, 1);                                                             //schermata 9 sistemata
        //chooseCard0 = new ChooseCard(screenSize, d, 0);                                                             //schermata 10 sistemata
        //board2 = new Board(screenSize, d, 2, players, "GID01");                                                                   //schermata 11
        //board3 = new Board(screenSize, d, 3, players, "GID01");                                                                   //schermata 12


        buttonBackground.setBounds(0, 0,intFrameSize.width, intFrameSize.height);
        buttonBackground.setOpaque(false);
        buttonBackground.setContentAreaFilled(false);
        buttonBackground.setBorderPainted(false);
        //popUp.add(buttonBackground);
        popUp.setPreferredSize(intFrameSize);
        popUp.setUndecorated(true);
        popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(popUp);
        popUp.pack();
        popUp.setLocationRelativeTo(null);
        popUp.setResizable(false);
        popUp.setVisible(false);

        frame.setPreferredSize(d);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.add(login);

        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

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

    public void logginToLobby(){
        frame.remove(login);
        try {
            lobby = new LobbyGui(this, d, numberOfPlayers, players);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        frame.setContentPane(lobby.getPane());
        frame.repaint();
        frame.validate();
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNamePlayer(String name) {
        players.add(new Player(name));
        players.get(0).setColor(Color.BLUE);
        nickname = name;
    }

    public static Dimension getD() {
        return d;
    }
    public void newPopUp(){
        lobbyPanel = constructorPopUp.lobbyPopUp(0);
        popUp.add(lobbyPanel);
    }

    public void backToLogin(boolean bool){
        frame.getContentPane().removeAll();
        panelInUse = 0;
        try {
            login = new Login(this, d, bool);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        frame.setContentPane(login);
        players.clear();
        frame.repaint();
        frame.validate();
    }

    @Override
    public void updateLobbyPlayer() {
        SwingUtilities.invokeLater(() -> {
            lobby.stamp(getPlayers());
        });
    }

    @Override
    public void nickUsed() {
        SwingUtilities.invokeLater(() -> {
            newPopUp();
            popUp.setVisible(true);
            popUp.repaint();
            popUp.validate();
        });

    }

    @Override
    public void onLobbyDisconnection() {
        SwingUtilities.invokeLater(() -> {
            popUp.remove(lobbyPanel);
            lobbyPanel = constructorPopUp.lobbyPopUp(1);
            popUp.add(lobbyPanel);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void onPingDisconnection() {
        SwingUtilities.invokeLater(() -> {
            popUp.remove(lobbyPanel);
            lobbyPanel = constructorPopUp.lobbyPopUp(2);
            popUp.add(lobbyPanel);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            frame.dispose();
            try {
                board.show(this, screenSize, numberOfPlayers, getPlayers(), getPlayers(),"GID01", nickname);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        });
    }

    @Override
    public void challengerChoice(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> {
            board.showChallenger(name, bool);
        });

    }

    @Override
    public void cardChoice(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> {
            board.showCardChoice(getAvailableCards(), name, bool);
        });
    }

    @Override
    public void placeWorker(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> {
            board.showPlaceWorkers(name, bool);
            System.out.println("qui Place");
        });

    }

    @Override
    public void updatePlacedWorkers(List<Square> squares) {
        System.out.println("stato updatePlaced");
    }

    @Override
    public void updateBoard() {
        System.out.println("stato updateBoard");
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

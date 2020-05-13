package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.MessageType;

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
    Dimension intFrameSize = new Dimension(d.width * 55/100, d.height * 60/100);
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
    static Font felixBold = new Font(FELIX, Font.BOLD, (int) (40 * screenSize.getHeight() / 1080));
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
        login = new Login(this, d, true);

        buttonBackground.setBounds(0, 0,intFrameSize.width, intFrameSize.height);
        buttonBackground.setOpaque(false);
        buttonBackground.setContentAreaFilled(false);
        buttonBackground.setBorderPainted(false);

        popUp.setPreferredSize(intFrameSize);
        popUp.setUndecorated(true);
        popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(popUp);
        popUp.setBounds((int) ((d.width * 53/100) - (intFrameSize.width / 2)), (int) ((d.height * 60/100) - (intFrameSize.height / 2)), intFrameSize.width, intFrameSize.height);
        popUp.pack();
        //popUp.setLocationRelativeTo(null);
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
            this.lobby.backButton.setEnabled(false);
            popUp.remove(lobbyPanel);
            lobbyPanel = constructorPopUp.lobbyPopUp(2);
            popUp.add(lobbyPanel);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void newChatMessage(String nick, String message) {
        SwingUtilities.invokeLater(() -> {
            board.writeInChat(nick, message);
        });
    }

    @Override
    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(false);
            try {
                board.show(this, screenSize, numberOfPlayers, getPlayers(), getPlayers(), nickname);
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
        });

    }

    @Override
    public void updatePlacedWorkers(List<Square> squares) {
        SwingUtilities.invokeLater(() -> {
            board.updatePlacedWorkers(squares);
        });
    }

    @Override
    public void updateBoard(String nick, List<Square> squares, MessageType type) {
        SwingUtilities.invokeLater(() -> {
            board.updateBoard(nick, squares, type);
        });
    }

    @Override
    public void notifyWin(String nick) {
        System.out.println("notifyWin");
        SwingUtilities.invokeLater(() -> {
            board.displayWinLose(nick);
        });
    }

    @Override
    public void notifyLose(String nick, boolean isYourPlayer) {

    }

    @Override
    public void displayActions(List<MessageType> actions) {
        SwingUtilities.invokeLater(() -> {
            board.displayButtons(actions);
        });
    }

    @Override
    public void addConstraint(String name) {

    }

    @Override
    public void removeConstraint(String name) {

    }

    @Override
    public void onTurnDisconnection() {

    }

    @Override
    public void errorMessage() {

    }

    @Override
    public void startTurn(String nick, boolean isYourPlayer) {
        SwingUtilities.invokeLater(() -> {
            board.startTurn(nick, isYourPlayer);
        });
    }


}

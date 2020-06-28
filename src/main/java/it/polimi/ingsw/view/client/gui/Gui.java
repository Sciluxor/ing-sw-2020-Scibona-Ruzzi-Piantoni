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

import static it.polimi.ingsw.utils.ConstantsContainer.MUSIC;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Class that extends ClientGameController that start the application for the Gui
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

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
    JPanel lobbyPanel = null;
    Board board;
    PopUp constructorPopUp = null;
    JFrame popUp = new JFrame();
    int panelInUse = 0;
    private int numberOfPlayers = 2;

    Font felixSmall;
    Font felixNormal;
    Font felixBold;
    static List<Player> players = new ArrayList<>();
    String nickname;
    JButton buttonBackground = new JButton();
    MP3 sound;

    /**
     * Getter of the bold dimension
     * @return Bold dimension
     */

    public static Font getFelixBold() {
        return new Font(FELIX, Font.BOLD, (int) (40 * screenSize.getHeight() / 1080));
    }

    /**
     * Method that start the Gui
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            gui.show();
        });
    }

    /**
     * Method that build the login
     */

    private void show() {

        addFontFelix();

        sound = new MP3(MUSIC + "Fruits.mp3");

        constructorPopUp = new PopUp(this, d, null);
        newPopUp();


        try {
            login = new Login(this, d, true);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        buttonBackground.setBounds(0, 0,intFrameSize.width, intFrameSize.height);
        buttonBackground.setOpaque(false);
        buttonBackground.setContentAreaFilled(false);
        buttonBackground.setBorderPainted(false);

        popUp.setPreferredSize(intFrameSize);
        popUp.setUndecorated(true);
        popUp.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(popUp);
        popUp.setBounds(((d.width * 53/100) - (intFrameSize.width / 2)), ((d.height * 60/100) - (intFrameSize.height / 2)), intFrameSize.width, intFrameSize.height);
        popUp.pack();
        popUp.setResizable(false);
        popUp.setVisible(false);

        sound.playLoop();

        frame.setPreferredSize(d);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.add(login);

        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /**
     * Method that add the font Felix used in the game
     */

    private void addFontFelix(){
        GraphicsEnvironment ge;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Graphics/Felix.TTF")));
        } catch(FontFormatException | IOException e){
            LOGGER.severe(e.getMessage());
        }
        felixSmall = new Font(FELIX, Font.PLAIN, (int) (13 * screenSize.getHeight() / 1080));
        felixNormal = new Font(FELIX, Font.PLAIN, (int) (20 * screenSize.getHeight() / 1080));
        felixBold = new Font(FELIX, Font.BOLD, (int) (40 * screenSize.getHeight() / 1080));
    }

    /**
     * Method that change from login to lobby pane
     */

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

    /**
     * Method that set the number of Players for the game
     * @param numberOfPlayers Number of Players
     */

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Method that set the Player name of the client
     * @param name Name chosen by the Player
     */

    public void setNamePlayer(String name) {
        players.add(new Player(name));
        players.get(0).setColor(Color.BLUE);
        nickname = name;
    }

    /**
     * Getter of the main window dimension
     * @return Main window dimension
     */

    public static Dimension getD() {
        return d;
    }

    /**
     * Method that reset the PopUp frame
     */

    public void newPopUp(){
        lobbyPanel = constructorPopUp.lobbyPopUp(0);
        popUp.add(lobbyPanel);
    }

    /**
     * Method that brings back to the login
     * @param bool Boolean saying if it's a first connection
     */

    public void backToLogin(boolean bool){
        sound.playLoop();
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
        SwingUtilities.invokeLater(() -> lobby.stamp(getPlayers()));
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
            popUp.setVisible(true);
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
    public void onEndGameDisconnection() {
        SwingUtilities.invokeLater(() -> {
            popUp.remove(lobbyPanel);
            lobbyPanel = constructorPopUp.lobbyPopUp(3);
            popUp.add(lobbyPanel);
            popUp.setVisible(true);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void newChatMessage(String nick, String message) {
        SwingUtilities.invokeLater(() -> board.writeInChat(nick, message));
    }

    @Override
    public void onErrorMessage(String stopper, boolean isYourPlayer) {
        SwingUtilities.invokeLater(() -> {
            constructorPopUp = new PopUp(this, d, stopper);
            if (board != null) {
                board.eliminateAllFromAll();
            }
            popUp.remove(lobbyPanel);
            if (isYourPlayer){
                lobbyPanel = constructorPopUp.lobbyPopUp(7);
            }
            else {
                lobbyPanel = constructorPopUp.lobbyPopUp(8);
            }
            popUp.add(lobbyPanel);
            popUp.setVisible(true);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void notYourTurn() {
        SwingUtilities.invokeLater(() -> board.notTurn());
    }


    @Override
    public void startGame() {
        SwingUtilities.invokeLater(() -> {
            sound.stop();
            frame.setVisible(false);
            board = new Board();
            try {
                board.show(this, screenSize, numberOfPlayers, getPlayers(), getPlayers(), nickname);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
        });
    }

    @Override
    public void challengerChoice(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> board.showChallenger(name, bool));
    }

    @Override
    public void cardChoice(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> board.showCardChoice(getAvailableCards(), name, bool));
    }

    @Override
    public void placeWorker(String name, boolean bool) {
        SwingUtilities.invokeLater(() -> board.showPlaceWorkers(name, bool));
    }

    @Override
    public void updatePlacedWorkers(List<Square> squares) {
        SwingUtilities.invokeLater(() -> board.updatePlacedWorkers(squares));
    }

    @Override
    public void updateBoard(String nick, List<Square> squares, MessageType type) {
        SwingUtilities.invokeLater(() -> board.updateBoard(nick, squares, type));
    }

    @Override
    public void notifyWin(String nick) {
        SwingUtilities.invokeLater(() -> board.displayWinLose(nick));
    }

    @Override
    public void notifyLose(String nick, boolean isYourPlayer) {
        SwingUtilities.invokeLater(() -> board.displayLose(nick, isYourPlayer));
    }

    @Override
    public void displayActions(List<MessageType> actions) {
        SwingUtilities.invokeLater(() -> board.setVisibleButtons(actions));
    }

    @Override
    public void addConstraint(String name) {
        SwingUtilities.invokeLater(() -> board.displayConstraint(name, false));
    }

    @Override
    public void removeConstraint(String name) {
        SwingUtilities.invokeLater(() -> board.displayConstraint(name, true));
    }

    @Override
    public void onTurnTimerEnded(String stopper) {
        SwingUtilities.invokeLater(() -> {
            constructorPopUp = new PopUp(this, d, stopper);
            board.eliminateAllFromAll();
            popUp.remove(lobbyPanel);
            if (board.getMyName().equalsIgnoreCase(stopper)){
                lobbyPanel = constructorPopUp.lobbyPopUp(6);
            }
            else {
                lobbyPanel = constructorPopUp.lobbyPopUp(5);
            }
            popUp.add(lobbyPanel);
            popUp.setVisible(true);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void onStoppedGame(String stopper) {
        SwingUtilities.invokeLater(() -> {
            constructorPopUp = new PopUp(this, d, stopper);
            board.eliminateAllFromAll();
            popUp.remove(lobbyPanel);
            lobbyPanel = constructorPopUp.lobbyPopUp(4);
            popUp.add(lobbyPanel);
            popUp.setVisible(true);
            popUp.repaint();
            popUp.validate();
        });
    }

    @Override
    public void startTurn(String nick, boolean isYourPlayer) {
        SwingUtilities.invokeLater(() -> board.startTurn(nick, isYourPlayer));
    }
}

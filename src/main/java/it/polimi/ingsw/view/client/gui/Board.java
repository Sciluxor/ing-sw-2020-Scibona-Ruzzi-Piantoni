package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Observable;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;
import static it.polimi.ingsw.view.client.gui.EliminateListeners.*;

public class Board extends Observable {
    Gui gui;
    List<Player> allPlayer = new ArrayList<>();
    List<Player> otherPlayers = new ArrayList<>();
    List<String> cardsChosen = new ArrayList<>();
    List<String> godCards = new ArrayList<>();
    String cardChosen = null;
    String firstPlayer = null;
    JFrame f;
    JDesktopPane desktopPane;
    JDesktopPane challengerChoiceCards;
    JDesktopPane waitChallenger;
    JDesktopPane challengerChoiceFirstPlayer;
    JDesktopPane youChosen;
    JDesktopPane chooseCard;
    JDesktopPane placeWorkers;
    JInternalFrame frameChat = new JInternalFrame("", false, false, false, false);
    JInternalFrame internalFrameChallenger1 = new JInternalFrame("", false, false, false, false);
    JInternalFrame internalFrameChallenger2 = new JInternalFrame("", false, false, false, false);
    JInternalFrame internalFrameChooseCards = new JInternalFrame("", false, false, false, false);
    JInternalFrame internalFramePlaceWorkers = new JInternalFrame("", false, false, false, false);
    JWindow windowPower;
    JScrollPane scrollPane;
    JButton buttonLv1 = new JButton();
    JButton buttonLv2 = new JButton();
    JButton buttonLv3 = new JButton();
    JButton buttonDome = new JButton();
    JButton buttonMove = new JButton();
    JButton buttonBuild = new JButton();
    JButton buttonPower = new JButton();
    JButton buttonChooseCards = new JButton();
    JButton buttonChooseFirst = new JButton();
    JButton buttonChoosePower = new JButton();
    JButton buttonChat = new JButton();
    JButton buttonEndturn = new JButton();
    JButton buttonConfirmPlace = new JButton();
    JButton buttonExit = new JButton();
    JButton backgroundFrameChat = new JButton();
    JButton sfondoFramePower = new JButton();
    JButton leftBoard = new JButton();
    JButton leftGod = new JButton();
    JButton opponent1 = new JButton();
    JTextArea chat = new JTextArea();
    JTextField field = new JTextField();
    private final JButton[] mapButtons = new JButton[25];
    int[] mapButtonslvl = new int[25];
    int[] mapWorker = new int[25];
    boolean[] mapButtonsPlayer = new boolean[25];
    static JLabel playerpower = new JLabel();
    JLabel nicknameLabel = new JLabel();
    JLabel nicknameLabel1 = new JLabel();
    JLabel gID = new JLabel();
    JLabel coverBoard;
    JLabel coverChat;
    JLabel coverBackground;
    JLabel coverLeftBoard;
    JLabel coverLeftGod;
    JLabel background;
    JLabel opponents = new JLabel("Opponents:");
    JLabel worker;
    JLabel workerCyan;
    JLabel workerWhite;
    JLabel workerPurple;
    JLabel lvl1;
    JLabel lvl2;
    JLabel lvl3;
    JLabel lvl1Building;
    JLabel lvl2Building;
    JLabel lvl3Building;
    JLabel domeBuilding;
    JLabel lvl1Worker;
    JLabel lvl2Worker;
    JLabel lvl3Worker;
    JLabel lvl1Cyan;
    JLabel lvl2Cyan;
    JLabel lvl3Cyan;
    JLabel lvl1Purple;
    JLabel lvl2Purple;
    JLabel lvl3Purple;
    JLabel lvl1White;
    JLabel lvl2White;
    JLabel lvl3White;
    JLabel lvl1Dome;
    JLabel lvl2Dome;
    JLabel lvl3Dome;
    JLabel dome;
    JLabel exit;
    JLabel lButtonMove;
    JLabel lButtonBuild;
    JLabel lButtonMovePress;
    JLabel lButtonBuildPress;
    JLabel lButtonPower;
    JLabel lButtonPowerPress;
    JLabel lButtonChat;
    JLabel lButtonChatPress;
    JLabel labelChooseCards = new JLabel("Choose Cards");
    JLabel lButtonChooseCards;
    JLabel lButtonChooseCardsPress;
    JLabel labelChooseFirst = new JLabel("Choose the first player");
    JLabel lbuttonChooseFirst;
    JLabel lbuttonChooseFirstPress;
    JLabel labelChoosePower = new JLabel("Choose your Power");
    JLabel lButtonChoosePower;
    JLabel lButtonChoosePowerPress;
    JLabel labelConfirmPlace = new JLabel("Confirm your positions");
    JLabel labelEndturn = new JLabel("End Turn");
    JLabel lbuttonEndturn;
    JLabel lbuttonEndturnPress;
    Dimension frameSize = new Dimension();
    Dimension boardSize = new Dimension();
    Dimension bottomSize = new Dimension();
    Dimension sideSize = new Dimension();
    Dimension buttonSize = new Dimension();
    Dimension scrollSize = new Dimension();
    Dimension internalFrameSize = new Dimension();
    Dimension internalFrameSize2 = new Dimension();
    Font felixSmall;
    Font felixNormal;
    Font felixBold;
    static final String PALETTE = "JInternalFrame.isPalette";
    Player mePlayer;
    String nickname;
    String nameChoosing;
    int numberOfPlayers = 2;
    it.polimi.ingsw.model.player.Color myColor;
    int placed = 0;

    public void show(Gui instance, Dimension screen, Integer numberOfPlayer, List<Player> players,List<Player> players2, String gameId, String nickname) throws IOException {

        f = new JFrame();
        gui = instance;
        nicknameLabel.setText("Nickname: ");
        nicknameLabel1.setText(nickname);
        gID.setText("GameID: " + gameId);
        this.nickname = nickname;
        numberOfPlayers = numberOfPlayer;
        allPlayer = players;
        otherPlayers = players2;
        mePlayer = pickNickFromPlayers();
        removeNickFromOtherPlayers();
        myColor = mePlayer.getColor();

        double ratio= (screen.getWidth()/screen.getHeight());
        int width = (int) ((screen.getWidth() * 95 / 100) * (1.689999 / ratio));
        int height = (int) (screen.getHeight() * 91 / 100);
        frameSize.setSize(width, height);
        internalFrameSize.setSize(frameSize.width * 90/100, frameSize.height * 90/100);
        internalFrameSize2.setSize(internalFrameSize.width * 70/100, internalFrameSize.height * 70/100);


        boardSize.setSize(frameSize.height * 80/100, frameSize.height * 80/100);
        bottomSize.setSize(frameSize.width, frameSize.height * 20/100);
        sideSize.setSize(width * 27/100, height);
        buttonSize.setSize(sideSize.width * 20/100, sideSize.width * 20/100);
        scrollSize.setSize(sideSize.getWidth() * 14/100 , sideSize.getHeight() * 28/100);

        JLabel coverBoard = ImageHandler.setImage("resources/Graphics/board3.png", 100, 100, width, height);
        this.coverBoard = new JLabel(coverBoard.getIcon());

        JLabel coverChat = ImageHandler.setImage("resources/Graphics/panel_chat.png", 100, 100, sideSize.width, sideSize.height);
        this.coverChat = new JLabel(coverChat.getIcon());

        coverBackground = ImageHandler.setImage("resources/Graphics/background.png", 100, 100, frameSize.width * 40/100, frameSize.height * 45/100);
        background = new JLabel(coverBackground.getIcon());

        JLabel coverLeftBoard = ImageHandler.setImage("resources/Graphics/left_board.png", 100, 100, frameSize.width, frameSize.height);
        this.coverLeftBoard = new JLabel(coverLeftBoard.getIcon());

        JLabel coverLeftGod = ImageHandler.setImage("resources/Graphics/left_god_board.png", 100, 100, frameSize.width, frameSize.height);
        this.coverLeftGod = new JLabel(coverLeftGod.getIcon());

        felixSmall = new Font(Gui.FELIX, Font.PLAIN, (int) (13 * screen.getHeight() / 1080));
        felixNormal = new Font(Gui.FELIX, Font.PLAIN, (int) (20 * screen.getHeight() / 1080));
        felixBold = new Font(Gui.FELIX, Font.BOLD, (int) (25 * screen.getHeight() / 1080));


        workerCyan = ImageHandler.setImage("resources/Graphics/worker_cyan.png", 100, 100, height * 13/100, height * 13/100);
        workerWhite = ImageHandler.setImage("resources/Graphics/worker_white.png", 100, 100, height * 13/100, height * 13/100);
        workerPurple = ImageHandler.setImage("resources/Graphics/worker_purple.png", 100, 100, height * 13/100, height * 13/100);
        lvl1 = ImageHandler.setImage("resources/Graphics/lvl1.png", 85, 85, height * 13/100, height * 13/100);
        lvl2 = ImageHandler.setImage("resources/Graphics/lvl2.png", 85, 85, height * 13/100, height * 13/100);
        lvl3 = ImageHandler.setImage("resources/Graphics/lvl3.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Building = ImageHandler.setImage("resources/Graphics/lvl1_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl2Building = ImageHandler.setImage("resources/Graphics/lvl2_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl3Building = ImageHandler.setImage("resources/Graphics/lvl3_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        domeBuilding = ImageHandler.setImage("resources/Graphics/dome_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl1Cyan = ImageHandler.setImage("resources/Graphics/lvl1_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Cyan = ImageHandler.setImage("resources/Graphics/lvl2_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Cyan = ImageHandler.setImage("resources/Graphics/lvl3_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Purple = ImageHandler.setImage("resources/Graphics/lvl1_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Purple = ImageHandler.setImage("resources/Graphics/lvl2_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Purple = ImageHandler.setImage("resources/Graphics/lvl3_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl1White = ImageHandler.setImage("resources/Graphics/lvl1_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl2White = ImageHandler.setImage("resources/Graphics/lvl2_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl3White = ImageHandler.setImage("resources/Graphics/lvl3_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Dome = ImageHandler.setImage("resources/Graphics/lvl1_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Dome = ImageHandler.setImage("resources/Graphics/lvl2_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Dome = ImageHandler.setImage("resources/Graphics/lvl3_dome.png", 85, 85, height * 13/100, height * 13/100);
        dome = ImageHandler.setImage("resources/Graphics/dome.png", 85, 85, frameSize.width * 5/100, frameSize.width * 5/100);
        exit = ImageHandler.setImage("resources/Graphics/exit.png", 75, 75, sideSize.width * 12/100, sideSize.width * 12/100);
        lButtonBuild = ImageHandler.setImage("resources/Graphics/button_build.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonMove = ImageHandler.setImage("resources/Graphics/button_move.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonPower = ImageHandler.setImage("resources/Graphics/button_power.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonChat = ImageHandler.setImage("resources/Graphics/button_chat.png", 100, 100, frameSize.width * 5/100, frameSize.height * 7/100);
        lButtonChooseCards = ImageHandler.setImage("resources/Graphics/button_choose_cards.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lbuttonChooseFirst = ImageHandler.setImage("resources/Graphics/button_choose_first.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonChoosePower = ImageHandler.setImage("resources/Graphics/button_power.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lbuttonEndturn = ImageHandler.setImage("resources/Graphics/button_endturn.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonBuildPress = ImageHandler.setImage("resources/Graphics/button_build_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonMovePress = ImageHandler.setImage("resources/Graphics/button_move_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonPowerPress = ImageHandler.setImage("resources/Graphics/button_power_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonChatPress = ImageHandler.setImage("resources/Graphics/button_chat_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 7/100);
        lButtonChooseCardsPress = ImageHandler.setImage("resources/Graphics/button_choose_cards_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lbuttonChooseFirstPress = ImageHandler.setImage("resources/Graphics/button_choose_first_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lButtonChoosePowerPress = ImageHandler.setImage("resources/Graphics/button_power_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        lbuttonEndturnPress = ImageHandler.setImage("resources/Graphics/button_endturn_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);

        setMyColorWorkers();

        windowPower = new JWindow();
        windowPower.setBounds((int)(frameSize.width * 35.5/100), (int) (frameSize.height * 37/100), frameSize.width * 40/100, frameSize.height * 45/100);

        setInternalFrames(internalFrameChallenger1);
        BasicInternalFrameUI bi = (BasicInternalFrameUI) internalFrameChallenger1.getUI();
        bi.setNorthPane(null);
        setInternalFrames(internalFrameChallenger2);
        BasicInternalFrameUI bi2 = (BasicInternalFrameUI) internalFrameChallenger2.getUI();
        bi2.setNorthPane(null);
        setInternalFrames(internalFrameChooseCards);
        BasicInternalFrameUI bi3 = (BasicInternalFrameUI) internalFrameChooseCards.getUI();
        bi3.setNorthPane(null);
        setInternalFrames(internalFramePlaceWorkers);
        BasicInternalFrameUI bi4 = (BasicInternalFrameUI) internalFrameChooseCards.getUI();
        bi4.setNorthPane(null);


        frameChat.setPreferredSize(sideSize);
        frameChat.setBounds(frameSize.width * 73/100, -20, sideSize.width, sideSize.height);
        internalFrameSetUp(frameChat);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)frameChat.getUI();
        bii.setNorthPane(null);


        desktopPane = new JDesktopPane(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage( ((ImageIcon)(Board.this.coverBoard.getIcon())).getImage(), -7, -18, null);
            }};

        desktopPane.setPreferredSize(frameSize);

        leftGod.setBounds(-7,-18, frameSize.width, frameSize.height);
        leftGod.setOpaque(false);
        leftGod.setContentAreaFilled(false);
        leftGod.setBorderPainted(false);
        leftGod.setIcon(coverLeftGod.getIcon());
        leftGod.setVisible(true);


        leftBoard.setBounds(-7,-18, frameSize.width, frameSize.height);
        leftBoard.setOpaque(false);
        leftBoard.setContentAreaFilled(false);
        leftBoard.setBorderPainted(false);
        leftBoard.setIcon(coverLeftBoard.getIcon());
        leftBoard.setVisible(true);




        nicknameLabel.setBounds((int) (frameSize.width * 3.5/100), (int) (frameSize.height * 2.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        nicknameLabel.setFont(felixNormal);
        nicknameLabel1.setBounds((int) (frameSize.width * 10.5/100), (int) (frameSize.height * 2.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        nicknameLabel1.setFont(felixNormal);
        nicknameLabel1.setForeground(getColorPlayer(mePlayer));
        desktopPane.add(nicknameLabel);
        desktopPane.add(nicknameLabel1);

        gID.setBounds((int) (frameSize.width * 3.5/100), (int) (frameSize.height * 4.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        gID.setFont(felixNormal);
        desktopPane.add(gID);

        opponents.setBounds((frameSize.width * 2/100), (frameSize.height * 55/100), frameSize.width * 20/100, frameSize.width * 5/100);
        opponents.setFont(felixNormal);
        desktopPane.add(opponents);

        opponent1.setText(otherPlayers.get(0).getNickname());
        opponent1.setBounds((frameSize.width * 3/100), (frameSize.height * 61/100), frameSize.width * 15/100, frameSize.height * 4/100);
        opponent1.setForeground(getColorPlayer(otherPlayers.get(0)));
        //opponent1.setName(returnGodChoosen().get(1).getName());
        opponentsButton(opponent1);
        if (numberOfPlayer == 3){
            JButton opponent2 = new JButton(otherPlayers.get(1).getNickname());
            //opponent2.setName(returnGodChoosen().get(2).getName());
            opponent2.setBounds((frameSize.width * 3/100), (frameSize.height * 64/100), frameSize.width * 15/100, frameSize.height * 4/100);
            opponent2.setForeground(getColorPlayer(otherPlayers.get(1)));
            opponentsButton(opponent2);
        }

        chat.setBounds(frameChat.getWidth() * 22/100 , frameChat.getHeight() * 28/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 38/100);
        chat.setEditable(false);
        chat.setBackground(new Color(232, 222, 208));
        chat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chat.setFont(felixSmall);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setVisible(true);
        scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(scrollSize);
        scrollPane.setBounds(frameChat.getWidth() * 22/100 , frameChat.getHeight() * 28/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 38/100);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frameChat.add(scrollPane);

        field.setBounds(frameChat.getWidth() * 22/100, frameChat.getHeight() * 66/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 4/100);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        field.setBackground(new Color(232, 222, 208));
        field.addActionListener(new Write());
        field.setFont(felixSmall);
        field.setVisible(true);
        frameChat.add(field);

        buttonExit.setBounds((frameChat.getWidth() * 82/100), (frameChat.getHeight() * 2/100), frameChat.getWidth() * 12/100, frameChat.getWidth() * 12/100);
        buttonExit.addActionListener(new ChatExit());
        chatStyleButtons(buttonExit, exit);
        frameChat.add(buttonExit);

        chatStyleButtons(backgroundFrameChat, this.coverChat);
        frameChat.getContentPane().add(backgroundFrameChat);


        chatStyleButtons(sfondoFramePower, background);
        windowPower.getContentPane().add(sfondoFramePower);

        //windowChallenger.getContentPane().add(sfondoFramePower);


        resetLevel();

        resetPlayer();

        resetWorkers();

        mapButtonStyle();

        try{
            String os = System.getProperty("os.name").toLowerCase();

            if(os.contains("win")) {
                //windows positions
                mapButtons[0].setBounds((int) (frameSize.width * (29.5)/100) , frameSize.width * 7/100,
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[1].setBounds((int) (frameSize.width * (37.75)/100) , frameSize.width * 7/100,
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[2].setBounds(frameSize.width * 46/100 , frameSize.width * 7/100,
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[3].setBounds((int) (frameSize.width * (54.25)/100) , frameSize.width * 7/100,
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[4].setBounds((int) (frameSize.width * (62.5)/100) , frameSize.width * 7/100,
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[5].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[6].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[7].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[8].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[9].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[10].setBounds(frameSize.width * 46/100 , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[11].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[12].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[13].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[14].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[15].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[16].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[17].setBounds(frameSize.width * 46/100 , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[18].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[19].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[20].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[21].setBounds(frameSize.width * 46/100 , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[22].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[23].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[24].setBounds(frameSize.width * 46/100 , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
            }
            else {
                //mac positions
                mapButtons[0].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (7.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[1].setBounds((int) (frameSize.width * (37.8)/100) , (int) (frameSize.width * (7.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[2].setBounds((int) (frameSize.width * (46.1)/100) , (int) (frameSize.width * (7.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[3].setBounds((int) (frameSize.width * (54.4)/100) , (int) (frameSize.width * (7.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[4].setBounds((int) (frameSize.width * (62.7)/100) , (int) (frameSize.width * (7.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[5].setBounds((int) (frameSize.width * (62.7)/100) , (int) (frameSize.width * (15.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[6].setBounds((int) (frameSize.width * (62.7)/100) , (int) (frameSize.width * (24.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[7].setBounds((int) (frameSize.width * (62.7)/100) , (int) (frameSize.width * (32.5)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[8].setBounds((int) (frameSize.width * (62.7)/100) , (int) (frameSize.width * (40.8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[9].setBounds((int) (frameSize.width * (54.4)/100) , (int) (frameSize.width * (40.8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[10].setBounds((int) (frameSize.width * (46.1)/100) , (int) (frameSize.width * (40.8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[11].setBounds((int) (frameSize.width * (37.8)/100) , (int) (frameSize.width * (40.8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[12].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (40.8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[13].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (32.5)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[14].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (24.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[15].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (15.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[16].setBounds((int) (frameSize.width * (37.8)/100) , (int) (frameSize.width * (15.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[17].setBounds((int) (frameSize.width * (46.1)/100) , (int) (frameSize.width * (15.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[18].setBounds((int) (frameSize.width * (54.4)/100) , (int) (frameSize.width * (15.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[19].setBounds((int) (frameSize.width * (54.4)/100) , (int) (frameSize.width * (24.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[20].setBounds((int) (frameSize.width * (54.4)/100) , (int) (frameSize.width * (32.5)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[21].setBounds((int) (frameSize.width * (46.1)/100) , (int) (frameSize.width * (32.5)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[22].setBounds((int) (frameSize.width * (37.8)/100) , (int) (frameSize.width * (32.5)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[23].setBounds((int) (frameSize.width * (37.8)/100) , (int) (frameSize.width * (24.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);

                mapButtons[24].setBounds((int) (frameSize.width * (46.1)/100) , (int) (frameSize.width * (24.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
            }
        }
        catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }


        addMapButton();


        buttonLv1.setBounds(frameSize.width * 81/100, frameSize.height * 19/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv1.setIcon(lvl1Building.getIcon());
        buttonLv1.setVisible(false);
        desktopPane.add(buttonLv1);

        buttonLv2.setBounds(frameSize.width * 89/100, frameSize.height * 19/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv2.setIcon(lvl2Building.getIcon());
        buttonLv2.setVisible(false);
        desktopPane.add(buttonLv2);

        buttonLv3.setBounds(frameSize.width * 81/100, frameSize.height * 31/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv3.setIcon(lvl3Building.getIcon());
        buttonLv3.setVisible(false);
        desktopPane.add(buttonLv3);

        buttonDome.setBounds(frameSize.width * 89/100, frameSize.height * 31/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonDome.setIcon(domeBuilding.getIcon());
        buttonDome.setVisible(false);
        buttonDome.setBorderPainted(false);
        buttonDome.setContentAreaFilled(false);
        desktopPane.add(buttonDome);


        buttonMove.setBounds(frameSize.width * 79/100, frameSize.height * 46/100, frameSize.width * 7/100, frameSize.height * 7/100);
        buttonMove.addActionListener(new AddPlaceWorker());
        consoleButtons(buttonMove, lButtonMove);

        buttonBuild.setBounds(frameSize.width * 85/100, frameSize.height * 46/100, frameSize.width * 7/100, frameSize.height * 7/100);
        buttonBuild.addActionListener(new AddBuildLvl());
        consoleButtons(buttonBuild, lButtonBuild);

        buttonPower.setBounds(frameSize.width * 81/100, frameSize.height * 59/100, frameSize.width * 5/100, frameSize.height * 5/100);
        consoleButtons(buttonPower, lButtonPower);

        buttonChat.setBounds(frameSize.width * 89/100, frameSize.height * 58/100, frameSize.width * 5/100, frameSize.height * 7/100);
        buttonChat.addActionListener(new Chat());
        consoleButtons(buttonChat, lButtonChat);
        buttonChat.setVisible(true);

        labelChooseCards.setBounds((int) (frameSize.width * 82.5/100), (int) (frameSize.height * 15.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        labelChooseCards.setFont(felixNormal);
        labelChooseCards.setVisible(false);
        desktopPane.add(labelChooseCards);
        buttonChooseCards.setBounds((int) (frameSize.width * 84/100), (int) (frameSize.height * 22/100), frameSize.width * 7/100, frameSize.height * 7/100);
        consoleButtons(buttonChooseCards, lButtonChooseCards);

        labelChooseFirst.setBounds((int) (frameSize.width * 80/100), (int) (frameSize.height * 27.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        labelChooseFirst.setFont(felixNormal);
        labelChooseFirst.setVisible(false);
        desktopPane.add(labelChooseFirst);
        buttonChooseFirst.setBounds((int) (frameSize.width * 84/100), (int) (frameSize.height * 34/100), frameSize.width * 7/100, frameSize.height * 7/100);
        consoleButtons(buttonChooseFirst, lbuttonChooseFirst);

        labelChoosePower.setBounds((int) (frameSize.width * 81/100), (int) (frameSize.height * 15.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        labelChoosePower.setFont(felixNormal);
        labelChoosePower.setVisible(false);
        desktopPane.add(labelChoosePower);
        buttonChoosePower.setBounds((int) (frameSize.width * 84/100), (int) (frameSize.height * 22/100), frameSize.width * 7/100, frameSize.height * 7/100);
        consoleButtons(buttonChoosePower, lButtonChoosePower);
        buttonChoosePower.setVisible(false);

        labelConfirmPlace.setBounds((int) (frameSize.width * 80/100), (int) (frameSize.height * 15.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        labelConfirmPlace.setFont(felixNormal);
        labelConfirmPlace.setVisible(false);
        desktopPane.add(labelConfirmPlace);
        buttonConfirmPlace.setBounds((int) (frameSize.width * 84/100), (int) (frameSize.height * 22/100), frameSize.width * 7/100, frameSize.height * 7/100);
        consoleButtons(buttonConfirmPlace, lButtonMove);


        labelEndturn.setBounds((int) (frameSize.width * 84.25/100), (int) (frameSize.height * 15.5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        labelEndturn.setFont(felixNormal);
        labelEndturn.setVisible(false);
        desktopPane.add(labelEndturn);
        buttonEndturn.setBounds((int) (frameSize.width * 84/100), (int) (frameSize.height * 22/100), frameSize.width * 7/100, frameSize.height * 7/100);
        consoleButtons(buttonEndturn, lbuttonEndturn);
        buttonEndturn.addActionListener(new EndTurn());



        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setPreferredSize(frameSize);

        frameChat.setVisible(false);
        desktopPane.add(frameChat);
        desktopPane.add(internalFrameChallenger1);
        desktopPane.add(internalFrameChallenger2);
        desktopPane.add(internalFrameChooseCards);
        desktopPane.add(internalFramePlaceWorkers);

        desktopPane.add(leftBoard);
        desktopPane.add(leftGod);

        f.setContentPane(desktopPane);

        SwingUtilities.updateComponentTreeUI(f);
        f.pack();

        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    private void setMyColorWorkers(){
        if (myColor.toString().equalsIgnoreCase("BLUE")){
            worker = workerCyan;
            lvl1Worker = lvl1Cyan;
            lvl2Worker = lvl2Cyan;
            lvl3Worker = lvl3Cyan;
        }
        else if (myColor.toString().equalsIgnoreCase("WHITE")){
            worker = workerWhite;
            lvl1Worker = lvl1White;
            lvl2Worker = lvl2White;
            lvl3Worker = lvl3White;
        }
        else if (myColor.toString().equalsIgnoreCase("PURPLE")){
            worker = workerPurple;
            lvl1Worker = lvl1Purple;
            lvl2Worker = lvl2Purple;
            lvl3Worker = lvl3Purple;
        }
    }

    private void setInternalFrames(JInternalFrame i){
        i.setPreferredSize(sideSize);
        i.setBounds((int)((frameSize.width * 50/100) - (internalFrameSize.width * 50/100)), (int) ((frameSize.height * 46/100) - (internalFrameSize.height * 50/100)), internalFrameSize.width, internalFrameSize.height);
        internalFrameSetUp(i);

    }

    private Color getColorPlayer(Player player){
        if(player.getColor().toString().equalsIgnoreCase("BLUE")){
            return Color.BLUE;
        }
        else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
            return Color.WHITE;
        }
        else {
            return Color.MAGENTA;
        }
    }

    public static void internalFrameSetUp(JInternalFrame intFrame){
        intFrame.putClientProperty(PALETTE, Boolean.TRUE);
        intFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        intFrame.setOpaque(false);
        intFrame.putClientProperty(PALETTE, Boolean.TRUE);
        intFrame.setBorder(null);
    }

    private void opponentsButton(JButton button){
        button.setFont(felixNormal);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addMouseListener(new SeePower());
        desktopPane.add(button);

    }

    private void chatStyleButtons(JButton button, JLabel label){
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setIcon(label.getIcon());
    }

    private void addMapButton(){
        for (int x = 0; x < 25; x++){
            desktopPane.add(mapButtons[x]);
        }
    }

    private void resetPlayer(){
        for (int x = 0; x < 25; x++){
            mapButtonsPlayer[x] = false;
        }
    }

    private void resetLevel(){
        for (int x = 0; x < 25; x++){
            mapButtonslvl[x] = 0;
        }
    }

    private void resetWorkers(){
        for (int x = 0; x < 25; x++){
            mapWorker[x] = 0;
        }
    }

    private void consoleButtons(JButton button, JLabel label){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(label.getIcon());
        button.addMouseListener(new ButtonPress());
        button.setVisible(false);
        desktopPane.add(button);
    }

    private void mapButtonStyle(){
        for (int x = 0; x < 25; x++){
            mapButtons[x] = new JButton();
            mapButtons[x].setContentAreaFilled(false);
            mapButtons[x].setOpaque(false);
            mapButtons[x].setBorderPainted(false);
            mapButtons[x].addMouseListener(new ColorBorder());
        }
    }

    private void removeNickFromOtherPlayers(){
        otherPlayers.removeIf(player -> player.getNickname().equalsIgnoreCase(nickname));
    }

    private Player pickNickFromPlayers(){
        for (Player player : otherPlayers){
            if (player.getNickname().equalsIgnoreCase(nickname))
                return player;
        }
        return null;
    }

    public void showChallenger(String name, boolean bool) {
        if (bool){
            //internalFrameChallenger.getContentPane().add(challengerChoiceCards);
            try {
                youChosen = new YouHaveBeenChosen(internalFrameChallenger1, internalFrameSize2);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            internalFrameChallenger1.setBounds((int)((frameSize.width * 50/100) - (internalFrameSize2.width * 50/100)), (int) ((frameSize.height * 46/100) - (internalFrameSize2.height * 50/100)), internalFrameSize2.width, internalFrameSize2.height);
            internalFrameChallenger1.getContentPane().add(youChosen);
            internalFrameChallenger1.setVisible(true);
            buttonChooseCards.setVisible(true);
            labelChooseCards.setVisible(true);
            buttonChooseCards.addActionListener(new ChooseCards());
            buttonChooseFirst.setVisible(true);
            labelChooseFirst.setVisible(true);
            buttonChooseFirst.addActionListener(new ChooseFirst());
        }
        else{
            try {
                waitChallenger = new WaitChallenger(internalFrameChallenger1, frameSize.width * 40/100, frameSize.height * 45/100);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            internalFrameChallenger1.setBounds((int) (frameSize.width * 29.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 40/100, frameSize.height * 45/100);
            internalFrameChallenger1.getContentPane().add(waitChallenger);
            internalFrameChallenger1.setVisible(true);
        }


        //buttonChooseFirst.setEnabled(false);
    }

    private class Write implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!field.getText().equals("")) {
                chat.append(mePlayer.getNickname() + ": " + field.getText().toLowerCase() + "\n");
                chat.setCaretPosition(chat.getDocument().getLength());
                field.setText("");
            }
        }
    }

    private class ChooseCards implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            internalFrameChallenger1.remove(youChosen);
            try {
                challengerChoiceCards = new ChallengerChoiceCards(gui, gui.board, internalFrameChallenger1, internalFrameSize, numberOfPlayers);
            } catch (IOException ie) {
                LOGGER.severe(ie.getMessage());
            }
            internalFrameChallenger1.setBounds((int)((frameSize.width * 50/100) - (internalFrameSize.width * 50/100)), (int) ((frameSize.height * 46/100) - (internalFrameSize.height * 50/100)), internalFrameSize.width, internalFrameSize.height);
            internalFrameChallenger1.getContentPane().add(challengerChoiceCards);
            internalFrameChallenger1.setVisible(true);
        }
    }

    private class ChooseFirst implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                challengerChoiceFirstPlayer = new ChallengerChoiceFirstPlayer(gui, gui.board, internalFrameChallenger2, internalFrameSize, numberOfPlayers, allPlayer);
            } catch (IOException ie) {
                LOGGER.severe(ie.getMessage());
            }
            internalFrameChallenger2.getContentPane().add(challengerChoiceFirstPlayer);
            internalFrameChallenger2.setVisible(true);
        }
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setCardsChosen(List<String> cardsChosen) {
        this.cardsChosen = cardsChosen;
    }

    public void callChallengerResponse(){
        if (cardsChosen.size()!=0 && firstPlayer != null){
            gui.challengerResponse(firstPlayer, cardsChosen);
            buttonChooseCards.setVisible(false);
            labelChooseCards.setVisible(false);
            buttonChooseFirst.setVisible(false);
            labelChooseFirst.setVisible(false);
            showEndturn();
        }
    }

    public void showCardChoice(List<String> cards, String name, boolean bool){

        internalFrameChallenger1.dispose();
        internalFrameChallenger2.dispose();
        godCards = cards;
        nameChoosing = name;

        if (chooseCard != null) {
            internalFrameChooseCards.remove(chooseCard);
        }
        if (bool) {
            try {
                chooseCard = new ChooseCard(this, internalFrameChooseCards, frameSize.width * 40/100, frameSize.height * 45/100, cards, 4, name);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            internalFrameChooseCards.setBounds((int) (frameSize.width * 29.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 40/100, frameSize.height * 45/100);
            buttonChoosePower.setVisible(true);
            labelChoosePower.setVisible(true);
            buttonChoosePower.addActionListener(new ChoosePower());
        }
        else {
            try {
                chooseCard = new ChooseCard(this, internalFrameChooseCards, frameSize.width * 40/100, frameSize.height * 45/100, cards, 0, name);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            internalFrameChooseCards.setBounds((int) (frameSize.width * 29.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 40/100, frameSize.height * 45/100);
        }
        internalFrameChooseCards.getContentPane().add(chooseCard);
        internalFrameChooseCards.setVisible(true);
    }

    public void callCardChoiseResponse(){
        if (cardChosen != null){
            gui.cardChoiceResponse(cardChosen);
            buttonChoosePower.setVisible(false);
            labelChoosePower.setVisible(false);
            buttonPower.setName(cardChosen);
            buttonPower.addActionListener(new ShowPower());
            buttonPower.setVisible(true);
            try {
                coverLeftGod = ImageHandler.setImage("resources/Graphics/gods/" + cardChosen + "_left.png", 100, 100, frameSize.width, frameSize.height);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            leftGod.setIcon(coverLeftGod.getIcon());
            showEndturn();
        }
    }


    public void setCardChosen(String cardChosen) {
        this.cardChosen = cardChosen;
    }

    private class ChoosePower implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            internalFrameChooseCards.remove(chooseCard);
            try {
                chooseCard = new ChooseCard(gui.board, internalFrameChooseCards, internalFrameSize.width, internalFrameSize.height, godCards, godCards.size(), nameChoosing);
            } catch (IOException ie) {
                LOGGER.severe(ie.getMessage());
            }
            internalFrameChooseCards.setBounds((int)((frameSize.width * 50/100) - (internalFrameSize.width * 50/100)), (int) ((frameSize.height * 46/100) - (internalFrameSize.height * 50/100)), internalFrameSize.width, internalFrameSize.height);
            internalFrameChooseCards.getContentPane().add(chooseCard);
            internalFrameChooseCards.setVisible(true);
        }
    }

    public void showPlaceWorkers(String name, boolean bool){
        internalFrameChooseCards.dispose();
        if (placeWorkers != null){
            internalFramePlaceWorkers.remove(placeWorkers);
        }
        if (bool){
            try {
                placeWorkers = new PlaceWorkers(internalFramePlaceWorkers, frameSize.width * 40/100, frameSize.height * 45/100, 0);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }
            labelConfirmPlace.setVisible(true);
            buttonConfirmPlace.setVisible(true);
            buttonConfirmPlace.setEnabled(false);
            addPlaceMove();
        }
        else{
            try {
                placeWorkers = new PlaceWorkers(internalFramePlaceWorkers, frameSize.width * 40/100, frameSize.height * 45/100, 1);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
            }

        }
        internalFramePlaceWorkers.setBounds((int) (frameSize.width * 29.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 40/100, frameSize.height * 45/100);
        internalFramePlaceWorkers.getContentPane().add(placeWorkers);
        internalFramePlaceWorkers.setVisible(true);
    }

    public void updateWorkers(List<Square> squares){
        for (Square square : squares){
            if(square.getPlayer().getColor().toString().equalsIgnoreCase("BLUE")){
                mapButtons[square.getTile() - 1].setIcon(workerCyan.getIcon());
            }
            else if(square.getPlayer().getColor().toString().equalsIgnoreCase("WHITE")){
                mapButtons[square.getTile() - 1].setIcon(workerWhite.getIcon());
            }
            else {
                mapButtons[square.getTile() - 1].setIcon(workerPurple.getIcon());
            }

        }

    }

    private void showEndturn(){
        labelEndturn.setVisible(true);
        buttonEndturn.setVisible(true);
    }

    private void hideEndturn(){
        labelEndturn.setVisible(false);
        buttonEndturn.setVisible(false);
    }


    private class EndTurn implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.endTurn();
            hideEndturn();
        }
    }

    private class Chat implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frameChat.setVisible(true);
        }
    }

    private class ChatExit implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frameChat.setVisible(false);
        }
    }

    private class AddBuildLvl implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            buttonLv1.addActionListener(new AddBuildLvl1());
            buttonLv2.addActionListener(new AddBuildLvl2());
            buttonLv3.addActionListener(new AddBuildLvl3());
            buttonDome.addActionListener(new AddBuildDome());
            buttonLv1.setVisible(true);
            buttonLv2.setVisible(true);
            buttonLv3.setVisible(true);
            buttonDome.setVisible(true);
            buttonMove.setVisible(false);
        }
    }

    private void removeBuildLvl() {
        for (int y = 0; y < buttonLv1.getActionListeners().length; y++){
            if (buttonLv1.getActionListeners()[y].getClass().equals(BuildLvl1.class))
                buttonLv1.addActionListener(new BuildLvl1());

        }
        for (int y = 0; y < buttonLv2.getActionListeners().length; y++){
            if (buttonLv2.getActionListeners()[y].getClass().equals(BuildLvl2.class))
                buttonLv2.addActionListener(new BuildLvl2());

        }
        for (int y = 0; y < buttonLv3.getActionListeners().length; y++){
            if (buttonLv3.getActionListeners()[y].getClass().equals(BuildLvl3.class))
                buttonLv3.addActionListener(new BuildLvl3());

        }
        for (int y = 0; y < buttonDome.getActionListeners().length; y++){
            if (buttonDome.getActionListeners()[y].getClass().equals(BuildDome.class))
                buttonDome.addActionListener(new BuildDome());

        }
    }

    private class AddBuildLvl1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int x = 0; x < 25; x++){

                if (mapButtonslvl[x] == 0 && !mapButtonsPlayer[x]){
                    mapButtons[x].addActionListener(new BuildLvl1());
                }
            }
        }
    }

    private class BuildLvl1 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton c = (JButton) e.getSource();
            for (int x = 0; x < 25; x++){

                if (mapButtons[x] == c){
                    c.setIcon(lvl1.getIcon());
                    mapButtonslvl[x] = 1;
                }

            }
            removeBuild();
            removeBuildLvl();
        }
    }

    private class AddBuildLvl2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int x = 0; x < 25; x++){

                if (mapButtonslvl[x] == 1 && !mapButtonsPlayer[x]){
                    mapButtons[x].addActionListener(new BuildLvl2());
                }
            }
        }
    }

    private class BuildLvl2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton c = (JButton) e.getSource();
            for (int x = 0; x < 25; x++){

                if (mapButtons[x] == c){
                    c.setIcon(lvl2.getIcon());
                    mapButtonslvl[x] = 2;
                }

            }
            removeBuild();
            removeBuildLvl();
        }
    }

    private class AddBuildLvl3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int x = 0; x < 25; x++){

                if (mapButtonslvl[x] == 2 && !mapButtonsPlayer[x]){
                    mapButtons[x].addActionListener(new BuildLvl3());
                }
            }
        }
    }

    private class BuildLvl3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton c = (JButton) e.getSource();
            for (int x = 0; x < 25; x++){

                if (mapButtons[x] == c){
                    c.setIcon(lvl3.getIcon());
                    mapButtonslvl[x] = 3;
                }

            }
            removeBuild();
            removeBuildLvl();
        }
    }

    private class AddBuildDome implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int x = 0; x < 25; x++){

                if (mapButtonslvl[x] == 3 && !mapButtonsPlayer[x]){
                    mapButtons[x].addActionListener(new BuildDome());
                }
            }
        }
    }

    private class BuildDome implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton c = (JButton) e.getSource();
            for (int x = 0; x < 25; x++){

                if (mapButtons[x] == c && !mapButtonsPlayer[x]){
                    c.setIcon(lvl3Dome.getIcon());
                    mapButtonslvl[x] = 4;
                }

            }
            removeBuild();
            removeBuildLvl();
        }
    }

    private void removeBuild() {
        for (int x = 0; x < 25; x++){
            for (int y = 0; y < mapButtons[x].getActionListeners().length; y++){
                if (mapButtons[x].getActionListeners()[y].getClass().equals(BuildLvl1.class)  || mapButtons[x].getActionListeners()[y].getClass().equals(BuildLvl2.class) ||
                        mapButtons[x].getActionListeners()[y].getClass().equals(BuildLvl3.class)  || mapButtons[x].getActionListeners()[y].getClass().equals(BuildDome.class))
                    mapButtons[x].removeActionListener(mapButtons[x].getActionListeners()[y]);
            }
        }
        buttonMove.setEnabled(true);
    }

    private void addPlaceMove(){
        buttonBuild.setEnabled(false);
        for (int x = 0; x < 25; x++){
            if (!mapButtonsPlayer[x] && mapButtonslvl[x] != 4)
                mapButtons[x].addActionListener(new PlaceWorker());
        }
    }

    private class AddPlaceWorker implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            buttonBuild.setEnabled(false);
            for (int x = 0; x < 25; x++){
                if (!mapButtonsPlayer[x] && mapButtonslvl[x] != 4)
                        mapButtons[x].addActionListener(new PlaceWorker());
            }
        }
    }

    private class PlaceWorker implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (placed <= 2) {
                JButton c = (JButton) e.getSource();
                for (int x = 0; x < 25; x++) {

                    if (mapButtons[x] == c) {

                        if (!mapButtonsPlayer[x] && placed < 2) {
                            switch (mapButtonslvl[x]) {
                                case 0:
                                    c.setIcon(worker.getIcon());
                                    mapButtonsPlayer[x] = true;
                                    break;
                                case 1:
                                    c.setIcon(lvl1Worker.getIcon());
                                    mapButtonsPlayer[x] = true;
                                    break;
                                case 2:
                                    c.setIcon(lvl2Worker.getIcon());
                                    mapButtonsPlayer[x] = true;
                                    break;
                                case 3:
                                    c.setIcon(lvl3Worker.getIcon());
                                    mapButtonsPlayer[x] = true;
                                    break;
                                default:
                            }
                            placed++;
                            mapWorker[x] = placed;
                        }
                        else if (mapButtonsPlayer[x]){
                            switch (mapButtonslvl[x]) {
                                case 0:
                                    c.setIcon(null);
                                    mapButtonsPlayer[x] = false;
                                    break;
                                case 1:
                                    c.setIcon(lvl1.getIcon());
                                    mapButtonsPlayer[x] = false;
                                    break;
                                case 2:
                                    c.setIcon(lvl2.getIcon());
                                    mapButtonsPlayer[x] = false;
                                    break;
                                case 3:
                                    c.setIcon(lvl3.getIcon());
                                    mapButtonsPlayer[x] = false;
                                    break;
                                default:
                            }
                            placed--;
                            mapWorker[x] = 0;
                            buttonConfirmPlace.setEnabled(false);
                        }
                    }
                }
                if (placed == 2) {
                    buttonBuild.setEnabled(true);
                    buttonConfirmPlace.setEnabled(true);
                    buttonConfirmPlace.addActionListener(new ConfirmPlace());
                }
            }
        }
    }
    private void  removePlaceWorker(){
        for (int x = 0; x < 25; x++) {
            eliminateActionClass(mapButtons[x], PlaceWorker.class);
        }
    }

    private class ConfirmPlace implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Integer> tiles;
            tiles = modifiedTiles();
            gui.placeWorkersResponse(tiles.get(0) + 1, tiles.get(1) + 1);
            buttonConfirmPlace.setVisible(false);
            labelConfirmPlace.setVisible(false);
            removePlaceWorker();
            showEndturn();
        }
    }

    private List<Integer> modifiedTiles(){
        List<Integer> tiles = new ArrayList<>();
        for (int x = 0; x < 25; x++){
            if (mapWorker[x] != 0){
                tiles.add(x);
            }
        }
        return tiles;
    }

    private static class ColorBorder extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
            c.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setBorderPainted(false);
        }
    }

    private class ButtonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton) e.getSource();
            if (buttonMove == c || buttonConfirmPlace == c) {
                buttonMove.setIcon(lButtonMovePress.getIcon());
            }
            else if (buttonBuild == c) {
                buttonBuild.setIcon(lButtonBuildPress.getIcon());
            }
            else if (buttonPower == c) {
                buttonPower.setIcon(lButtonPowerPress.getIcon());
            }
            else if (buttonChat == c){
                buttonChat.setIcon(lButtonChatPress.getIcon());
            }
            else if (buttonChooseCards == c) {
                buttonChooseCards.setIcon(lButtonChooseCardsPress.getIcon());
            }
            else if (buttonChooseFirst == c) {
                buttonChooseFirst.setIcon(lbuttonChooseFirstPress.getIcon());
            }
            else if (buttonChoosePower == c) {
                buttonChoosePower.setIcon(lButtonChoosePowerPress.getIcon());
            }
            else if (buttonEndturn == c) {
                buttonEndturn.setIcon(lbuttonEndturnPress.getIcon());
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();

            if (buttonMove == c || buttonConfirmPlace == c){
                buttonMove.setIcon(lButtonMove.getIcon());
            }
            else if (buttonBuild == c){
                buttonBuild.setIcon(lButtonBuild.getIcon());
            }
            else if (buttonPower == c){
                buttonPower.setIcon(lButtonPower.getIcon());
            }
            else if(buttonChat == c) {
                buttonChat.setIcon(lButtonChat.getIcon());
            }
            else if (buttonChooseCards == c) {
                buttonChooseCards.setIcon(lButtonChooseCards.getIcon());
            }
            else if (buttonChooseFirst == c) {
                buttonChooseFirst.setIcon(lbuttonChooseFirst.getIcon());
            }
            else if (buttonChoosePower == c) {
                buttonChoosePower.setIcon(lButtonChoosePower.getIcon());
            }
            else if (buttonEndturn == c) {
                buttonEndturn.setIcon(lbuttonEndturn.getIcon());
            }
        }
    }


    private class SeePower extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            /*try {
                cover2 = ImageHandler.setImage("resources/Graphics/gods/" + c.getName() + "_description.png", 100, 100, frameSize.width * 40/100, frameSize.height * 45/100);
            } catch (IOException ioException) {
                LOGGER.severe(ioException.getMessage());
            }
            background = new JLabel(cover2.getIcon());
            framePower.getContentPane().removeAll();
            chatStyleButtons(sfondoFramePower, background);
            framePower.getContentPane().add(sfondoFramePower);*/
            windowPower.setVisible(true);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            windowPower.setVisible(false);
        }
    }

    private class ShowPower implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, Board.ShowPower.class);
            try {
                coverBackground = ImageHandler.setImage("resources/Graphics/gods/" + c.getName() + "_description.png", 100, 100, frameSize.width * 40/100, frameSize.height * 45/100);
            } catch (IOException ioException) {
                LOGGER.severe(ioException.getMessage());
            }
            background = new JLabel(coverBackground.getIcon());
            windowPower.getContentPane().removeAll();
            chatStyleButtons(sfondoFramePower, background);
            windowPower.getContentPane().add(sfondoFramePower);
            c.addActionListener(new HidePower());
            windowPower.setVisible(true);
        }
    }
    private class HidePower implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, Board.HidePower.class);
            c.addActionListener(new ShowPower());
            windowPower.setVisible(false);
        }
    }
}

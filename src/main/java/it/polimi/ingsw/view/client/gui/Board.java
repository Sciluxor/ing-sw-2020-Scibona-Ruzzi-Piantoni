package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Observable;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.ChallengerChoiseCards.returnGodChoosen;
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;
import static it.polimi.ingsw.view.client.gui.EliminateListeners.*;

public class Board extends Observable {
    List<Player> allPlayer = new ArrayList<>();
    JFrame f;
    JDesktopPane desktopPane;
    JInternalFrame frameChat;
    JWindow framePower;
    JScrollPane scrollPane;
    JButton buttonLv1 = new JButton();
    JButton buttonLv2 = new JButton();
    JButton buttonLv3 = new JButton();
    JButton buttonDome = new JButton();
    JButton buttonMove = new JButton();
    JButton buttonBuild = new JButton();
    JButton buttonPower = new JButton();
    JButton buttonChat = new JButton();
    JButton buttonExit = new JButton();
    JButton sfondoFrame = new JButton();
    JButton sfondoFramePower = new JButton();
    JButton opponent1 = new JButton();
    JTextArea chat = new JTextArea();
    JTextField field = new JTextField();
    private final JButton[] mapButtons = new JButton[25];
    int[] mapButtonslvl = new int[25];
    boolean[] mapButtonsPlayer = new boolean[25];
    static JLabel playerpower = new JLabel();
    JLabel nicknameLabel = new JLabel();
    JLabel gID = new JLabel();
    JLabel sfondo;
    JLabel sfondo2;
    JLabel cover2;
    JLabel background;
    JLabel opponents = new JLabel("Opponents:");
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
    private boolean chatOpen = false;
    Dimension frameSize = new Dimension();
    Dimension boardSize = new Dimension();
    Dimension bottomSize = new Dimension();
    Dimension sideSize = new Dimension();
    Dimension buttonSize = new Dimension();
    Dimension scrollSize = new Dimension();
    Font felixSmall;
    Font felixNormal;
    Font felixBold;
    static final String PALETTE = "JInternalFrame.isPalette";

    public void show(Dimension screen, Integer numberOfPlayer, final List<Player> players, String gameId) throws IOException {

        f = new JFrame();

        nicknameLabel.setText("Nickname: " + players.get(0).getNickname());
        gID.setText("GameID: " + gameId);
        opponent1.setText(players.get(1).getNickname());
        allPlayer = players;

        double ratio= (screen.getWidth()/screen.getHeight());
        int width = (int) ((screen.getWidth() * 95 / 100) * (1.689999 / ratio));
        int height = (int) (screen.getHeight() * 91 / 100);
        frameSize.setSize(width, height);


        boardSize.setSize(frameSize.height * 80/100, frameSize.height * 80/100);
        bottomSize.setSize(frameSize.width, frameSize.height * 20/100);
        sideSize.setSize(width * 27/100, height);
        buttonSize.setSize(sideSize.width * 20/100, sideSize.width * 20/100);
        scrollSize.setSize(sideSize.getWidth() * 14/100 , sideSize.getHeight() * 28/100);

        JLabel cover = ImageHandler.setImage("src/main/resources/Graphics/board.png", 100, 100, width, height);
        sfondo = new JLabel(cover.getIcon());

        JLabel cover1 = ImageHandler.setImage("src/main/resources/Graphics/panel_chat.png", 100, 100, sideSize.width, sideSize.height);
        sfondo2 = new JLabel(cover1.getIcon());

        cover2 = ImageHandler.setImage("src/main/resources/Graphics/title_sky.png", 100, 100, frameSize.width * 40/100, frameSize.height * 45/100);
        background = new JLabel(cover2.getIcon());

        felixSmall = new Font(Gui.FELIX, Font.PLAIN, (int) (13 * screen.getHeight() / 1080));
        felixNormal = new Font(Gui.FELIX, Font.PLAIN, (int) (20 * screen.getHeight() / 1080));
        felixBold = new Font(Gui.FELIX, Font.BOLD, (int) (25 * screen.getHeight() / 1080));


        workerCyan = ImageHandler.setImage("src/main/resources/Graphics/worker_cyan.png", 100, 100, height * 13/100, height * 13/100);
        workerWhite = ImageHandler.setImage("src/main/resources/Graphics/worker_white.png", 100, 100, height * 13/100, height * 13/100);
        workerPurple = ImageHandler.setImage("src/main/resources/Graphics/worker_purple.png", 100, 100, height * 13/100, height * 13/100);
        lvl1 = ImageHandler.setImage("src/main/resources/Graphics/lvl1.png", 85, 85, height * 13/100, height * 13/100);
        lvl2 = ImageHandler.setImage("src/main/resources/Graphics/lvl2.png", 85, 85, height * 13/100, height * 13/100);
        lvl3 = ImageHandler.setImage("src/main/resources/Graphics/lvl3.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Building = ImageHandler.setImage("src/main/resources/Graphics/lvl1_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl2Building = ImageHandler.setImage("src/main/resources/Graphics/lvl2_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl3Building = ImageHandler.setImage("src/main/resources/Graphics/lvl3_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        domeBuilding = ImageHandler.setImage("src/main/resources/Graphics/dome_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl1Cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl1_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl2_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl3_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Purple = ImageHandler.setImage("src/main/resources/Graphics/lvl1_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Purple = ImageHandler.setImage("src/main/resources/Graphics/lvl2_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Purple = ImageHandler.setImage("src/main/resources/Graphics/lvl3_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl1White = ImageHandler.setImage("src/main/resources/Graphics/lvl1_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl2White = ImageHandler.setImage("src/main/resources/Graphics/lvl2_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl3White = ImageHandler.setImage("src/main/resources/Graphics/lvl3_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl1Dome = ImageHandler.setImage("src/main/resources/Graphics/lvl1_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl2Dome = ImageHandler.setImage("src/main/resources/Graphics/lvl2_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl3Dome = ImageHandler.setImage("src/main/resources/Graphics/lvl3_dome.png", 85, 85, height * 13/100, height * 13/100);
        dome = ImageHandler.setImage("src/main/resources/Graphics/dome.png", 85, 85, frameSize.width * 5/100, frameSize.width * 5/100);
        exit = ImageHandler.setImage("src/main/resources/Graphics/exit.png", 75, 75, sideSize.width * 12/100, sideSize.width * 12/100);
        lButtonBuild = ImageHandler.setImage("src/main/resources/Graphics/button_build.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonMove = ImageHandler.setImage("src/main/resources/Graphics/button_move.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonPower = ImageHandler.setImage("src/main/resources/Graphics/button_power.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonChat = ImageHandler.setImage("src/main/resources/Graphics/button_chat.png", 100, 100, frameSize.width * 5/100, frameSize.height * 7/100);
        lButtonBuildPress = ImageHandler.setImage("src/main/resources/Graphics/button_build_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonMovePress = ImageHandler.setImage("src/main/resources/Graphics/button_move_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonPowerPress = ImageHandler.setImage("src/main/resources/Graphics/button_power_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        lButtonChatPress = ImageHandler.setImage("src/main/resources/Graphics/button_chat_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 7/100);


        framePower = new JWindow();
        framePower.setBounds((int)(frameSize.width * 35.5/100), (int) (frameSize.height * 37/100), frameSize.width * 40/100, frameSize.height * 45/100);


        frameChat = new JInternalFrame("frameChat", false, false, false, false);
        frameChat.setPreferredSize(sideSize);
        frameChat.setBounds(frameSize.width * 73/100, -20, sideSize.width, sideSize.height);
        internalFrameSetUp(frameChat);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)frameChat.getUI();
        bii.setNorthPane(null);


        desktopPane = new JDesktopPane(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage( ((ImageIcon)(sfondo.getIcon())).getImage(), -7, -18, null);
            }};

        desktopPane.setPreferredSize(frameSize);



        nicknameLabel.setBounds((frameSize.width * 4/100), (frameSize.height * 3/100), frameSize.width * 20/100, frameSize.width * 5/100);
        nicknameLabel.setFont(felixNormal);
        desktopPane.add(nicknameLabel);

        gID.setBounds((frameSize.width * 4/100), (frameSize.height * 5/100), frameSize.width * 20/100, frameSize.width * 5/100);
        gID.setFont(felixNormal);
        desktopPane.add(gID);

        opponents.setBounds((frameSize.width * 3/100), (frameSize.height * 55/100), frameSize.width * 20/100, frameSize.width * 5/100);
        opponents.setFont(felixNormal);
        desktopPane.add(opponents);

        opponent1.setBounds((frameSize.width * 4/100), (frameSize.height * 61/100), frameSize.width * 10/100, frameSize.height * 4/100);
        opponent1.setName(returnGodChoosen().get(1).getName());
        opponentsButton(opponent1);
        if (numberOfPlayer == 3){
            JButton opponent2 = new JButton(players.get(2).getNickname());
            opponent2.setName(returnGodChoosen().get(2).getName());
            opponent2.setBounds((frameSize.width * 4/100), (frameSize.height * 64/100), frameSize.width * 10/100, frameSize.height * 4/100);
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

        chatStyleButtons(sfondoFrame, sfondo2);
        frameChat.getContentPane().add(sfondoFrame);


        chatStyleButtons(sfondoFramePower, background);
        framePower.getContentPane().add(sfondoFramePower);


        resetLevel();

        resetPlayer();

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
        desktopPane.add(buttonLv1);

        buttonLv2.setBounds(frameSize.width * 89/100, frameSize.height * 19/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv2.setIcon(lvl2Building.getIcon());
        desktopPane.add(buttonLv2);

        buttonLv3.setBounds(frameSize.width * 81/100, frameSize.height * 31/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv3.setIcon(lvl3Building.getIcon());
        desktopPane.add(buttonLv3);

        buttonDome.setBounds(frameSize.width * 89/100, frameSize.height * 31/100, frameSize.width * 5/100, frameSize.width * 5/100);
        buttonDome.setIcon(domeBuilding.getIcon());
        buttonDome.setBorderPainted(false);
        buttonDome.setContentAreaFilled(false);
        desktopPane.add(buttonDome);



        buttonMove.setBounds(frameSize.width * 79/100, frameSize.height * 46/100, frameSize.width * 5/100, frameSize.height * 5/100);
        buttonMove.addActionListener(new AddMove());
        consoleButtons(buttonMove, lButtonMove);

        buttonBuild.setBounds(frameSize.width * 85/100, frameSize.height * 46/100, frameSize.width * 5/100, frameSize.height * 5/100);
        buttonBuild.addActionListener(new AddBuildLvl());
        consoleButtons(buttonBuild, lButtonBuild);

        buttonPower.setBounds(frameSize.width * 91/100, frameSize.height * 46/100, frameSize.width * 5/100, frameSize.height * 5/100);
        consoleButtons(buttonPower, lButtonPower);

        buttonChat.setBounds(frameSize.width * 85/100, frameSize.height * 58/100, frameSize.width * 5/100, frameSize.height * 7/100);
        buttonChat.addActionListener(new Chat());
        consoleButtons(buttonChat, lButtonChat);



        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setPreferredSize(frameSize);

        frameChat.setVisible(false);
        desktopPane.add(frameChat);
        f.setContentPane(desktopPane);


        SwingUtilities.updateComponentTreeUI(f);
        f.pack();

        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    private void internalFrameSetUp(JInternalFrame intFrame){
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

    private void consoleButtons(JButton button, JLabel label){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(label.getIcon());
        button.addMouseListener(new ButtonPress());
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

    private class Write implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!field.getText().equals("")) {
                chat.append(allPlayer.get(0).getNickname() + ": " + field.getText().toLowerCase() + "\n");
                chat.setCaretPosition(chat.getDocument().getLength());
                field.setText("");
            }
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

    private class AddMove implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            buttonBuild.setEnabled(false);
            for (int x = 0; x < 25; x++){
                if (!mapButtonsPlayer[x] && mapButtonslvl[x] != 4)
                        mapButtons[x].addActionListener(new Move());
            }
        }
    }

    private class Move implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton c = (JButton) e.getSource();
            for (int x = 0; x < 25; x++){

                if (mapButtonsPlayer[x]){

                    switch (mapButtonslvl[x]) {

                        case 0:
                            mapButtons[x].setIcon(null);
                            mapButtonsPlayer[x] = false;
                            break;
                        case 1:
                            mapButtons[x].setIcon(lvl1.getIcon());
                            mapButtonsPlayer[x] = false;
                            break;
                        case 2:
                            mapButtons[x].setIcon(lvl2.getIcon());
                            mapButtonsPlayer[x] = false;
                            break;
                        case 3:
                            mapButtons[x].setIcon(lvl3.getIcon());
                            mapButtonsPlayer[x] = false;
                            break;
                        default:
                    }
                }
                if (mapButtons[x] == c){

                    switch (mapButtonslvl[x]) {

                        case 0:
                            c.setIcon(workerCyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 1:
                            c.setIcon(lvl1Cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 2:
                            c.setIcon(lvl2Cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 3:
                            c.setIcon(lvl3Cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        default:
                    }
                }
            }
            removeMove();
            buttonBuild.setEnabled(true);
        }
        private void removeMove() {
            for (int x = 0; x < 25; x++){
                eliminateActionClass(mapButtons[x], Move.class);
            }
        }
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
            JButton c = (JButton)e.getSource();
            if (buttonMove == c){
                buttonMove.setIcon(lButtonMovePress.getIcon());
            }
            else if (buttonBuild == c){
                buttonBuild.setIcon(lButtonBuildPress.getIcon());
            }
            else if (buttonPower == c){
                buttonPower.setIcon(lButtonPowerPress.getIcon());
            }
            else
                buttonChat.setIcon(lButtonChatPress.getIcon());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();

            if (buttonMove == c){
                buttonMove.setIcon(lButtonMove.getIcon());
            }
            else if (buttonBuild == c){
                buttonBuild.setIcon(lButtonBuild.getIcon());
            }
            else if (buttonPower == c){
                buttonPower.setIcon(lButtonPower.getIcon());
            }
            else
                buttonChat.setIcon(lButtonChat.getIcon());
        }
    }


    private class SeePower extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            try {
                cover2 = ImageHandler.setImage(c.getName(), 100, 100, frameSize.width * 40/100, frameSize.height * 45/100);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            background = new JLabel(cover2.getIcon());
            framePower.getContentPane().removeAll();
            chatStyleButtons(sfondoFramePower, background);
            framePower.getContentPane().add(sfondoFramePower);
            framePower.setVisible(true);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            framePower.setVisible(false);
        }
    }
}

package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.utils.Observable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends Observable {
    ArrayList<Player> allPlayer = new ArrayList<>();
    JFrame f;
    JDesktopPane desktopPane;
    JInternalFrame frameChat;
    static JInternalFrame framePower;
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
    private final JButton[] mapButtons = new JButton[25];
    int[] mapButtonslvl = new int[25];
    boolean[] mapButtonsPlayer = new boolean[25];
    JLabel nicknameLabel = new JLabel();
    JLabel gID = new JLabel(), sfondo, sfondo2, background;
    JLabel opponents = new JLabel("Opponents:");
    static JLabel playerpower = new JLabel();
    JButton opponent1 = new JButton();
    JTextArea chat = new JTextArea();
    JTextField field = new JTextField();
    Dimension frameSize = new Dimension(), boardSize = new Dimension(), bottomSize = new Dimension(),
            sideSize = new Dimension(), buttonSize = new Dimension(), scrollSize = new Dimension();
    JLabel worker_cyan, worker_white, worker_purple, lvl1, lvl2, lvl3, lvl1_building, lvl2_building, lvl3_building, dome_building, lvl1_cyan, lvl2_cyan, lvl3_cyan, lvl1_purple, lvl2_purple, lvl3_purple,
            lvl1_white, lvl2_white, lvl3_white, lvl1_dome, lvl2_dome, lvl3_dome, dome, exit, button_move, button_build, button_move_press, button_build_press,
            button_power, button_power_press, button_chat, button_chat_press;
    private boolean chatOpen = false;
    Font felixSmall, felixNormal, felixBold;

    public void show(Dimension screen, Integer numberOfPlayer, final ArrayList<Player> players, String gameId) throws IOException {

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

        JLabel cover2 = ImageHandler.setImage("src/main/resources/Graphics/background.png", 100, 100, frameSize.width * 40/100, frameSize.height * 40/100);
        background = new JLabel(cover2.getIcon());

        felixSmall = new Font("Felix Titling", Font.PLAIN, (int) (13 * screen.getHeight() / 1080));
        felixNormal = new Font("Felix Titling", Font.PLAIN, (int) (20 * screen.getHeight() / 1080));
        felixBold = new Font("Felix Titling", Font.BOLD, (int) (25 * screen.getHeight() / 1080));


        worker_cyan = ImageHandler.setImage("src/main/resources/Graphics/worker_cyan.png", 100, 100, height * 13/100, height * 13/100);
        worker_white = ImageHandler.setImage("src/main/resources/Graphics/worker_white.png", 100, 100, height * 13/100, height * 13/100);
        worker_purple = ImageHandler.setImage("src/main/resources/Graphics/worker_purple.png", 100, 100, height * 13/100, height * 13/100);
        lvl1 = ImageHandler.setImage("src/main/resources/Graphics/lvl1.png", 85, 85, height * 13/100, height * 13/100);
        lvl2 = ImageHandler.setImage("src/main/resources/Graphics/lvl2.png", 85, 85, height * 13/100, height * 13/100);
        lvl3 = ImageHandler.setImage("src/main/resources/Graphics/lvl3.png", 85, 85, height * 13/100, height * 13/100);
        lvl1_building = ImageHandler.setImage("src/main/resources/Graphics/lvl1_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl2_building = ImageHandler.setImage("src/main/resources/Graphics/lvl2_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl3_building = ImageHandler.setImage("src/main/resources/Graphics/lvl3_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        dome_building = ImageHandler.setImage("src/main/resources/Graphics/dome_building.png", 100, 100, frameSize.width * 5/100, frameSize.width * 5/100);
        lvl1_cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl1_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl2_cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl2_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl3_cyan = ImageHandler.setImage("src/main/resources/Graphics/lvl3_cyan.png", 85, 85, height * 13/100, height * 13/100);
        lvl1_purple = ImageHandler.setImage("src/main/resources/Graphics/lvl1_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl2_purple = ImageHandler.setImage("src/main/resources/Graphics/lvl2_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl3_purple = ImageHandler.setImage("src/main/resources/Graphics/lvl3_purple.png", 85, 85, height * 13/100, height * 13/100);
        lvl1_white = ImageHandler.setImage("src/main/resources/Graphics/lvl1_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl2_white = ImageHandler.setImage("src/main/resources/Graphics/lvl2_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl3_white = ImageHandler.setImage("src/main/resources/Graphics/lvl3_white.png", 85, 85, height * 13/100, height * 13/100);
        lvl1_dome = ImageHandler.setImage("src/main/resources/Graphics/lvl1_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl2_dome = ImageHandler.setImage("src/main/resources/Graphics/lvl2_dome.png", 85, 85, height * 13/100, height * 13/100);
        lvl3_dome = ImageHandler.setImage("src/main/resources/Graphics/lvl3_dome.png", 85, 85, height * 13/100, height * 13/100);
        dome = ImageHandler.setImage("src/main/resources/Graphics/dome.png", 85, 85, frameSize.width * 5/100, frameSize.width * 5/100);
        exit = ImageHandler.setImage("src/main/resources/Graphics/exit.png", 75, 75, sideSize.width * 12/100, sideSize.width * 12/100);
        button_build = ImageHandler.setImage("src/main/resources/Graphics/button_build.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_move = ImageHandler.setImage("src/main/resources/Graphics/button_move.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_power = ImageHandler.setImage("src/main/resources/Graphics/button_power.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_chat = ImageHandler.setImage("src/main/resources/Graphics/button_chat.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);
        button_build_press = ImageHandler.setImage("src/main/resources/Graphics/button_build_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_move_press = ImageHandler.setImage("src/main/resources/Graphics/button_move_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_power_press = ImageHandler.setImage("src/main/resources/Graphics/button_power_press.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        button_chat_press = ImageHandler.setImage("src/main/resources/Graphics/button_chat_press.png", 100, 100, frameSize.width * 7/100, frameSize.height * 7/100);


        framePower = new JInternalFrame("frameChat", false, false, false, false);
        framePower.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        framePower.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        framePower.setOpaque(false);
        framePower.setBounds(frameSize.width * 50/100 - frameSize.width * 20/100, frameSize.height * 50/100 - frameSize.height * 20/100, frameSize.width * 40/100, frameSize.height * 40/100);
        framePower.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        framePower.setBorder(null);
        BasicInternalFrameUI bi = (BasicInternalFrameUI)framePower.getUI();
        bi.setNorthPane(null);



        frameChat = new JInternalFrame("frameChat", false, false, false, false);
        frameChat.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        frameChat.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frameChat.setPreferredSize(sideSize);
        frameChat.setOpaque(false);
        frameChat.setBounds(frameSize.width * 73/100, -20, sideSize.width, sideSize.height);
        frameChat.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        frameChat.setBorder(null);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)frameChat.getUI();
        bii.setNorthPane(null);


        desktopPane = new JDesktopPane(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage( ((ImageIcon)(sfondo.getIcon())).getImage(), -7, -18, null);
            }};

        desktopPane.setPreferredSize(frameSize);



        nicknameLabel.setBounds((frameSize.width * 3/100), (frameSize.height * 16/100), frameSize.width * 20/100, frameSize.width * 5/100);
        nicknameLabel.setFont(felixNormal);
        desktopPane.add(nicknameLabel);

        gID.setBounds((frameSize.width * 3/100), (frameSize.height * 21/100), frameSize.width * 20/100, frameSize.width * 5/100);
        gID.setFont(felixNormal);
        desktopPane.add(gID);

        opponents.setBounds((frameSize.width * 3/100), (frameSize.height * 25/100), frameSize.width * 20/100, frameSize.width * 5/100);
        opponents.setFont(felixNormal);
        desktopPane.add(opponents);

        opponent1.setBounds((frameSize.width * 4/100), (frameSize.height * 31/100), frameSize.width * 10/100, frameSize.height * 4/100);
        opponent1.setFont(felixNormal);
        opponent1.setHorizontalAlignment(JTextField.LEFT);
        opponent1.setOpaque(false);
        opponent1.setContentAreaFilled(false);
        opponent1.setFocusPainted(false);
        opponent1.setBorderPainted(false);
        desktopPane.add(opponent1);
        opponent1.addMouseListener(new SeePower());
        if (numberOfPlayer == 3){
            JButton opponent2 = new JButton(players.get(2).getNickname());
            opponent2.setBounds((frameSize.width * 4/100), (frameSize.height * 34/100), frameSize.width * 10/100, frameSize.height * 4/100);
            opponent2.setFont(felixNormal);
            opponent2.setHorizontalAlignment(SwingConstants.LEFT);
            opponent2.setOpaque(false);
            opponent2.setContentAreaFilled(false);
            opponent2.setFocusPainted(false);
            opponent2.setBorderPainted(false);
            desktopPane.add(opponent2);
            opponent2.addMouseListener(new SeePower());
        }

        chat.setBounds(frameChat.getWidth() * 14/100 , frameChat.getHeight() * 28/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 38/100);
        chat.setEditable(false);
        chat.setBackground(new Color(232, 222, 208));
        //chat.setForeground(new Color(232, 222, 208));
        chat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chat.setFont(felixSmall);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setVisible(true);
        scrollPane = new JScrollPane(chat);
        scrollPane.setPreferredSize(scrollSize);
        scrollPane.setBounds(frameChat.getWidth() * 14/100 , frameChat.getHeight() * 28/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 38/100);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frameChat.add(scrollPane);

        field.setBounds(frameChat.getWidth() * 14/100, frameChat.getHeight() * 66/100, frameChat.getWidth() * 63/100, frameChat.getHeight() * 4/100);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        field.setBackground(new Color(232, 222, 208));
        field.addActionListener(new Write());
        field.setFont(felixSmall);
        field.setVisible(true);
        frameChat.add(field);

        buttonExit.setBounds((frameChat.getWidth() * 82/100), (frameChat.getHeight() * 2/100), frameChat.getWidth() * 12/100, frameChat.getWidth() * 12/100);
        buttonExit.addActionListener(new ChatExit());
        buttonExit.setContentAreaFilled(false);
        buttonExit.setOpaque(false);
        buttonExit.setBorderPainted(false);
        buttonExit.setIcon(exit.getIcon());
        frameChat.add(buttonExit);

        sfondoFrame.setContentAreaFilled(false);
        sfondoFrame.setOpaque(false);
        sfondoFrame.setBorderPainted(false);
        sfondoFrame.setIcon(sfondo2.getIcon());
        frameChat.getContentPane().add(sfondoFrame);

        playerpower.setFont(felixBold);
        framePower.add(playerpower);
        sfondoFramePower.setContentAreaFilled(false);
        sfondoFramePower.setOpaque(false);
        sfondoFramePower.setBorderPainted(false);
        sfondoFramePower.setIcon(background.getIcon());
        framePower.getContentPane().add(sfondoFramePower);


        for (int x = 0; x < 25; x++){
            mapButtonslvl[x] = 0;
        }

        for (int x = 0; x < 25; x++){
            mapButtonsPlayer[x] = false;
        }


        try{
            String os = System.getProperty("os.name").toLowerCase();

            if(os.contains("win")) {
                //windows positions

                mapButtons[0] = new JButton();
                mapButtons[0].setContentAreaFilled(false);
                mapButtons[0].setOpaque(false);
                mapButtons[0].setBorderPainted(false);
                mapButtons[0].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (7)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[0].addMouseListener(new ColorBorder());

                mapButtons[1] = new JButton();
                mapButtons[1].setContentAreaFilled(false);
                mapButtons[1].setOpaque(false);
                mapButtons[1].setBorderPainted(false);
                mapButtons[1].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (7)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[1].addMouseListener(new ColorBorder());

                mapButtons[2] = new JButton();
                mapButtons[2].setContentAreaFilled(false);
                mapButtons[2].setOpaque(false);
                mapButtons[2].setBorderPainted(false);
                mapButtons[2].setBounds((int) (frameSize.width * (46)/100) , (int) (frameSize.width * (7)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[2].addMouseListener(new ColorBorder());

                mapButtons[3] = new JButton();
                mapButtons[3].setContentAreaFilled(false);
                mapButtons[3].setOpaque(false);
                mapButtons[3].setBorderPainted(false);
                mapButtons[3].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (7)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[3].addMouseListener(new ColorBorder());

                mapButtons[4] = new JButton();
                mapButtons[4].setContentAreaFilled(false);
                mapButtons[4].setOpaque(false);
                mapButtons[4].setBorderPainted(false);
                mapButtons[4].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (7)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[4].addMouseListener(new ColorBorder());

                mapButtons[5] = new JButton();
                mapButtons[5].setContentAreaFilled(false);
                mapButtons[5].setOpaque(false);
                mapButtons[5].setBorderPainted(false);
                mapButtons[5].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[5].addMouseListener(new ColorBorder());

                mapButtons[6] = new JButton();
                mapButtons[6].setContentAreaFilled(false);
                mapButtons[6].setOpaque(false);
                mapButtons[6].setBorderPainted(false);
                mapButtons[6].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[6].addMouseListener(new ColorBorder());

                mapButtons[7] = new JButton();
                mapButtons[7].setContentAreaFilled(false);
                mapButtons[7].setOpaque(false);
                mapButtons[7].setBorderPainted(false);
                mapButtons[7].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[7].addMouseListener(new ColorBorder());

                mapButtons[8] = new JButton();
                mapButtons[8].setContentAreaFilled(false);
                mapButtons[8].setOpaque(false);
                mapButtons[8].setBorderPainted(false);
                mapButtons[8].setBounds((int) (frameSize.width * (62.5)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[8].addMouseListener(new ColorBorder());

                mapButtons[9] = new JButton();
                mapButtons[9].setContentAreaFilled(false);
                mapButtons[9].setOpaque(false);
                mapButtons[9].setBorderPainted(false);
                mapButtons[9].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[9].addMouseListener(new ColorBorder());

                mapButtons[10] = new JButton();
                mapButtons[10].setContentAreaFilled(false);
                mapButtons[10].setOpaque(false);
                mapButtons[10].setBorderPainted(false);
                mapButtons[10].setBounds((int) (frameSize.width * (46)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[10].addMouseListener(new ColorBorder());

                mapButtons[11] = new JButton();
                mapButtons[11].setContentAreaFilled(false);
                mapButtons[11].setOpaque(false);
                mapButtons[11].setBorderPainted(false);
                mapButtons[11].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[11].addMouseListener(new ColorBorder());

                mapButtons[12] = new JButton();
                mapButtons[12].setContentAreaFilled(false);
                mapButtons[12].setOpaque(false);
                mapButtons[12].setBorderPainted(false);
                mapButtons[12].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (40.24)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[12].addMouseListener(new ColorBorder());

                mapButtons[13] = new JButton();
                mapButtons[13].setContentAreaFilled(false);
                mapButtons[13].setOpaque(false);
                mapButtons[13].setBorderPainted(false);
                mapButtons[13].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[13].addMouseListener(new ColorBorder());

                mapButtons[14] = new JButton();
                mapButtons[14].setContentAreaFilled(false);
                mapButtons[14].setOpaque(false);
                mapButtons[14].setBorderPainted(false);
                mapButtons[14].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[14].addMouseListener(new ColorBorder());

                mapButtons[15] = new JButton();
                mapButtons[15].setContentAreaFilled(false);
                mapButtons[15].setOpaque(false);
                mapButtons[15].setBorderPainted(false);
                mapButtons[15].setBounds((int) (frameSize.width * (29.5)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[15].addMouseListener(new ColorBorder());

                mapButtons[16] = new JButton();
                mapButtons[16].setContentAreaFilled(false);
                mapButtons[16].setOpaque(false);
                mapButtons[16].setBorderPainted(false);
                mapButtons[16].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[16].addMouseListener(new ColorBorder());

                mapButtons[17] = new JButton();
                mapButtons[17].setContentAreaFilled(false);
                mapButtons[17].setOpaque(false);
                mapButtons[17].setBorderPainted(false);
                mapButtons[17].setBounds((int) (frameSize.width * (46)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[17].addMouseListener(new ColorBorder());
                mapButtons[18] = new JButton();
                mapButtons[18].setContentAreaFilled(false);
                mapButtons[18].setOpaque(false);
                mapButtons[18].setBorderPainted(false);
                mapButtons[18].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (15.31)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[18].addMouseListener(new ColorBorder());

                mapButtons[19] = new JButton();
                mapButtons[19].setContentAreaFilled(false);
                mapButtons[19].setOpaque(false);
                mapButtons[19].setBorderPainted(false);
                mapButtons[19].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[19].addMouseListener(new ColorBorder());

                mapButtons[20] = new JButton();
                mapButtons[20].setContentAreaFilled(false);
                mapButtons[20].setOpaque(false);
                mapButtons[20].setBorderPainted(false);
                mapButtons[20].setBounds((int) (frameSize.width * (54.25)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[20].addMouseListener(new ColorBorder());

                mapButtons[21] = new JButton();
                mapButtons[21].setContentAreaFilled(false);
                mapButtons[21].setOpaque(false);
                mapButtons[21].setBorderPainted(false);
                mapButtons[21].setBounds((int) (frameSize.width * (46)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[21].addMouseListener(new ColorBorder());

                mapButtons[22] = new JButton();
                mapButtons[22].setContentAreaFilled(false);
                mapButtons[22].setOpaque(false);
                mapButtons[22].setBorderPainted(false);
                mapButtons[22].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (31.93)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[22].addMouseListener(new ColorBorder());

                mapButtons[23] = new JButton();
                mapButtons[23].setContentAreaFilled(false);
                mapButtons[23].setOpaque(false);
                mapButtons[23].setBorderPainted(false);
                mapButtons[23].setBounds((int) (frameSize.width * (37.75)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[23].addMouseListener(new ColorBorder());

                mapButtons[24] = new JButton();
                mapButtons[24].setContentAreaFilled(false);
                mapButtons[24].setOpaque(false);
                mapButtons[24].setBorderPainted(false);
                mapButtons[24].setBounds((int) (frameSize.width * (46)/100) , (int) (frameSize.width * (23.62)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[24].addMouseListener(new ColorBorder());
            }
            else {
                //mac positions
                mapButtons[0] = new JButton();
                mapButtons[0].setContentAreaFilled(false);
                mapButtons[0].setOpaque(false);
                mapButtons[0].setBorderPainted(false);
                mapButtons[0].setBounds((int) (frameSize.width * (29.8)/100) , (int) (frameSize.width * (8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[0].addMouseListener(new ColorBorder());

                mapButtons[1] = new JButton();
                mapButtons[1].setContentAreaFilled(false);
                mapButtons[1].setOpaque(false);
                mapButtons[1].setBorderPainted(false);
                mapButtons[1].setBounds((int) (frameSize.width * (38.1)/100) , (int) (frameSize.width * (8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[1].addMouseListener(new ColorBorder());

                mapButtons[2] = new JButton();
                mapButtons[2].setContentAreaFilled(false);
                mapButtons[2].setOpaque(false);
                mapButtons[2].setBorderPainted(false);
                mapButtons[2].setBounds((int) (frameSize.width * (46.4)/100) , (int) (frameSize.width * (8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[2].addMouseListener(new ColorBorder());

                mapButtons[3] = new JButton();
                mapButtons[3].setContentAreaFilled(false);
                mapButtons[3].setOpaque(false);
                mapButtons[3].setBorderPainted(false);
                mapButtons[3].setBounds((int) (frameSize.width * (54.7)/100) , (int) (frameSize.width * (8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[3].addMouseListener(new ColorBorder());

                mapButtons[4] = new JButton();
                mapButtons[4].setContentAreaFilled(false);
                mapButtons[4].setOpaque(false);
                mapButtons[4].setBorderPainted(false);
                mapButtons[4].setBounds((int) (frameSize.width * (63)/100) , (int) (frameSize.width * (8)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[4].addMouseListener(new ColorBorder());

                mapButtons[5] = new JButton();
                mapButtons[5].setContentAreaFilled(false);
                mapButtons[5].setOpaque(false);
                mapButtons[5].setBorderPainted(false);
                mapButtons[5].setBounds((int) (frameSize.width * (63)/100) , (int) (frameSize.width * (16.3)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[5].addMouseListener(new ColorBorder());

                mapButtons[6] = new JButton();
                mapButtons[6].setContentAreaFilled(false);
                mapButtons[6].setOpaque(false);
                mapButtons[6].setBorderPainted(false);
                mapButtons[6].setBounds((int) (frameSize.width * (63)/100) , (int) (frameSize.width * (24.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[6].addMouseListener(new ColorBorder());

                mapButtons[7] = new JButton();
                mapButtons[7].setContentAreaFilled(false);
                mapButtons[7].setOpaque(false);
                mapButtons[7].setBorderPainted(false);
                mapButtons[7].setBounds((int) (frameSize.width * (63)/100) , (int) (frameSize.width * (32.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[7].addMouseListener(new ColorBorder());

                mapButtons[8] = new JButton();
                mapButtons[8].setContentAreaFilled(false);
                mapButtons[8].setOpaque(false);
                mapButtons[8].setBorderPainted(false);
                mapButtons[8].setBounds((int) (frameSize.width * (63)/100) , (int) (frameSize.width * (41.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[8].addMouseListener(new ColorBorder());

                mapButtons[9] = new JButton();
                mapButtons[9].setContentAreaFilled(false);
                mapButtons[9].setOpaque(false);
                mapButtons[9].setBorderPainted(false);
                mapButtons[9].setBounds((int) (frameSize.width * (54.7)/100) , (int) (frameSize.width * (41.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[9].addMouseListener(new ColorBorder());
                mapButtons[10] = new JButton();
                mapButtons[10].setContentAreaFilled(false);
                mapButtons[10].setOpaque(false);
                mapButtons[10].setBorderPainted(false);
                mapButtons[10].setBounds((int) (frameSize.width * (46.4)/100) , (int) (frameSize.width * (41.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[10].addMouseListener(new ColorBorder());

                mapButtons[11] = new JButton();
                mapButtons[11].setContentAreaFilled(false);
                mapButtons[11].setOpaque(false);
                mapButtons[11].setBorderPainted(false);
                mapButtons[11].setBounds((int) (frameSize.width * (38.1)/100) , (int) (frameSize.width * (41.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[11].addMouseListener(new ColorBorder());

                mapButtons[12] = new JButton();
                mapButtons[12].setContentAreaFilled(false);
                mapButtons[12].setOpaque(false);
                mapButtons[12].setBorderPainted(false);
                mapButtons[12].setBounds((int) (frameSize.width * (29.8)/100) , (int) (frameSize.width * (41.2)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[12].addMouseListener(new ColorBorder());

                mapButtons[13] = new JButton();
                mapButtons[13].setContentAreaFilled(false);
                mapButtons[13].setOpaque(false);
                mapButtons[13].setBorderPainted(false);
                mapButtons[13].setBounds((int) (frameSize.width * (29.8)/100) , (int) (frameSize.width * (32.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[13].addMouseListener(new ColorBorder());

                mapButtons[14] = new JButton();
                mapButtons[14].setContentAreaFilled(false);
                mapButtons[14].setOpaque(false);
                mapButtons[14].setBorderPainted(false);
                mapButtons[14].setBounds((int) (frameSize.width * (29.8)/100) , (int) (frameSize.width * (24.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[14].addMouseListener(new ColorBorder());

                mapButtons[15] = new JButton();
                mapButtons[15].setContentAreaFilled(false);
                mapButtons[15].setOpaque(false);
                mapButtons[15].setBorderPainted(false);
                mapButtons[15].setBounds((int) (frameSize.width * (29.8)/100) , (int) (frameSize.width * (16.3)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[15].addMouseListener(new ColorBorder());

                mapButtons[16] = new JButton();
                mapButtons[16].setContentAreaFilled(false);
                mapButtons[16].setOpaque(false);
                mapButtons[16].setBorderPainted(false);
                mapButtons[16].setBounds((int) (frameSize.width * (38.1)/100) , (int) (frameSize.width * (16.3)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[16].addMouseListener(new ColorBorder());

                mapButtons[17] = new JButton();
                mapButtons[17].setContentAreaFilled(false);
                mapButtons[17].setOpaque(false);
                mapButtons[17].setBorderPainted(false);
                mapButtons[17].setBounds((int) (frameSize.width * (46.4)/100) , (int) (frameSize.width * (16.3)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[17].addMouseListener(new ColorBorder());

                mapButtons[18] = new JButton();
                mapButtons[18].setContentAreaFilled(false);
                mapButtons[18].setOpaque(false);
                mapButtons[18].setBorderPainted(false);
                mapButtons[18].setBounds((int) (frameSize.width * (54.7)/100) , (int) (frameSize.width * (16.3)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[18].addMouseListener(new ColorBorder());

                mapButtons[19] = new JButton();
                mapButtons[19].setContentAreaFilled(false);
                mapButtons[19].setOpaque(false);
                mapButtons[19].setBorderPainted(false);
                mapButtons[19].setBounds((int) (frameSize.width * (54.7)/100) , (int) (frameSize.width * (24.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[19].addMouseListener(new ColorBorder());

                mapButtons[20] = new JButton();
                mapButtons[20].setContentAreaFilled(false);
                mapButtons[20].setOpaque(false);
                mapButtons[20].setBorderPainted(false);
                mapButtons[20].setBounds((int) (frameSize.width * (54.7)/100) , (int) (frameSize.width * (32.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[20].addMouseListener(new ColorBorder());
                mapButtons[21] = new JButton();
                mapButtons[21].setContentAreaFilled(false);
                mapButtons[21].setOpaque(false);
                mapButtons[21].setBorderPainted(false);
                mapButtons[21].setBounds((int) (frameSize.width * (46.4)/100) , (int) (frameSize.width * (32.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[21].addMouseListener(new ColorBorder());

                mapButtons[22] = new JButton();
                mapButtons[22].setContentAreaFilled(false);
                mapButtons[22].setOpaque(false);
                mapButtons[22].setBorderPainted(false);
                mapButtons[22].setBounds((int) (frameSize.width * (38.1)/100) , (int) (frameSize.width * (32.9)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[22].addMouseListener(new ColorBorder());

                mapButtons[23] = new JButton();
                mapButtons[23].setContentAreaFilled(false);
                mapButtons[23].setOpaque(false);
                mapButtons[23].setBorderPainted(false);
                mapButtons[23].setBounds((int) (frameSize.width * (38.1)/100) , (int) (frameSize.width * (24.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[23].addMouseListener(new ColorBorder());

                mapButtons[24] = new JButton();
                mapButtons[24].setContentAreaFilled(false);
                mapButtons[24].setOpaque(false);
                mapButtons[24].setBorderPainted(false);
                mapButtons[24].setBounds((int) (frameSize.width * (46.4)/100) , (int) (frameSize.width * (24.6)/100),
                        frameSize.height * 13/100, frameSize.height * 13/100);
                mapButtons[24].addMouseListener(new ColorBorder());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }


        for (int x = 0; x < 25; x++){
            desktopPane.add(mapButtons[x]);
        }


        buttonLv1.setBounds((frameSize.width * 81/100), (frameSize.height * 18/100), frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv1.setIcon(lvl1_building.getIcon());
        desktopPane.add(buttonLv1);

        buttonLv2.setBounds((frameSize.width * 89/100), (frameSize.height * 18/100), frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv2.setIcon(lvl2_building.getIcon());
        desktopPane.add(buttonLv2);

        buttonLv3.setBounds((frameSize.width * 81/100), (frameSize.height * 31/100), frameSize.width * 5/100, frameSize.width * 5/100);
        buttonLv3.setIcon(lvl3_building.getIcon());
        desktopPane.add(buttonLv3);

        buttonDome.setBounds((frameSize.width * 89/100), (frameSize.height * 31/100), frameSize.width * 5/100, frameSize.width * 5/100);
        buttonDome.setIcon(dome_building.getIcon());
        buttonDome.setBorderPainted(false);
        buttonDome.setContentAreaFilled(false);
        desktopPane.add(buttonDome);



        buttonMove.setBounds((int) (frameSize.width * 79/100), (frameSize.height * 45/100), frameSize.width * 5/100, frameSize.height * 5/100);
        buttonMove.addActionListener(new AddMove());
        buttonMove.setOpaque(false);
        buttonMove.setContentAreaFilled(false);
        buttonMove.setBorderPainted(false);
        buttonMove.setIcon(button_move.getIcon());
        buttonMove.addMouseListener(new ButtonPress());
        desktopPane.add(buttonMove);

        buttonBuild.setBounds((int) (frameSize.width * 85/100), (frameSize.height * 45/100), frameSize.width * 5/100, frameSize.height * 5/100);
        buttonBuild.addActionListener(new AddBuildLvl());
        buttonBuild.setOpaque(false);
        buttonBuild.setContentAreaFilled(false);
        buttonBuild.setBorderPainted(false);
        buttonBuild.setIcon(button_build.getIcon());
        buttonBuild.addMouseListener(new ButtonPress());
        desktopPane.add(buttonBuild);

        buttonPower.setBounds((int) (frameSize.width * 91/100), (frameSize.height * 45/100), frameSize.width * 5/100, frameSize.height * 5/100);
        buttonPower.setOpaque(false);
        buttonPower.setContentAreaFilled(false);
        buttonPower.setBorderPainted(false);
        buttonPower.setIcon(button_power.getIcon());
        buttonPower.addMouseListener(new ButtonPress());
        desktopPane.add(buttonPower);

        buttonChat.setBounds((int) (frameSize.width * 83.5/100), (frameSize.height * 58/100), frameSize.width * 7/100, frameSize.height * 7/100);
        buttonChat.addMouseListener(new ButtonPress());
        buttonChat.addActionListener(new Chat());
        buttonChat.setOpaque(false);
        buttonChat.setContentAreaFilled(false);
        buttonChat.setBorderPainted(false);
        buttonChat.setIcon(button_chat.getIcon());
        desktopPane.add(buttonChat);



        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(frameSize);

        frameChat.setVisible(false);
        desktopPane.add(frameChat);
        desktopPane.add(framePower);
        f.setContentPane(desktopPane);


        SwingUtilities.updateComponentTreeUI(f);
        f.pack();

        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

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
            buttonMove.setEnabled(false);
        }
    }

    private void RemoveBuildLvl() {
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
            RemoveBuild();
            RemoveBuildLvl();
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
            RemoveBuild();
            RemoveBuildLvl();
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
            RemoveBuild();
            RemoveBuildLvl();
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
                    c.setIcon(lvl3_dome.getIcon());
                    mapButtonslvl[x] = 4;
                }

            }
            RemoveBuild();
            RemoveBuildLvl();
        }
    }

    private void RemoveBuild() {
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
                if (!mapButtonsPlayer[x])
                    if (mapButtonslvl[x] != 4)
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
                    }
                }
                if (mapButtons[x] == c){

                    switch (mapButtonslvl[x]) {

                        case 0:
                            c.setIcon(worker_cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 1:
                            c.setIcon(lvl1_cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 2:
                            c.setIcon(lvl2_cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                        case 3:
                            c.setIcon(lvl3_cyan.getIcon());
                            mapButtonsPlayer[x] = true;
                            break;
                    }
                }
            }
            RemoveMove();
            buttonBuild.setEnabled(true);
        }
    }

    private void RemoveMove() {
        for (int x = 0; x < 25; x++){
            for (int y = 0; y < mapButtons[x].getActionListeners().length; y++){
                if (mapButtons[x].getActionListeners()[y].getClass().equals(Move.class))
                    mapButtons[x].removeActionListener(mapButtons[x].getActionListeners()[y]);
            }
        }
    }

    private static class ColorBorder implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

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

    private class ButtonPress implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            if (buttonMove == c){
                buttonMove.setIcon(button_move_press.getIcon());
            }
            else if (buttonBuild == c){
                buttonBuild.setIcon(button_build_press.getIcon());
            }
            else if (buttonPower == c){
                buttonPower.setIcon(button_power_press.getIcon());
            }
            else
                buttonChat.setIcon(button_chat_press.getIcon());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();

            if (buttonMove == c){
                buttonMove.setIcon(button_move.getIcon());
            }
            else if (buttonBuild == c){
                buttonBuild.setIcon(button_build.getIcon());
            }
            else if (buttonPower == c){
                buttonPower.setIcon(button_power.getIcon());
            }
            else
                buttonChat.setIcon(button_chat.getIcon());
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }


    private static class SeePower implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            playerpower.setText("Ecco il potere di " + c.getText());
            playerpower.setHorizontalTextPosition(JTextField.CENTER);
            playerpower.setBounds((framePower.getWidth() * 50/100 - 150), (framePower.getHeight() * 50/100) - 50, 300, 100);
            framePower.setVisible(true);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            framePower.setVisible(false);
        }
    }
}

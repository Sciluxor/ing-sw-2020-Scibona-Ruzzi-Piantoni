package it.polimi.ingsw.view.Client.GUI;

import it.polimi.ingsw.controller.ClientGameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Gui extends ClientGameController {
    JFrame f = new JFrame();
    static JLabel santoriniLabel, sfondo;
    static Dimension boardSize, d ,screenSize;
    static JFrame frame;
    static JPanel login = null, lobby = null, challengerChoiseCards2 = null, challengerChoiseCards3 = null, waitChallenger = null,
            challengerChoiseFirst2 = null, challengerChoiseFirst3 = null, chooseCard3 = null, chooseCard2 = null, chooseCard1 = null,
            chooseCard0 = null;
    static JLayer layer;
    static int panelInUse = 0;

    static ArrayList<String> players;

    private void show() throws IOException {


        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double ratio = (screenSize.getWidth()/screenSize.getHeight());
        int width = (int)(screenSize.getWidth());
        int height = (int)(screenSize.getHeight());

        d = new Dimension();
        d.setSize(width * 95/100, height * 95/100);
        players = new ArrayList<>();
        players.add("Alessandro");
        players.add("Edoardo");
        players.add("Luigi");
        frame = new JFrame("Santorini");

        login = new Login(screenSize, d);                                                                   //schermata 0
        lobby = new Lobby(screenSize, d, 2, 3, players);                                    //schermata 1
        challengerChoiseCards2 = new ChallengerChoiseCards(screenSize, d, 2);                 //schermata 2
        challengerChoiseCards3 = new ChallengerChoiseCards(screenSize, d, 3);                 //schermata 3
        waitChallenger = new WaitChallenger(screenSize, d);                                                 //schermata 4
        challengerChoiseFirst2 = new ChallengerChoiseFirstPlayer(screenSize, d, 2, players);  //schermata 5
        challengerChoiseFirst3 = new ChallengerChoiseFirstPlayer(screenSize, d, 3, players);  //schermata 6
        chooseCard3 = new ChooseCard(screenSize, d, 3);                                        //schermata 7
        chooseCard2 = new ChooseCard(screenSize, d, 2);                                        //schermata 8
        chooseCard1 = new ChooseCard(screenSize, d, 1);                                        //schermata 9
        chooseCard0 = new ChooseCard(screenSize, d, 0);                                        //schermata 10
        //board2 = new Board(screenSize, d, 2, players, "GID01");                        //schermata 11
        //board3 = new Board(screenSize, d, 3, players, "GID01");                        //schermata 12




        santoriniLabel = new JLabel("scegli altri God");
        santoriniLabel.setBounds(100, 100, 100, 100);
        layer.setPreferredSize(d);
        layer.setVisible(true);
        frame.setPreferredSize(d);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.add(login);

        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }


    public static void PanelManager(int panel) throws IOException {
        switch (panel){
            case 0:
                frame.remove(login);
                frame.add(challengerChoiseCards3);
                panelInUse = 1;
                break;
            case 1:
                frame.remove(challengerChoiseCards3);
                frame.add(challengerChoiseFirst3);
                panelInUse = 2;
                break;

            case 2:
                frame.remove(challengerChoiseFirst3);
                frame.add(chooseCard3);
                panelInUse = 3;
                break;

            case 3:
                frame.dispose();
                Board board = new Board();
                board.show(screenSize, 3, players, "GID01");
                panelInUse = 4;
                break;

            case 4:

                //frame.add(new Board(boardSize, 2), BorderLayout.CENTER);
                //frame.remove(board3);
                //frame.add(board2);
                panelInUse = 5;
                break;


        }
        if (panelInUse != 4){
            frame.repaint();
            frame.validate();
        }
    }



    public static void main(String[] args) { //avvio
        SwingUtilities.invokeLater(() -> {
            try {
                Gui gui = new Gui();
                gui.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    public static class ChangePanel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (panelInUse){
                case 0:
                    try {
                        PanelManager(0);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 1:
                    try {
                        PanelManager(1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 2:
                    try {
                        PanelManager(2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 3:
                    try {
                        PanelManager(3);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        PanelManager(4);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }

        }

    }

}

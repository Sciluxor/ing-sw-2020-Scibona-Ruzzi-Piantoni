package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class LobbyGui{
    Gui gui;
    static Dimension frameSize = new Dimension();
    private static JDesktopPane pane;
    JInternalFrame popUp;
    PopUp constructorPopUp = null;
    static JButton backButton = new JButton();
    ConfirmButton confirm;
    static JLabel lactualNumber = new JLabel();
    static JLabel lnumber = new JLabel();
    static JLabel lback;
    static JLabel lbackPress;
    static Style colorStyle;
    static StyledDocument doc;
    static Style numberStyle;
    static MutableAttributeSet fontSize;
    static StyledDocument numberDoc;
    static int num = 2;
    static List<Player> players = new ArrayList<>();

    public LobbyGui(Gui instance, Dimension frame, Integer numberOfPlayer, List<Player> actualPlayers) throws IOException {

        gui = instance;
        num = numberOfPlayer;
        players = actualPlayers;
        pane = new JDesktopPane();
        frameSize.setSize(frame);
        pane.setPreferredSize(frameSize);
        //pane.setLayout(null);

        confirm = new ConfirmButton();
        window.add(confirm);
        nicknameLabel = new JLabel("nickname");

        nickname.setBounds((int) (Gui.getD().getWidth() * 40/100), (int) (Gui.getD().getHeight() * 35/100), Gui.getD().width * 20/100,Gui.getD().height * 3/100);
        nickname.setText("Nickname");
        window.add(nickname);
        nicknameLabel.setBounds((int) (Gui.getD().getWidth() * 24.5/100), (int) (Gui.getD().getHeight() * 34/100), Gui.getD().width * 15/100,Gui.getD().height * 5/100);
        window.add(nicknameLabel);
        window = new JInternalFrame("", false, false, false, false);
        window.setPreferredSize(intFrameSize);
        internalFrameSetUp(window);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)window.getUI();
        bii.setNorthPane(null);
        window.setVisible(false);
        popUp.setVisible(false);
        pane.add(popUp);

        lback = ImageHandler.setImage("src/main/resources/Graphics/button_back.png", 100, 100, frameSize.width * 13/100, frameSize.height * 5/100);
        lbackPress = ImageHandler.setImage("src/main/resources/Graphics/button_back_press.png", 100, 100, frameSize.width * 13/100, frameSize.height * 5/100);
        backButton.setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 79.5 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setIcon(lback.getIcon());
        backButton.setEnabled(true);
        backButton.addMouseListener(new BackButtonPress());
        backButton.addActionListener(new BackToLogin());
        pane.add(backButton);

        JLabel wait = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_others_players.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel of = ImageHandler.setImage("src/main/resources/Graphics/Texts/of.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        JTextPane textPane = new JTextPane();
        JTextPane numberPane = new JTextPane();
        doc = textPane.getStyledDocument();
        numberDoc = numberPane.getStyledDocument();




        wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        pane.add(wait);



        if (num == 2){
            lnumber = ImageHandler.setImage("src/main/resources/Graphics/Texts/2.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else {
            lnumber = ImageHandler.setImage("src/main/resources/Graphics/Texts/3.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }



        of.setBounds(frameSize.width * 47/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        of.setFont(Gui.felixBold);
        pane.add(of);
        lnumber.setBounds(frameSize.width * 52/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        pane.add(lnumber);


        textPane.setBounds((int) (frameSize.width * 30/100), frameSize.height * 45/100, frameSize.width * 40/100, frameSize.height * 20/100);
        textPane.setOpaque(false);
        textPane.setFont(Gui.felixBold);
        pane.add(textPane);
        colorStyle = textPane.addStyle("colorStyle", null);
        StyleConstants.setAlignment(colorStyle, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), colorStyle, false);

        numberPane.setBounds((int) (frameSize.width * 42/100), frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        numberPane.setOpaque(false);
        numberPane.setFont(Gui.felixBold);
        pane.add(numberPane);
        fontSize = numberPane.getInputAttributes();
        numberStyle = numberPane.addStyle("colorStyle", null);
        StyleConstants.setAlignment(numberStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(numberStyle, Color.BLACK);
        StyleConstants.setFontSize(fontSize, 30);
        numberDoc.setParagraphAttributes(0, numberDoc.getLength(), numberStyle, false);

        stamp(players);


        JButton backgroundButton = backgroundButton();
        pane.add(backgroundButton);
    }

    private static class BackButtonPress extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setIcon(lbackPress.getIcon());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setIcon(lback.getIcon());
        }
    }

    private class BackToLogin implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.backToLogin();
            gui.onBackCommand();
        }
    }

    public static void stamp(List<Player> players){
        clean();
        for (Player player : players){
            if(player.getColor().toString().equalsIgnoreCase("BLUE")){
                StyleConstants.setForeground(colorStyle, Color.BLUE);
            }
            else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
                StyleConstants.setForeground(colorStyle, Color.WHITE);
            }
            else {
                StyleConstants.setForeground(colorStyle, Color.MAGENTA);
            }
            try{
                doc.insertString(doc.getLength(), player.getNickname() + "\n \n", colorStyle);
            }catch (BadLocationException e){
               LOGGER.severe("InsertString Failed");
            }
        }
        number(players.size());
    }

    private static void clean(){
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
        try {
            numberDoc.remove(0, numberDoc.getLength());
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private static void number(int size){
        String value;
        if (size == 1){
            value = "1";
        }
        else if (size == 2){
            value = "2";
        }
        else{
            value = "3";
        }
        try {
            numberDoc.insertString(numberDoc.getLength(), value, fontSize);
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
    }


    public JDesktopPane getPane() {
        return pane;
    }


    public JInternalFrame getPopUp() {
        return popUp;
    }


}

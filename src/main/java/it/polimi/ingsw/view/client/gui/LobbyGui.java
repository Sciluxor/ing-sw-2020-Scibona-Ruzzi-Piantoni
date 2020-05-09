package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class LobbyGui{
    Gui gui;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    private JDesktopPane pane;
    private static JInternalFrame popUp;
    PopUp constructorPopUp = null;
    MyButton backButton = null;
    MyButton confirm = null;
    static JLabel lactualNumber = new JLabel();
    static JLabel lnumber = new JLabel();
    static JLabel lback;
    static JLabel lbackPress;
    JLabel nicknameLabel;
    JTextField nickname = new JTextField(20);
    static Style colorStyle;
    static StyledDocument doc;
    static Style numberStyle;
    static MutableAttributeSet fontSize;
    static StyledDocument numberDoc;
    static int num = 2;
    static List<Player> players = new ArrayList<>();

    public LobbyGui(Gui instance, Dimension frame, Integer numberOfPlayer, List<Player> actualPlayers) throws IOException {

        frameSize.setSize(frame);
        gui = instance;
        num = numberOfPlayer;
        players = actualPlayers;
        pane = new JDesktopPane();
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 45/100);
        pane.setPreferredSize(frameSize);
        pane.setLayout(null);

        popUp = new JInternalFrame("", false, false, false, false);
        confirm = new MyButton(0);
        popUp.add(confirm);
        nicknameLabel = new JLabel("nickname");
        nickname.setBounds((int) (intFrameSize.getWidth() * 40/100), (int) (intFrameSize.getHeight() * 35/100), intFrameSize.width * 20/100,intFrameSize.height * 3/100);
        nickname.setText("Nickname");
        popUp.add(nickname);
        nicknameLabel.setBounds((int) (Gui.getD().getWidth() * 24.5/100), (int) (Gui.getD().getHeight() * 34/100), Gui.getD().width * 15/100,Gui.getD().height * 5/100);
        popUp.add(nicknameLabel);
        popUp.setPreferredSize(intFrameSize);
        internalFrameSetUp(popUp);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)popUp.getUI();
        bii.setNorthPane(null);
        popUp.setVisible(false);
        pane.add(popUp);

        backButton = new MyButton(1);
        backButton.setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 70 / 100), d.width * 13/100, d.height * 5/100);
        backButton.setEnabled(true);
        backButton.addActionListener(new BackToLogin());
        pane.add(backButton);

        JLabel wait = ImageHandler.setImage("resources/Graphics/Texts/waiting_others_players.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel of = ImageHandler.setImage("resources/Graphics/Texts/of.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        JTextPane textPane = new JTextPane();
        JTextPane numberPane = new JTextPane();
        doc = textPane.getStyledDocument();
        numberDoc = numberPane.getStyledDocument();




        wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        pane.add(wait);



        if (num == 2){
            lnumber = ImageHandler.setImage("resources/Graphics/Texts/2.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else {
            lnumber = ImageHandler.setImage("resources/Graphics/Texts/3.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
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


        JButton backgroundButton = backgroundButton(0);
        backgroundButton.setBounds(0, 0, getD().width, getD().height);
        pane.add(backgroundButton);
    }

    private class BackToLogin implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.backToLogin(false);
            gui.onBackCommand();
        }
    }

    public void stamp(List<Player> players){
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
                doc.insertString(doc.getLength(), player.getNickName() + "\n \n", colorStyle);
            }catch (BadLocationException e){
               LOGGER.severe("InsertString Failed");
            }
        }
        number(players.size());
    }

    private void clean(){
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

    private void number(int size){
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


    public void setVisiblePopUp() {
        popUp.setVisible(true);
    }


}

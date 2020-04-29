package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class LobbyGui extends JDesktopPane{
    Dimension frameSize = new Dimension();
    static JButton backButton = new JButton();
    static JLabel lback;
    static JLabel lbackPress;

    public LobbyGui(Gui istance, Dimension frame, Integer actualPlayer, Integer numberOfPlayer, List<Player> players) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

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
        backButton.addActionListener(new Gui.ChangePanel());
        add(backButton);

        JLabel wait = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_others_players.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel actualNumber;
        JLabel number;
        JLabel of = ImageHandler.setImage("src/main/resources/Graphics/Texts/of.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        if (actualPlayer == 1){
            actualNumber = ImageHandler.setImage("src/main/resources/Graphics/Texts/1.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else if (actualPlayer == 2){
            actualNumber = ImageHandler.setImage("src/main/resources/Graphics/Texts/2.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else{
            actualNumber = ImageHandler.setImage("src/main/resources/Graphics/Texts/3.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }

        if (numberOfPlayer == 2){
            number = ImageHandler.setImage("src/main/resources/Graphics/Texts/2.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else {
            number = ImageHandler.setImage("src/main/resources/Graphics/Texts/3.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }


        wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(wait);

        actualNumber.setBounds(frameSize.width * 42/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        add(actualNumber);
        of.setBounds(frameSize.width * 47/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        of.setFont(Gui.felixBold);
        add(of);
        number.setBounds(frameSize.width * 52/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        add(number);


        textPane.setBounds((int) (frameSize.width * 30/100), frameSize.height * 45/100, frameSize.width * 40/100, frameSize.height * 20/100);
        textPane.setOpaque(false);
        textPane.setFont(Gui.felixBold);
        add(textPane);
        Style blue = textPane.addStyle("Blue", null);
        StyleConstants.setAlignment(blue, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), blue, false);

        for (Player player : players){
            if(player.getColor().toString().equalsIgnoreCase("BLUE")){
                StyleConstants.setForeground(blue, Color.BLUE);
            }
            else if(player.getColor().toString().equalsIgnoreCase("WHITE")){
                StyleConstants.setForeground(blue, Color.WHITE);
            }
            else {
                StyleConstants.setForeground(blue, Color.MAGENTA);
            }
            try{
                doc.insertString(doc.getLength(), player.getNickname() + "\n \n", blue);
            }catch (BadLocationException e){
                Logger.info("InsertString Failed");
            }
        }


        JButton backgroundButton = backgroundButton();
        add(backgroundButton);
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
}

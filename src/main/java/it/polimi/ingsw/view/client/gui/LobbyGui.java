package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;

public class LobbyGui extends JPanel{
    Dimension frameSize = new Dimension();

    public LobbyGui(Dimension frame, Integer actualPlayer, Integer numberOfPlayer, List<Player> players) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

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
        JButton back = backgroundButton();
        add(back);
    }
}

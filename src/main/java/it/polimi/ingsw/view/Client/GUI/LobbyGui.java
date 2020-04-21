package it.polimi.ingsw.view.Client.GUI;

import it.polimi.ingsw.model.Player.Player;
import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.view.Client.GUI.Gui.BackgroundButton;

public class LobbyGui extends JPanel{
    Dimension frameSize = new Dimension();

    public LobbyGui(Dimension frame, Integer actualPlayer, Integer numberOfPlayer, ArrayList<Player> players, JLabel background) throws IOException {

        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel wait = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_others_players.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel number = new JLabel(actualPlayer + " of " + numberOfPlayer);
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(wait);

        number.setBounds(frameSize.width * 47/100, frameSize.height * 17/100, frameSize.width * 20/100, frameSize.height * 10/100);
        number.setFont(Gui.felixBold);
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
            }catch (BadLocationException e){}
        }
        JButton back = BackgroundButton();
        add(back);
    }
}

package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.network.message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Board.getBoldDimension;
import static it.polimi.ingsw.view.client.gui.Gui.felixBold;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

/**
 * Classe che estende JDesktopPane che costruisce il pane per gli aggiornamenti durante il gioco
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class UpdateBoard extends JDesktopPane {

    transient Board board;
    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    double bold = getBoldDimension();
    String nameChoosing;

    /**
     * Costruttore della classe
     * @param istance Riferimento alla classe Board istanziata dalla GUI
     * @param frame Riferimento al JInternalFrame in cui verrà inserito l'attuale JDesktopPane UpdateBoard
     * @param dimensionFrame Dimensione del JInternalFrame
     * @param name Nome del Player che ha effettuato un'azione
     * @param type Tipo di azione effettuata
     * @throws IOException se il caricamento delle scritte non andasse a buon fine
     */

    public UpdateBoard(Board istance, JInternalFrame frame, Dimension dimensionFrame, String name, MessageType type) throws IOException {

        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        board = istance;
        nameChoosing = name;
        setPreferredSize(frameSize);
        setLayout(null);

        if (type.toString().equalsIgnoreCase("moveworker")){
            JLabel label = ImageHandler.setImage("resources/Graphics/Texts/moved.png", 100, 100, frameSize.width * 25/100, frameSize.height * 20/100);
            label.setBounds(frameSize.width * 55/100, frameSize.height * 35/100, frameSize.width * 25/100, frameSize.height * 20/100);
            add(label);
        }
        else if (type.toString().equalsIgnoreCase("buildworker")) {
            JLabel label1 = ImageHandler.setImage("resources/Graphics/Texts/builded.png", 100, 100, frameSize.width * 25 / 100, frameSize.height * 20 / 100);
            label1.setBounds(frameSize.width * 55 / 100, frameSize.height * 35 / 100, frameSize.width * 25 / 100, frameSize.height * 20 / 100);
            add(label1);
        }

        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) (((double)frameSize.width * 48/100) - (otherName.getText().length() * bold)), (int) (frameSize.height * 41.5/100), frameSize.width * 60/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);
        add(otherName);

        close.addActionListener(new Close());
        close.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 83 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize );
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    /**
     * Classe che implementa ActionListener per il JButton Close che chiude l'attuale JInternalFrame
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }
}

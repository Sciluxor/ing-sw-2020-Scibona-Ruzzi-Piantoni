package it.polimi.ingsw.view.client.gui;

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
 * Classe che estende JDesktopPane per la costruzione del pane per la segnalazione di inizio turno
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class StartTurn extends JDesktopPane{
    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;
    double bold = getBoldDimension();

    /**
     * Costruttore della classe
     * @param frame Riferimento al JInternalFrame in cui verrà inserito l'attuale JDesktopPane StartTurn
     * @param size Dimensione del JInternalFrame
     * @param numberOfPanel Parametro che indica il JDesktopPane da costruire in base a chi inizia il turno
     * @param name Nome del Player che inizia il turno
     * @throws IOException se il caricamento delle scritte non andasse a buon fine
     */

    public StartTurn(JInternalFrame frame, Dimension size, int numberOfPanel, String name) throws IOException {
        frameSize.setSize(size);
        intFrame = frame;
        nameChoosing = name;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);



        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/is_starting_the_turn.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label2 = ImageHandler.setImage("resources/Graphics/Texts/it's_your_turn.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 39.5/100), frameSize.width * 85/100, frameSize.height * 22/100);
        label2.setBounds((int) (frameSize.width * 20/100), frameSize.height * 39/100, frameSize.width * 60/100, frameSize.height * 22/100);
        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) (((double)frameSize.width * 48/100) - ((otherName.getText().length() * bold) / 2)), (int) (frameSize.height * 34.5/100), frameSize.width * 60/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);

        if (numberOfPanel == 0){
            add(label2);
        }
        else if (numberOfPanel == 1){
            add(label);
            add(otherName);
        }


        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/100) - (buttonSize.width / 2)), (int) (frameSize.height * 81 / 100),  buttonSize.width, buttonSize.height);
        add(close);


        JButton back = backgroundButtonPersonalized(2, frameSize);
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

package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class YouHaveBeenChosen extends JDesktopPane {

    transient Board board;
    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;

    public YouHaveBeenChosen(Board istance, JInternalFrame frame, Dimension dimensionFrame) throws IOException {

        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        board = istance;
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/you_have_been_chosen_as_challenger.png", 100, 100, frameSize.width * 70/100, frameSize.height * 20/100);
        label.setBounds(frameSize.width * 15/100, frameSize.height * 25/100, frameSize.width * 70/100, frameSize.height * 20/100);
        add(label);

        JLabel label1 = ImageHandler.setImage("resources/Graphics/Texts/please_chose_the_gods_and.png", 100, 100, frameSize.width * 60/100, frameSize.height * 20/100);
        label1.setBounds(frameSize.width * 20/100, frameSize.height * 40/100, frameSize.width * 60/100, frameSize.height * 20/100);
        add(label1);

        JLabel label2 = ImageHandler.setImage("resources/Graphics/Texts/the_first_player_that_start.png", 100, 100, frameSize.width * 60/100, frameSize.height * 20/100);
        label2.setBounds(frameSize.width * 20/100, frameSize.height * 55/100, frameSize.width * 60/100, frameSize.height * 20/100);
        add(label2);

        close.addActionListener(new Close());
        close.setBounds((int) ((double)(frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 83 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize );
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
            board.enableCardsFirst(true);
        }
    }
}

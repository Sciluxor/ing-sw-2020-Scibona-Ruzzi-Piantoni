package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.Gui.getD;
import static it.polimi.ingsw.view.client.gui.GuiUtils.backAndCloseSetter;

/**
 * Class that extends JDesktopPane that build the pane for the message of chosen as Challenger
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class YouHaveBeenChosen extends JDesktopPane {

    transient Board board;
    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;

    /**
     * Public constructor
     * @param instance Reference to the Board class instanced by the GUI
     * @param intFrame Reference to the JInternalFrame where the current JDesktopPane YouHaveBeenChosen will be inserted
     * @param intFrameSize Size of the JInternalFrame
     * @throws IOException if the loading of the inscription was not successful
     */

    public YouHaveBeenChosen(Board instance, JInternalFrame intFrame, Dimension intFrameSize) throws IOException {

        frameSize.setSize(intFrameSize);
        this.intFrame = intFrame;
        board = instance;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage(TEXT + "you_have_been_chosen_as_challenger.png", 100, 100, frameSize.width * 70/100, frameSize.height * 20/100);
        label.setBounds(frameSize.width * 15/100, frameSize.height * 25/100, frameSize.width * 70/100, frameSize.height * 20/100);
        add(label);

        JLabel label1 = ImageHandler.setImage(TEXT + "please_chose_the_gods_and.png", 100, 100, frameSize.width * 60/100, frameSize.height * 20/100);
        label1.setBounds(frameSize.width * 20/100, frameSize.height * 40/100, frameSize.width * 60/100, frameSize.height * 20/100);
        add(label1);

        JLabel label2 = ImageHandler.setImage(TEXT + "the_first_player_that_start.png", 100, 100, frameSize.width * 60/100, frameSize.height * 20/100);
        label2.setBounds(frameSize.width * 20/100, frameSize.height * 55/100, frameSize.width * 60/100, frameSize.height * 20/100);
        add(label2);

        close.addActionListener(new Close());
        backAndCloseSetter(this, frameSize, buttonSize, close);
    }

    /**
     * Class that implements ActionListener for the JButton Close which closes the current JInternalFrame
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
            board.enableCardsFirst(true);
        }
    }
}

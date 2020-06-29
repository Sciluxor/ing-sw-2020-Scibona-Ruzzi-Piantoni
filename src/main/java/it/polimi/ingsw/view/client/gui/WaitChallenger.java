package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.Gui.*;
import static it.polimi.ingsw.view.client.gui.GuiUtils.backAndCloseSetter;

/**
 * Class that extends JDesktopPane that builds pane for the message of waiting for the challenger choices
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class WaitChallenger extends JDesktopPane{

    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;
    double posx;

    /**
     * Public constructor
     * @param frame Reference to the JInternalFrame where the current JDesktopPane WaitChallenger will be inserted
     * @param dimensionFrame Size of the JInternalFrame
     * @param name Name of the Player designated as a Challenger
     * @throws IOException if the loading of the inscription was not successful
     */

    public WaitChallenger(JInternalFrame frame, Dimension dimensionFrame, String name) throws IOException {

        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);
        nameChoosing = name;
        posx = (double) nameChoosing.length() / 2;

        JLabel label = ImageHandler.setImage(TEXT + "wait_for.png", 99, 99, frameSize.width * 25/100, frameSize.height * 20/100);
        JLabel label2 = ImageHandler.setImage(TEXT + "as_challenger_to_choose_the_gods.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label3 = ImageHandler.setImage(TEXT + "and_the_first_player.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 18.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 25/100, frameSize.height * 20/100);
        label2.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 39.5/100), frameSize.width * 85/100, frameSize.height * 22/100);
        label3.setBounds((frameSize.width * 20/100), (int) (frameSize.height * 54.5/100), frameSize.width * 60/100, frameSize.height * 22/100);

        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) (frameSize.width * 45.75/100), (int) (frameSize.height * 32.5/100), frameSize.width * 50/100, frameSize.width * 5/100);
        otherName.setFont(getFelixBold());

        add(label);
        add(label2);
        add(label3);
        add(otherName);


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
        }
    }
}

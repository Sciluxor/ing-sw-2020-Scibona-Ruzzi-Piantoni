package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.felixBold;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

/**
 * Class that extends JDesktopPane that builds pane for the message of waiting for the challenger choices
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class WaitChallenger extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;
    double posx;

    /**
     * Class builder
     * @param frame Reference to the JInternalFrame where the current JDesktopPane WaitChallenger will be inserted
     * @param dim Size of the JInternalFrame
     * @param name Name of the Player designated as a Challenger
     * @throws IOException if the loading of the inscription was not successful
     */

    public WaitChallenger(JInternalFrame frame, Dimension dim, String name) throws IOException {

        frameSize.setSize(dim);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);
        nameChoosing = name;
        posx = (double) nameChoosing.length() / 2;

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/wait_for.png", 99, 99, frameSize.width * 25/100, frameSize.height * 20/100);
        JLabel label2 = ImageHandler.setImage("resources/Graphics/Texts/as_challenger_to_choose_the_gods.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label3 = ImageHandler.setImage("resources/Graphics/Texts/and_the_first_player.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 18.5/100), (int) (frameSize.height * 25.5/100), frameSize.width * 25/100, frameSize.height * 20/100);
        label2.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 39.5/100), frameSize.width * 85/100, frameSize.height * 22/100);
        label3.setBounds((int) (frameSize.width * 20/100), (int) (frameSize.height * 54.5/100), frameSize.width * 60/100, frameSize.height * 22/100);

        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) (frameSize.width * 45.75/100), (int) (frameSize.height * 32.5/100), frameSize.width * 50/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);

        add(label);
        add(label2);
        add(label3);
        add(otherName);


        close.addActionListener(new Close());
        close.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
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

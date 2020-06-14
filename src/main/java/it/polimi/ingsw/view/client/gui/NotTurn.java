package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

/**
 * Class that extends JDesktopPane to build the pane for actions out of its turn
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class NotTurn extends JDesktopPane {

    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;

    /**
     * Class builder
     * @param frame Reference to the JInternalFrame where the current JDesktopPane NotTurn will be inserted
     * @param size Size of the JInternalFrame
     * @throws IOException if the loading of the inscription was not successful
     */

    public NotTurn(JInternalFrame frame, Dimension size) throws IOException {
        frameSize.setSize(size);
        intFrame = frame;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label1 = ImageHandler.setImage("resources/Graphics/Texts/it's_not_your_turn.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label1.setBounds((int) (frameSize.width * 20/100), frameSize.height * 39/100, frameSize.width * 60/100, frameSize.height * 22/100);
        add(label1);

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/100) - (buttonSize.width / 2)), (int) (frameSize.height * 81 / 100),  buttonSize.width, buttonSize.height);
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

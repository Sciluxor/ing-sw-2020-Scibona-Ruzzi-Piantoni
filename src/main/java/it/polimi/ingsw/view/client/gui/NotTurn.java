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
 * Class that extends JDesktopPane to build the pane for actions out of its turn
 * @author Luigi Scibona
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
     * @param dimensionFrame Size of the JInternalFrame
     * @throws IOException if the loading of the inscription was not successful
     */

    public NotTurn(JInternalFrame frame, Dimension dimensionFrame) throws IOException {
        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label1 = ImageHandler.setImage(TEXT + "it's_not_your_turn.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label1.setBounds((frameSize.width * 20/100), frameSize.height * 39/100, frameSize.width * 60/100, frameSize.height * 22/100);
        add(label1);

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

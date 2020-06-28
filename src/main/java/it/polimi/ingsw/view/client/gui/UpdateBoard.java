package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.network.message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.Board.getBolddimension;
import static it.polimi.ingsw.view.client.gui.Gui.*;
import static it.polimi.ingsw.view.client.gui.GuiUtils.backAndCloseSetter;

/**
 * Class that extends JDesktopPane that builds the pane for updates during the game
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class UpdateBoard extends JDesktopPane {

    transient Board board;
    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    double bold = getBolddimension();
    String nameChoosing;

    /**
     * Class builder
     * @param istance Reference to the Board class instanced by the GUI
     * @param frame Reference to the JInternalFrame where the current JDesktopPane UpdateBoard will be inserted
     * @param dimensionFrame Size of the JInternalFrame
     * @param name Name of the Player who performed an action
     * @param type Type of action taken
     * @throws IOException if the loading of the inscription was not successful
     */

    public UpdateBoard(Board istance, JInternalFrame frame, Dimension dimensionFrame, String name, MessageType type) throws IOException {

        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        board = istance;
        nameChoosing = name;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
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
        otherName.setFont(getFelixBold());
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

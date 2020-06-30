package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Board.getBoldDimension;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class that extends JDesktopPane for the construction of the pane that warns of the game phase of the positioning of the Workers
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class PlaceWorkers extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;
    double bold = getBoldDimension();

    /**
     * Public constructor
     * @param intFrame Reference to the JInternalFrame where the current JDesktopPane PlaceWorkers will be inserted
     * @param intFrameSize Size of the JInternalFrame
     * @param numberOfPanel Parameter indicating the JDesktopPane to be built if you are the one who chooses or not
     * @param name Name of the Player that is choosing
     * @throws IOException if the loading of the inscription was not successful
     */

    public PlaceWorkers(JInternalFrame intFrame, Dimension intFrameSize, int numberOfPanel, String name) throws IOException {

        frameSize.setSize(intFrameSize);
        this.intFrame = intFrame;
        nameChoosing = name;
        setPreferredSize(frameSize);
        setLayout(null);
        JLabel label;
        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) (((double)frameSize.width * 48/100) - ((otherName.getText().length() * bold) / 2)), (int) (frameSize.height * 32.5/100), frameSize.width * 60/100, frameSize.width * 5/100);
        otherName.setFont(getFelixBold());

        if (numberOfPanel == 0) {
            label = ImageHandler.setImage(TEXT + "place_your_two_workers.png", 100, 100, frameSize.width * 85/100, frameSize.height * 25/100);
            label.setBounds((int) (frameSize.width * 7.5 / 100), (frameSize.height * 35 / 100), frameSize.width * 85 / 100, frameSize.height * 25 / 100);
            add(label);
        }
        else if (numberOfPanel == 1){
            add(otherName);
            label = ImageHandler.setImage(TEXT + "is_placing_the_workers.png", 100, 100, frameSize.width * 85/100, frameSize.height * 25/100);
            label.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 37.5/100), frameSize.width * 85/100, frameSize.height * 25/100);
            add(label);
        }

        close.addActionListener(new Close());
        close.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
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

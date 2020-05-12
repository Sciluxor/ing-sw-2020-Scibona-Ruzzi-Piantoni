package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.network.message.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.felixBold;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class UpdateBoard extends JDesktopPane {

    Board board;
    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;

    String nameChoosing;

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
        otherName.setBounds((int) ((frameSize.width * 10/100)), (int) (frameSize.height * 41.5/100), frameSize.width * 50/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);
        add(otherName);

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 83 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize );
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }
}

package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class YouHaveBeenChosen extends JDesktopPane {

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;

    public YouHaveBeenChosen(JInternalFrame frame, Dimension dimensionFrame) throws IOException {

        frameSize.setSize(dimensionFrame);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/waiting_for_challenger_to_choose_the_god.png", 100, 100, frameSize.width * 40/100, frameSize.height * 20/100);
        label.setBounds(frameSize.width * 30/100, frameSize.height * 5/100, frameSize.width * 40/100, frameSize.height * 20/100);
        add(label);

        close.addActionListener(new Close());
        close.setBounds((int) (frameSize.width * 43.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButton(0 );
        add(back);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            intFrame.setVisible(false);
        }
    }
}

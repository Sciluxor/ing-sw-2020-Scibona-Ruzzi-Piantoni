package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class WaitChallenger extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;


    public WaitChallenger(JInternalFrame frame, Dimension dim, String name) throws IOException {

        frameSize.setSize(dim);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);

        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/wait_for.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label2 = ImageHandler.setImage("resources/Graphics/Texts/as_challenger_to_choose_the_gods.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label3 = ImageHandler.setImage("resources/Graphics/Texts/and_the_first_player.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 7.5/100), frameSize.height * 39/100, frameSize.width * 85/100, frameSize.height * 22/100);
        label2.setBounds((int) (frameSize.width * 7.5/100), frameSize.height * 45/100, frameSize.width * 85/100, frameSize.height * 22/100);
        label3.setBounds((int) (frameSize.width * 7.5/100), frameSize.height * 60/100, frameSize.width * 85/100, frameSize.height * 22/100);
        add(label);

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButtonPersonalized(2, frameSize);
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

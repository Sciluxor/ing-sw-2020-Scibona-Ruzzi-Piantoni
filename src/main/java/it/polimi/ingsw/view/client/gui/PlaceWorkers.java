package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Board.getBoldDimension;
import static it.polimi.ingsw.view.client.gui.Gui.felixBold;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class PlaceWorkers extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;
    double bold = getBoldDimension();


    public PlaceWorkers(JInternalFrame frame, Dimension size, int numberOfPanel, String name) throws IOException {

        frameSize.setSize(size);
        intFrame = frame;
        nameChoosing = name;
        setPreferredSize(frameSize);
        setLayout(null);
        JLabel label = new JLabel();
        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) ((frameSize.width * 48/100) - ((otherName.getText().length() * bold) / 2)), (int) (frameSize.height * 32.5/100), frameSize.width * 60/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);

        if (numberOfPanel == 0) {
            label = ImageHandler.setImage("resources/Graphics/Texts/place_your_two_workers.png", 100, 100, frameSize.width * 85/100, frameSize.height * 25/100);
            label.setBounds((int) (frameSize.width * 7.5 / 100), (int) (frameSize.height * 35 / 100), frameSize.width * 85 / 100, frameSize.height * 25 / 100);
            add(label);
        }
        else if (numberOfPanel == 1){
            add(otherName);
            label = ImageHandler.setImage("resources/Graphics/Texts/is_placing_the_workers.png", 100, 100, frameSize.width * 85/100, frameSize.height * 25/100);
            label.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 37.5/100), frameSize.width * 85/100, frameSize.height * 25/100);
            add(label);
        }

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
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

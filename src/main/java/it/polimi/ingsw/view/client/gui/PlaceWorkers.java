package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class PlaceWorkers extends JDesktopPane{

    Dimension frameSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;


    public PlaceWorkers(JInternalFrame frame, int wi, int he, int numberOfPanel) throws IOException {

        frameSize.setSize(wi, he);
        intFrame = frame;
        setPreferredSize(frameSize);
        setLayout(null);
        JLabel label = new JLabel();

        if (numberOfPanel == 0) {
            //JLabel label = ImageHandler.setImage("resources/Graphics/Texts/waiting_for_challenger_to_choose_the_god.png", 99, 99, frameSize.width * 85/100, frameSize.height * 25/100);
            label.setText("place workers");
            label.setBounds((int) (frameSize.width * 7.5 / 100), frameSize.height * 30 / 100, frameSize.width * 85 / 100, frameSize.height * 25 / 100);
            add(label);
        }
        else if (numberOfPanel == 1){
            label.setText("altro is placing");
            label.setBounds((int) (frameSize.width * 7.5 / 100), frameSize.height * 30 / 100, frameSize.width * 85 / 100, frameSize.height * 25 / 100);
            add(label);
        }

        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(close);

        JButton back = backgroundButton(1 );
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

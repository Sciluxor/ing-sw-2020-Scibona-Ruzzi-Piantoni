package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButtonPersonalized;
import static it.polimi.ingsw.view.client.gui.Gui.felixBold;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class StartTurn extends JDesktopPane{
    Dimension frameSize = new Dimension();
    Dimension buttonSize = new Dimension();
    MyButton close = new MyButton(3);
    JInternalFrame intFrame;
    String nameChoosing;

    public StartTurn(JInternalFrame frame, Dimension size, int numberOfPanel, String name) throws IOException {
        frameSize.setSize(size);
        intFrame = frame;
        nameChoosing = name;
        buttonSize.setSize((getD().getWidth() * 13 / 100), (getD().getHeight() * 5 / 100));
        setPreferredSize(frameSize);
        setLayout(null);



        JLabel label = ImageHandler.setImage("resources/Graphics/Texts/is_starting_the_turn.png", 99, 99, frameSize.width * 85/100, frameSize.height * 22/100);
        JLabel label2 = ImageHandler.setImage("resources/Graphics/Texts/it's_your_turn.png", 99, 99, frameSize.width * 60/100, frameSize.height * 22/100);
        label.setBounds((int) (frameSize.width * 7.5/100), (int) (frameSize.height * 39.5/100), frameSize.width * 85/100, frameSize.height * 22/100);
        label2.setBounds((int) (frameSize.width * 20/100), frameSize.height * 39/100, frameSize.width * 60/100, frameSize.height * 22/100);
        JLabel otherName = new JLabel(nameChoosing);
        otherName.setBounds((int) ((frameSize.width * 35/100)), (int) (frameSize.height * 34.5/100), frameSize.width * 50/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);

        if (numberOfPanel == 0){
            add(label2);
        }
        else if (numberOfPanel == 1){
            add(label);
            add(otherName);
        }


        close.addActionListener(new Close());
        close.setBounds((int) ((frameSize.width * 50/100) - (buttonSize.width / 2)), (int) (frameSize.height * 81 / 100),  buttonSize.width, buttonSize.height);
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

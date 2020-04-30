package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;

import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;

public class PopUp {
    JInternalFrame window;
    Dimension intFrameSize;
    ConfirmButton confirm;
    JTextField nickname;
    JLabel nicknameLabel;

    public PopUp(){
        window = new JInternalFrame();
       // intFrameSize.setSize(Gui.getD().getWidth() * 40/100, Gui.getD().getHeight() * 45/100);
        nickname = new JTextField(20);
    }

    public JInternalFrame lobbyPopUp(){
        window.removeAll();
        confirm = new ConfirmButton();
        window.add(confirm);
        nicknameLabel = new JLabel("nickname");

        nickname.setBounds((int) (Gui.getD().getWidth() * 40/100), (int) (Gui.getD().getHeight() * 35/100), Gui.getD().width * 20/100,Gui.getD().height * 3/100);
        nickname.setText("Nickname");
        window.add(nickname);
        nicknameLabel.setBounds((int) (Gui.getD().getWidth() * 24.5/100), (int) (Gui.getD().getHeight() * 34/100), Gui.getD().width * 15/100,Gui.getD().height * 5/100);
        window.add(nicknameLabel);
        window = new JInternalFrame("", false, false, false, false);
        window.setPreferredSize(intFrameSize);
        internalFrameSetUp(window);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)window.getUI();
        bii.setNorthPane(null);
        window.setVisible(false);
        return window;
    }
}

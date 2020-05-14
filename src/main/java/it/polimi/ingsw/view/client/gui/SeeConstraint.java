package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

public class SeeConstraint extends JDesktopPane {

    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    Dimension cardSize = new Dimension();
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    JButton buttonBackground = new JButton();
    List<JButton> godList = new ArrayList<>();
    ButtonGodsList costructor;
    MyButton back = new MyButton(1);
    JLabel cover =new JLabel();
    JLabel label =new JLabel();
    JLabel power;
    JLabel current;
    JButton mePower;
    JButton constraint1;
    JButton constraint2;

    public SeeConstraint(JInternalFrame frame, Dimension dimensionFrame, List<String> constraint) throws IOException {

        guiIntFrame = frame;
        frameSize.setSize(dimensionFrame);
        intFrameSize.setSize(frameSize.getWidth() * 48/100, frameSize.getHeight() * 54/100);
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)

        final int xconst = frameSize.width * 9/100;
        final int yconst = frameSize.height * 24/100;
        int x = xconst;
        int y = yconst;

        costructor = new ButtonGodsList(frameSize, godList);

        intFrame = new JInternalFrame("", false, false, false, false);
        intFrame.setPreferredSize(intFrameSize);
        internalFrameSetUp(intFrame);
        BasicInternalFrameUI bii = (BasicInternalFrameUI) intFrame.getUI();
        bii.setNorthPane(null);
        intFrame.setVisible(false);
        add(intFrame);

        buttonBackground.setBounds(0, 0,intFrameSize.width, intFrameSize.height);
        buttonBackground.setOpaque(false);
        buttonBackground.setContentAreaFilled(false);
        buttonBackground.setBorderPainted(false);
        intFrame.add(buttonBackground);

        setPreferredSize(frameSize);

        buttonStyle();

        if (constraint.size() == 3){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(x, y, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(x + frameSize.width * 12/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint1);

            constraint2 = getButtonFromConstraint(constraint.get(2));
            if (constraint2 != null) {
                constraint2.setBounds(x + frameSize.width * 24/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint2);
        }

        if (constraint.size() == 2){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(x + frameSize.width * 6/100, y, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(x + frameSize.width * 18/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint1);
        }


        back.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(back);
        back.addActionListener(new Close());

        JButton back = backgroundButton(0);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);

    }

    private void buttonStyle(){
        for (JButton button : godList){
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addMouseListener(new ColorBorderGodCards());
            button.addMouseListener(new ShowPower());
        }
    }

    private class ShowPower extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            if (c.getX() < frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100) {
                intFrame.setBounds((int) ((frameSize.width * 9 / 100) + c.getX()), (int) ((frameSize.height * 8.5 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() >= frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100){
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 48 / 100)), (int) ((frameSize.height * 8.5 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() < frameSize.width * 50/100 && c.getY() >= frameSize.height * 40/100){
                intFrame.setBounds((int) ((frameSize.width * 9 / 100) + c.getX()), (int) ((frameSize.height * 33 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 48 / 100)), (int) ((frameSize.height * 33 / 100)), intFrameSize.width, intFrameSize.height);

            buttonBackground.setIcon(null);
            try {
                cover = ImageHandler.setImage("resources/Graphics/gods/" + c.getName() + "_description.png", 100, 100, intFrame.getWidth() , intFrame.getHeight() );
            } catch (IOException ex) {
                LOGGER.severe(ex.getMessage());
            }
            label.setIcon(cover.getIcon());
            buttonBackground.setIcon(label.getIcon());
            intFrame.setVisible(true);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            intFrame.setVisible(false);
        }
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiIntFrame.setVisible(false);
        }
    }

    private JButton getButtonFromConstraint(String god){
        for (JButton button : godList){
            if (button.getName().equalsIgnoreCase(god)){
                return button;
            }
        }
        return null;
    }
}

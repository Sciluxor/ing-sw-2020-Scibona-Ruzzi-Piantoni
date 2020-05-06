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
import static it.polimi.ingsw.view.client.gui.EliminateListeners.eliminateActionClass;
import static it.polimi.ingsw.view.client.gui.EliminateListeners.eliminateMouseClass;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChooseCard extends JDesktopPane{

    Board board;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    JButton buttonBackground = new JButton();
    JLabel cover = new JLabel();
    JLabel label = new JLabel();
    List<String> godCards = null;
    List<JButton> godChoosen = new ArrayList<>();
    List<JButton> godList = new ArrayList<>();
    ButtonGodsList costructor;
    MyButton confirm = new MyButton(0);
    MyButton backButton = new MyButton(1);
    MyButton close = new MyButton(3);
    private int chosen = 0;
    String cardChosen = null;
    String nameChoosing;
    int posx;

    public ChooseCard(Board instance, JInternalFrame aframe,Dimension frame, List<String> cards, Integer numberOfPanel, String name) throws IOException {

        board = instance;
        guiIntFrame = aframe;
        frameSize.setSize(frame);
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 45/100);
        setPreferredSize(frameSize);

        nameChoosing = name;
        posx = nameChoosing.length() * 50/100;
        godCards = cards;
        costructor = new ButtonGodsList(frameSize, godList);

        intFrame = new JInternalFrame("", false, false, false, false);
        intFrame.setPreferredSize(intFrameSize);
        internalFrameSetUp(intFrame);
        BasicInternalFrameUI bii = (BasicInternalFrameUI)intFrame.getUI();
        bii.setNorthPane(null);
        intFrame.setVisible(false);
        add(intFrame);

        buttonBackground.setBounds(0, 0,intFrameSize.width, intFrameSize.height);
        buttonBackground.setOpaque(false);
        buttonBackground.setContentAreaFilled(false);
        buttonBackground.setBorderPainted(false);
        intFrame.add(buttonBackground);

        int x = (int) (frameSize.width * 33.5/100);
        int y = (int) (frameSize.height * 35/100);
        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100));

        JLabel choose = ImageHandler.setImage("resources/Graphics/Texts/choose_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel choise = ImageHandler.setImage("resources/Graphics/Texts/this_is_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel isChoosing = ImageHandler.setImage("resources/Graphics/Texts/is_choosing_the_god_power.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel first = ImageHandler.setImage("resources/Graphics/Texts/choose_your_god_power.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel otherName = new JLabel(name);
        otherName.setBounds((int) ((frameSize.width * 40/100) - posx), (int) (frameSize.height * 10/100), frameSize.width * 20/100, frameSize.width * 5/100);
        otherName.setFont(felixBold);

        selectGodsChosen();

        buttonStyle();

        if(numberOfPanel == 3 || numberOfPanel == 2) {

            choose.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
            add(choose);

            confirm.setBounds((int) (frameSize.width * 31.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
            add(confirm);

            backButton.setBounds((int) (frameSize.width * 51.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
            add(backButton);
            backButton.addActionListener(new Close());


            if (numberOfPanel == 3){
                godChoosen.get(0).setBounds(x, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));

                godChoosen.get(1).setBounds(x + frameSize.width * 12/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(1));

                godChoosen.get(2).setBounds(x + frameSize.width * 24/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(2));
            }

            if (numberOfPanel == 2){
                godChoosen.get(0).setBounds(x + frameSize.width * 6/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));

                godChoosen.get(1).setBounds(x + frameSize.width * 18/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(1));
            }
            JButton back = backgroundButton(0);
            back.setBounds(0, 0, frameSize.width, frameSize.height);
            add(back);
        }

        else {

            if (numberOfPanel == 1){

                choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(choise);

                godChoosen.get(0).setBounds((int) (frameSize.width * 45.5/100), y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));

                confirm.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                add(confirm);

                JButton back = backgroundButton(0);
                back.setBounds(0, 0, frameSize.width, frameSize.height);
                add(back);
            }
            else if (numberOfPanel == 4){

                first.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(first);

                close.addActionListener(new Close());
                close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                add(close);

                JButton back = backgroundButton(0);
                back.setBounds(0, 0, frameSize.width, frameSize.height);
                add(back);
            }
            else{
                add(otherName);

                isChoosing.setBounds(frameSize.width * 35/100, frameSize.height * 15/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(isChoosing);

                close.addActionListener(new Close());
                close.setBounds((int) ((frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) * 50/100)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
                add(close);

                JButton back = backgroundButton(1);
                back.setBounds(0, 0, frameSize.width, frameSize.height);
                add(back);
            }
        }
        JButton back = backgroundButton(0);
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        add(back);
    }

    private class ShowPower extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            if (c.getX() < frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100) {
                intFrame.setBounds((int) ((frameSize.width * 11 / 100) + c.getX()), (int) ((frameSize.height * 25 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 41.5 / 100)), (int) ((frameSize.height * 25 / 100)), intFrameSize.width, intFrameSize.height);

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

    private void buttonStyle(){
        for (JButton button : godChoosen){
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addMouseListener(new ColorBorderGodCards());
            button.addMouseListener(new ShowPower());
            button.addActionListener(new ChooseGod());
        }
    }

    private void selectGodsChosen(){
        for (String god : godCards){
            for (JButton button : godList){
                if (god.equalsIgnoreCase(button.getName())){
                    godChoosen.add(button);
                }
            }
        }
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiIntFrame.setVisible(false);
        }
    }

    private class ChooseGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            if (chosen == 0){

                eliminateMouseClass(c, ColorBorderGodCards.class);
                eliminateActionClass(c, ChooseCard.ChooseGod.class);
                c.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                c.setBorderPainted(true);
                cardChosen = c.getName();
                chosen++;
                c.addActionListener(new ChooseCard.RemoveGod());
            }
            if (chosen == 1 && confirm.getActionListeners().length == 0){
                confirm.addActionListener(new Confirm());
            }
        }
    }
    private class RemoveGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, ChooseCard.RemoveGod.class);
            c.setBorder(null);
            c.setBorderPainted(false);
            c.addMouseListener(new ColorBorderGodCards());
            cardChosen = null;
            chosen--;
            c.addActionListener(new ChooseCard.ChooseGod());
            eliminateActionClass(confirm, Confirm.class);
        }
    }

    private class Confirm implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            board.setCardChosen(cardChosen);
            board.callCardChoiseResponse();
            guiIntFrame.setVisible(false);
        }
    }
}

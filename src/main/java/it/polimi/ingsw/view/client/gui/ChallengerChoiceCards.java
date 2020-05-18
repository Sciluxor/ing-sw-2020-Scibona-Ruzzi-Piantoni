package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.EliminateListeners.*;
import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChallengerChoiceCards extends JDesktopPane{

    Gui gui;
    Board board;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    Dimension cardSize = new Dimension();
    Dimension size30x10 = new Dimension();
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    JButton buttonBackground = new JButton();
    JLabel cover =new JLabel();
    JLabel label =new JLabel();
    private int count = 0;
    private int chosen = 0;
    private final int numberPlayers;
    private final List<JButton> godChosen = new ArrayList<>();
    MyButton confirm = new MyButton(0);
    MyButton back = new MyButton(1);
    List<String> cardsChosen = new ArrayList<>();
    List<JButton> godList = new ArrayList<>();
    ButtonGodsList costructor;
    MP3 click;

    public ChallengerChoiceCards(Gui instance, Board instance2, JInternalFrame aframe, Dimension dimensionFrame, Integer numberOfPlayer) throws IOException {

        gui = instance;
        board = instance2;
        guiIntFrame = aframe;
        frameSize.setSize(dimensionFrame);
        numberPlayers = numberOfPlayer;
        intFrameSize.setSize(frameSize.getWidth() * 48/100, frameSize.getHeight() * 54/100);
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)
        size30x10.setSize(frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        click = new MP3("resources/Music/Click.mp3");

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

        JButton chronus = new JButton();
        chronus.setName("chronus");

        buttonStyle();

        JLabel choise;
        if (numberOfPlayer == 2){
            choise = ImageHandler.setImage("resources/Graphics/Texts/choose_2_gods.png", 100, 100, size30x10.width, size30x10.height);
        }
        else{
            choise = ImageHandler.setImage("resources/Graphics/Texts/choose_3_gods.png", 100, 100, size30x10.width, size30x10.height);
        }
        choise.setBounds(((frameSize.width * 50/100) - (size30x10.width / 2)), frameSize.height * 10/100, size30x10.width, size30x10.height);
        add(choise);

        confirm.setBounds((int) (frameSize.width * 31.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(confirm);

        back.setBounds((int) (frameSize.width * 51.5/ 100), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(back);
        back.addActionListener(new Close());


        if(numberOfPlayer == 2){
            addForTwo(x, y, yconst);
        }
        else{
            addForThree(chronus, x, y, yconst);
        }

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
             button.addActionListener(new ChooseGod());
         }
     }

     private void buttonPositioning(JButton button, int x, int y){
         button.setBounds(x, y, cardSize.width, cardSize.height);
         add(button);
     }

     private void addForTwo(int x, int y, int yconst){
         for (JButton button : godList){
            if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
             else{
                if (y == yconst){

                    x = - frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
         }
     }

     private void addForThree(JButton chronus, int x, int y, int yconst){
         for (JButton button : godList){
             if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7 && !button.getName().equalsIgnoreCase(chronus.getName())){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
            else if(!button.getName().equalsIgnoreCase(chronus.getName())){
                if (y == yconst){

                    x = frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
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



    private class ChooseGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            click.play();
            JButton c = (JButton)e.getSource();
            if (0 <= chosen && chosen < numberPlayers){

                eliminateMouseClass(c, ColorBorderGodCards.class);
                eliminateActionClass(c, ChooseGod.class);
                c.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                c.setBorderPainted(true);
                addGod(c);
                chosen++;
                c.addActionListener(new RemoveGod());
            }
            if (chosen == numberPlayers && confirm.getActionListeners().length == 0){
                confirm.addActionListener(new Confirm());
            }
        }
    }
    private class RemoveGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            click.play();
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, RemoveGod.class);
            c.setBorder(null);
            c.setBorderPainted(false);
            c.addMouseListener(new ColorBorderGodCards());
            godChosen.remove(c);
            chosen--;
            c.addActionListener(new ChooseGod());
            eliminateActionClass(confirm, ChallengerChoiceCards.Confirm.class);
        }
    }

    private void addGod(JButton god){

        for (int x = 0; x < godChosen.size(); x++){
            if (godChosen.get(x).getName().compareTo(god.getName()) > 0){
                godChosen.add(x, god);
                return;
            }
        }
        godChosen.add(god);
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiIntFrame.setVisible(false);
        }
    }

    private class Confirm implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button : godChosen){
                cardsChosen.add(button.getName());
            }
            board.setCardsChosen(cardsChosen);
            guiIntFrame.setVisible(false);
            board.callChallengerResponse();
            board.buttonChooseCards.setEnabled(false);
        }
    }
}

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
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    private final List<JButton> buttons = new ArrayList<>();
    JButton buttonBackground = new JButton();
    JLabel cover =new JLabel();
    JLabel label =new JLabel();
    private int count = 0;
    private int chosen = 0;
    private final int numberPlayers;
    private static final List<JButton> godChosen = new ArrayList<>();
    MyButton confirm = new MyButton(0);
    MyButton back = new MyButton(1);
    List<String> cardsChosen = new ArrayList<>();

    public ChallengerChoiceCards(Gui instance, Board instance2, JInternalFrame aframe, Dimension dimensionFrame, Integer numberOfPlayer) throws IOException {

        gui = instance;
        board = instance2;
        guiIntFrame = aframe;
        frameSize.setSize(dimensionFrame);
        numberPlayers = numberOfPlayer;
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 45/100);
        final int xconst = frameSize.width * 9/100;
        final int yconst = frameSize.height * 24/100;
        int x = xconst;
        int y = yconst;

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



        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)
        setPreferredSize(frameSize);


        JButton apollo = new JButton();
        apollo.setName("apollo");
        buttons.add(apollo);
        JButton artemis = new JButton();
        artemis.setName("artemis");
        buttons.add(artemis);
        JButton athena = new JButton();
        athena.setName("athena");
        buttons.add(athena);
        JButton atlas = new JButton();
        atlas.setName("atlas");
        buttons.add(atlas);
        JButton chronus = new JButton();
        chronus.setName("chronus");
        buttons.add(chronus);
        JButton demeter = new JButton();
        demeter.setName("demeter");
        buttons.add(demeter);
        JButton hepha = new JButton();
        hepha.setName("hephaestus");
        buttons.add(hepha);
        JButton hera = new JButton();
        hera.setName("hera");
        buttons.add(hera);
        JButton hestia = new JButton();
        hestia.setName("hestia");
        buttons.add(hestia);
        JButton hypnus = new JButton();
        hypnus.setName("hypnus");
        buttons.add(hypnus);
        JButton mino = new JButton();
        mino.setName("minotaur");
        buttons.add(mino);
        JButton pan = new JButton();
        pan.setName("pan");
        buttons.add(pan);
        JButton prome = new JButton();
        prome.setName("prometheus");
        buttons.add(prome);
        JButton zeus = new JButton();
        zeus.setName("zeus");
        buttons.add(zeus);

        buttonStyle();

        JLabel lapollo = ImageHandler.setImage("resources/Graphics/gods/apollo.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lartemis = ImageHandler.setImage("resources/Graphics/gods/artemis.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lathena = ImageHandler.setImage("resources/Graphics/gods/athena.png", 100, 100, cardSize.width, cardSize.height);
        JLabel latlas = ImageHandler.setImage("resources/Graphics/gods/atlas.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lchronus = ImageHandler.setImage("resources/Graphics/gods/chronus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel ldemeter = ImageHandler.setImage("resources/Graphics/gods/demeter.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhepha = ImageHandler.setImage("resources/Graphics/gods/hephaestus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhera = ImageHandler.setImage("resources/Graphics/gods/hera.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhestia = ImageHandler.setImage("resources/Graphics/gods/hestia.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhypnus = ImageHandler.setImage("resources/Graphics/gods/hypnus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lmino = ImageHandler.setImage("resources/Graphics/gods/minotaur.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lpan = ImageHandler.setImage("resources/Graphics/gods/pan.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lprome = ImageHandler.setImage("resources/Graphics/gods/prometheus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lzeus = ImageHandler.setImage("resources/Graphics/gods/zeus.png", 100, 100, cardSize.width, cardSize.height);

        apollo.setIcon(lapollo.getIcon());
        artemis.setIcon(lartemis.getIcon());
        athena.setIcon(lathena.getIcon());
        atlas.setIcon(latlas.getIcon());
        chronus.setIcon(lchronus.getIcon());
        demeter.setIcon(ldemeter.getIcon());
        hepha.setIcon(lhepha.getIcon());
        hera.setIcon(lhera.getIcon());
        hestia.setIcon(lhestia.getIcon());
        hypnus.setIcon(lhypnus.getIcon());
        mino.setIcon(lmino.getIcon());
        pan.setIcon(lpan.getIcon());
        prome.setIcon(lprome.getIcon());
        zeus.setIcon(lzeus.getIcon());

        JLabel choise;
        if (numberOfPlayer == 2){
            choise = ImageHandler.setImage("resources/Graphics/Texts/choose_2_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        else{
            choise = ImageHandler.setImage("resources/Graphics/Texts/choose_3_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
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
        add(back);


    }

     private void buttonStyle(){
         for (JButton button : buttons){
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
         for (JButton button : buttons){
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
         for (JButton button : buttons){
             if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7 && !button.equals(chronus)){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
            else if(!button.equals(chronus)){
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
                intFrame.setBounds((int) ((frameSize.width * 11 / 100) + c.getX()), (int) ((frameSize.height * 15 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() >= frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100){
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 41.5 / 100)), (int) ((frameSize.height * 15 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() < frameSize.width * 50/100 && c.getY() >= frameSize.height * 40/100){
                intFrame.setBounds((int) ((frameSize.width * 11 / 100) + c.getX()), (int) ((frameSize.height * 38 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 41.5 / 100)), (int) ((frameSize.height * 38 / 100)), intFrameSize.width, intFrameSize.height);

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
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, RemoveGod.class);
            c.setBorder(null);
            c.setBorderPainted(false);
            c.addMouseListener(new ColorBorderGodCards());
            godChosen.remove(c);
            chosen--;
            c.addActionListener(new ChooseGod());
            eliminateActionClass(confirm, ChangePanel.class);
        }
    }

    public static List<JButton> returnGodChoosen(){
        for (JButton button : godChosen){
            button.setBorderPainted(false);
            eliminateAllActionClass(button);
            eliminateAllMouseClass(button);
        }
        return godChosen;
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
            board.setCardChosen(cardsChosen);
            guiIntFrame.setVisible(false);
            board.challResponse();
        }
    }
}

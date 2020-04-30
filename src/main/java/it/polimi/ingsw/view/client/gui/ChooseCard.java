package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.ChallengerChoiseCards.returnGodChoosen;
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

public class ChooseCard extends  JDesktopPane{

    Gui gui;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    JInternalFrame intFrame;
    JButton buttonBackground = new JButton();
    JLabel cover = new JLabel();
    JLabel label = new JLabel();
    List<JButton> godChoosen = null;

    public ChooseCard(Gui instance, Dimension screen, Dimension frame, Integer numberOfPanel) throws IOException {

        gui = instance;
        frameSize.setSize(frame);
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 45/100);
        setPreferredSize(frameSize);

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
        int y = (int) screen.getHeight() * 35/100;
        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100));

        JLabel choose = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/this_is_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel wait = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_for_other_players_choice.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        ConfirmButton confirm = new ConfirmButton();
        godChoosen = returnGodChoosen();
        buttonStyle();

        if(numberOfPanel == 3 || numberOfPanel == 2) {

            choose.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
            add(choose);
            confirm.addActionListener(new ChangePanel(gui));
            add(confirm);

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
        }

        else {

            if (numberOfPanel == 1){

                choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(choise);

                godChoosen.get(0).setBounds((int) (frameSize.width * 45.5/100), y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));
            }
            else{
                wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(wait);
            }
        }
        JButton back = backgroundButton();
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
                cover = ImageHandler.setImage(c.getName(), 100, 100, intFrame.getWidth() , intFrame.getHeight() );
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
        }
    }
}

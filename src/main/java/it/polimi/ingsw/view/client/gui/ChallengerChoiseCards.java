package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChallengerChoiseCards extends JPanel{

    Dimension frameSize = new Dimension();
    Dimension cardSize = new Dimension();
    ArrayList<JButton> buttons = new ArrayList<>();
    private int count = 0;

    public ChallengerChoiseCards(Dimension frame, Integer numberOfPlayer) throws IOException {

        frameSize.setSize(frame);
        final int xconst =  (int) (frameSize.width * 9/100);
        final int yconst = (int) frameSize.height * 24/100;
        int x = xconst;
        int y = yconst;



        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)
        setPreferredSize(frameSize);
        setLayout(null);


        JButton apollo = new JButton();
        buttons.add(apollo);
        JButton artemis = new JButton();
        buttons.add(artemis);
        JButton athena = new JButton();
        buttons.add(athena);
        JButton atlas = new JButton();
        buttons.add(atlas);
        JButton chronus = new JButton();
        buttons.add(chronus);
        JButton demeter = new JButton();
        buttons.add(demeter);
        JButton hepha = new JButton();
        buttons.add(hepha);
        JButton hera = new JButton();
        buttons.add(hera);
        JButton hestia = new JButton();
        buttons.add(hestia);
        JButton hypnus = new JButton();
        buttons.add(hypnus);
        JButton mino = new JButton();
        buttons.add(mino);
        JButton pan = new JButton();
        buttons.add(pan);
        JButton prome = new JButton();
        buttons.add(prome);
        JButton zeus = new JButton();
        buttons.add(zeus);

        buttonStyle();

        JLabel lapollo = ImageHandler.setImage("src/main/resources/Graphics/gods/apollo.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lartemis = ImageHandler.setImage("src/main/resources/Graphics/gods/artemis.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lathena = ImageHandler.setImage("src/main/resources/Graphics/gods/athena.png", 100, 100, cardSize.width, cardSize.height);
        JLabel latlas = ImageHandler.setImage("src/main/resources/Graphics/gods/atlas.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lchronus = ImageHandler.setImage("src/main/resources/Graphics/gods/chronus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel ldemeter = ImageHandler.setImage("src/main/resources/Graphics/gods/demeter.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhepha = ImageHandler.setImage("src/main/resources/Graphics/gods/hephaestus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhera = ImageHandler.setImage("src/main/resources/Graphics/gods/hera.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhestia = ImageHandler.setImage("src/main/resources/Graphics/gods/hestia.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhypnus = ImageHandler.setImage("src/main/resources/Graphics/gods/hypnus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lmino = ImageHandler.setImage("src/main/resources/Graphics/gods/minotaur.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lpan = ImageHandler.setImage("src/main/resources/Graphics/gods/pan.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lprome = ImageHandler.setImage("src/main/resources/Graphics/gods/prometheus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lzeus = ImageHandler.setImage("src/main/resources/Graphics/gods/zeus.png", 100, 100, cardSize.width, cardSize.height);

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
            choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_2_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        else{
            choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_3_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(choise);

        JButton confirm = confirmButtonCreate();
        add(confirm);




        if(numberOfPlayer == 2){
            addForTwo(x, y, yconst);
        }
        else{
            addForThree(chronus, x, y, yconst);
        }

        JButton back = backgroundButton();
        add(back);
    }

     private void buttonStyle(){
         for (JButton button : buttons){
             button.setOpaque(false);
             button.setContentAreaFilled(false);
             button.setFocusPainted(false);
             //button.setBorderPainted(false);
         }
     }

     private void buttonPositioning(JButton button, int x, int y){
         button.setBounds(x, y, cardSize.width, cardSize.height);
         this.add(button);
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
}

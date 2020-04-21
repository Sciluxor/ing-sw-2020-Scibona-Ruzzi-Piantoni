package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ChallengerChoiseCards extends JPanel{
    private JButton apollo = new JButton(),
            artemis = new JButton(),
            athena = new JButton(),
            atlas = new JButton(),
            chronus = new JButton(),
            demeter = new JButton(),
            hepha = new JButton(),
            hera = new JButton(),
            hestia = new JButton(),
            hypnus = new JButton(),
            mino = new JButton(),
            pan = new JButton(),
            prome = new JButton(),
            zeus = new JButton(),
            confirm = new JButton("Confirm");


    Dimension frameSize = new Dimension(), cardSize = new Dimension();

    public ChallengerChoiseCards(Dimension screen, Dimension frame, Integer numberOfPlayer) throws IOException {

        frameSize.setSize(frame);
        final int xconst =  (int) (frameSize.width * 9/100);
        final int yconst = (int) frameSize.height * 21/100;
        int x = xconst;
        int y = yconst;
        int count = 0;


        cardSize.setSize(screen.getWidth() * 9/100, screen.getHeight() * 22/100);
        setPreferredSize(frameSize);
        setLayout(null);

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(apollo);
        buttons.add(artemis);
        buttons.add(athena);
        buttons.add(atlas);
        buttons.add(chronus);
        buttons.add(demeter);
        buttons.add(hepha);
        buttons.add(hera);
        buttons.add(hestia);
        buttons.add(hypnus);
        buttons.add(mino);
        buttons.add(pan);
        buttons.add(prome);
        buttons.add(zeus);

        for (JButton button : buttons){
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            //button.setBorderPainted(false);
        }

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


        JLabel choise = new JLabel("Choose 3 Gods");
        choise.setBounds(frameSize.width * 50/100 - 50, frameSize.height * 10/100, 100, 100);
        add(choise);

        confirm.setBounds(frameSize.width * 50/100 - (screen.width * 10/100)/2, frameSize.height * 82/100 - 50, screen.width * 10/100, screen.height * 3/100);
        add(confirm);
        confirm.addActionListener(new Gui.ChangePanel());

        for (JButton button : buttons){

            if(numberOfPlayer == 2){
                if(count < 7){
                    x = x + frameSize.width * 12/100;
                    button.setBounds(x, y, cardSize.width, cardSize.height);
                    this.add(button);
                    count++;
                }
                else{
                    if (y == yconst){

                        x = xconst;
                        y = frameSize.height * 49/100;
                    }
                    x = x + frameSize.width * 12/100;
                    button.setBounds(x, y, cardSize.width, cardSize.height);
                    this.add(button);
                }
            }
            else{
                if(count == 0){
                    button.setBounds(x, y, cardSize.width, cardSize.height);
                    this.add(button);
                    count++;
                }

                else if(count < 7 && !button.equals(chronus)){
                    x = x + frameSize.width * 12/100;
                    button.setBounds(x, y, cardSize.width, cardSize.height);
                    this.add(button);
                    count++;
                }
                else if(!button.equals(chronus)){
                    if (y == yconst){

                        x = frameSize.width * 3/100;
                        y = frameSize.height * 49/100;
                    }
                    x = x + frameSize.width * 12/100;
                    button.setBounds(x, y, cardSize.width, cardSize.height);
                    this.add(button);
                }
            }
        }

    }
}

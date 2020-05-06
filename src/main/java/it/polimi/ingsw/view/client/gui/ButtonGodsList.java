package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ButtonGodsList {



    public ButtonGodsList(Dimension frameSize, List<JButton> buttons) throws IOException {

        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)

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
    }
}

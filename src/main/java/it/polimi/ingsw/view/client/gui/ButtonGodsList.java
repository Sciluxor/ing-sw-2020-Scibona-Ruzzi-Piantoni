package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.GODS;

/**
 * Class for building the deck of cards as single JButtons
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class ButtonGodsList {

    /**
     * Class builder
     * @param dimensionFrame Window size to scale the size of the cards
     * @param buttons Reference to the List of JButton to be filled with cards
     * @throws IOException if the loading of the images of the cards was not successful
     */

    public ButtonGodsList(Dimension dimensionFrame, List<JButton> buttons) throws IOException {

        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (dimensionFrame.getWidth() * 9/100), (int) (dimensionFrame.getHeight() * 23.15/100)); //(9, 22)

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


        JLabel lapollo = ImageHandler.setImage(GODS + "apollo.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lartemis = ImageHandler.setImage(GODS + "artemis.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lathena = ImageHandler.setImage(GODS + "athena.png", 100, 100, cardSize.width, cardSize.height);
        JLabel latlas = ImageHandler.setImage(GODS + "atlas.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lchronus = ImageHandler.setImage(GODS + "chronus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel ldemeter = ImageHandler.setImage(GODS + "demeter.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhepha = ImageHandler.setImage(GODS + "hephaestus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhera = ImageHandler.setImage(GODS + "hera.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhestia = ImageHandler.setImage(GODS + "hestia.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhypnus = ImageHandler.setImage(GODS + "hypnus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lmino = ImageHandler.setImage(GODS + "minotaur.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lpan = ImageHandler.setImage(GODS + "pan.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lprome = ImageHandler.setImage(GODS + "prometheus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lzeus = ImageHandler.setImage(GODS + "zeus.png", 100, 100, cardSize.width, cardSize.height);

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

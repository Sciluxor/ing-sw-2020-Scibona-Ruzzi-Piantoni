package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.GODS;

/**
 * Class for building the deck of cards as a List of single JButtons
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class ButtonGodsList {

    /**
     * Public constructor
     * @param dimensionFrame Window size to scale the size of the cards
     * @param buttons Reference to the List of JButton to be filled with cards
     * @throws IOException if the loading of the images of the cards was not successful
     */

    public ButtonGodsList(Dimension dimensionFrame, List<JButton> buttons) throws IOException {

        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (dimensionFrame.getWidth() * 9/100), (int) (dimensionFrame.getHeight() * 23.15/100));

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


        JLabel lApollo = ImageHandler.setImage(GODS + "apollo.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lArtemis = ImageHandler.setImage(GODS + "artemis.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lAthena = ImageHandler.setImage(GODS + "athena.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lAtlas = ImageHandler.setImage(GODS + "atlas.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lChronus = ImageHandler.setImage(GODS + "chronus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lDemeter = ImageHandler.setImage(GODS + "demeter.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lHepha = ImageHandler.setImage(GODS + "hephaestus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lHera = ImageHandler.setImage(GODS + "hera.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lHestia = ImageHandler.setImage(GODS + "hestia.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lHypnus = ImageHandler.setImage(GODS + "hypnus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lMino = ImageHandler.setImage(GODS + "minotaur.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lPan = ImageHandler.setImage(GODS + "pan.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lProme = ImageHandler.setImage(GODS + "prometheus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lZeus = ImageHandler.setImage(GODS + "zeus.png", 100, 100, cardSize.width, cardSize.height);

        apollo.setIcon(lApollo.getIcon());
        artemis.setIcon(lArtemis.getIcon());
        athena.setIcon(lAthena.getIcon());
        atlas.setIcon(lAtlas.getIcon());
        chronus.setIcon(lChronus.getIcon());
        demeter.setIcon(lDemeter.getIcon());
        hepha.setIcon(lHepha.getIcon());
        hera.setIcon(lHera.getIcon());
        hestia.setIcon(lHestia.getIcon());
        hypnus.setIcon(lHypnus.getIcon());
        mino.setIcon(lMino.getIcon());
        pan.setIcon(lPan.getIcon());
        prome.setIcon(lProme.getIcon());
        zeus.setIcon(lZeus.getIcon());
    }
}

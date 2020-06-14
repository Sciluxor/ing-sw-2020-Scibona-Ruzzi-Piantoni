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
import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;
import static it.polimi.ingsw.view.client.gui.Gui.getD;

/**
 * Classe che estende JDesktopPane per la costruzione del pane per la visualizzazione di eventuali limitazioni dovute alle altre divinità
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class SeeConstraint extends JDesktopPane {

    transient Board board;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    Dimension cardSize = new Dimension();
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    JButton buttonBackground = new JButton();
    private List<JButton> godList = new ArrayList<>();
    transient ButtonGodsList costructor;
    MyButton back = new MyButton(1);
    JLabel cover =new JLabel();
    JLabel label =new JLabel();
    JLabel power;
    JLabel current;
    JButton mePower;
    JButton constraint1;
    JButton constraint2;

    /**
     * Costruttore della classe
     * @param istance Riferimento alla classe Board istanziata dalla GUI
     * @param frame Riferimento al JInternalFrame in cui verrà inserito l'attuale JDesktopPane SeeConstraints
     * @param dimensionFrame Dimensione del JInternalFrame
     * @param constraint List delle limitazioni attualmente presenti
     * @throws IOException se il caricamento delle scritte o delle descrizioni delle divinità non andasse a buon fine
     */

    public SeeConstraint(Board istance, JInternalFrame frame, Dimension dimensionFrame, List<String> constraint) throws IOException {

        guiIntFrame = frame;
        board = istance;
        frameSize.setSize(dimensionFrame);
        intFrameSize.setSize(frameSize.getWidth() * 48/100, frameSize.getHeight() * 54/100);
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)

        final int yconst = frameSize.height * 35/100;
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

        power = ImageHandler.setImage("resources/Graphics/Texts/your_power.png", 99, 99, frameSize.width * 30/100, frameSize.height * 10/100);
        power.setBounds(frameSize.width * 15/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(power);
        current = ImageHandler.setImage("resources/Graphics/Texts/current_constraints.png", 99, 99, frameSize.width * 40/100, frameSize.height * 10/100);
        current.setBounds(frameSize.width * 50/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(current);

        setPreferredSize(frameSize);

        buttonStyle();

        if (constraint.size() == 3){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(frameSize.width * 25/100, y, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(frameSize.width * 57/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint1);

            constraint2 = getButtonFromConstraint(constraint.get(2));
            if (constraint2 != null) {
                constraint2.setBounds(frameSize.width * 69/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint2);
        }

        if (constraint.size() == 2){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(frameSize.width * 25/100, y, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(frameSize.width * 63/100, y, cardSize.width, cardSize.height);
            }
            this.add(constraint1);
        }


        back.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (int) (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(back);
        back.addActionListener(new Close());

        JButton backBack = backgroundButton(0);
        backBack.setBounds(0, 0, frameSize.width, frameSize.height);
        add(backBack);

    }

    /**
     * Metodo per settare i parametri dei JButton delle carte
     */

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

    /**
     * Classe che estende MouseAdapter per la visualizzazione delle descrizioni delle carte passandoci sopra col mouse
     */

    private class ShowPower extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            if (c.getX() < frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100) {
                intFrame.setBounds((int) ((frameSize.width * 9 / 100) + c.getX()), (int) (frameSize.height * 8.5 / 100), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() >= frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100){
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 48 / 100)), (int) (frameSize.height * 8.5 / 100), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() < frameSize.width * 50/100 && c.getY() >= frameSize.height * 40/100){
                intFrame.setBounds((int) ((frameSize.width * 9 / 100) + c.getX()), (int) (frameSize.height * 33 / 100), intFrameSize.width, intFrameSize.height);
            }
            else
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 48 / 100)), (int) (frameSize.height * 33 / 100), intFrameSize.width, intFrameSize.height);

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

    /**
     * Classe che implementa ActionListener per il JButton Close che chiude l'attuale JInternalFrame
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiIntFrame.setVisible(false);
            eliminateActionClass(board.buttonPower, Board.HideConstraint.class);
            board.buttonPower.addActionListener(new ShowConstraint());
            board.buttonPower.setIcon(board.lButtonPowerPing.getIcon());
        }
    }

    /**
     * Classe che implementa ActionListener per il JButton buttonPower di Board per la visualizzazione del JInternalFrame delle limitazioni delle altre divinità
     */

    public class ShowConstraint implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, Board.ShowConstraint.class);
            c.setIcon(board.lButtonPowerPressPing.getIcon());
            board.internalFrameConstraint.setVisible(true);
            c.addActionListener(new HideConstraint());
        }
    }

    /**
     * Classe che implementa ActionListener per il JButton buttonPower di Board per la scomparsa del JInternalFrame delle limitazioni delle altre divinità
     */

    public class HideConstraint implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, Board.HideConstraint.class);
            c.addActionListener(new ShowConstraint());
            c.setIcon(board.lButtonPowerPing.getIcon());
            board.internalFrameConstraint.setVisible(false);
        }
    }

    /**
     * Metodo che ritorna il JButton della carta corrispondente alla divinità fornita
     * @param god Parametro che indica quale è la divinità
     * @return JButton della divinità corrispondente al god
     */

    private JButton getButtonFromConstraint(String god){
        for (JButton button : godList){
            if (button.getName().equalsIgnoreCase(god)){
                return button;
            }
        }
        return null;
    }
}

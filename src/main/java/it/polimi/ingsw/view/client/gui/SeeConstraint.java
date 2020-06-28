package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Gui.getD;
import static it.polimi.ingsw.view.client.gui.GuiUtils.*;

/**
 * Class that extends JDesktopPane for the build of the pane to display any limitations due to the other gods
 * @author Luigi Scibona
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
    private final List<JButton> godList = new ArrayList<>();
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
     * Class builder
     * @param istance Reference to the Board class instanced by the GUI
     * @param frame Reference to the JInternalFrame where the current JDesktopPane SeeConstraint will be inserted
     * @param dimensionFrame Size of the JInternalFrame
     * @param constraint List of limitations currently present
     * @throws IOException if the loading of the inscription or the descriptions of the gods was not successful
     */

    public SeeConstraint(Board istance, JInternalFrame frame, Dimension dimensionFrame, List<String> constraint) throws IOException {

        guiIntFrame = frame;
        board = istance;
        frameSize.setSize(dimensionFrame);
        intFrameSize.setSize(frameSize.getWidth() * 48/100, frameSize.getHeight() * 54/100);
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)

        final int yconst = frameSize.height * 35/100;

        costructor = new ButtonGodsList(frameSize, godList);

        intFrame = internalAndBackgroundSetter(this, intFrameSize, buttonBackground);

        power = ImageHandler.setImage(TEXT + "your_power.png", 99, 99, frameSize.width * 30/100, frameSize.height * 10/100);
        power.setBounds(frameSize.width * 15/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(power);
        current = ImageHandler.setImage(TEXT + "current_constraints.png", 99, 99, frameSize.width * 40/100, frameSize.height * 10/100);
        current.setBounds(frameSize.width * 50/100, frameSize.height * 10/100, frameSize.width * 40/100, frameSize.height * 10/100);
        add(current);

        setPreferredSize(frameSize);

        setButtonStyle(godList, intFrame, frameSize, intFrameSize, buttonBackground, cover, true, 1);

        if (constraint.size() == 3){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(frameSize.width * 25/100, yconst, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(frameSize.width * 57/100, yconst, cardSize.width, cardSize.height);
            }
            this.add(constraint1);

            constraint2 = getButtonFromConstraint(constraint.get(2));
            if (constraint2 != null) {
                constraint2.setBounds(frameSize.width * 69/100, yconst, cardSize.width, cardSize.height);
            }
            this.add(constraint2);
        }

        if (constraint.size() == 2){
            mePower = getButtonFromConstraint(constraint.get(0));
            if (mePower != null) {
                mePower.setBounds(frameSize.width * 25/100, yconst, cardSize.width, cardSize.height);
            }
            this.add(mePower);

            constraint1 = getButtonFromConstraint(constraint.get(1));
            if (constraint1 != null) {
                constraint1.setBounds(frameSize.width * 63/100, yconst, cardSize.width, cardSize.height);
            }
            this.add(constraint1);
        }


        back.setBounds((int) (((double)frameSize.width * 50/ 100) - ((getD().getWidth() * 13 / 100) / 2)), (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(back);
        back.addActionListener(new Close());

        JButton backBack = backgroundButton(0);
        backBack.setBounds(0, 0, frameSize.width, frameSize.height);
        add(backBack);

    }

    /**
     * Class that implements ActionListener for the JButton Close which closes the current JInternalFrame
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
     * Class implementing ActionListener for Board's JButton buttonPower for displaying the JInternalFrame of the limitations of other gods
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
     * Class implementing ActionListener for the Board's JButton buttonPower for the disappearance of the JInternalFrame of the limitations of the other deities
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
     * Method that returns the JButton of the card corresponding to the god provided
     * @param god Parameter indicating the god
     * @return JButton of the god corresponding to the god parameter
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
